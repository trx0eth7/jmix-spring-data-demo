package ru.trx.jmix.spring.data.demo.app.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Alexander Vasiliev
 */
@Data
@Builder
public class TeacherSignDto implements Serializable {
    private String teacherName;
    private LocalDate createdSign;
}
