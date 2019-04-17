package com.bearcat2.utils;

import java.io.Serializable;

/**
 * <p>Description: 返回layui分页响应参数 </p>
 * <p>Title: LayuiPageResult </p>
 * <p>Create Time: 2018/5/17 22:23 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
public class LayuiPageResult implements Serializable {

    private static final long serialVersionUID = 8273329295993248042L;

    private Integer code;

    private String msg;
    /**
     * 数据总数
     */
    private Long count;
    /**
     * 分页数据
     */
    private Object data;

    public LayuiPageResult() {
    }

    public LayuiPageResult(Long count, Object data) {
        this.code = 0;
        this.msg = "";
        this.count = count;
        this.data = data;
    }

    public static LayuiPageResult build(Long count, Object data) {
        return new LayuiPageResult(count, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LayuiPageResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
