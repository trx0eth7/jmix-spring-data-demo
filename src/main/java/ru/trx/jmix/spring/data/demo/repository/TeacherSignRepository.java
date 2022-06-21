package ru.trx.jmix.spring.data.demo.repository;

import io.jmix.core.FetchPlan;
import io.jmix.core.repository.JmixDataRepository;
import org.springframework.data.domain.Sort;
import ru.trx.jmix.spring.data.demo.entity.TeacherSign;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * @author Alexander Vasiliev
 */
public interface TeacherSignRepository extends JmixDataRepository<TeacherSign, UUID> {
    
    @Override
    List<TeacherSign> findAll(FetchPlan fetchPlan);

    @Override
    List<TeacherSign> findAll(Iterable<UUID> uuids, @Nullable FetchPlan fetchPlan);

    @Override
    List<TeacherSign> findAll(Sort sort, @Nullable FetchPlan fetchPlan);

    @Override
    List<TeacherSign> findAll(Sort sort);

    @Override
    <S extends TeacherSign> List<S> saveAll(Iterable<S> entities);

    @Override
    List<TeacherSign> findAll();

    @Override
    List<TeacherSign> findAllById(Iterable<UUID> uuids);

    @Nullable
    TeacherSign findFirstByExamSheetIdAndTeacherId(UUID examSheetId, UUID teacherId);
}
