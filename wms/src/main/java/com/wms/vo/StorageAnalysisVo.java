package com.wms.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: bo
 * @DATE: 2023/1/12 9:52
 **/
@Data
@EqualsAndHashCode(callSuper = false)

public class StorageAnalysisVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Integer value;
}
