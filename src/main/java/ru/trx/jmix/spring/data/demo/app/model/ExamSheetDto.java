package ru.trx.jmix.spring.data.demo.app.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alexander Vasiliev
 */
@Data
@Builder
public class ExamSheetDto implements Serializable {
    private String examSheetName;
    private List<TeacherSignDto> teacherSigns;
}
