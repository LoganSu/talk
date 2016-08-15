/**
 * Copyright 2015 Unionbon' Studio
 * All right Reserved.
 * Created on 2015年9月1日.
 * Created by garyxin.
 */

package com.youlb.entity.common;


/**
 * @author garyxin on 2015年9月1日.
 *
 */
public class ResultDTO {
    private String code = "0";
    private String msg = "";
    private Object result;
    
    public ResultDTO() {}
    
    public ResultDTO(String code) {
    	this.code = code;
    }
    
    public ResultDTO(String code, String msg) {
    	this.code = code;
    	this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
