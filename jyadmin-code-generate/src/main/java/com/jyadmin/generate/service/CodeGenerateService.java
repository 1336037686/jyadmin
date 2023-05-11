package com.jyadmin.generate.service;

import com.jyadmin.generate.model.vo.TableOptionRespVO;
import com.jyadmin.generate.model.vo.TableQueryReqVO;

import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-11 11:10 <br>
 * @description: CodeGeneratorService <br>
 */
public interface CodeGenerateService {

    List<TableOptionRespVO> getTableOptionsList(TableQueryReqVO vo);

    boolean saveTable(String tableName);
}
