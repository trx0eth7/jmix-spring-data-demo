package ru.trx.jmix.spring.data.demo.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "JSDD_TEACHER_SIGN")
@Entity(name = "jsdd_TeacherSign")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TeacherSign {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @EqualsAndHashCode.Exclude
    @NotNull
    @JoinColumn(name = "EXAM_SHEET_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ExamSheet examSheet;

    @EqualsAndHashCode.Exclude
    @NotNull
    @JoinColumn(name = "TEAHCER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Teacher teacher;

    @EqualsAndHashCode.Exclude
    @NotNull
    @Column(name = "SIGN_CREATED_DATE")
    private LocalDateTime signCreatedDate;
}