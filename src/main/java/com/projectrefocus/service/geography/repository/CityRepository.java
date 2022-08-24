package com.projectrefocus.service.geography.repository;

import com.projectrefocus.service.geography.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityEntity, Short> {
}
