package com.wms.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: bo
 * @DATE: 2023/1/13 11:14
 **/
@Data
@EqualsAndHashCode(callSuper = false)

public class UserAgeAnalysisVo {
    private static final long serialVersionUID = 1L;
     private String age;
     private Integer count;
}
