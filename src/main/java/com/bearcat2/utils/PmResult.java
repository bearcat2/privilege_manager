package com.bearcat2.utils;

import java.io.Serializable;

/**
 * <p>Description: 封装系统响应消息 </p>
 * <p>Title: PmResult </p>
 * <p>Create Time: 2018/8/16 14:46 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
public class PmResult implements Serializable {

    private static final long serialVersionUID = -8360540713890965004L;

    private boolean success;

    private String msg;

    private Object obj;

    public PmResult(boolean success, String msg, Object obj) {
        this.success = success;
        this.msg = msg;
        this.obj = obj;
    }

    public static PmResult build(boolean success, String msg, Object obj) {
        return new PmResult(success, msg, obj);
    }

    public static PmResult build(boolean success, String msg) {
        return new PmResult(success, msg, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "PmResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }
}
