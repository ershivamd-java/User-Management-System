package com.gajendra.repository;

import com.gajendra.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {
    List<Batch> findByCourseId(Long courseId);
}
