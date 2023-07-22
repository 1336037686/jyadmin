package com.jyadmin.system.announcements.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jyadmin.annotation.DataAccessControl;
import com.jyadmin.consts.DataAccessControlConstant;
import com.jyadmin.system.announcements.domain.Announcements;
import org.apache.ibatis.annotations.Mapper;


/**
 * 系统公告 Mapper <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-07-12 20:08:30 <br>
 * @Entity com.jyadmin.system.announcements.domain.Announcements
 * @description: AnnouncementsMapper <br>
 */
@DataAccessControl(enableMybatisPlusDefaultMethods = true)
@Mapper
public interface AnnouncementsMapper extends BaseMapper<Announcements> {

}




