package com.ross53.cobar.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ross53.cobar.enums.ItemStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class OrderDetail {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;

    //product id;
    @JsonProperty(value = "product_id")
    private Integer productId;

    //unique id for each item;
    @JsonProperty(value = "item_id")
    private Integer itemId;

    // whether customized item or not by user;
    @JsonProperty(value = "customize")
    private Boolean customize;

    //item count;
    @JsonProperty(value = "count")
    private Integer count;

    //product's price
    @JsonProperty(value = "price")
    private BigDecimal price;

    //total prices;
    @JsonProperty(value = "total_price")
    private BigDecimal totalPrice;

    //remark;
    @JsonProperty(value = "remark")
    private String remark;

    //buyer's comments for order;
    @JsonProperty(value = "message")
    private String message;

    //buyer we_chat nickname;
    @JsonProperty(value = "guest_name")
    private String guestName;

    //category en name;
    @JsonProperty(value = "c_name_en")
    @Column(name = "c_name_en")
    private String c_name_en;

    //category cn name;
    @JsonProperty(value = "c_name_cn")
    @Column(name = "c_name_cn")
    private  String c_name_cn;

    //product's en name
    @JsonProperty(value = "p_name_en")
    @Column(name = "p_name_en")
    private String p_name_en;

    //product's cn name
    @JsonProperty(value = "p_name_cn")
    @Column(name = "p_name_cn")
    private String p_name_cn;

    //product's en introduce;
    @JsonProperty(value = "p_intro_en")
    @Column(name = "p_intro_en")
    private String p_intro_en;

    //product's cn introduce;
    @JsonProperty(value = "p_intro_cn")
    @Column(name = "p_intro_cn")
    private String p_intro_cn;

    //product's image url
    @JsonProperty(value = "p_imgurl")
    @Column(name = "p_img_url")
    private String p_imgurl;

    //unit en name;
    @JsonProperty(value = "u_name_en")
    @Column(name = "u_name_en")
    private String u_name_en;

    //unit cn name;
    @JsonProperty(value = "u_name_cn")
    @Column(name = "u_name_cn")
    private String u_name_cn;

    @JsonIgnore
    @Column(name = "detail_id")
    private Integer detailId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="command_list", joinColumns=@JoinColumn(name="orderdetail_id"))
    @Column(name="command",length = 1024)
    @JsonProperty(value = "command")
    private List<String> command = new ArrayList<String>();

    @JsonIgnore
    private ItemStatus itemStatus = ItemStatus.UNDO;

    @JsonIgnore
    private Integer completeCount=0;
}
