package com.projectrefocus.service.categories.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.geography.entity.ZipcodeEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.sql.Timestamp;

@Entity
@Table(name = "motor_vehicle_collisions")
public class MotorVehicleCollisionEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "crash_time")
    private Timestamp crashTime;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_date_id")
    private CalendarDateEntity date;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zipcode_id")
    private ZipcodeEntity zipcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Timestamp getCrashTime() {
        return crashTime;
    }

    public void setCrashTime(Timestamp crashTime) {
        this.crashTime = crashTime;
    }

    public CalendarDateEntity getDate() {
        return date;
    }

    public void setDate(CalendarDateEntity date) {
        this.date = date;
    }

    public ZipcodeEntity getZipcode() {
        return zipcode;
    }

    public void setZipcode(ZipcodeEntity zipcode) {
        this.zipcode = zipcode;
    }
}