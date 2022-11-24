package com.jyadmin.system.email.record.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.email.record.domain.EmailRecord;
import com.jyadmin.system.email.record.service.EmailRecordService;
import com.jyadmin.system.email.record.mapper.EmailRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【sys_email_record(邮件发送记录)】的数据库操作Service实现
* @createDate 2022-11-23 00:12:03
*/
@Service
public class EmailRecordServiceImpl extends ServiceImpl<EmailRecordMapper, EmailRecord>
    implements EmailRecordService{

}




