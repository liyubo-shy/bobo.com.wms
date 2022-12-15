package com.wms.common;
import lombok.Data;

/**
 * @Author: bo
 * @DATE: 2022/11/6 18:18
 **/
@Data
public class Result {
    private int code;
    private String msg;
    private Long total;
    private Object data;

    private static Result result(int code, String msg, Long total, Object data) {
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        res.setData(data);
        return res;
    }


    public static Result fail(){
        return result(400,"失败！",0L,null);
    }

    public static Result scu(){
        return result(200,"成功~！",0L,null);
    }

    public static Result scu(Object data){
        return result(200,"成功~！",0L,data);
    }

    public static Result scu(Object data, Long total){
        return result(200,"成功~！",total,data);
    }
}
