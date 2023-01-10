package com.wms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: bo
 * @DATE: 2022/12/30 16:30
 **/

@Data
@EqualsAndHashCode(callSuper = false)
public class ExportRecordVo implements Serializable {


    private static final long serialVersionUID = 1L;

     /**
     * 物品
     */
    private String goods;

    /**
     * 物品类型
     */
    private String goodstype;

    /**
     * 仓库
     */
    private String storage;

    /**
     * 操作人
     */
    private String userId;

    /**
     * 管理人
     */
    private String adminId;

    /**
     * 出库/入库
     */
    private String inOrOut;

    /**
     * 数量
     */
    private int count;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;



}
