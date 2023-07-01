package com.jyadmin.generate.service;

import com.jyadmin.generate.model.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-11 11:10 <br>
 * @description: CodeGeneratorService <br>
 */
public interface CodeGenerateService {

    List<TableOptionRespVO> getTableOptionsList(TableQueryReqVO vo);

    boolean saveTable(String tableName);

    boolean updateTable(Long tableId);

    boolean removeByIds(Set<Long> ids);

    boolean getTableExist(Long tableId);

    boolean updateTableConfig(UserConfigReqVO vo);

    void generateCode(Long tableId, HttpServletResponse response);

    UserConfigResVO getTableConfig(Long tableId);

    List<CodePreviewResVO> generatePreviewCode(Long tableId);
}
