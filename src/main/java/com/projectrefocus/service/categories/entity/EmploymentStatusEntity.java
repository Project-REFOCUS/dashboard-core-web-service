package com.projectrefocus.service.categories.entity;

import com.projectrefocus.service.geography.entity.CensusTractEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "employment_status")
public class EmploymentStatusEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "employed")
    private Integer employed;

    @Column(name = "unemployed")
    private Integer unemployed;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "census_tract_id")
    private CensusTractEntity censusTract;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployed() {
        return employed;
    }

    public void setEmployed(Integer employed) {
        this.employed = employed;
    }

    public Integer getUnemployed() {
        return unemployed;
    }

    public void setUnemployed(Integer unemployed) {
        this.unemployed = unemployed;
    }

    public CensusTractEntity getCensusTract() {
        return censusTract;
    }

    public void setCensusTract(CensusTractEntity censusTract) {
        this.censusTract = censusTract;
    }
}