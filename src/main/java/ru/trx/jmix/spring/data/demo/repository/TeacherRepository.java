package ru.trx.jmix.spring.data.demo.repository;

import ru.trx.jmix.spring.data.demo.entity.Teacher;

import java.util.UUID;

/**
 * @author Alexander Vasiliev
 */
public interface TeacherRepository extends JpaJmixDataRepository<Teacher, UUID> {
}
