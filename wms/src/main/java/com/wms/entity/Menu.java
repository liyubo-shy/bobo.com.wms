package com.wms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author bobo
 * @since 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private int id;

    /**
     * 菜单编码
     */
    @TableField("menuCode")
    private String menucode;

    /**
     * 菜单名字
     */
    @TableField("menuName")
    private String menuname;

    /**
     * 菜单级别
     */
    @TableField("menuLevel")
    private String menulevel;

    /**
     * 菜单的父code
     */
    @TableField("menuParentCode")
    private String menuparentcode;

    /**
     * 点击触发的函数
     */
    @TableField("menuClick")
    private String menuclick;

    /**
     * 权限 0超级管理员，1表示管理员，2表示普通用户，可以用逗号组合使用
     */
    @TableField("menuRight")
    private String menuright;

    @TableField("menuComponent")
    private String menucomponent;

    @TableField("menuIcon")
    private String menuicon;


}
