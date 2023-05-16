package com.jyadmin.generate.service;

import com.jyadmin.generate.model.vo.TableOptionRespVO;
import com.jyadmin.generate.model.vo.TableQueryReqVO;
import com.jyadmin.generate.model.vo.UserConfigReqVO;

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

    boolean updateTable(String tableId);

    boolean removeByIds(Set<String> ids);

    boolean getTableExist(String tableId);

    boolean updateTableConfig(UserConfigReqVO vo);

    void generateCode(String tableId, HttpServletResponse response);

}
