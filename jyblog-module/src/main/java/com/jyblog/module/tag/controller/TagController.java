package com.jyblog.module.tag.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyblog.domain.PageResult;
import com.jyblog.domain.Result;
import com.jyblog.module.tag.domain.Tag;
import com.jyblog.module.tag.model.vo.TagCreateVO;
import com.jyblog.module.tag.model.vo.TagQueryVO;
import com.jyblog.module.tag.model.vo.TagUpdateVO;
import com.jyblog.module.tag.service.TagService;
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
 * Create by 2022-04-09 15:50 <br>
 * @description: TagController <br>
 */
@Slf4j
@Api(value = "博客标签", tags = {"博客标签接口"})
@RequestMapping("tag")
@RestController
public class TagController {

    @Resource
    private TagService tagService;

    @ApiOperation(value = "新增标签", notes = "")
    @PostMapping("/create")
    public Result<Object> doCreate(@RequestBody @Valid TagCreateVO vo) {
        return ResultUtil.toResult(tagService.save(BeanUtil.copyProperties(vo, Tag.class)));
    }

    @ApiOperation(value = "更新标签", notes = "")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid TagUpdateVO vo) {
        Tag tag = tagService.getById(vo.getId());
        BeanUtil.copyProperties(vo, tag);
        return ResultUtil.toResult(tagService.updateById(tag));
    }

    @ApiOperation(value = "删除标签", notes = "")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(tagService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前标签信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(tagService.getById(id));
    }

    @ApiOperation(value = "分页查询标签", notes = "")
    @GetMapping("/query")
    public PageResult<Tag> doQueryPage(TagQueryVO vo) {
        return PageUtil.toPageResult(
                this.tagService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<Tag>()
                        .like(StringUtils.isNotBlank(vo.getName()), Tag::getName, vo.getName())
                        .like(StringUtils.isNotBlank(vo.getCode()), Tag::getCode, vo.getCode())
                )
        );
    }

}
