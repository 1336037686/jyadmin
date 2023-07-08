package com.jyadmin.module.category.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.module.category.domain.Category;
import com.jyadmin.module.category.model.vo.CategoryExportResVO;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResponseUtil;
import com.jyadmin.util.ResultUtil;
import com.jyadmin.module.category.model.vo.CategoryCreateVO;
import com.jyadmin.module.category.model.vo.CategoryQueryVO;
import com.jyadmin.module.category.model.vo.CategoryUpdateVO;
import com.jyadmin.module.category.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-09 16:05 <br>
 * @description: CategoryController <br>
 */
@Slf4j
@Api(value = "博客类别", tags = {"博客：博客类别接口"})
@RequestMapping("/api/category")
@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @ApiOperation(value = "新增类别", notes = "")
    @PostMapping("/create")
    public Result<Object> doCreate(@RequestBody @Valid CategoryCreateVO vo) {
        return ResultUtil.toResult(categoryService.save(BeanUtil.copyProperties(vo, Category.class)));
    }

    @ApiOperation(value = "更新类别", notes = "")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid CategoryUpdateVO vo) {
        Category category = categoryService.getById(vo.getId());
        BeanUtil.copyProperties(vo, category);
        return ResultUtil.toResult(categoryService.updateById(category));
    }

    @ApiOperation(value = "删除类别", notes = "")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(categoryService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前类别信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(categoryService.getById(Long.parseLong(id)));
    }

    @ApiOperation(value = "分页查询类别", notes = "")
    @GetMapping("/query")
    public PageResult<Category> doQueryPage(CategoryQueryVO vo) {
        return PageUtil.toPageResult(
                this.categoryService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Category>()
                                .like(StringUtils.isNotBlank(vo.getName()), Category::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), Category::getCode, vo.getCode())
                )
        );
    }

    @ApiOperation(value = "数据导出", notes = "")
    @GetMapping("/data-export")
    public void download(CategoryQueryVO vo, HttpServletResponse response) throws IOException {
        // 初始化Xlsx响应
        ResponseUtil.initXlsxResponse(response, StrUtil.format("博客类别-{}", DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATE_PATTERN)));
        // 查询数据
        List<Category> categories = this.categoryService.list(new LambdaQueryWrapper<Category>()
                .like(StringUtils.isNotBlank(vo.getName()), Category::getName, vo.getName())
                .like(StringUtils.isNotBlank(vo.getCode()), Category::getCode, vo.getCode())
        );
        // 数据转义
        List<CategoryExportResVO> exportResVOS = BeanUtil.copyToList(categories, CategoryExportResVO.class);
        // 写出
        EasyExcel.write(response.getOutputStream(), CategoryExportResVO.class).sheet("sheet1").doWrite(exportResVOS);
    }
    
}
