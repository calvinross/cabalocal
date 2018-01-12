package com.ross53.cobar.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class CompleteItem {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;

    private String orderNumber;

    @JsonProperty(value = "item_id")
    private Integer itemId;

    @JsonProperty(value = "completed_quantity")
    private Integer completedQuantity=1;

    @JsonProperty(value ="item_status" )
    private Integer itemStatus;

    @JsonProperty(value = "position")
    private Integer position;

    @JsonProperty(value = "position_number")
    private Integer positionNumber;

    @JsonIgnore
    private Integer orderId;

}
