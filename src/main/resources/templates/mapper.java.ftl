package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Parent}.entity.dto.querydto.${entity}QueryDto;
<#if mapperAnnotation>
    import org.apache.ibatis.annotations.Mapper;
    import org.apache.ibatis.annotations.Param;
</#if>

/**
* <p>
    * ${table.comment!} Mapper 接口
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if mapperAnnotation>
    @Mapper
</#if>
<#if kotlin>
    interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
    public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
       Page<${entity}Vo> getPageVoByQueryDto(Page page, @Param("query") ${entity}QueryDto query);
    }
</#if>
