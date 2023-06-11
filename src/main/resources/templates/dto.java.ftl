package ${package.Other}.dto;

<#list table.importPackages as pkg>
    import ${pkg};
</#list>
<#if entityLombokModel>
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;
</#if>

/**
* <p>
    * ${table.comment!}
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if entityLombokModel>
@Data
<#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
<#else>
@EqualsAndHashCode(callSuper = false)
</#if>
@Accessors(chain = true)
</#if>
<#if swagger>
    @ApiModel(value="${entity}Dto对象", description="${table.comment!}")
</#if>
public class ${entity}Dto extends ${entity} implements Serializable {

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

}
