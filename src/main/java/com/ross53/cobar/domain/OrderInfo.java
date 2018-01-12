package com.ross53.cobar.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ross53.cobar.enums.OrderStatus;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class OrderInfo {

    @Id
    @GeneratedValue
    @JsonIgnore
    private  Integer id;

    @JsonProperty(value = "orderNumber")
    private String orderNumber;

    @JsonProperty("store_id")
    private String storeId;


    @JsonProperty(value = "tableNumber")
    private Integer tableNumber;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "count")
    private Integer count;

    @JsonProperty(value = "openid")
    private String openId;

    @JsonProperty(value = "nickname")
    private String nickName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = true, columnDefinition="timestamp default CURRENT_TIMESTAMP", insertable=false, updatable=false)
    @Type(type="java.sql.Timestamp")
    @JsonProperty(value = "creatTime")
    private Timestamp creatTime;

    @JsonProperty(value = "pay_status")
    private boolean payStatus;

    @Column
    @Type(type="date")
    @JsonProperty(value = "pay_success_date")
    private Date paySuccessDate;

    @Column(name = "orderStatus")
    @JsonProperty(value = "orderStatus")
    private boolean orderStatus;

    @JsonIgnore
    @Column(name="status", columnDefinition = "int default 0")
    @Enumerated(value = EnumType.ORDINAL)
    private OrderStatus status = OrderStatus.START;

    @JsonProperty("comefrom")
    @Column(name = "come_from")
    private Integer comeFrom;

    @OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
    @JoinColumn(name="detail_id")
    @JsonProperty("orderDetail")
    private List<OrderDetail> orderDetail = new ArrayList<OrderDetail>();


}
