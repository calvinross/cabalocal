package com.ross53.cobar.domain;


import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class InfoCode {

    @Id
    @GeneratedValue
    private Integer id;

    private String infoCode;

    private Integer infoStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Type(type="java.sql.Timestamp")
    private Timestamp createTime;
}
