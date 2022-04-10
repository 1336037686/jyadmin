package com.jyblog.util;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyblog.consts.JyBusinessStatus;
import com.jyblog.domain.PageResult;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-07 21:12 <br>
 * @description: PageUtil <br>
 */
public class PageUtil {

    /**
     * 将MybatisPlus Page对象转换为通用PageResult对象
     * @param page MybatisPlus Page对象
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> toPageResult (Page<T> page) {
        return new PageResult<T>()
        .setStatus(JyBusinessStatus.SUCCESS)
        .setSuccess(true)
        .setPageNumber(page.getCurrent())
        .setPageSize(page.getSize())
        .setPages(page.getPages())
        .setTotal(page.getTotal())
        .setRecords(page.getRecords())
        .setHasPrevious(page.hasPrevious())
        .setHasNext(page.hasNext());
    }

}
