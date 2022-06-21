package ru.trx.jmix.spring.data.demo.app;

import ru.trx.jmix.spring.data.demo.app.model.ExamSheetDto;

import java.util.List;

/**
 * @author Alexander Vasiliev
 */
public interface ReportGenerator {
    List<ExamSheetDto> generate();
}
