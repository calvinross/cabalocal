package com.ross53.cobar.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class OrderGate {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;

    private Integer gateStatus;

    private String gateDescription;

    @Column(updatable=true)
    private Timestamp updateTime;

    private String operatorName;

}
