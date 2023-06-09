package com.zk.gen.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zk.gen.dto.GenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "代码生产工具")
@RestController("/genMybatis")
public class GenController {

    @ApiOperation("代码生成")
    @GetMapping("/genCode")
    public void getTaskPage(GenDto genDto) {

    }
}
