package com.perjpasample.jpapersistance.main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "billed_manhour")
public class BilledManhourModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "Billed_Manhour_id")
    private Long id;
    
    @Column(name = "Billed_Manhour_date")
    private String BMdate;

 
    @Column(name = "Total_Billed_Manhour")
    private float TBMH;

    @Column(name = "Minimum_Order_Value")
    private float MOV;

    @Column(name = "Total_Billed_Manhour_Up_To_Date")
    private float TBMHUTD;

    @Column(name = "Total_Shortfall_Manhour_Up_To_Date")
    private float TSMHUTD;

    @Column(name = "Shortfall_Manhour")
    private float SMH;
    

    public BilledManhourModel() {
    }

  

    public BilledManhourModel(Long id, String BMdate, float TBMH, float MOV, float TBMHUTD, float TSMHUTD, float SMH) {
        this.id = id;
        this.BMdate = BMdate;
        this.TBMH = TBMH;
        this.MOV = MOV;
        this.TBMHUTD = TBMHUTD;
        this.TSMHUTD = TSMHUTD;
        this.SMH = SMH;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBMdate() {
        return this.BMdate;
    }

    public void setBMdate(String BMdate) {
        this.BMdate = BMdate;
    }

    public float getTBMH() {
        return this.TBMH;
    }

    public void setTBMH(float TBMH) {
        this.TBMH = TBMH;
    }

    public float getMOV() {
        return this.MOV;
    }

    public void setMOV(float MOV) {
        this.MOV = MOV;
    }

    public float getTBMHUTD() {
        return this.TBMHUTD;
    }

    public void setTBMHUTD(float TBMHUTD) {
        this.TBMHUTD = TBMHUTD;
    }

    public float getTSMHUTD() {
        return this.TSMHUTD;
    }

    public void setTSMHUTD(float TSMHUTD) {
        this.TSMHUTD = TSMHUTD;
    }

    public float getSMH() {
        return this.SMH;
    }

    public void setSMH(float SMH) {
        this.SMH = SMH;
    }


   
   
 

  




 }
