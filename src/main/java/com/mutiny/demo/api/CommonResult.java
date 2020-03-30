package com.mutiny.demo.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回对象
 * Created by macro on 2019/4/19.
 */
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @return
     */
    public static <T> ResponseEntity<Map<String, Object>> success(T data) {
        Map<String,Object> map = new HashMap<>();
        HttpStatus  httpStatus = HttpStatus.OK;
        map.put("code", httpStatus.value());
        map.put("message",httpStatus.getReasonPhrase());
        map.put("data",data);
        return new ResponseEntity<Map<String,Object>>(map,httpStatus);
//        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ResponseEntity<Map<String, Object>> success(T data, String message) {
        Map<String,Object> map = new HashMap<>();
        HttpStatus  httpStatus = HttpStatus.OK;
        map.put("message",message);
        map.put("code", httpStatus.value());
        map.put("data",data);
        return new ResponseEntity<Map<String,Object>>(map,httpStatus);
//        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param httpStatus 错误码
     * @return
     */
    public static <T> ResponseEntity<Map<String, Object>> failed(HttpStatus httpStatus) {
        Map<String,Object> map = new HashMap<>();
//        HttpStatus  httpStatus = HttpStatus.OK;
        map.put("code", httpStatus.value());
        map.put("message",httpStatus.getReasonPhrase());
        map.put("data",null);
        return new ResponseEntity<Map<String,Object>>(map,httpStatus);
//        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     * @return
     */
    public static <T> ResponseEntity<Map<String, Object>> failed(String message) {
        Map<String,Object> map = new HashMap<>();
        HttpStatus  httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        map.put("code", httpStatus.value());
        map.put("message",message);
        map.put("data",null);
        return new ResponseEntity<Map<String,Object>>(map,httpStatus);
//        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @return
     */
    public static <T> ResponseEntity<Map<String, Object>> failed() {
        return failed(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 参数验证失败返回结果
     * @return
     */
    public static <T> ResponseEntity<Map<String, Object>> validateFailed() {
        return failed(HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     * @return
     */
    public static <T> ResponseEntity<Map<String, Object>> validateFailed(String message) {
        Map<String,Object> map = new HashMap<>();
        HttpStatus  httpStatus = HttpStatus.NOT_ACCEPTABLE;
        map.put("code", httpStatus.value());
        map.put("message",message);
        map.put("data",null);
        return new ResponseEntity<Map<String,Object>>(map,httpStatus);
//        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     * @return
     */
    public static <T> ResponseEntity<Map<String, Object>> unauthorized(T data) {
        Map<String,Object> map = new HashMap<>();
        HttpStatus  httpStatus = HttpStatus.UNAUTHORIZED;
        map.put("code", httpStatus.value());
        map.put("message",httpStatus.getReasonPhrase());
        map.put("data",data);
        return new ResponseEntity<Map<String,Object>>(map,httpStatus);
//        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     * @return
     */
    public static <T> ResponseEntity<Map<String, Object>> forbidden(T data) {
        Map<String,Object> map = new HashMap<>();
        HttpStatus  httpStatus = HttpStatus.FORBIDDEN;
        map.put("code", httpStatus.value());
        map.put("message",httpStatus.getReasonPhrase());
        map.put("data",data);
        return new ResponseEntity<Map<String,Object>>(map,httpStatus);
//        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
