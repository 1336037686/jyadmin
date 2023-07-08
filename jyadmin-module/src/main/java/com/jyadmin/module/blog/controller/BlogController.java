package com.jyadmin.module.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import com.jyadmin.module.blog.domain.Blog;
import com.jyadmin.module.blog.model.vo.BlogCreateVO;
import com.jyadmin.module.blog.model.vo.BlogQueryVO;
import com.jyadmin.module.blog.model.vo.BlogUpdateVO;
import com.jyadmin.module.blog.service.BlogService;
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
@Api(value = "博客文章", tags = {"博客：博客文章接口"})
@RequestMapping("/api/blog")
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
        return ResultUtil.toResult(blogService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前博客信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(blogService.getById(Long.parseLong(id)));
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