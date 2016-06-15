package com.yanz.machine.shinva.entity;

import java.io.Serializable;

/**
 * Created by yanzi on 2016-05-09.
 */
public class SCurrentStock implements Serializable {
    private Integer icsautoId;
    private String ccsWhCode;
    private String ccsWhName;
    private String ccspartCode;
    private String ccsPartName;
    private String ccsPartStd;
    private Double fcsQuantity;
    private Double fcsApplyQuantity;
    private Double fcsPrice;
    private String ccsFree1;
    private String ccsFree2;
    private Double fcsFree1;
    private Double fcsFree2;
    private String ccsPosition;
    private Double fcsTopQuantity;
    private Double fcsBottomQuantity;
    private Double fcsBuyPeriod;
    private Double fcsMonthQuantity;
    private String ccsType;
    private String ccsQualityType;
    private String ccsMccode;
    private Boolean bcsMcaudit;
    private String ccsMcauditerCode;
    private String ccsMcauditerName;
    private String dtCsMcauditDate;
    private String ccsMcflag;
    private Boolean bcsInvalid;
    private String ccsInvalidCause;
    private String ccsSpecialRequirement;
    private String ccsMaterialCode;
    private String ccsMaterialName;
    private String ccsMaterialStd;
    private String cfileUrl;
    private Integer itakingStock;

    public Integer getIcsautoId() {
        return icsautoId;
    }

    public void setIcsautoId(Integer icsautoId) {
        this.icsautoId = icsautoId;
    }

    public String getCcsWhCode() {
        return ccsWhCode;
    }

    public void setCcsWhCode(String ccsWhCode) {
        this.ccsWhCode = ccsWhCode;
    }

    public String getCcsWhName() {
        return ccsWhName;
    }

    public void setCcsWhName(String ccsWhName) {
        this.ccsWhName = ccsWhName;
    }

    public String getCcspartCode() {
        return ccspartCode;
    }

    public void setCcspartCode(String ccspartCode) {
        this.ccspartCode = ccspartCode;
    }

    public String getCcsPartName() {
        return ccsPartName;
    }

    public void setCcsPartName(String ccsPartName) {
        this.ccsPartName = ccsPartName;
    }

    public String getCcsPartStd() {
        return ccsPartStd;
    }

    public void setCcsPartStd(String ccsPartStd) {
        this.ccsPartStd = ccsPartStd;
    }

    public Double getFcsQuantity() {
        return fcsQuantity;
    }

    public void setFcsQuantity(Double fcsQuantity) {
        this.fcsQuantity = fcsQuantity;
    }

    public Double getFcsApplyQuantity() {
        return fcsApplyQuantity;
    }

    public void setFcsApplyQuantity(Double fcsApplyQuantity) {
        this.fcsApplyQuantity = fcsApplyQuantity;
    }

    public Double getFcsPrice() {
        return fcsPrice;
    }

    public void setFcsPrice(Double fcsPrice) {
        this.fcsPrice = fcsPrice;
    }

    public String getCcsFree1() {
        return ccsFree1;
    }

    public void setCcsFree1(String ccsFree1) {
        this.ccsFree1 = ccsFree1;
    }

    public String getCcsFree2() {
        return ccsFree2;
    }

    public void setCcsFree2(String ccsFree2) {
        this.ccsFree2 = ccsFree2;
    }

    public Double getFcsFree1() {
        return fcsFree1;
    }

    public void setFcsFree1(Double fcsFree1) {
        this.fcsFree1 = fcsFree1;
    }

    public Double getFcsFree2() {
        return fcsFree2;
    }

    public void setFcsFree2(Double fcsFree2) {
        this.fcsFree2 = fcsFree2;
    }

    public String getCcsPosition() {
        return ccsPosition;
    }

    public void setCcsPosition(String ccsPosition) {
        this.ccsPosition = ccsPosition;
    }

    public Double getFcsTopQuantity() {
        return fcsTopQuantity;
    }

