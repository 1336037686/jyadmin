package com.jyadmin.system.permission.menu.model.dto;

import com.jyadmin.system.permission.menu.domain.PermissionMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-17 16:04 <br>
 * @description: PermissionMenuLayerDTO <br>
 */
@Data
public class PermissionMenuLayerDTO extends PermissionMenu {

    private List<PermissionMenuLayerDTO> children = new ArrayList<>();

}
