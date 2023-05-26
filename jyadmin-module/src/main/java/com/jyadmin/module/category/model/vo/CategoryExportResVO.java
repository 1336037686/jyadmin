package com.jyadmin.module.category.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 导出
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-26 09:43 <br>
 * @description: CategoryExportResVO <br>
 */
@Data
@Accessors(chain = true)
@ContentRowHeight(30)
@HeadRowHeight(35)
@ColumnWidth(30)
public class CategoryExportResVO {

    /**
     * 类别名称
     */
    @ExcelProperty({"博文类别", "类别名称"})
    private String name;

    /**
     * 类别编码
     */
    @ExcelProperty({"博文类别", "类别编码"})
    private String code;

    /**
     * 简介
     */
    @ExcelProperty({"博文类别", "类别简介"})
    private String intro;

}
