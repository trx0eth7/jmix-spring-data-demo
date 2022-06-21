package ru.trx.jmix.spring.data.demo.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@JmixEntity
@Table(name = "JSDD_TEACHER")
@Entity(name = "jsdd_Teacher")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Teacher {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @EqualsAndHashCode.Exclude
    @Column(name = "FIRST_NAME")
    private String firstName;

    @EqualsAndHashCode.Exclude
    @Column(name = "LAST_NAME")
    private String lastName;

    @InstanceName
    @DependsOnProperties({"firstName", "lastName"})
    public String getInstanceName() {
        return String.format("%s %s", firstName, lastName);
    }
}