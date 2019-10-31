package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author DingYuHui
 * @Date 2019/10/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Page implements Serializable {
    private Integer start =0;

    private Integer rows = 1;

    private Integer totalCount;

    private Integer totalPage;

    private String url;



}
