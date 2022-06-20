package ru.trx.jmix.spring.data.demo.repository;

import io.jmix.core.FetchPlan;
import io.jmix.core.repository.JmixDataRepository;
import org.springframework.data.domain.Sort;
import ru.trx.jmix.spring.data.demo.entity.ExamSheet;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * @author Alexander Vasiliev
 */
//@ApplyConstraints(false)
public interface ExamSheetRepository extends JmixDataRepository<ExamSheet, UUID> {

    List<ExamSheet> findAllByNameContainingIgnoreCase(String name);

    List<ExamSheet> findAllByNameContainingIgnoreCase(String name, FetchPlan fetchPlan);

    @Override
    List<ExamSheet> findAll(FetchPlan fetchPlan);

    @Override
    List<ExamSheet> findAll(Iterable<UUID> uuids, @Nullable FetchPlan fetchPlan);

    @Override
    List<ExamSheet> findAll(Sort sort, @Nullable FetchPlan fetchPlan);

    @Override
    List<ExamSheet> findAll(Sort sort);

    @Override
    <S extends ExamSheet> List<S> saveAll(Iterable<S> entities);

    @Override
    List<ExamSheet> findAll();

    @Override
    List<ExamSheet> findAllById(Iterable<UUID> uuids);
}
