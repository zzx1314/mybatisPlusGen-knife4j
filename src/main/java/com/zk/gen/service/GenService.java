package com.zk.gen.service;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.zk.gen.dto.GenDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class GenService {
    /**
     * 代码生成
     */
    public void genCode(GenDto genDto){
        // 代码生成器
        FastAutoGenerator.create(genDto.getDbUrl(), genDto.getUserName(), genDto.getPassword())
                // 全局配置
                .globalConfig( builder -> {
                    String projectPath = System.getProperty("user.dir");
                    if (StringUtils.isEmpty(genDto.getProjectPath())){
                        genDto.setProjectPath(projectPath);
                    }
                    builder
                            .enableSwagger() // 是否启用swagger注解
                            .author("zzx") // 作者名称
                            .dateType(DateType.ONLY_DATE) // 时间策略
                            .commentDate("yyyy-MM-dd") // 注释日期
                            .outputDir(genDto.getProjectPath()) // 输出目录
                            .fileOverride() // 覆盖已生成文件
                            .enableSwagger() // 生成swagger
                            .disableOpenDir(); // 生成后禁止打开所生成的系统目录
                })
                // 包配置
                .packageConfig( builder -> {
                    String packgeName = genDto.getPackgeName();
                    builder
                            .parent(genDto.getParent()) // 父包名
                            .moduleName(packgeName) // 模块包名
                            .entity(packgeName+ ".domain") // 实体类包名
                            .service(packgeName + ".service") // service包名
                            .serviceImpl(packgeName + ".service.impl") // serviceImpl包名
                            .mapper(packgeName + ".mapper") // mapper包名
                            .controller(packgeName +".controller") // controller包名
                            .other(packgeName); // 自定义包名

                })
                // 策略配置
                .strategyConfig(( builder) -> {
                    builder
                            .addInclude(genDto.getTableName()) // 表匹配

                            // Entity 策略配置
                            .entityBuilder()
                            .enableLombok() // 开启lombok
                            .enableChainModel() // 链式
                            .enableRemoveIsPrefix() // 开启boolean类型字段移除is前缀
                            .enableTableFieldAnnotation() //开启生成实体时生成的字段注解
                            .logicDeleteColumnName("is_deleted") // 逻辑删除数据库中字段名
                            .logicDeletePropertyName("isDeleted") // 逻辑删除实体类中的字段名
                            .naming(NamingStrategy.underline_to_camel) // 表名 下划线 -》 驼峰命名
                            .columnNaming(NamingStrategy.underline_to_camel) // 字段名 下划线 -》 驼峰命名
                            .idType(IdType.AUTO) // 主键生成策略 雪花算法生成id
                            .formatFileName("%s") // Entity 文件名称
                            .addTableFills(new Column("create_time", FieldFill.INSERT)) // 表字段填充
                            .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE)) // 表字段填充

                            // Controller 策略配置
                            .controllerBuilder()
                            .enableRestStyle() // 开启@RestController
                            .formatFileName("%sController") // Controller 文件名称

                            // Service 策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") // Service 文件名称
                            .formatServiceImplFileName("%sServiceImpl") // ServiceImpl 文件名称

                            // Mapper 策略配置
                            .mapperBuilder()
                            .enableMapperAnnotation() // 开启@Mapper
                            .enableBaseColumnList() // 启用 columnList (通用查询结果列)
                            .enableBaseResultMap() // 启动resultMap
                            .formatMapperFileName("%sMapper") // Mapper 文件名称
                            .formatXmlFileName("%sMapper"); // Xml 文件名称
                })
                // 注入配置
                .injectionConfig((scanner, builder) -> {
                    // 自定义vo，ro，qo等数据模型
                    Map<String, String> customFile = new HashMap<>();
                    customFile.put("VO.java", "templates/model/vo.java.vm");
                    customFile.put("RO.java", "templates/model/ro.java.vm");
                    customFile.put("QO.java", "templates/model/qo.java.vm");
                    customFile.put("URO.java", "templates/model/uro.java.vm");
                    // 自定义MapStruct
                    customFile.put("Converter.java", "templates/converter/converter.java.vm");

                    // 自定义配置对象
                    Map<String, Object> customMap = new HashMap<>();
                    customMap.put("vo", "VO");
                    customMap.put("ro", "RO");
                    customMap.put("qo", "QO");
                    customMap.put("uro", "URO");
                    builder
                            .customFile(customFile) // 自定义模板
                            .customMap(customMap); // 自定义map
                })
                .templateEngine(new FreemarkerTemplateEngine()).execute();;


    }


}
