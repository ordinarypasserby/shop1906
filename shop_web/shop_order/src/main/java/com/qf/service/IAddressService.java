package com.qf.service;

import com.qf.entity.Address;

import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/23
 */
public interface IAddressService {
    /**
     * 通过用户id查询对应的收货地址信息
     * @param uid
     * @return
     */
    List<Address> queryByUid(Integer uid);

    /**
     * 添加收获地址
     * @param address
     */
    int insertAddress(Address address);

    /**
     * 通过地址id得到地址信息
     * @param aid
     * @return
     */
    Address queryById(Integer aid);
}
