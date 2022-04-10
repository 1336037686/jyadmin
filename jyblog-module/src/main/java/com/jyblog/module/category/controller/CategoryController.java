package com.jyblog.module.category.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyblog.domain.PageResult;
import com.jyblog.domain.Result;
import com.jyblog.module.category.domain.Category;
import com.jyblog.module.category.model.vo.CategoryCreateVO;
import com.jyblog.module.category.model.vo.CategoryQueryVO;
import com.jyblog.module.category.model.vo.CategoryUpdateVO;
import com.jyblog.module.category.service.CategoryService;
import com.jyblog.module.tag.domain.Tag;
import com.jyblog.util.PageUtil;
import com.jyblog.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-09 16:05 <br>
 * @description: CategoryController <br>
 */
@Slf4j
@Api(value = "博客类别", tags = {"博客类别接口"})
@RequestMapping("category")
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
        return ResultUtil.toResult(categoryService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前类别信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(categoryService.getById(id));
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
    
}
