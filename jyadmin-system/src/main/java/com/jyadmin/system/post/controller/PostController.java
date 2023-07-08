package com.jyadmin.system.post.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.post.domain.Post;
import com.jyadmin.system.post.model.vo.PostCreateVO;
import com.jyadmin.system.post.model.vo.PostQueryVO;
import com.jyadmin.system.post.model.vo.PostUpdateVO;
import com.jyadmin.system.post.service.PostService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @RateLimit
    @Log(title = "岗位管理：新增岗位", desc = "新增岗位")
    @ApiOperation(value = "新增岗位", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('post:create')")
    public Result<Object> doCreate(@RequestBody @Valid PostCreateVO vo) {
        return ResultUtil.toResult(postService.save(BeanUtil.copyProperties(vo, Post.class)));
    }

    @RateLimit
    @Log(title = "岗位管理：更新岗位", desc = "更新岗位")
    @ApiOperation(value = "更新岗位", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('post:update')")
    public Result<Object> doUpdate(@RequestBody @Valid PostUpdateVO vo) {
        Post post = postService.getById(vo.getId());
        BeanUtil.copyProperties(vo, post);
        return ResultUtil.toResult(postService.updateById(post));
    }

    @RateLimit
    @Log(title = "岗位管理：删除岗位", desc = "删除岗位")
    @ApiOperation(value = "删除岗位", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('post:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(postService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前岗位信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('post:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(postService.getById(Long.parseLong(id)));
    }

    @ApiOperation(value = "列表查询岗位", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('post:list')")
    public Result<List<Post>> doQueryList(PostQueryVO vo) {
        return Result.ok(this.postService.list(new LambdaQueryWrapper<Post>()
                        .like(StringUtils.isNotBlank(vo.getName()), Post::getName, vo.getName())
                        .like(StringUtils.isNotBlank(vo.getCode()), Post::getCode, vo.getCode())
                        .orderByDesc(Post::getCreateTime)
        ));
    }


    @ApiOperation(value = "分页查询岗位", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('post:query')")
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
