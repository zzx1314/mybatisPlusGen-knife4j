package com.zk.gen.service;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.zk.gen.dto.GenDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenService {
    /**
     * 代码生成
     */
    public void genCode(GenDto genDto){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        mpg.setGlobalConfig(getGlobalCon(genDto));
        mpg.setDataSource(getDfon(genDto));

        mpg.setPackageInfo(getPackConfig(genDto));
        mpg.setCfg(getInjectionConfig(genDto));


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

        // 公共父类
        // 写于父类中的公共字段
        strategy.setInclude(genDto.getTableName());
        // // 配置驼峰转连字符
        strategy.setControllerMappingHyphenStyle(false);
        // 配置 rest 风格的控制器（@RestController）
        strategy.setRestControllerStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }

    /**
     * 全局配置
     */
    private GlobalConfig getGlobalCon(GenDto genDto){
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        if (StringUtils.isNotEmpty(genDto.getProjectPath())){
            projectPath = genDto.getProjectPath();
        }else {
            genDto.setProjectPath(projectPath);
        }
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("zzx");
        gc.setOpen(false);
        gc.setFileOverride(true);//是否覆盖文件
        gc.setBaseResultMap(true); // xml resultmap
        gc.setBaseColumnList(true); // xml columlist
        gc.setSwagger2(true);  //实体属性 Swagger2 注解

        return gc;
    }


    /**
     * 数据源配置
     */
    private DataSourceConfig getDfon(GenDto genDto){
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(genDto.getDbUrl());
        dsc.setDriverName(genDto.getDriverName());
        dsc.setUsername(genDto.getUserName());
        dsc.setPassword(genDto.getPassword());
        dsc.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                if ( fieldType.toLowerCase().contains( "datetime" ) ) {
                    return DbColumnType.DATE;
                }
                return super.processTypeConvert(globalConfig, fieldType);
            }

        });
        return dsc;
    }

    /**
     * 包配置
     */
    private PackageConfig getPackConfig(GenDto genDto){
        PackageConfig pc = new PackageConfig();
        String packgeName = genDto.getPackgeName();
        pc.setParent(genDto.getParent());
        pc.setController(packgeName +".controller");
        pc.setEntity(packgeName+ ".domain");
        pc.setService(packgeName + ".service");
        pc.setServiceImpl(packgeName + ".service.impl");
        pc.setMapper(packgeName + ".mapper");
        pc.setXml(packgeName + ".mapper");
        pc.setModuleName(packgeName);
        return pc;
    }

    /**
     * 自定义配置
     */
    private InjectionConfig getInjectionConfig(GenDto genDto){
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        /*// 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return genDto.getProjectPath() + "/src/main/resources/mapper/" + genDto.getPackgeName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);*/
        return cfg;
    }


}
