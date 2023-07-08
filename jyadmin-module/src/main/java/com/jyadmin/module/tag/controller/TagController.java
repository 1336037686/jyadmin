package com.jyadmin.module.tag.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.Idempotent;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.module.tag.domain.Tag;
import com.jyadmin.module.tag.model.vo.TagCreateVO;
import com.jyadmin.module.tag.model.vo.TagQueryVO;
import com.jyadmin.module.tag.model.vo.TagUpdateVO;
import com.jyadmin.module.tag.service.TagService;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-09 15:50 <br>
 * @description: TagController <br>
 */
@Slf4j
@Api(value = "博客标签", tags = {"博客：博客标签接口"})
@RequestMapping("/api/tag")
@RestController
public class TagController {

    @Resource
    private TagService tagService;

    @Idempotent
    @ApiOperation(value = "新增标签", notes = "")
    @PreAuthorize("@jy.check('tag:create')")
    @PostMapping("/create")
    public Result<Object> doCreate(@RequestBody @Valid TagCreateVO vo) {
        return ResultUtil.toResult(tagService.save(BeanUtil.copyProperties(vo, Tag.class)));
    }

    @Idempotent
    @ApiOperation(value = "更新标签", notes = "")
    @PreAuthorize("@jy.check('tag:update')")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid TagUpdateVO vo) {
        Tag tag = tagService.getById(vo.getId());
        BeanUtil.copyProperties(vo, tag);
        return ResultUtil.toResult(tagService.updateById(tag));
    }

    @ApiOperation(value = "删除标签", notes = "")
    @PreAuthorize("@jy.check('tag:remove')")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(tagService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前标签信息", notes = "")
    @PreAuthorize("@jy.check('tag:queryById')")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(tagService.getById(Long.parseLong(id)));
    }


    @RateLimit(period = 1, count = 2) // 添加限流注解，每秒（period）2次 （count），不做设置会默认采用JyLimitProperties配置
    @ApiOperation(value = "分页查询标签", notes = "")
    @PreAuthorize("@jy.check('tag:queryPage')")
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
