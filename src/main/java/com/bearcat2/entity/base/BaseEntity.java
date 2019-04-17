package com.bearcat2.entity.base;

import java.io.Serializable;

/**
 * <p>Description: 通用对象 </p>
 * <p>Title: BaseEntity </p>
 * <p>Create Time: 2018/8/17 18:20 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8299758838346475517L;

    private Integer page;

    private Integer limit;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "page=" + page +
                ", limit=" + limit +
                '}';
    }
}
