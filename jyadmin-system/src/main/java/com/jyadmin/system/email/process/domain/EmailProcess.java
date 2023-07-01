package com.jyadmin.system.email.process.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:38 <br>
 * @description: EmailProcess <br>
 */
@Data
@Accessors(chain = true)
public class EmailProcess {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

}
