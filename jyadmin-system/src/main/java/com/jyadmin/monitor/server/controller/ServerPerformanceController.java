package com.jyadmin.monitor.server.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.RuntimeInfo;
import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.OshiUtil;
import com.jyadmin.domain.Result;
import com.jyadmin.monitor.server.model.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.hardware.GlobalMemory;
import oshi.hardware.NetworkIF;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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
    @PreAuthorize("@jy.check('server-performance:queryCpuInfo')")
    public Result<CpuInfo> doQueryCpuInfo() {
        cn.hutool.system.oshi.CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        CpuInfo info = new CpuInfo();
        BeanUtil.copyProperties(cpuInfo, info);
        return Result.ok(info);
    }

    @ApiOperation(value = "获取服务器磁盘信息", notes = "")
    @GetMapping("/query/disk-info")
    @PreAuthorize("@jy.check('server-performance:queryDiskInfo')")
    public Result<List<DiskInfo>> doQueryDiskInfo() {
        FileSystem fileSystem = OshiUtil.getOs().getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        List<DiskInfo> diskInfos = fileStores.stream().map(x -> {
            long free = x.getUsableSpace();
            long total = x.getTotalSpace();
            long used = total - free;
            return new DiskInfo()
                    .setDirName(x.getMount()).setSysTypeName(x.getType())
                    .setTypeName(x.getName()).setTotal(total)
                    .setFree(free).setUsed(used).setUsage(NumberUtil.round(NumberUtil.div(used, total), 2).doubleValue());
        }).collect(Collectors.toList());
        return Result.ok(diskInfos);
    }

    @ApiOperation(value = "获取服务器内存信息", notes = "")
    @GetMapping("/query/memory-info")
    @PreAuthorize("@jy.check('server-performance:queryMemoryInfo')")
    public Result<MemoryInfo> doQueryMemoryInfo() {
        GlobalMemory globalMemory = OshiUtil.getMemory();
        MemoryInfo memoryInfo = new MemoryInfo()
                .setTotal(globalMemory.getTotal())
                .setUsed(globalMemory.getTotal() - globalMemory.getAvailable())
                .setFree(globalMemory.getAvailable());
        return Result.ok(memoryInfo);
    }

    @ApiOperation(value = "获取JVM信息", notes = "")
    @GetMapping("/query/jvm-info")
    @PreAuthorize("@jy.check('server-performance:queryJvmInfo')")
    public Result<JvmInfo> doQueryJvmInfo() {
        cn.hutool.system.JvmInfo hutoolJvmInfo = SystemUtil.getJvmInfo();
        RuntimeInfo runtimeInfo = SystemUtil.getRuntimeInfo();
        Properties props = System.getProperties();
        JvmInfo jvmInfo = new JvmInfo()
            .setName(hutoolJvmInfo.getName())
            .setVendor(hutoolJvmInfo.getVendor())
            .setVersion(props.getProperty("java.version"))
            .setHome(props.getProperty("java.home"))
            .setFreeMemory(runtimeInfo.getFreeMemory())
            .setMaxMemory(runtimeInfo.getMaxMemory())
            .setTotalMemory(runtimeInfo.getTotalMemory())
            .setUsableMemory(runtimeInfo.getUsableMemory());
        return Result.ok(jvmInfo);
    }

    @ApiOperation(value = "获取操作系统信息", notes = "")
    @GetMapping("/query/system-info")
    @PreAuthorize("@jy.check('server-performance:querySystemInfo')")
    public Result<SystemInfo> doQuerySystemInfo() {
        Properties props = System.getProperties();
        SystemInfo sys = new SystemInfo()
            .setComputerName(NetUtil.getLocalHostName())
            .setComputerIp(NetUtil.getLocalhostStr())
            .setOsName(props.getProperty("os.name"))
            .setOsArch(props.getProperty("os.arch"))
            .setUserDir(props.getProperty("user.dir"));
        return Result.ok(sys);
    }

    @ApiOperation(value = "获取网络信息", notes = "")
    @GetMapping("/query/network-info")
    @PreAuthorize("@jy.check('server-performance:queryNetworkInfo')")
    public Result<List<NetworkInfo>> doQueryNetworkInfo() {
        List<NetworkIF> networkIFs = OshiUtil.getNetworkIFs();
        List<NetworkInfo> networkInfos = networkIFs.stream().map(x ->
                new NetworkInfo()
                        .setDisplayName(x.getDisplayName()).setMacAddr(x.getMacaddr())
                        .setIpv4Addr(String.join(",", x.getIPv4addr())).setIpv6Addr(String.join(",", x.getIPv6addr()))
                        .setSpeed(x.getSpeed())
        ).collect(Collectors.toList());
        return Result.ok(networkInfos);
    }

}
