package com.jyblog.module.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyblog.domain.PageResult;
import com.jyblog.domain.Result;
import com.jyblog.module.blog.domain.Blog;
import com.jyblog.module.blog.model.vo.BlogCreateVO;
import com.jyblog.module.blog.model.vo.BlogQueryVO;
import com.jyblog.module.blog.model.vo.BlogUpdateVO;
import com.jyblog.module.blog.service.BlogService;
import com.jyblog.module.category.domain.Category;
import com.jyblog.module.category.model.vo.CategoryCreateVO;
import com.jyblog.module.category.model.vo.CategoryQueryVO;
import com.jyblog.module.category.model.vo.CategoryUpdateVO;
import com.jyblog.util.PageUtil;
import com.jyblog.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-09 16:42 <br>
 * @description: BlogController <br>
 */
@Slf4j
@Api(value = "博客文章", tags = {"博客文章接口"})
@RequestMapping("blog")
@RestController
public class BlogController {


    @Resource
    private BlogService blogService;

    @ApiOperation(value = "新增博客", notes = "")
    @PostMapping("/create")
    public Result<Object> doCreate(@RequestBody @Valid BlogCreateVO vo) {
        return ResultUtil.toResult(blogService.save(BeanUtil.copyProperties(vo, Blog.class)));
    }

    @ApiOperation(value = "更新博客", notes = "")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid BlogUpdateVO vo) {
        Blog blog = blogService.getById(vo.getId());
        BeanUtil.copyProperties(vo, blog);
        return ResultUtil.toResult(blogService.updateById(blog));
    }

    @ApiOperation(value = "删除博客", notes = "")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(blogService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前博客信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(blogService.getById(id));
    }

    @ApiOperation(value = "分页查询", notes = "")
    @GetMapping("/query")
    public PageResult<Blog> doQueryPage(BlogQueryVO vo) {
        return PageUtil.toPageResult(
                this.blogService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Blog>()
                                .like(StringUtils.isNotBlank(vo.getTitle()), Blog::getTitle, vo.getTitle())
                                .like(StringUtils.isNotBlank(vo.getAuthor()), Blog::getAuthor, vo.getAuthor())
                                .eq(StringUtils.isNotBlank(vo.getCategory()), Blog::getCategory, vo.getCategory())
                                .eq(StringUtils.isNotBlank(vo.getTag()), Blog::getTag, vo.getTag())
                                .eq(Objects.nonNull(vo.getSource()), Blog::getSource, vo.getSource())
                                .eq(Objects.nonNull(vo.getStatus()), Blog::getStatus, vo.getStatus())
                                .eq(Objects.nonNull(vo.getVisible()), Blog::getVisible, vo.getVisible())
                )
        );
    }





}