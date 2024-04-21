<#if notification_field?contains("content_type")>
    <#if contentBean.contentType?contains("reply")>
        <#assign contentType = "[回文]">
    <#else>
        <#assign contentType = "[主文]">
    </#if>
    ${contentType!}<#t>
</#if>
${contentBean.title!}<#lt>
<#if !contentBean.isRestrictSource()>
    <#if notification_field?contains("content_title")>
        內容: <#t>
    </#if>
    <#if notification_field?contains("content")>
        ${contentBean.content!}<#lt>
    </#if>
    <#if notification_field?contains("comment_count") && contentBean.contentType?contains("1")>
        回應數: ${contentBean.commentCount!}<#lt>
    </#if>
    <#if notification_field?contains("comment_count") && contentBean.contentType?contains("main")>
        回應數: ${contentBean.commentCount!}<#lt>
    </#if>
</#if>
<#if notification_field?contains("sentiment")>
    <#switch contentBean.sentimentTag>
        <#case "P">
            ${"情緒: 正面"}<#lt>
            <#break>
        <#case "N">
            ${"情緒: 負面"}<#lt>
            <#break>
        <#case "M">
            ${"情緒: 中立"}<#lt>
            <#break>
    </#switch>
</#if><#lt>
<#if notification_field?contains("source")>
    ${contentBean.sidName!} <#t>
    <#if notification_field?contains("channel")>
        > ${contentBean.sareaName!}<#t>
    </#if>
</#if>

${contentBean.url}

