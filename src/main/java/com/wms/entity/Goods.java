package com.wms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author bobo
 * @since 2022-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 货名
     */
    private String name;

    /**
     * 仓库
     */
    private Integer storage;

    /**
     * 分类
     */
    @TableField("goodsType")
    private Integer goodstype;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 备注
     */
    private String remark;


}
