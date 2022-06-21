package ru.trx.jmix.spring.data.demo.repository;

import io.jmix.core.FetchPlan;
import ru.trx.jmix.spring.data.demo.entity.ExamSheet;

import java.util.List;
import java.util.UUID;

/**
 * @author Alexander Vasiliev
 */
public interface ExamSheetRepository extends JpaJmixDataRepository<ExamSheet, UUID> {

    List<ExamSheet> findAllByNameContainingIgnoreCase(String name);

    List<ExamSheet> findAllByNameContainingIgnoreCase(String name, FetchPlan fetchPlan);
}
