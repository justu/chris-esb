package com.chris.esb.rest.springboot.utils;

import org.apache.commons.httpclient.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chris
 * @since Apr 20.18
 */
public class CommonResponse<T> extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public CommonResponse() {
        put("code", HttpStatus.SC_OK);
        put("msg", "success");
    }

    public static CommonResponse error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static CommonResponse error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static CommonResponse error(int code, String msg) {
        CommonResponse r = new CommonResponse();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static CommonResponse ok(String msg) {
        CommonResponse r = new CommonResponse();
        r.put("msg", msg);
        return r;
    }

    public static CommonResponse ok(Map<String, Object> map) {
        CommonResponse r = new CommonResponse();
        r.putAll(map);
        return r;
    }

    public static CommonResponse ok() {
        return new CommonResponse();
    }

    @Override
    public CommonResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public CommonResponse setData(T data) {
        super.put("data", data);
        return this;
    }

    public boolean isOK() {
        return HttpStatus.SC_OK ==  Integer.valueOf(this.get("code").toString());
    }

    public T getData() {
        return (T) this.get("data");
    }
}
