package com.projectrefocus.service.police.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.ethnicity.entity.RaceEthnicityEntity;
import com.projectrefocus.service.geography.entity.CityEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "fatal_police_shootings")
public class FatalPoliceShootingsEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "name")
    private String name;

    @Column(name = "manner_of_death")
    private String mannerOfDeath;

    @Column(name = "armed")
    private String armed;

    @Column(name = "age")
    private Byte age;

    @Column(name = "gender")
    private Byte gender;

    @Column(name = "mental")
    private Boolean mental;

    @Column(name = "threat_level")
    private String threatLevel;

    @Column(name = "body_camera")
    private Boolean bodyCamera;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_date_id")
    private CalendarDateEntity date;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_ethnicity_id")
    private RaceEthnicityEntity raceEthnicity;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public Byte getAge() {
        return age;
    }

    public void setArmed(String armed) {
        this.armed = armed;
    }

    public String getArmed() {
        return armed;
    }

    public void setBodyCamera(Boolean bodyCamera) {
        this.bodyCamera = bodyCamera;
    }

    public Boolean getBodyCamera() {
        return bodyCamera;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Byte getGender() {
        return gender;
    }

    public void setMental(Boolean mental) {
        this.mental = mental;
    }

    public Boolean getMental() {
        return mental;
    }

    public void setMannerOfDeath(String mannerOfDeath) {
        this.mannerOfDeath = mannerOfDeath;
    }

    public String getMannerOfDeath() {
        return mannerOfDeath;
    }

    public void setThreatLevel(String threatLevel) {
        this.threatLevel = threatLevel;
    }

    public String getThreatLevel() {
        return threatLevel;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setDate(CalendarDateEntity date) {
        this.date = date;
    }

    public CalendarDateEntity getDate() {
        return date;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setRaceEthnicity(RaceEthnicityEntity raceEthnicity) {
        this.raceEthnicity = raceEthnicity;
    }

    public RaceEthnicityEntity getRaceEthnicity() {
        return raceEthnicity;
    }
}
