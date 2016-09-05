package com.yanz.machine.shinva.entity;

/**
 * Created by yanz on 2016-07-25.
 */
public class SUnqualifyPartDeal {

        // Fields
    private Integer iautoId;
    private String ctype;
    private String cwareHouse;
    private String cwlcode;
    private String cplanCode;
    private String cpartCode;
    private String cpartName;
    private Integer igxh;
    private String cgxnr;
    private String dtBackDate;
    private Double fmhtime;
    private Double fplanQuantity;
    private Double funQualifyQuantity;
    private Double fwasteQuantity;
    private Double freworkQuantity;
    private Double fconcessionQuantity;
    private Double funQualifyTotalCost;
    private String ffollowCost;
    private String cmaterialCode;
    private String cmaterialName;
    private String cmaterialStd;
    private Double fmaterialCount;
    private Double fmaterialPrice;
    private String clxh;
    private String ccheckerCode;
    private String ccheckerName;
    private String dcheckDate;
    private String cdepartmentCode;
    private String cdepartmentName;
    private String cplanerCode;
    private String cplanerName;
    private String csheji;
    private Boolean btechnicSubmit;
    private String dtSubmitTech;
    private String cunqualifyDealResult;
    private String ctechnicalPersonCode;
    private String ctechnicalPersonName;
    private String dtechnicalDealDate;
    private String cunqualifyCause;
    private String cresponsibleDepartmentCode;
    private String cresponsibleDepartmentName;
    private String cresponsiblePersonCode;
    private String cresponsiblePersonName;
    private String cnewOrderCode;
    private String cnewPlaneDate;
    private String dwpPlaneDate;
    private String cfinancePersonCode;
    private String cfinancePersonName;
    private String cfinanceDealDate;
    private String cqcauditFlag;
    private String cqcauditPersonCode;
    private String cqcauditPersonName;
    private String dqcauditDate;
    private Boolean bsign;
    private Boolean breceived;
    private String creceivePersonCode;
    private String creceivePersonName;
    private String dtReceiveDate;
    private String ccopy;
    private String bcorrected;
    private String cunQualifyType;
    private String bissued;
    private String cfileUrl;
    private String ccntrNo;
    private Integer cunqualifyPrintTimes;
    private Integer ccorrectPrintTimes;
    private String ccorrectPrintPersonCode;
    private String ccorrectPrintPersonName;
    private String ccorrectPrintDate;
    private String cfinallyAudit;
    private String cfinallyAuditPersonCode;
    private String cfinallyAuditPersonName;
    private String dfinallyAuditDate;
    private Boolean cmanualFlag;

    private SUnqualifyPartDetail detail;

    public SUnqualifyPartDetail getDetail() {
        return detail;
    }

    public void setDetail(SUnqualifyPartDetail detail) {
        this.detail = detail;
    }
    // Constructors

        /** default constructor */
        public SUnqualifyPartDeal() {
        }

    public Integer getIautoId() {
        return iautoId;
    }

