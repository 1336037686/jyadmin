package com.jyadmin.system.announcements.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.system.announcements.domain.Announcements;
import com.jyadmin.system.announcements.model.vo.AnnouncementsCreateReqVO;
import com.jyadmin.system.announcements.model.vo.AnnouncementsQueryReqVO;
import com.jyadmin.system.announcements.model.vo.AnnouncementsUpdateReqVO;
import com.jyadmin.system.announcements.service.AnnouncementsService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;

/**
 * 系统公告 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-07-12 20:08:30 <br>
 * @description: AnnouncementsController <br>
 */
@Slf4j
@Api(value = "系统公告", tags = {"系统：系统公告"})
@RequestMapping("/api/announcements")
@RestController
public class AnnouncementsController {

    @Resource
    private AnnouncementsService AnnouncementsService;

    @ApiOperation(value = "新增系统公告", notes = "")
    @PostMapping("/create")
    //@PreAuthorize("@jy.check('Announcements:create')")
    public Result<Object> doCreate(@RequestBody @Valid AnnouncementsCreateReqVO vo) {
        return ResultUtil.toResult(AnnouncementsService.save(BeanUtil.copyProperties(vo, Announcements.class)));
    }

    @ApiOperation(value = "更新系统公告", notes = "")
    @PutMapping("/update")
    //@PreAuthorize("@jy.check('Announcements:update')")
    public Result<Object> doUpdate(@RequestBody @Valid AnnouncementsUpdateReqVO vo) {
        Announcements Announcements = AnnouncementsService.getById(vo.getId());
        BeanUtil.copyProperties(vo, Announcements);
        return ResultUtil.toResult(AnnouncementsService.updateById(Announcements));
    }

    @ApiOperation(value = "删除系统公告", notes = "")
    @DeleteMapping("/remove")
    //@PreAuthorize("@jy.check('Announcements:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(AnnouncementsService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前系统公告", notes = "")
    @GetMapping("/query/{id}")
    //@PreAuthorize("@jy.check('Announcements:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(AnnouncementsService.getById(Long.parseLong(id)));
    }

    @ApiOperation(value = "分页查询系统公告", notes = "")
    @GetMapping("/query")
    //@PreAuthorize("@jy.check('Announcements:query')")
    public PageResult<Announcements> doQueryPage(AnnouncementsQueryReqVO vo) {
        return PageUtil.toPageResult(
                this.AnnouncementsService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Announcements>()
                            .like(StringUtils.isNotBlank(vo.getTitle()), Announcements::getTitle, vo.getTitle())
                            .eq(Objects.nonNull(vo.getStatus()), Announcements::getStatus, vo.getStatus())
                            .orderByDesc(Announcements::getCreateTime)
                )
        );
    }
}
