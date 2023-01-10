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
public class RecordVo implements Serializable {


        private static final long serialVersionUID = 1L;

        /**
         * 货品id
         *

        /**
         * 取货人/补货人
         */
        private String goods;


        private String goodstype;

        private String storage;
        /**
         * 操作人id
         */
        private String userId;

        /**
         * 数量
         */
        private String adminId;

        /**
         * 备注
         */
        private int count;

        private String remark;

        /**
         * 操作时间
         */
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createDate;



}
