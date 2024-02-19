package com.zk.gen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "配置")
public class GenDto {
    @ApiModelProperty(position = 0,value = "表名")
    private String tableName;

    @ApiModelProperty(position = 1,value = "ip地址")
    private String ip;

    @ApiModelProperty(position = 2,value = "数据库名称")
    private String dbName;

    @ApiModelProperty(position = 3,value = "驱动名称", example = "com.mysql.cj.jdbc.Driver")
    private String driverName;

    @ApiModelProperty(position = 4,value = "用户名", example = "root")
    private String userName;

    @ApiModelProperty(position = 5,value = "密码", example = "123456")
    private String password;

    @ApiModelProperty(position = 6,value = "包名")
    private String packgeName;

    @ApiModelProperty(position = 7,value = "前缀", example = "com.superred.th.upms.biz")
    private String parent;

    @ApiModelProperty(position = 8,value = "项目位置")
    private String projectPath;

}
