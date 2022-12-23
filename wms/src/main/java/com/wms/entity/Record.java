package com.wms.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author bobo
 * @since 2022-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 货品id
     */
    private Integer goods;

    /**
     * 取货人/补货人
     */
    @TableField("userId")
    private Integer userid;

    /**
     * 操作人id
     */
    private Integer adminId;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;


}
