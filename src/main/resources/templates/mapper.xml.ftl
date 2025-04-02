<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <#if enableCache>
        <!-- 开启二级缓存 -->
        <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

    </#if>
    <#if baseResultMap>
        <!-- 通用查询映射结果 -->
        <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
            <#list table.fields as field>
                <#if field.keyFlag><#--生成主键排在第一位-->
                    <id column="${field.name}" property="${field.propertyName}"/>
                </#if>
            </#list>
            <#list table.commonFields as field><#--生成公共字段 -->
                <result column="${field.name}" property="${field.propertyName}"/>
            </#list>
            <#list table.fields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <result column="${field.name}" property="${field.propertyName}"/>
                </#if>
            </#list>
        </resultMap>

    </#if>
    <#if baseColumnList>
        <!-- 通用查询结果列 -->
        <sql id="Base_Column_List">
            <#list table.commonFields as field>
                ${field.name},
            </#list>
            ${table.fieldNames}
        </sql>
    </#if>
    <select id="getPageVoByQueryDto" resultType="${package.Parent}.entity.vo.${entity}Vo">
        select
        <#list table.fields as field>
            <#if field_index lt table.fields?size - 1 || field_index == 0>
                ${table.entityName?uncap_first}.${field.name},
            <#else>
                ${table.entityName?uncap_first}.${field.name}
            </#if>
        </#list>
        from ${table.name} as ${table.entityName?uncap_first}
        <where>
            ${table.entityName?uncap_first}.is_deleted = 0
            <#list table.fields as field>
                <#if field.keyFlag>
                    <if test="query.${field.propertyName} != null">
                        and ${table.entityName?uncap_first}.${field.name} = <#noparse>#{</#noparse>query.${field.propertyName},jdbcType=Integer<#noparse>}</#noparse>
                    </if>
                </#if>
                <#if (field.columnName != "create_time") && (field.columnName != "modified_time") && (field.columnName != "is_deleted") && (field.columnName != "id")>
                    <if test="query.${field.propertyName} != null">
                        <#if field.getPropertyType() == "String">
                            and ${table.entityName?uncap_first}.${field.name}  LIKE CONCAT('%',<#noparse>#{</#noparse>query.${field.propertyName}<#noparse>}</#noparse>,'%')
                        <#else>
                            and ${table.entityName?uncap_first}.${field.name} = <#noparse>#{</#noparse>query.${field.propertyName}<#noparse>}</#noparse>
                        </#if>
                    </if>
                </#if>
            </#list>
            <if test="query.beginTime != null">
                and ${table.entityName?uncap_first}.create_time &gt;= #{query.beginTime}
            </if>
            <if test="query.endTime != null">
                and ${table.entityName?uncap_first}.create_time &lt;= #{query.endTime}
            </if>
        </where>
    </select>
</mapper>