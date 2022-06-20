package ru.trx.jmix.spring.data.demo.repository;

import io.jmix.core.FetchPlan;
import io.jmix.core.repository.JmixDataRepository;
import org.springframework.data.domain.Sort;
import ru.trx.jmix.spring.data.demo.entity.Teacher;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * @author Alexander Vasiliev
 */
public interface TeacherRepository extends JmixDataRepository<Teacher, UUID> {

    @Override
    List<Teacher> findAll(FetchPlan fetchPlan);

    @Override
    List<Teacher> findAll(Iterable<UUID> uuids, @Nullable FetchPlan fetchPlan);

    @Override
    List<Teacher> findAll(Sort sort, @Nullable FetchPlan fetchPlan);

    @Override
    List<Teacher> findAll(Sort sort);

    @Override
    <S extends Teacher> List<S> saveAll(Iterable<S> entities);

    @Override
    List<Teacher> findAll();

    @Override
    List<Teacher> findAllById(Iterable<UUID> uuids);
}
