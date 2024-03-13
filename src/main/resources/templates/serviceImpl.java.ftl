package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ${package.Parent}.entity.vo.${entity}Vo;
import  ${package.Parent}.entity.dto.querydto.${entity}QueryDto;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Override
    public  IPage<${entity}> findListByPage(Page<${entity}> page){
        LambdaQueryWrapper query = Wrappers.lambdaQuery(${entity}.class);
        IPage iPage = baseMapper.selectPage(page, query);
        return iPage;
    }

    @Override
    public IPage<${entity}Vo> findListVoByPage(Page<${entity}Vo> page, ${entity}QueryDto queryDto) {
        return baseMapper.getPageVoByQueryDto(page, queryDto);
    }

    @Override
    public int add(${entity} ${entity?uncap_first}){
        return baseMapper.insert(${entity?uncap_first});
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(${entity} ${entity?uncap_first}){
        return baseMapper.updateById(${entity?uncap_first});
    }

    @Override
    public ${entity} findById(Long id){
        return  baseMapper.selectById(id);
    }
}
</#if>
