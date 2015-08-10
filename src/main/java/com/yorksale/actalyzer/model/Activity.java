package com.yorksale.actalyzer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.yorksale.actalyzer.common.DateToLongSerializer;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Yashar HN
 * Date: 29/07/15 4:07 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Activity implements Serializable {
    private static final long serialVersionUID = 6143389776406290066L;
    private String id;
    private String appId;

    @JsonProperty("dateTime")
    @JsonDeserialize(using = DateToLongSerializer.class)
    private Long timestamp;

    @JsonIgnore
    private DateTime dateTime;

    private String sessionId;
    private String ipAddress;
    private String type;
    private String userAgent;
    private String categoryId;
    private String categoryName;
    private String keywords;
    private String companyID;
    private String companyName;
    private String referrer;
    private String username;
    private String source;
    private String language;
    @JsonProperty("page")
    private String pageType;

    private String emailAddress;

    private String userInfo;

    private String productName;

    private String mobileBrowser;
    @JsonProperty("url")
    private String targetURL;
    @JsonProperty("topics")
    private String topic;
    @JsonProperty("sectors")
    private String sector;

//    @JsonProperty("results")
//    private List<Organization> organizations;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        if(timestamp!=null){
            this.dateTime = new DateTime(timestamp);
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getUsername() {
        if(StringUtils.isEmpty(this.username)){
            if(StringUtils.isEmpty(this.emailAddress)){
                return this.userInfo;
            } else {
                return this.emailAddress;
            }
        }
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

//    public List<Organization> getOrganizations() {
//        return organizations;
//    }
//
//    public void setOrganizations(List<Organization> organizations) {
//        this.organizations = organizations;
//    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMobileBrowser() {
        return mobileBrowser;
    }

    public void setMobileBrowser(String mobileBrowser) {
        this.mobileBrowser = mobileBrowser;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Integer getMonth() {
        return (this.dateTime!=null)?dateTime.getMonthOfYear(): null;
    }

    public Integer getDay() {
        return (this.dateTime!=null)?dateTime.getDayOfMonth(): null;
    }

    public Integer getWeekDay() {
        return (this.dateTime!=null)?dateTime.getDayOfWeek(): null;
    }

    public Integer getHour() {
        return (this.dateTime!=null)?dateTime.getHourOfDay(): null;
    }
}