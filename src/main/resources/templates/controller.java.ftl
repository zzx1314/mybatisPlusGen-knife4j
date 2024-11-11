package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zk.common.code.utis.R;
import ${package.Parent}.entity.vo.${entity}Vo;
import  ${package.Parent}.entity.dto.querydto.${entity}QueryDto;

import javax.annotation.Resource;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@Api(tags = {"${table.comment!}"})
@RestController
<#else>
@Controller
</#if>
@RequestMapping("${table.entityPath}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??>:${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>public class ${table.controllerName} extends ${superControllerClass}{
<#else>public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} ${table.serviceName?uncap_first};


    @ApiOperation(value = "新增${table.comment!}")
    @PostMapping("save")
    public R add(@RequestBody ${entity} ${entity?uncap_first}){
        ${table.serviceName?uncap_first}.add(${entity?uncap_first});
        return R.ok();
    }

    @ApiOperation(value = "删除${table.comment!}")
    @DeleteMapping("{id}")
    public R delete(@PathVariable("id") Long id){
        ${table.serviceName?uncap_first}.delete(id);
        return R.ok();
    }

    @ApiOperation(value = "更新${table.comment!}")
    @PutMapping("update")
    public R update(@RequestBody ${entity} ${entity?uncap_first}){
        ${table.serviceName?uncap_first}.updateData(${entity?uncap_first});
        return R.ok();
    }

    @ApiOperation(value = "查询${table.comment!}分页数据")
    @GetMapping("page")
    public R findListByPage(Page<${entity}Vo> page,${entity}QueryDto queryDto){
        return R.ok(${table.serviceName?uncap_first}.findListVoByPage(page, queryDto));
    }

    @ApiOperation(value = "id查询${table.comment!}")
    @GetMapping("{id}")
    public R findById(@PathVariable Long id){
        return R.ok(${table.serviceName?uncap_first}.findById(id));
    }

}
</#if>