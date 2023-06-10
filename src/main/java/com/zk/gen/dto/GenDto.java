package com.zk.gen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "配置")
public class GenDto {
    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "数据库连接", example = "jdbc:mysql://192.168.0.103:3306/th?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai")
    private String dbUrl;


    @ApiModelProperty(value = "驱动名称", example = "com.mysql.cj.jdbc.Driver")
    private String driverName;

    @ApiModelProperty(value = "用户名", example = "root")
    private String userName;

    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    @ApiModelProperty(value = "包名")
    private String packgeName;

    @ApiModelProperty(value = "前缀", example = "com.superred.th.upms.biz")
    private String parent;

    @ApiModelProperty(value = "项目位置")
    private String projectPath;

}
