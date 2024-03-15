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
        <#list table.fields as field, index>
            <#if index lt table.fields?size - 1 || index == 0>
                ${table.name}.${field.name},
            <#else>
                ${table.name}.${field.name}
            </#if>
        </#list>
        from ${table.name} as ${table.name}
        <where>
            ${table.name}.is_deleted = 0
            <#list table.fields as field>
                <#if field.keyFlag>
                    <if test="query.${field.propertyName} != null">
                        and ${table.name}.${field.name} = <#noparse>#{</#noparse>query.${field.propertyName},jdbcType=Integer<#noparse>}</#noparse>
                    </if>
                </#if>
                <#if (field.name != "create_time") || (field.name != "modified_time") || (field.name != "is_deleted") || (field.name != "id")>
                    <if test="query.${field.propertyName} != null">
                        <#if field?bean.getPropertyType() == "String">
                            and ${table.name}.${field.name}  LIKE CONCAT('%',<#noparse>#{</#noparse>query.${field.propertyName} <#noparse>}</#noparse>,'%')
                        <#else>
                            and ${table.name}.${field.name} = <#noparse>#{</#noparse>query.${field.propertyName} <#noparse>}</#noparse>
                        </#if>
                    </if>
                </#if>
            </#list>
        </where>
    </select>
</mapper>