package ru.trx.jmix.spring.data.demo.app.component;

import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.trx.jmix.spring.data.demo.app.ReportGenerator;
import ru.trx.jmix.spring.data.demo.app.model.ExamSheetDto;
import ru.trx.jmix.spring.data.demo.app.model.TeacherSignDto;
import ru.trx.jmix.spring.data.demo.entity.ExamSheet;
import ru.trx.jmix.spring.data.demo.entity.TeacherSign;
import ru.trx.jmix.spring.data.demo.repository.ExamSheetRepository;
import ru.trx.jmix.spring.data.demo.repository.TeacherRepository;
import ru.trx.jmix.spring.data.demo.repository.TeacherSignRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alexander Vasiliev
 */
@SpringBootTest
class ReportGeneratorImplTest {

    @Autowired
    @Qualifier("jsdd_ReportGeneratorImpl")
    ReportGenerator reportGenerator;

    @Autowired
    ExamSheetRepository examSheetRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TeacherSignRepository teacherSignRepository;

    @Autowired
    SystemAuthenticator authenticator;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        authenticator.begin();
    }

    @AfterEach
    void tearDown() {
        authenticator.end();

        jdbcTemplate.execute("delete from JSDD_TEACHER_SIGN");
        jdbcTemplate.execute("delete from JSDD_EXAM_SHEET_TEACHER_LINK");
        jdbcTemplate.execute("delete from JSDD_TEACHER");
        jdbcTemplate.execute("delete from JSDD_EXAM_SHEET");
    }

    @Test
    void generate() {
        // given
        var teacherFirstNames = List.of("Алексей", "Александр", "Николай", "Иван");
        var teacherLastNames = List.of("Сидоров", "Опятов", "Жевнов", "Петров");
        var localRandom = ThreadLocalRandom.current();

        var teachers = Stream.generate(teacherRepository::create)
                .peek(teacher -> {
                    teacher.setFirstName(teacherFirstNames.get(localRandom.nextInt(teacherFirstNames.size())));
                    teacher.setLastName(teacherLastNames.get(localRandom.nextInt(teacherLastNames.size())));
                })
                .limit(10)
                .collect(Collectors.toList());

        var teacherSigns = new ArrayList<TeacherSign>();
        var examSheets = Stream.generate(examSheetRepository::create)
                .peek(examSheet -> examSheet.setName("Exam sheet " + UUID.randomUUID()))
                .peek(examSheet -> {
                    var fromIndex = localRandom.nextInt(teachers.size());
                    var toIndex = localRandom.nextInt(fromIndex, teachers.size());
                    examSheet.setTeachers(teachers.subList(fromIndex, toIndex));
                })
                .peek(examSheet -> {
                    for (var teacher : examSheet.getTeachers()) {
                        var teacherSign = teacherSignRepository.create();

                        teacherSign.setTeacher(teacher);
                        teacherSign.setExamSheet(examSheet);
                        teacherSign.setSignCreatedDate(LocalDateTime.now());

                        teacherSigns.add(teacherSign);
                    }
                })
                .limit(250)
                .collect(Collectors.toList());

        teacherRepository.saveAll(teachers);
        examSheetRepository.saveAll(examSheets);
        teacherSignRepository.saveAll(teacherSigns);


        // when
        var examSheetDtos = reportGenerator.generate();

        // then
        assertEquals(250, examSheetDtos.size(), "Not all exam sheets were received");

        assertEquals(examSheets.stream()
                        .map(ExamSheet::getName)
                        .collect(Collectors.toSet()),
                examSheetDtos.stream()
                        .map(ExamSheetDto::getExamSheetName)
                        .collect(Collectors.toSet()), "Exam sheets are not the same");

        assertEquals(teacherSigns.stream().
                        map(teacherSign -> teacherSign.getTeacher().getInstanceName())
                        .collect(Collectors.toSet()),
                examSheetDtos.stream()
                        .flatMap(dto -> dto.getTeacherSigns().stream())
                        .map(TeacherSignDto::getTeacherName)
                        .collect(Collectors.toSet()), "Teacher sign are not the same");
    }
}