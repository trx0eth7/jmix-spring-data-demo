package ru.trx.jmix.spring.data.demo.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "JSDD_EXAM_SHEET")
@Entity(name = "jsdd_ExamSheet")
@Getter
@Setter
@EqualsAndHashCode
public class ExamSheet {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @EqualsAndHashCode.Exclude
    @Column(name = "NAME")
    private String name;

    @EqualsAndHashCode.Exclude
    @JoinTable(name = "JSDD_EXAM_SHEET_TEACHER_LINK",
            joinColumns = @JoinColumn(name = "EXAM_SHEET_ID"),
            inverseJoinColumns = @JoinColumn(name = "TEACHER_ID"))
    @ManyToMany
    private List<Teacher> teachers;
}