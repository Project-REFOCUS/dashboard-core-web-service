package com.projectrefocus.service.categories.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.geography.entity.CountyEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "snap_data")
public class SupplementalNutritionAssistanceProgramEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_date_id")
    private CalendarDateEntity date;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "county_id")
    private CountyEntity county;

    @Column(name = "total_households")
    private Integer totalHouseholds;

    @Column(name = "total_persons")
    private Integer totalPersons;

    @Column(name = "total_benefits")
    private Integer totalBenefits;

    @Column(name = "ta_households")
    private Integer temporaryAssistanceHouseholds;

    @Column(name = "ta_persons")
    private Integer temporaryAssistancePersons;

    @Column(name = "ta_benefits")
    private Integer temporaryAssistanceBenefits;

    @Column(name = "non_ta_households")
    private Integer nonTemporaryAssistanceHouseholds;

    @Column(name = "non_ta_persons")
    private Integer nonTemporaryAssistancePersons;

    @Column(name = "non_ta_benefits")
    private Integer nonTemporaryAssistanceBenefits;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public CalendarDateEntity getDate() {
        return date;
    }

    public void setDate(CalendarDateEntity date) {
        this.date = date;
    }

    public void setCounty(CountyEntity county) {
        this.county = county;
    }

    public CountyEntity getCounty() {
        return county;
    }

    public Integer getTotalHouseholds() {
        return totalHouseholds;
    }

    public void setTotalHouseholds(Integer totalHouseholds) {
        this.totalHouseholds = totalHouseholds;
    }

    public Integer getTotalPersons() {
        return totalPersons;
    }

    public void setTotalPersons(Integer totalPersons) {
        this.totalPersons = totalPersons;
    }

    public Integer getTotalBenefits() {
        return totalBenefits;
    }

    public void setTotalBenefits(Integer totalBenefits) {
        this.totalBenefits = totalBenefits;
    }

    public Integer getTemporaryAssistanceHouseholds() {
        return temporaryAssistanceHouseholds;
    }

    public void setTemporaryAssistanceHouseholds(Integer temporaryAssistanceHouseholds) {
        this.temporaryAssistanceHouseholds = temporaryAssistanceHouseholds;
    }

    public Integer getTemporaryAssistancePersons() {
        return temporaryAssistancePersons;
    }

    public void setTemporaryAssistancePersons(Integer temporaryAssistancePersons) {
        this.temporaryAssistancePersons = temporaryAssistancePersons;
    }

    public Integer getTemporaryAssistanceBenefits() {
        return temporaryAssistanceBenefits;
    }

    public void setTemporaryAssistanceBenefits(Integer temporaryAssistanceBenefits) {
        this.temporaryAssistanceBenefits = temporaryAssistanceBenefits;
    }

    public Integer getNonTemporaryAssistanceHouseholds() {
        return nonTemporaryAssistanceHouseholds;
    }

    public void setNonTemporaryAssistanceHouseholds(Integer nonTemporaryAssistanceHouseholds) {
        this.nonTemporaryAssistanceHouseholds = nonTemporaryAssistanceHouseholds;
    }

    public Integer getNonTemporaryAssistancePersons() {
        return nonTemporaryAssistancePersons;
    }

    public void setNonTemporaryAssistancePersons(Integer nonTemporaryAssistancePersons) {
        this.nonTemporaryAssistancePersons = nonTemporaryAssistancePersons;
    }

    public Integer getNonTemporaryAssistanceBenefits() {
        return nonTemporaryAssistanceBenefits;
    }

    public void setNonTemporaryAssistanceBenefits(Integer nonTemporaryAssistanceBenefits) {
        this.nonTemporaryAssistanceBenefits = nonTemporaryAssistanceBenefits;
    }
}