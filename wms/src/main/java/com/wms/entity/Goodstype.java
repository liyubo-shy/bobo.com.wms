package com.wms.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author bobo
 * @since 2022-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goodstype implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private int id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;


}