    public void setIautoId(Integer iautoId) {
        this.iautoId = iautoId;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getCwareHouse() {
        return cwareHouse;
    }

    public void setCwareHouse(String cwareHouse) {
        this.cwareHouse = cwareHouse;
    }

    public String getCwlcode() {
        return cwlcode;
    }

    public void setCwlcode(String cwlcode) {
        this.cwlcode = cwlcode;
    }

    public String getCplanCode() {
        return cplanCode;
    }

    public void setCplanCode(String cplanCode) {
        this.cplanCode = cplanCode;
    }

    public String getCpartCode() {
        return cpartCode;
    }

    public void setCpartCode(String cpartCode) {
        this.cpartCode = cpartCode;
    }

    public String getCpartName() {
        return cpartName;
    }

    public void setCpartName(String cpartName) {
        this.cpartName = cpartName;
    }

    public Integer getIgxh() {
        return igxh;
    }

    public void setIgxh(Integer igxh) {
        this.igxh = igxh;
    }

    public String getCgxnr() {
        return cgxnr;
    }

    public void setCgxnr(String cgxnr) {
        this.cgxnr = cgxnr;
    }

    public String getDtBackDate() {
        return dtBackDate;
    }

    public void setDtBackDate(String dtBackDate) {
        this.dtBackDate = dtBackDate;
    }

    public Double getFmhtime() {
        return fmhtime;
    }

    public void setFmhtime(Double fmhtime) {
        this.fmhtime = fmhtime;
    }

    public Double getFplanQuantity() {
        return fplanQuantity;
    }

    public void setFplanQuantity(Double fplanQuantity) {
        this.fplanQuantity = fplanQuantity;
    }

    public Double getFunQualifyQuantity() {
        return funQualifyQuantity;
    }

    public void setFunQualifyQuantity(Double funQualifyQuantity) {
        this.funQualifyQuantity = funQualifyQuantity;
    }

    public Double getFwasteQuantity() {
        return fwasteQuantity;
    }

    public void setFwasteQuantity(Double fwasteQuantity) {
        this.fwasteQuantity = fwasteQuantity;
    }

    public Double getFreworkQuantity() {
        return freworkQuantity;
    }

    public void setFreworkQuantity(Double freworkQuantity) {
        this.freworkQuantity = freworkQuantity;
    }

    public Double getFconcessionQuantity() {
        return fconcessionQuantity;
    }

    public void setFconcessionQuantity(Double fconcessionQuantity) {
        this.fconcessionQuantity = fconcessionQuantity;
    }

    public Double getFunQualifyTotalCost() {
        return funQualifyTotalCost;
    }

    public void setFunQualifyTotalCost(Double funQualifyTotalCost) {
        this.funQualifyTotalCost = funQualifyTotalCost;
    }

    public String getFfollowCost() {
        return ffollowCost;
    }

    public void setFfollowCost(String ffollowCost) {
        this.ffollowCost = ffollowCost;
    }

    public String getCmaterialCode() {
        return cmaterialCode;
    }

    public void setCmaterialCode(String cmaterialCode) {
        this.cmaterialCode = cmaterialCode;
    }

    public String getCmaterialName() {
        return cmaterialName;
    }

    public void setCmaterialName(String cmaterialName) {
        this.cmaterialName = cmaterialName;
    }

    public String getCmaterialStd() {
        return cmaterialStd;
    }

    public void setCmaterialStd(String cmaterialStd) {
        this.cmaterialStd = cmaterialStd;
    }

    public Double getFmaterialCount() {
        return fmaterialCount;
    }

    public void setFmaterialCount(Double fmaterialCount) {
        this.fmaterialCount = fmaterialCount;
    }

    public Double getFmaterialPrice() {
        return fmaterialPrice;
    }

    public void setFmaterialPrice(Double fmaterialPrice) {
        this.fmaterialPrice = fmaterialPrice;
    }

    public String getClxh() {
        return clxh;
    }

    public void setClxh(String clxh) {
        this.clxh = clxh;
    }

    public String getCcheckerCode() {
        return ccheckerCode;
    }

    public void setCcheckerCode(String ccheckerCode) {
        this.ccheckerCode = ccheckerCode;
    }

    public String getCcheckerName() {
        return ccheckerName;
    }

    public void setCcheckerName(String ccheckerName) {
        this.ccheckerName = ccheckerName;
    }

    public String getDcheckDate() {
        return dcheckDate;
    }

    public void setDcheckDate(String dcheckDate) {
        this.dcheckDate = dcheckDate;
    }

    public String getCdepartmentCode() {
        return cdepartmentCode;
    }

    public void setCdepartmentCode(String cdepartmentCode) {
        this.cdepartmentCode = cdepartmentCode;
    }

    public String getCdepartmentName() {
        return cdepartmentName;
    }

    public void setCdepartmentName(String cdepartmentName) {
        this.cdepartmentName = cdepartmentName;
    }

    public String getCplanerCode() {
        return cplanerCode;
    }

    public void setCplanerCode(String cplanerCode) {
        this.cplanerCode = cplanerCode;
    }

    public String getCplanerName() {
        return cplanerName;
    }

    public void setCplanerName(String cplanerName) {
        this.cplanerName = cplanerName;
    }

    public String getCsheji() {
        return csheji;
    }

    public void setCsheji(String csheji) {
        this.csheji = csheji;
    }

    public Boolean getBtechnicSubmit() {
        return btechnicSubmit;
    }

    public void setBtechnicSubmit(Boolean btechnicSubmit) {
        this.btechnicSubmit = btechnicSubmit;
    }

    public String getDtSubmitTech() {
        return dtSubmitTech;
    }

    public void setDtSubmitTech(String dtSubmitTech) {
        this.dtSubmitTech = dtSubmitTech;
    }

    public String getCunqualifyDealResult() {
        return cunqualifyDealResult;
    }

    public void setCunqualifyDealResult(String cunqualifyDealResult) {
        this.cunqualifyDealResult = cunqualifyDealResult;
    }

    public String getCtechnicalPersonCode() {
        return ctechnicalPersonCode;
    }

    public void setCtechnicalPersonCode(String ctechnicalPersonCode) {
        this.ctechnicalPersonCode = ctechnicalPersonCode;
    }

    public String getCtechnicalPersonName() {
        return ctechnicalPersonName;
    }

    public void setCtechnicalPersonName(String ctechnicalPersonName) {
        this.ctechnicalPersonName = ctechnicalPersonName;
    }

    public String getDtechnicalDealDate() {
        return dtechnicalDealDate;
    }

    public void setDtechnicalDealDate(String dtechnicalDealDate) {
        this.dtechnicalDealDate = dtechnicalDealDate;
    }

    public String getCunqualifyCause() {
        return cunqualifyCause;
    }

    public void setCunqualifyCause(String cunqualifyCause) {
        this.cunqualifyCause = cunqualifyCause;
    }

    public String getCresponsibleDepartmentCode() {
        return cresponsibleDepartmentCode;
    }

    public void setCresponsibleDepartmentCode(String cresponsibleDepartmentCode) {
        this.cresponsibleDepartmentCode = cresponsibleDepartmentCode;
    }

    public String getCresponsibleDepartmentName() {
        return cresponsibleDepartmentName;
    }

    public void setCresponsibleDepartmentName(String cresponsibleDepartmentName) {
        this.cresponsibleDepartmentName = cresponsibleDepartmentName;
    }

    public String getCresponsiblePersonCode() {
        return cresponsiblePersonCode;
    }

    public void setCresponsiblePersonCode(String cresponsiblePersonCode) {
        this.cresponsiblePersonCode = cresponsiblePersonCode;
    }

    public String getCresponsiblePersonName() {
        return cresponsiblePersonName;
    }

    public void setCresponsiblePersonName(String cresponsiblePersonName) {
        this.cresponsiblePersonName = cresponsiblePersonName;
    }

    public String getCnewOrderCode() {
        return cnewOrderCode;
    }

    public void setCnewOrderCode(String cnewOrderCode) {
        this.cnewOrderCode = cnewOrderCode;
    }

    public String getCnewPlaneDate() {
        return cnewPlaneDate;
    }

    public void setCnewPlaneDate(String cnewPlaneDate) {
        this.cnewPlaneDate = cnewPlaneDate;
    }

    public String getDwpPlaneDate() {
        return dwpPlaneDate;
    }

    public void setDwpPlaneDate(String dwpPlaneDate) {
        this.dwpPlaneDate = dwpPlaneDate;
    }

    public String getCfinancePersonCode() {
        return cfinancePersonCode;
    }

    public void setCfinancePersonCode(String cfinancePersonCode) {
        this.cfinancePersonCode = cfinancePersonCode;
    }

    public String getCfinancePersonName() {
        return cfinancePersonName;
    }

    public void setCfinancePersonName(String cfinancePersonName) {
        this.cfinancePersonName = cfinancePersonName;
    }

    public String getCfinanceDealDate() {
        return cfinanceDealDate;
    }

    public void setCfinanceDealDate(String cfinanceDealDate) {
        this.cfinanceDealDate = cfinanceDealDate;
    }

    public String getCqcauditFlag() {
        return cqcauditFlag;
    }

    public void setCqcauditFlag(String cqcauditFlag) {
        this.cqcauditFlag = cqcauditFlag;
    }

    public String getCqcauditPersonCode() {
        return cqcauditPersonCode;
    }

    public void setCqcauditPersonCode(String cqcauditPersonCode) {
        this.cqcauditPersonCode = cqcauditPersonCode;
    }

    public String getCqcauditPersonName() {
        return cqcauditPersonName;
    }

    public void setCqcauditPersonName(String cqcauditPersonName) {
        this.cqcauditPersonName = cqcauditPersonName;
    }

    public String getDqcauditDate() {
        return dqcauditDate;
    }

    public void setDqcauditDate(String dqcauditDate) {
        this.dqcauditDate = dqcauditDate;
    }

    public Boolean getBsign() {
        return bsign;
    }

    public void setBsign(Boolean bsign) {
        this.bsign = bsign;
    }

    public Boolean getBreceived() {
        return breceived;
    }

    public void setBreceived(Boolean breceived) {
        this.breceived = breceived;
    }

    public String getCreceivePersonCode() {
        return creceivePersonCode;
    }

    public void setCreceivePersonCode(String creceivePersonCode) {
        this.creceivePersonCode = creceivePersonCode;
    }

    public String getCreceivePersonName() {
        return creceivePersonName;
    }

    public void setCreceivePersonName(String creceivePersonName) {
        this.creceivePersonName = creceivePersonName;
    }

    public String getDtReceiveDate() {
        return dtReceiveDate;
    }

    public void setDtReceiveDate(String dtReceiveDate) {
        this.dtReceiveDate = dtReceiveDate;
    }

    public String getCcopy() {
        return ccopy;
    }

    public void setCcopy(String ccopy) {
        this.ccopy = ccopy;
    }

    public String getBcorrected() {
        return bcorrected;
    }

    public void setBcorrected(String bcorrected) {
        this.bcorrected = bcorrected;
    }

    public String getCunQualifyType() {
        return cunQualifyType;
    }

    public void setCunQualifyType(String cunQualifyType) {
        this.cunQualifyType = cunQualifyType;
    }

    public String getBissued() {
        return bissued;
    }

    public void setBissued(String bissued) {
        this.bissued = bissued;
    }

    public String getCfileUrl() {
        return cfileUrl;
    }

    public void setCfileUrl(String cfileUrl) {
        this.cfileUrl = cfileUrl;
    }

    public String getCcntrNo() {
        return ccntrNo;
    }

    public void setCcntrNo(String ccntrNo) {
        this.ccntrNo = ccntrNo;
    }

    public Integer getCunqualifyPrintTimes() {
        return cunqualifyPrintTimes;
    }

    public void setCunqualifyPrintTimes(Integer cunqualifyPrintTimes) {
        this.cunqualifyPrintTimes = cunqualifyPrintTimes;
    }

    public Integer getCcorrectPrintTimes() {
        return ccorrectPrintTimes;
    }

    public void setCcorrectPrintTimes(Integer ccorrectPrintTimes) {
        this.ccorrectPrintTimes = ccorrectPrintTimes;
    }

    public String getCcorrectPrintPersonCode() {
        return ccorrectPrintPersonCode;
    }

    public void setCcorrectPrintPersonCode(String ccorrectPrintPersonCode) {
        this.ccorrectPrintPersonCode = ccorrectPrintPersonCode;
    }

    public String getCcorrectPrintPersonName() {
        return ccorrectPrintPersonName;
    }

    public void setCcorrectPrintPersonName(String ccorrectPrintPersonName) {
        this.ccorrectPrintPersonName = ccorrectPrintPersonName;
    }

    public String getCcorrectPrintDate() {
        return ccorrectPrintDate;
    }

    public void setCcorrectPrintDate(String ccorrectPrintDate) {
        this.ccorrectPrintDate = ccorrectPrintDate;
    }

    public String getCfinallyAudit() {
        return cfinallyAudit;
    }

    public void setCfinallyAudit(String cfinallyAudit) {
        this.cfinallyAudit = cfinallyAudit;
    }

    public String getCfinallyAuditPersonCode() {
        return cfinallyAuditPersonCode;
    }

    public void setCfinallyAuditPersonCode(String cfinallyAuditPersonCode) {
        this.cfinallyAuditPersonCode = cfinallyAuditPersonCode;
    }

    public String getCfinallyAuditPersonName() {
        return cfinallyAuditPersonName;
    }

    public void setCfinallyAuditPersonName(String cfinallyAuditPersonName) {
        this.cfinallyAuditPersonName = cfinallyAuditPersonName;
    }

    public String getDfinallyAuditDate() {
        return dfinallyAuditDate;
    }

    public void setDfinallyAuditDate(String dfinallyAuditDate) {
        this.dfinallyAuditDate = dfinallyAuditDate;
    }

    public Boolean getCmanualFlag() {
        return cmanualFlag;
    }

    public void setCmanualFlag(Boolean cmanualFlag) {
        this.cmanualFlag = cmanualFlag;
    }
}

