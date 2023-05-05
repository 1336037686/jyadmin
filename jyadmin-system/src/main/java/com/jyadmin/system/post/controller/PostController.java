package com.jyadmin.system.post.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.system.post.domain.Post;
import com.jyadmin.system.post.model.vo.PostCreateVO;
import com.jyadmin.system.post.model.vo.PostQueryVO;
import com.jyadmin.system.post.model.vo.PostUpdateVO;
import com.jyadmin.system.post.service.PostService;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * 岗位管理
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-05 00:00 <br>
 * @description: DepartmentController <br>
 */
@Slf4j
@Api(value = "岗位管理", tags = {"系统：岗位管理接口"})
@RequestMapping("/api/post")
@RestController
public class PostController {

    @Resource
    private PostService postService;

    @ApiOperation(value = "新增岗位", notes = "")
    @PostMapping("/create")
    public Result<Object> doCreate(@RequestBody @Valid PostCreateVO vo) {
        return ResultUtil.toResult(postService.save(BeanUtil.copyProperties(vo, Post.class)));
    }

    @ApiOperation(value = "更新岗位", notes = "")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid PostUpdateVO vo) {
        Post post = postService.getById(vo.getId());
        BeanUtil.copyProperties(vo, post);
        return ResultUtil.toResult(postService.updateById(post));
    }

    @ApiOperation(value = "删除岗位", notes = "")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(postService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前岗位信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(postService.getById(id));
    }

    @ApiOperation(value = "分页查询岗位", notes = "")
    @GetMapping("/query")
    public PageResult<Post> doQueryPage(PostQueryVO vo) {
        return PageUtil.toPageResult(
                this.postService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Post>()
                                .like(StringUtils.isNotBlank(vo.getName()), Post::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), Post::getCode, vo.getCode())
                                .orderByDesc(Post::getCreateTime)
                )
        );
    }
    
}
