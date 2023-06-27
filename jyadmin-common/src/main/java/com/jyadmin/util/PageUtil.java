package com.jyadmin.util;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.base.BasePageVO;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.PageResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分页工具类
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
     * @return /
     */
    public static <T> PageResult<T> toPageResult (Page<T> page) {
        return new PageResult<T>()
        .setStatus(ResultStatus.SUCCESS)
        .setSuccess(true)
        .setPageNumber(page.getCurrent())
        .setPageSize(page.getSize())
        .setPages(page.getPages())
        .setTotal(page.getTotal())
        .setRecords(page.getRecords())
        .setHasPrevious(page.hasPrevious())
        .setHasNext(page.hasNext());
    }

    /**
     * 手动分页
     * @param records 记录
     * @param vo 分页模型
     * @param <T> /
     * @return /
     */
    public static <T> PageResult<T> toPageResult (List<T> records, BasePageVO vo) {
        // 总页数
        long pages = records.size() / vo.getPageSize() + (records.size() % vo.getPageSize() == 0 ? 0 : 1);
        // 截取记录
        List<T> recordList = records.stream()
                .skip((vo.getPageNumber() - 1) * vo.getPageSize())
                .limit(vo.getPageSize())
                .collect(Collectors.toList());
        // 是否有上一页
        boolean hasPre = vo.getPageNumber() > 1 && recordList.size() > 0;
        // 是否有下一页
        boolean hasNext = (vo.getPageNumber() - 1) * vo.getPageSize() < records.size()
                && vo.getPageNumber() * vo.getPageSize() > records.size();

        return new PageResult<T>()
                .setStatus(ResultStatus.SUCCESS)
                .setSuccess(true)
                .setPageNumber((long) vo.getPageNumber())
                .setPageSize((long) vo.getPageSize())
                .setPages(pages)
                .setTotal((long) records.size())
                .setRecords(recordList)
                .setHasPrevious(hasPre)
                .setHasNext(hasNext);
    }

}
