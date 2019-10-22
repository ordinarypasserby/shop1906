package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author DingYuHui
 * @Date 2019/10/19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NoteCode implements Serializable {

    private String phoneNumbers;
    private String signName = "GodHui";
    private String templateCode = "SMS_175532595";
    private String templateParam;
    private String regionId = "cn-hangzhou";
    private String accessKeyID ="LTAI4Fqhf3yVWVsqvYGL2GZ8";
    private String accessKeySecret = "50IAXBoz3upEoWR0abaLJOXEV1nZ1w";
    private Integer code;
    private String Version = "2017-05-25";


}
