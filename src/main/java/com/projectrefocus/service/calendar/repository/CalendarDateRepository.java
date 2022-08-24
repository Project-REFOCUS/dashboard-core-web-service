package com.projectrefocus.service.calendar.repository;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarDateRepository extends JpaRepository<CalendarDateEntity, Short> {
}
