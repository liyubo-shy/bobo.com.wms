package com.wms.entity;

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
public class Storage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;


}
