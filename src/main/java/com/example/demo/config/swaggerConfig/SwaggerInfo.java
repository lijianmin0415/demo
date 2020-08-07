package com.example.demo.config.swaggerConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * swagger 配置信息
 */
@Component
@ConfigurationProperties(prefix = "spring.swagger")
public class SwaggerInfo {
    private String groupName = "TMS";

    private String basePackage;

    private String antPath;

    private String title = "ei-model系统接口文档";

    private String description = "ei-quartz系统模块接口文档";

    private String license = "数起科技 Version 2.0";

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getAntPath() {
        return antPath;
    }

    public void setAntPath(String antPath) {
        this.antPath = antPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
