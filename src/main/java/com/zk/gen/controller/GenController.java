package com.zk.gen.controller;

import com.zk.gen.dto.GenDto;
import com.zk.gen.service.GenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "代码生产工具")
@RestController("/genMybatis")
public class GenController {

    @Autowired
    public GenService genService;

    @ApiOperation("代码生成")
    @GetMapping("/genCode")
    public String getTaskPage(GenDto genDto) {
        genService.genCode(genDto);
        return "生成成功";
    }
}
