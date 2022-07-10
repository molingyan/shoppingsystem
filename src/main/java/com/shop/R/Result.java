package com.shop.R;
public class Result<T> {

    private int code;	// 约定好的，业务状态响应码。

    private String msg; // 业务状态提示信息。

/*    private String token; //传给前端的token*/

    private T data; //接口处理完成后，返回的数据结果。

    public Result(ResultCode code, T data) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultCode.SUCCESS,data);
    }
    public static <T> Result<T> error(T data) {
        return new Result<T>(ResultCode.ERROR, data);
    }
    public static <T> Result<T> error(ResultCode code,T data) {
        return new Result<T>(code, data);
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Object getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
/*    public String getToken() {
        return token;
    }
    public void setData(String token) {
        this.token = token;
    }*/




}

