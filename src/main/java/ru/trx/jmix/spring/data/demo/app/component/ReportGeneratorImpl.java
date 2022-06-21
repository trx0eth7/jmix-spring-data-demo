package ru.trx.jmix.spring.data.demo.app.component;

import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.trx.jmix.spring.data.demo.app.ReportGenerator;
import ru.trx.jmix.spring.data.demo.app.model.ExamSheetDto;
import ru.trx.jmix.spring.data.demo.app.model.TeacherSignDto;
import ru.trx.jmix.spring.data.demo.entity.ExamSheet;
import ru.trx.jmix.spring.data.demo.repository.ExamSheetRepository;
import ru.trx.jmix.spring.data.demo.repository.TeacherSignRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Vasiliev
 */
@Component("jsdd_ReportGeneratorImpl")
public class ReportGeneratorImpl implements ReportGenerator {
    private static final int FETCH_LIMIT = 100;

    private final ExamSheetRepository examSheetRepository;
    private final TeacherSignRepository teacherSignRepository;

    private final FetchPlan fetchPlan;

    public ReportGeneratorImpl(FetchPlans fetchPlans, ExamSheetRepository examSheetRepository,
                               TeacherSignRepository teacherSignRepository) {
        this.examSheetRepository = examSheetRepository;
        this.teacherSignRepository = teacherSignRepository;

        fetchPlan = fetchPlans.builder(ExamSheet.class)
                .add("name")
                .add("teachers", FetchPlan.LOCAL)
                .build();
    }

    @Override
    public List<ExamSheetDto> generate() {
        var examSheetDtos = new ArrayList<ExamSheetDto>();
        var pageRequest = (Pageable) PageRequest.of(0, FETCH_LIMIT, Sort.by(Sort.Direction.DESC, "id"));

        Page<ExamSheet> page;
        do {
            page = examSheetRepository.findAll(pageRequest, fetchPlan);

            for (var examSheet : page) {
                var teachers = examSheet.getTeachers();
                var teacherSigns = new ArrayList<TeacherSignDto>();

                for (var teacher : teachers) {
                    var teacherSign = teacherSignRepository
                            .findFirstByExamSheetIdAndTeacherId(examSheet.getId(), teacher.getId());

                    if (teacherSign != null) {
                        teacherSigns.add(TeacherSignDto.builder()
                                .teacherName(teacher.getInstanceName())
                                .createdSign(teacherSign.getSignCreatedDate().toLocalDate())
                                .build());
                    }
                }

                examSheetDtos.add(ExamSheetDto.builder()
                        .examSheetName(examSheet.getName())
                        .teacherSigns(teacherSigns)
                        .build());
            }

            pageRequest = page.nextOrLastPageable();
        } while (page.hasNext());

        return examSheetDtos;
    }
}