package com.jyadmin.generate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.generate.domain.CodeGenerateTable;
import com.jyadmin.generate.service.CodeGenerateTableService;
import com.jyadmin.generate.mapper.CodeGenerateTableMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【code_generate_table(代码生成器-数据库表信息)】的数据库操作Service实现
* @createDate 2023-05-11 11:15:25
*/
@Service
public class CodeGenerateTableServiceImpl extends ServiceImpl<CodeGenerateTableMapper, CodeGenerateTable>
    implements CodeGenerateTableService{

}




