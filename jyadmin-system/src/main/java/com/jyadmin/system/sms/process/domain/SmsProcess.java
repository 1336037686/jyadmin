package com.jyadmin.system.sms.process.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:38 <br>
 * @description: SmsProcess <br>
 */
@Data
@Accessors(chain = true)
public class SmsProcess {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

}
