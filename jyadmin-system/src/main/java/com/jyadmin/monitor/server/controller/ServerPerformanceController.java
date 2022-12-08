package com.jyadmin.monitor.server.controller;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.jyadmin.domain.Result;
import com.jyadmin.monitor.server.model.vo.MemoryInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.hardware.GlobalMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.util.List;

/**
 * 服务器性能监控
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-12-08 22:48 <br>
 * @description: ServerPerformanceController <br>
 */
@Slf4j
@Api(value = "服务器性能监控", tags = {"监控：服务器性能监控"})
@RequestMapping("/api/server-performance")
@RestController
public class ServerPerformanceController {

    @ApiOperation(value = "获取服务器CPU信息", notes = "")
    @GetMapping("/query/cpu-info")
    public Result<CpuInfo> doQueryCpuInfo() {
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        return Result.ok(cpuInfo);
    }

    @ApiOperation(value = "获取服务器磁盘信息", notes = "")
    @GetMapping("/query/disk-info")
    public Result<List<OSFileStore>> doQueryDiskInfo() {
        FileSystem fileSystem = OshiUtil.getOs().getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        return Result.ok(fileStores);
    }

    @ApiOperation(value = "获取服务器内存信息", notes = "")
    @GetMapping("/query/memory-info")
    public Result<MemoryInfo> doQueryMemoryInfo() {
        GlobalMemory globalMemory = OshiUtil.getMemory();
        MemoryInfo memoryInfo = new MemoryInfo()
                .setTotal(globalMemory.getTotal())
                .setUsed(globalMemory.getTotal() - globalMemory.getAvailable())
                .setFree(globalMemory.getAvailable());
        return Result.ok(memoryInfo);
    }






}
