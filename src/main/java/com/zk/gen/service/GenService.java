package com.zk.gen.service;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.sun.istack.internal.NotNull;
import com.zk.gen.dto.GenDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
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
                    builder.enableSwagger() // 是否启用swagger注解
                            .author("zzx") // 作者名称
                            .dateType(DateType.ONLY_DATE) // 时间策略
                            .commentDate("yyyy-MM-dd") // 注释日期
                            .outputDir(genDto.getProjectPath() + "/src/main/java") // 输出目录
                            .fileOverride() // 覆盖已生成文件
                            .enableSwagger() // 生成swagger
                            .disableOpenDir(); // 生成后禁止打开所生成的系统目录
                })
                // 包配置
                .packageConfig( builder -> {
                    String packgeName = genDto.getPackgeName();
                    if (!StringUtils.isEmpty(packgeName)){
                        builder.parent(genDto.getParent()) // 父包名
                                .moduleName(packgeName) // 模块包名
                                .entity("entity") // 实体类包名
                                .service("service") // service包名
                                .serviceImpl("service.impl") // serviceImpl包名
                                .mapper("mapper") // mapper包名
                                .controller("controller") // controller包名
                                .xml("mapper")
                                .other(packgeName); // 自定义包名
                    } else {
                        builder.parent(genDto.getParent()) // 父包名
                                .entity("entity") // 实体类包名
                                .service("service") // service包名
                                .serviceImpl("service.impl") // serviceImpl包名
                                .mapper("mapper") // mapper包名
                                .controller("controller") // controller包名
                                .xml("mapper"); // 自定义包名
                    }


                })
                // 策略配置
                .strategyConfig(( builder) -> {
                    builder.addInclude(genDto.getTableName()) // 表匹配

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
                            .formatFileName("%s") // Entity 文件名称
                            .addTableFills(new Column("create_time", FieldFill.INSERT)) // 表字段填充
                            .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE)) // 表字段填充
                            .addTableFills(new Column("modified_time", FieldFill.INSERT_UPDATE)) // 表字段填充

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
                .injectionConfig( builder -> {
                    // 自定义vo，ro，qo等数据模型
                    Map<String, String> customFile = new HashMap<>();
                    customFile.put("Vo", "templates/vo.java.ftl");
                    customFile.put("QueryDto", "templates/queryDto.java.ftl");
                    customFile.put("Dto", "templates/dto.java.ftl");

                    // 自定义配置对象
                    builder.customFile(customFile);// 自定义模板
                })
                .templateEngine(new EnFreemarkerTemplateEngine()).execute();;
    }

    /**
     * 代码生成器支持自定义[DTO\VO等]模版
     */
    public final static class EnFreemarkerTemplateEngine extends FreemarkerTemplateEngine {
        /**
         * 文件输出路径
         *
         * @param customFile 自定义文件map
         * @param tableInfo  表信息
         * @param objectMap  对象map
         */
        @Override
        protected void outputCustomFile(@NotNull Map<String, String> customFile, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
            // 获取实体类名字
            String entityName = tableInfo.getEntityName();
            // 获取other包盘符路径
            String otherPath = this.getPathInfo(OutputFile.other);
            // 输出自定义java模板
            customFile.forEach((key, value) -> {
                String fileName = "";
                // 去除other路径
                String resultPath = otherPath.substring(0, otherPath.lastIndexOf(File.separator));
                if ("QueryDto".equals(key)){
                    fileName = resultPath + File.separator + "entity" + File.separator + "dto" + File.separator+ key.toLowerCase() + File.separator + entityName + key + ".java";
                } else if ("Dto".equals(key) || "Vo".equals(key)){
                    fileName = resultPath + File.separator + "entity" + File.separator + key.toLowerCase() + File.separator + entityName + key + ".java";
                } else{
                    fileName = resultPath + File.separator + key.toLowerCase() + File.separator + entityName + key + ".java";
                }
                // 输出velocity的java模板
                this.outputFile(new File(fileName), objectMap, value);
            });
        }
    }


}
