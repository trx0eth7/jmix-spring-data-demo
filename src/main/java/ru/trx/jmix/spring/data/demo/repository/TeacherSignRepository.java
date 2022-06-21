package ru.trx.jmix.spring.data.demo.repository;

import ru.trx.jmix.spring.data.demo.entity.TeacherSign;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * @author Alexander Vasiliev
 */
public interface TeacherSignRepository extends JpaJmixDataRepository<TeacherSign, UUID> {

    @Nullable
    TeacherSign findFirstByExamSheetIdAndTeacherId(UUID examSheetId, UUID teacherId);
}
