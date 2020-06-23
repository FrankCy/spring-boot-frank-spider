package com.frank.jsoup.test.util;

import lombok.Data;

import java.util.List;

/**
 * 带分页信息的结果集对象
 *
 * @param <T> 返回的数据对应的对象
 * @author sky.lixin
 * @version PageList.java, v 0.1 2020年06月01日 15:06 sky.lixin Exp $
 */
@Data
public class PageList<T> {

    /**
     * 当前页数
     */
    private Integer pageNo;

    /**
     * 是否还有更多记录，true表示有，false表示没有
     */
    private Boolean hasMore;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 由返回的数据对象组成的List对象
     */
    private List<T> data;

    /**
     * 默认的构造函数
     */
    public PageList() {}

    /**
     * 带参数的构造函数
     *
     * @param pageNo  当前页数
     * @param hasMore 是否还有更多记录，true表示有，false表示没有
     * @param total   总的记录数
     * @param data    由返回的数据对象组成的List对象
     */
    public PageList(Integer pageNo, Boolean hasMore, Long total, List<T> data) {
        this.setPageNo(pageNo);
        this.setHasMore(hasMore);
        this.setTotal(total);
        this.setData(data);
    }

}
