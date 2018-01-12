package com.ross53.cobar.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class PositionInfo {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer positionId;

    private Integer positionType;

    private String  positionNumber;

    private Integer positionStatus;

    private String orderNumber;

    private Integer itemId;
}
