package com.wms.vo;

import com.sun.rowset.internal.SyncResolverImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: bo
 * @DATE: 2023/1/11 17:26
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class RecordAnalysisVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String date;
    private Integer count;
}
