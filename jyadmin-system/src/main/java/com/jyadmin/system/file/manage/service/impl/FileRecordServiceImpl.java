package com.jyadmin.system.file.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.file.manage.domain.FileRecord;
import com.jyadmin.system.file.manage.service.FileRecordService;
import com.jyadmin.system.file.manage.mapper.FileRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【sys_file_record(附件文件记录)】的数据库操作Service实现
* @createDate 2022-11-17 22:41:22
*/
@Service
public class FileRecordServiceImpl extends ServiceImpl<FileRecordMapper, FileRecord>
    implements FileRecordService{

}




