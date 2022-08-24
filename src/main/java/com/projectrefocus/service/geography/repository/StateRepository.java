package com.projectrefocus.service.geography.repository;

import com.projectrefocus.service.geography.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<StateEntity, Byte> {
}
