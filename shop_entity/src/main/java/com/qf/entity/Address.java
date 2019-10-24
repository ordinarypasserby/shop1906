package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author DingYuHui
 * @Date 2019/10/23
 * 收货地址实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Address extends BaseEntity {
    private String person;
    private String address;
    private String phone;
    private String code;
    private Integer isdefault = 0;
    private Integer uid;
}
