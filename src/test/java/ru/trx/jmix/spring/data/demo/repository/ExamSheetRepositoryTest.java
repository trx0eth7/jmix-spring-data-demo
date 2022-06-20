package ru.trx.jmix.spring.data.demo.repository;

import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.trx.jmix.spring.data.demo.entity.ExamSheet;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alexander Vasiliev
 */
@SpringBootTest
class ExamSheetRepositoryTest {

    @Autowired
    ExamSheetRepository examSheetRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SystemAuthenticator authenticator;

    @Autowired
    FetchPlans fetchPlans;

    @BeforeEach
    void setUp() {
        authenticator.begin();
    }

    @AfterEach
    void tearDown() {
        authenticator.end();
    }

    @Test
    void findAllByNameContainingIgnoreCase() {
        // given
        var examSheet = examSheetRepository.create();
        var examSheetNumber = UUID.randomUUID().toString();

        examSheet.setName("Exam sheet " + examSheetNumber);

        var savedExamSheet = examSheetRepository.save(examSheet);

        // when
        var foundExamSheets = examSheetRepository.findAllByNameContainingIgnoreCase(examSheetNumber);

        // then
        assertEquals(List.of(savedExamSheet.getId()), foundExamSheets.stream()
                .map(ExamSheet::getId)
                .collect(Collectors.toList()), "Not found exam sheet by name");
    }

    @Test
    void findAllByNameContainingIgnoreCaseWithFetchPlan() {
        // given
        var number = UUID.randomUUID().toString();

        var examSheet = examSheetRepository.create();
        var teacher1 = teacherRepository.create();
        var teacher2 = teacherRepository.create();

        teacher1.setFirstName("Teacher1 " + number);
        teacher1.setLastName("Teacher1 " + number);

        teacher2.setFirstName("Teacher2 " + number);
        teacher2.setLastName("Teacher2 " + number);

        examSheet.setName("Exam sheet " + number);
        examSheet.setTeachers(List.of(teacher1, teacher2));

        teacherRepository.saveAll(List.of(teacher1, teacher2));
        examSheetRepository.save(examSheet);

        //when
        var fetchPlan = fetchPlans.builder(ExamSheet.class)
                .add("teachers", FetchPlan.LOCAL)
                .build();

        var foundExamSheets = examSheetRepository.findAllByNameContainingIgnoreCase(number, fetchPlan);

        //then
        assertEquals(1, foundExamSheets.size(), "Exam sheet not found");
        assertEquals(2, foundExamSheets.get(0).getTeachers().size(), "Teachers are empty");
        assertEquals(Set.of(number), foundExamSheets.get(0).getTeachers().stream()
                .map(teacher -> teacher.getLastName().substring(9))
                .collect(Collectors.toSet()), "Teachers don't have the same numbers");
    }

}