    public void setFcsTopQuantity(Double fcsTopQuantity) {
        this.fcsTopQuantity = fcsTopQuantity;
    }

    public Double getFcsBottomQuantity() {
        return fcsBottomQuantity;
    }

    public void setFcsBottomQuantity(Double fcsBottomQuantity) {
        this.fcsBottomQuantity = fcsBottomQuantity;
    }

    public Double getFcsBuyPeriod() {
        return fcsBuyPeriod;
    }

    public void setFcsBuyPeriod(Double fcsBuyPeriod) {
        this.fcsBuyPeriod = fcsBuyPeriod;
    }

    public Double getFcsMonthQuantity() {
        return fcsMonthQuantity;
    }

    public void setFcsMonthQuantity(Double fcsMonthQuantity) {
        this.fcsMonthQuantity = fcsMonthQuantity;
    }

    public String getCcsType() {
        return ccsType;
    }

    public void setCcsType(String ccsType) {
        this.ccsType = ccsType;
    }

    public String getCcsQualityType() {
        return ccsQualityType;
    }

    public void setCcsQualityType(String ccsQualityType) {
        this.ccsQualityType = ccsQualityType;
    }

    public String getCcsMccode() {
        return ccsMccode;
    }

    public void setCcsMccode(String ccsMccode) {
        this.ccsMccode = ccsMccode;
    }

    public Boolean getBcsMcaudit() {
        return bcsMcaudit;
    }

    public void setBcsMcaudit(Boolean bcsMcaudit) {
        this.bcsMcaudit = bcsMcaudit;
    }

    public String getCcsMcauditerCode() {
        return ccsMcauditerCode;
    }

    public void setCcsMcauditerCode(String ccsMcauditerCode) {
        this.ccsMcauditerCode = ccsMcauditerCode;
    }

    public String getCcsMcauditerName() {
        return ccsMcauditerName;
    }

    public void setCcsMcauditerName(String ccsMcauditerName) {
        this.ccsMcauditerName = ccsMcauditerName;
    }

    public String getDtCsMcauditDate() {
        return dtCsMcauditDate;
    }

    public void setDtCsMcauditDate(String dtCsMcauditDate) {
        this.dtCsMcauditDate = dtCsMcauditDate;
    }

    public String getCcsMcflag() {
        return ccsMcflag;
    }

    public void setCcsMcflag(String ccsMcflag) {
        this.ccsMcflag = ccsMcflag;
    }

    public Boolean getBcsInvalid() {
        return bcsInvalid;
    }

    public void setBcsInvalid(Boolean bcsInvalid) {
        this.bcsInvalid = bcsInvalid;
    }

    public String getCcsInvalidCause() {
        return ccsInvalidCause;
    }

    public void setCcsInvalidCause(String ccsInvalidCause) {
        this.ccsInvalidCause = ccsInvalidCause;
    }

    public String getCcsSpecialRequirement() {
        return ccsSpecialRequirement;
    }

    public void setCcsSpecialRequirement(String ccsSpecialRequirement) {
        this.ccsSpecialRequirement = ccsSpecialRequirement;
    }

    public String getCcsMaterialCode() {
        return ccsMaterialCode;
    }

    public void setCcsMaterialCode(String ccsMaterialCode) {
        this.ccsMaterialCode = ccsMaterialCode;
    }

    public String getCcsMaterialName() {
        return ccsMaterialName;
    }

    public void setCcsMaterialName(String ccsMaterialName) {
        this.ccsMaterialName = ccsMaterialName;
    }

    public String getCcsMaterialStd() {
        return ccsMaterialStd;
    }

    public void setCcsMaterialStd(String ccsMaterialStd) {
        this.ccsMaterialStd = ccsMaterialStd;
    }

    public String getCfileUrl() {
        return cfileUrl;
    }

    public void setCfileUrl(String cfileUrl) {
        this.cfileUrl = cfileUrl;
    }

    public Integer getItakingStock() {
        return itakingStock;
    }

    public void setItakingStock(Integer itakingStock) {
        this.itakingStock = itakingStock;
    }
}
