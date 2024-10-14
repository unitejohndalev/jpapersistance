package com.perjpasample.jpapersistance.main.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "manhour_estimation")
public class ManhourEstimationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mhe_id")
    private Integer id;

    @Column(name = "\"control no_\"")
    private Double controlno;

    @Column(name = "\"group\"")
    private String group;

    @Column(name = "department")
    private String department;

    @Column(name = "\"project name\"")
    private String projectname;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "\"revision no_\"")
    private Double revisionno;

    @Column(name = "\"date issued\"")
    private Date dateissued;

    @Column(name = "mh_id")
    private Integer mh_id;

    @Column(name = "activity")
    private String activity;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "computation")
    private Double computation;

    @Column(name = "manhour")
    private Double manhour;

    @Column(name = "\"grand total\"")
    private Double grandtotal;

    @Column(name = "\"prepared by\"")
    private String preparedby;

    @Column(name = "\"checked by\"")
    private String checkedby;

    @Column(name = "\"approved by\"")
    private String approvedby;

    public ManhourEstimationModel() {
    }

    public ManhourEstimationModel(Integer id, Double controlno, String group, String department, String projectname,
            String description, Double duration, Double revisionno, Date dateissued, Integer mh_id, String activity,
            Double rate, Double computation, Double manhour, Double grandtotal, String preparedby, String checkedby,
            String approvedby) {
        this.id = id;
        this.controlno = controlno;
        this.group = group;
        this.department = department;
        this.projectname = projectname;
        this.description = description;
        this.duration = duration;
        this.revisionno = revisionno;
        this.dateissued = dateissued;
        this.mh_id = mh_id;
        this.activity = activity;
        this.rate = rate;
        this.computation = computation;
        this.manhour = manhour;
        this.grandtotal = grandtotal;
        this.preparedby = preparedby;
        this.checkedby = checkedby;
        this.approvedby = approvedby;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getControlno() {
        return this.controlno;
    }

    public void setControlno(Double controlno) {
        this.controlno = controlno;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProjectname() {
        return this.projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDuration() {
        return this.duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getRevisionno() {
        return this.revisionno;
    }

    public void setRevisionno(Double revisionno) {
        this.revisionno = revisionno;
    }

    public Date getDateissued() {
        return this.dateissued;
    }

    public void setDateissued(Date dateissued) {
        this.dateissued = dateissued;
    }

    public Integer getMh_id() {
        return this.mh_id;
    }

    public void setMh_id(Integer mh_id) {
        this.mh_id = mh_id;
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Double getRate() {
        return this.rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getComputation() {
        return this.computation;
    }

    public void setComputation(Double computation) {
        this.computation = computation;
    }

    public Double getManhour() {
        return this.manhour;
    }

    public void setManhour(Double manhour) {
        this.manhour = manhour;
    }

    public Double getGrandtotal() {
        return this.grandtotal;
    }

    public void setGrandtotal(Double grandtotal) {
        this.grandtotal = grandtotal;
    }

    public String getPreparedby() {
        return this.preparedby;
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }

    public String getCheckedby() {
        return this.checkedby;
    }

    public void setCheckedby(String checkedby) {
        this.checkedby = checkedby;
    }

    public String getApprovedby() {
        return this.approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }
}
