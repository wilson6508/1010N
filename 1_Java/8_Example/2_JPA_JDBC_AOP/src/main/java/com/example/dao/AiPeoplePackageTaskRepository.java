package com.example.dao;

import com.example.entity.AiPeoplePackageTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface AiPeoplePackageTaskRepository extends JpaRepository<AiPeoplePackageTaskEntity, Integer> {
    List<AiPeoplePackageTaskEntity> findByServiceId(int serviceId);
    List<AiPeoplePackageTaskEntity> findByUserId(int userId);
    List<AiPeoplePackageTaskEntity> findByUserIdAndUpdateTimeIsAfter(int userId, Timestamp timestamp);
    List<AiPeoplePackageTaskEntity> findByServiceIdAndUpdateTimeIsAfter(int serviceId, Timestamp timestamp);
}

