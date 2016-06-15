package com.yanz.machine.shinva.entity;



public class SPlan implements java.io.Serializable {

	// Fields

	private Integer iwpAutoId;
	private String cwpPstatusFlag;
	private String cwpPlanCode;
	private String cwpPartCode;
	private String cwpPartName;
	private Integer cwpCode;
	private String cwpName;
	private String cwpDepartmentCode;
	private String cwpDepartmentName;
	private String dwpPlanBdate;
	private String dwpPlanEdate;
	private Double fwpQuantity;
	private Double fwpZbgs;
	private Double fwpRatioMh;
	private Double fwpMhtime;
	private String cwpRemark;
	private Integer iwpPctimes;
	private String cwpOutPlanType;
	private String cwpOutPlanCode;
	private String dwpOutPlanEdate;
	private String cwpOutCompany;
	private String cwpProductType;
	private String cwpCntrType;
	private String cwpCntrNo;
	private String cwpMakerCode;
	private String cwpMakerName;
	private String dwpMakeDate;
	private Double fwpPlanQuantity;
	private Double fwpFinishQuantity;
	private Double fwpFailedQuantity;
	private Double fwpActQuantity;
	private Double fwpActTime;
	private Boolean bwpPrint;
	private Integer iwpPrintQuantity;
	private String cwpReoprterCode;
	private String cwpReporterName;
	private String dwpReportDate;
	private String cwpsendMaterialFlag;
	private String cwpLxh;
	private String cwpMtjs;
	private String cwpMpjs;
	private String cwpGzbm;
	private String cwpGzmc;
	private String cwpSbbm;
	private String cwpSbxh;
	private String cwpSbmc;
	private String cwpDy;
	private String cwpMaterialCode;
	private String cwpMaterialName;
	private String cwpMaterialStd;
	private String cwpClph;
	private String cwpMpzl;
	private String cwpLlcc;
	private String cwpCllyl;
	private String cwpMz;
	private String cwpSjr;
	private String cwpSjrq;
	private String cwpErpCode;
	private String cwpinventoryCode;
	private String cwpInventoryName;
	private String cwpMhcode;
	private Double fwpSubsidy;
	private Boolean bwpCommonProcedure;
	private String cwpMemo;
	private Double fwpFree1;
	private String cwpFree2;
	private Boolean bwpNotInUse;
	private Double fwpUnitQuantity;
	private String cwpFinisherCode;
	private String cwpFinisherName;
	private String cwpStatusFlag;
	private String cwpReciverName;
	private Boolean bwpupdated;
	private String cwpmergenStep;
	private String cwpRelativePlanCode;
	private String cwpRePlanCode;
	private String cwpRePlanSource;
	private Boolean bwpPartFinish;
	private Boolean bwpToolManaged;
	private Double fwpInCount;
	private String dwpInDateTime;
	private String dwpOutDateTime;
	private Boolean bwpPlanNight;
	private String cwpShr;
	private String cpucode;
	private String cpuname;

	// Constructors

	/** default constructor */
	public SPlan() {
	}

	/** minimal constructor */
	public SPlan(Integer iwpAutoId) {
		this.iwpAutoId = iwpAutoId;
	}

	/** full constructor */
	public SPlan(Integer iwpAutoId, String cwpPstatusFlag, String cwpPlanCode,
			String cwpPartCode, String cwpPartName, Integer cwpCode,
			String cwpName, String cwpDepartmentCode, String cwpDepartmentName,
			String dwpPlanBdate, String dwpPlanEdate, Double fwpQuantity,
			Double fwpZbgs, Double fwpRatioMh, Double fwpMhtime,
			String cwpRemark, Integer iwpPctimes, String cwpOutPlanType,
			String cwpOutPlanCode, String dwpOutPlanEdate,
			String cwpOutCompany, String cwpProductType, String cwpCntrType,
			String cwpCntrNo, String cwpMakerCode, String cwpMakerName,
			String dwpMakeDate, Double fwpPlanQuantity,
			Double fwpFinishQuantity, Double fwpFailedQuantity,
			Double fwpActQuantity, Double fwpActTime, Boolean bwpPrint,
			Integer iwpPrintQuantity, String cwpReoprterCode,
			String cwpReporterName, String dwpReportDate,
			String cwpsendMaterialFlag, String cwpLxh, String cwpMtjs,
			String cwpMpjs, String cwpGzbm, String cwpGzmc, String cwpSbbm,
			String cwpSbxh, String cwpSbmc, String cwpDy,
			String cwpMaterialCode, String cwpMaterialName,
			String cwpMaterialStd, String cwpClph, String cwpMpzl,
			String cwpLlcc, String cwpCllyl, String cwpMz, String cwpSjr,
			String cwpSjrq, String cwpErpCode, String cwpinventoryCode,
			String cwpInventoryName, String cwpMhcode, Double fwpSubsidy,
			Boolean bwpCommonProcedure, String cwpMemo, Double fwpFree1,
			String cwpFree2, Boolean bwpNotInUse, Double fwpUnitQuantity,
			String cwpFinisherCode, String cwpFinisherName,
			String cwpStatusFlag, String cwpReciverName, Boolean bwpupdated,
			String cwpmergenStep, String cwpRelativePlanCode,
			String cwpRePlanCode, String cwpRePlanSource,
			Boolean bwpPartFinish, Boolean bwpToolManaged, Double fwpInCount,
			String dwpInDateTime, String dwpOutDateTime,
			Boolean bwpPlanNight, String cwpShr, String cpucode, String cpuname) {
		this.iwpAutoId = iwpAutoId;
		this.cwpPstatusFlag = cwpPstatusFlag;
		this.cwpPlanCode = cwpPlanCode;
		this.cwpPartCode = cwpPartCode;
		this.cwpPartName = cwpPartName;
		this.cwpCode = cwpCode;
		this.cwpName = cwpName;
		this.cwpDepartmentCode = cwpDepartmentCode;
		this.cwpDepartmentName = cwpDepartmentName;
		this.dwpPlanBdate = dwpPlanBdate;
		this.dwpPlanEdate = dwpPlanEdate;
		this.fwpQuantity = fwpQuantity;
		this.fwpZbgs = fwpZbgs;
		this.fwpRatioMh = fwpRatioMh;
		this.fwpMhtime = fwpMhtime;
		this.cwpRemark = cwpRemark;
		this.iwpPctimes = iwpPctimes;
		this.cwpOutPlanType = cwpOutPlanType;
		this.cwpOutPlanCode = cwpOutPlanCode;
		this.dwpOutPlanEdate = dwpOutPlanEdate;
		this.cwpOutCompany = cwpOutCompany;
		this.cwpProductType = cwpProductType;
		this.cwpCntrType = cwpCntrType;
		this.cwpCntrNo = cwpCntrNo;
		this.cwpMakerCode = cwpMakerCode;
		this.cwpMakerName = cwpMakerName;
		this.dwpMakeDate = dwpMakeDate;
		this.fwpPlanQuantity = fwpPlanQuantity;
		this.fwpFinishQuantity = fwpFinishQuantity;
		this.fwpFailedQuantity = fwpFailedQuantity;
		this.fwpActQuantity = fwpActQuantity;
		this.fwpActTime = fwpActTime;
		this.bwpPrint = bwpPrint;
		this.iwpPrintQuantity = iwpPrintQuantity;
		this.cwpReoprterCode = cwpReoprterCode;
		this.cwpReporterName = cwpReporterName;
		this.dwpReportDate = dwpReportDate;
		this.cwpsendMaterialFlag = cwpsendMaterialFlag;
		this.cwpLxh = cwpLxh;
		this.cwpMtjs = cwpMtjs;
		this.cwpMpjs = cwpMpjs;
		this.cwpGzbm = cwpGzbm;
		this.cwpGzmc = cwpGzmc;
		this.cwpSbbm = cwpSbbm;
		this.cwpSbxh = cwpSbxh;
		this.cwpSbmc = cwpSbmc;
		this.cwpDy = cwpDy;
		this.cwpMaterialCode = cwpMaterialCode;
		this.cwpMaterialName = cwpMaterialName;
		this.cwpMaterialStd = cwpMaterialStd;
		this.cwpClph = cwpClph;
		this.cwpMpzl = cwpMpzl;
		this.cwpLlcc = cwpLlcc;
		this.cwpCllyl = cwpCllyl;
		this.cwpMz = cwpMz;
		this.cwpSjr = cwpSjr;
		this.cwpSjrq = cwpSjrq;
		this.cwpErpCode = cwpErpCode;
		this.cwpinventoryCode = cwpinventoryCode;
		this.cwpInventoryName = cwpInventoryName;
		this.cwpMhcode = cwpMhcode;
		this.fwpSubsidy = fwpSubsidy;
		this.bwpCommonProcedure = bwpCommonProcedure;
		this.cwpMemo = cwpMemo;
		this.fwpFree1 = fwpFree1;
		this.cwpFree2 = cwpFree2;
		this.bwpNotInUse = bwpNotInUse;
		this.fwpUnitQuantity = fwpUnitQuantity;
		this.cwpFinisherCode = cwpFinisherCode;
		this.cwpFinisherName = cwpFinisherName;
		this.cwpStatusFlag = cwpStatusFlag;
		this.cwpReciverName = cwpReciverName;
		this.bwpupdated = bwpupdated;
		this.cwpmergenStep = cwpmergenStep;
		this.cwpRelativePlanCode = cwpRelativePlanCode;
		this.cwpRePlanCode = cwpRePlanCode;
		this.cwpRePlanSource = cwpRePlanSource;
		this.bwpPartFinish = bwpPartFinish;
		this.bwpToolManaged = bwpToolManaged;
		this.fwpInCount = fwpInCount;
		this.dwpInDateTime = dwpInDateTime;
		this.dwpOutDateTime = dwpOutDateTime;
		this.bwpPlanNight = bwpPlanNight;
		this.cwpShr = cwpShr;
		this.cpucode = cpucode;
		this.cpuname = cpuname;
	}

	public Integer getIwpAutoId() {
		return iwpAutoId;
	}

	public void setIwpAutoId(Integer iwpAutoId) {
		this.iwpAutoId = iwpAutoId;
	}

	public String getCwpPstatusFlag() {
		return cwpPstatusFlag;
	}

	public void setCwpPstatusFlag(String cwpPstatusFlag) {
		this.cwpPstatusFlag = cwpPstatusFlag;
	}

	public String getCwpPlanCode() {
		return cwpPlanCode;
	}

	public void setCwpPlanCode(String cwpPlanCode) {
		this.cwpPlanCode = cwpPlanCode;
	}

	public String getCwpPartCode() {
		return cwpPartCode;
	}

	public void setCwpPartCode(String cwpPartCode) {
		this.cwpPartCode = cwpPartCode;
	}

	public String getCwpPartName() {
		return cwpPartName;
	}

	public void setCwpPartName(String cwpPartName) {
		this.cwpPartName = cwpPartName;
	}

	public Integer getCwpCode() {
		return cwpCode;
	}

	public void setCwpCode(Integer cwpCode) {
		this.cwpCode = cwpCode;
	}

	public String getCwpName() {
		return cwpName;
	}

	public void setCwpName(String cwpName) {
		this.cwpName = cwpName;
	}

	public String getCwpDepartmentCode() {
		return cwpDepartmentCode;
	}

	public void setCwpDepartmentCode(String cwpDepartmentCode) {
		this.cwpDepartmentCode = cwpDepartmentCode;
	}

	public String getCwpDepartmentName() {
		return cwpDepartmentName;
	}

	public void setCwpDepartmentName(String cwpDepartmentName) {
		this.cwpDepartmentName = cwpDepartmentName;
	}

	public String getDwpPlanBdate() {
		return dwpPlanBdate;
	}

	public void setDwpPlanBdate(String dwpPlanBdate) {
		this.dwpPlanBdate = dwpPlanBdate;
	}

	public String getDwpPlanEdate() {
		return dwpPlanEdate;
	}

	public void setDwpPlanEdate(String dwpPlanEdate) {
		this.dwpPlanEdate = dwpPlanEdate;
	}

	public Double getFwpQuantity() {
		return fwpQuantity;
	}

	public void setFwpQuantity(Double fwpQuantity) {
		this.fwpQuantity = fwpQuantity;
	}

	public Double getFwpZbgs() {
		return fwpZbgs;
	}

	public void setFwpZbgs(Double fwpZbgs) {
		this.fwpZbgs = fwpZbgs;
	}

	public Double getFwpRatioMh() {
		return fwpRatioMh;
	}

	public void setFwpRatioMh(Double fwpRatioMh) {
		this.fwpRatioMh = fwpRatioMh;
	}

	public Double getFwpMhtime() {
		return fwpMhtime;
	}

	public void setFwpMhtime(Double fwpMhtime) {
		this.fwpMhtime = fwpMhtime;
	}

	public String getCwpRemark() {
		return cwpRemark;
	}

	public void setCwpRemark(String cwpRemark) {
		this.cwpRemark = cwpRemark;
	}

	public Integer getIwpPctimes() {
		return iwpPctimes;
	}

	public void setIwpPctimes(Integer iwpPctimes) {
		this.iwpPctimes = iwpPctimes;
	}

	public String getCwpOutPlanType() {
		return cwpOutPlanType;
	}

	public void setCwpOutPlanType(String cwpOutPlanType) {
		this.cwpOutPlanType = cwpOutPlanType;
	}

	public String getCwpOutPlanCode() {
		return cwpOutPlanCode;
	}

	public void setCwpOutPlanCode(String cwpOutPlanCode) {
		this.cwpOutPlanCode = cwpOutPlanCode;
	}

	public String getDwpOutPlanEdate() {
		return dwpOutPlanEdate;
	}

	public void setDwpOutPlanEdate(String dwpOutPlanEdate) {
		this.dwpOutPlanEdate = dwpOutPlanEdate;
	}

	public String getCwpOutCompany() {
		return cwpOutCompany;
	}

	public void setCwpOutCompany(String cwpOutCompany) {
		this.cwpOutCompany = cwpOutCompany;
	}

	public String getCwpProductType() {
		return cwpProductType;
	}

	public void setCwpProductType(String cwpProductType) {
		this.cwpProductType = cwpProductType;
	}

	public String getCwpCntrType() {
		return cwpCntrType;
	}

	public void setCwpCntrType(String cwpCntrType) {
		this.cwpCntrType = cwpCntrType;
	}

	public String getCwpCntrNo() {
		return cwpCntrNo;
	}

	public void setCwpCntrNo(String cwpCntrNo) {
		this.cwpCntrNo = cwpCntrNo;
	}

	public String getCwpMakerCode() {
		return cwpMakerCode;
	}

	public void setCwpMakerCode(String cwpMakerCode) {
		this.cwpMakerCode = cwpMakerCode;
	}

	public String getCwpMakerName() {
		return cwpMakerName;
	}

	public void setCwpMakerName(String cwpMakerName) {
		this.cwpMakerName = cwpMakerName;
	}

	public String getDwpMakeDate() {
		return dwpMakeDate;
	}

	public void setDwpMakeDate(String dwpMakeDate) {
		this.dwpMakeDate = dwpMakeDate;
	}

	public Double getFwpPlanQuantity() {
		return fwpPlanQuantity;
	}

	public void setFwpPlanQuantity(Double fwpPlanQuantity) {
		this.fwpPlanQuantity = fwpPlanQuantity;
	}

	public Double getFwpFinishQuantity() {
		return fwpFinishQuantity;
	}

	public void setFwpFinishQuantity(Double fwpFinishQuantity) {
		this.fwpFinishQuantity = fwpFinishQuantity;
	}

	public Double getFwpFailedQuantity() {
		return fwpFailedQuantity;
	}

	public void setFwpFailedQuantity(Double fwpFailedQuantity) {
		this.fwpFailedQuantity = fwpFailedQuantity;
	}

	public Double getFwpActQuantity() {
		return fwpActQuantity;
	}

	public void setFwpActQuantity(Double fwpActQuantity) {
		this.fwpActQuantity = fwpActQuantity;
	}

	public Double getFwpActTime() {
		return fwpActTime;
	}

	public void setFwpActTime(Double fwpActTime) {
		this.fwpActTime = fwpActTime;
	}

	public Boolean getBwpPrint() {
		return bwpPrint;
	}

	public void setBwpPrint(Boolean bwpPrint) {
		this.bwpPrint = bwpPrint;
	}

	public Integer getIwpPrintQuantity() {
		return iwpPrintQuantity;
	}

	public void setIwpPrintQuantity(Integer iwpPrintQuantity) {
		this.iwpPrintQuantity = iwpPrintQuantity;
	}

	public String getCwpReoprterCode() {
		return cwpReoprterCode;
	}

	public void setCwpReoprterCode(String cwpReoprterCode) {
		this.cwpReoprterCode = cwpReoprterCode;
	}

	public String getCwpReporterName() {
		return cwpReporterName;
	}

	public void setCwpReporterName(String cwpReporterName) {
		this.cwpReporterName = cwpReporterName;
	}

	public String getDwpReportDate() {
		return dwpReportDate;
	}

	public void setDwpReportDate(String dwpReportDate) {
		this.dwpReportDate = dwpReportDate;
	}

	public String getCwpsendMaterialFlag() {
		return cwpsendMaterialFlag;
	}

	public void setCwpsendMaterialFlag(String cwpsendMaterialFlag) {
		this.cwpsendMaterialFlag = cwpsendMaterialFlag;
	}

	public String getCwpLxh() {
		return cwpLxh;
	}

	public void setCwpLxh(String cwpLxh) {
		this.cwpLxh = cwpLxh;
	}

	public String getCwpMtjs() {
		return cwpMtjs;
	}

	public void setCwpMtjs(String cwpMtjs) {
		this.cwpMtjs = cwpMtjs;
	}

	public String getCwpMpjs() {
		return cwpMpjs;
	}

	public void setCwpMpjs(String cwpMpjs) {
		this.cwpMpjs = cwpMpjs;
	}

	public String getCwpGzbm() {
		return cwpGzbm;
	}

	public void setCwpGzbm(String cwpGzbm) {
		this.cwpGzbm = cwpGzbm;
	}

	public String getCwpGzmc() {
		return cwpGzmc;
	}

	public void setCwpGzmc(String cwpGzmc) {
		this.cwpGzmc = cwpGzmc;
	}

	public String getCwpSbbm() {
		return cwpSbbm;
	}

	public void setCwpSbbm(String cwpSbbm) {
		this.cwpSbbm = cwpSbbm;
	}

	public String getCwpSbxh() {
		return cwpSbxh;
	}

	public void setCwpSbxh(String cwpSbxh) {
		this.cwpSbxh = cwpSbxh;
	}

	public String getCwpSbmc() {
		return cwpSbmc;
	}

	public void setCwpSbmc(String cwpSbmc) {
		this.cwpSbmc = cwpSbmc;
	}

	public String getCwpDy() {
		return cwpDy;
	}

	public void setCwpDy(String cwpDy) {
		this.cwpDy = cwpDy;
	}

	public String getCwpMaterialCode() {
		return cwpMaterialCode;
	}

	public void setCwpMaterialCode(String cwpMaterialCode) {
		this.cwpMaterialCode = cwpMaterialCode;
	}

	public String getCwpMaterialName() {
		return cwpMaterialName;
	}

	public void setCwpMaterialName(String cwpMaterialName) {
		this.cwpMaterialName = cwpMaterialName;
	}

	public String getCwpMaterialStd() {
		return cwpMaterialStd;
	}

	public void setCwpMaterialStd(String cwpMaterialStd) {
		this.cwpMaterialStd = cwpMaterialStd;
	}

	public String getCwpClph() {
		return cwpClph;
	}

	public void setCwpClph(String cwpClph) {
		this.cwpClph = cwpClph;
	}

	public String getCwpMpzl() {
		return cwpMpzl;
	}

	public void setCwpMpzl(String cwpMpzl) {
		this.cwpMpzl = cwpMpzl;
	}

	public String getCwpLlcc() {
		return cwpLlcc;
	}

	public void setCwpLlcc(String cwpLlcc) {
		this.cwpLlcc = cwpLlcc;
	}

	public String getCwpCllyl() {
		return cwpCllyl;
	}

	public void setCwpCllyl(String cwpCllyl) {
		this.cwpCllyl = cwpCllyl;
	}

	public String getCwpMz() {
		return cwpMz;
	}

	public void setCwpMz(String cwpMz) {
		this.cwpMz = cwpMz;
	}

	public String getCwpSjr() {
		return cwpSjr;
	}

	public void setCwpSjr(String cwpSjr) {
		this.cwpSjr = cwpSjr;
	}

	public String getCwpSjrq() {
		return cwpSjrq;
	}

	public void setCwpSjrq(String cwpSjrq) {
		this.cwpSjrq = cwpSjrq;
	}

	public String getCwpErpCode() {
		return cwpErpCode;
	}

	public void setCwpErpCode(String cwpErpCode) {
		this.cwpErpCode = cwpErpCode;
	}

	public String getCwpinventoryCode() {
		return cwpinventoryCode;
	}

	public void setCwpinventoryCode(String cwpinventoryCode) {
		this.cwpinventoryCode = cwpinventoryCode;
	}

	public String getCwpInventoryName() {
		return cwpInventoryName;
	}

	public void setCwpInventoryName(String cwpInventoryName) {
		this.cwpInventoryName = cwpInventoryName;
	}

	public String getCwpMhcode() {
		return cwpMhcode;
	}

	public void setCwpMhcode(String cwpMhcode) {
		this.cwpMhcode = cwpMhcode;
	}

	public Double getFwpSubsidy() {
		return fwpSubsidy;
	}

	public void setFwpSubsidy(Double fwpSubsidy) {
		this.fwpSubsidy = fwpSubsidy;
	}

	public Boolean getBwpCommonProcedure() {
		return bwpCommonProcedure;
	}

	public void setBwpCommonProcedure(Boolean bwpCommonProcedure) {
		this.bwpCommonProcedure = bwpCommonProcedure;
	}

	public String getCwpMemo() {
		return cwpMemo;
	}

	public void setCwpMemo(String cwpMemo) {
		this.cwpMemo = cwpMemo;
	}

	public Double getFwpFree1() {
		return fwpFree1;
	}

	public void setFwpFree1(Double fwpFree1) {
		this.fwpFree1 = fwpFree1;
	}

	public String getCwpFree2() {
		return cwpFree2;
	}

	public void setCwpFree2(String cwpFree2) {
		this.cwpFree2 = cwpFree2;
	}

	public Boolean getBwpNotInUse() {
		return bwpNotInUse;
	}

	public void setBwpNotInUse(Boolean bwpNotInUse) {
		this.bwpNotInUse = bwpNotInUse;
	}

	public Double getFwpUnitQuantity() {
		return fwpUnitQuantity;
	}

	public void setFwpUnitQuantity(Double fwpUnitQuantity) {
		this.fwpUnitQuantity = fwpUnitQuantity;
	}

	public String getCwpFinisherCode() {
		return cwpFinisherCode;
	}

	public void setCwpFinisherCode(String cwpFinisherCode) {
		this.cwpFinisherCode = cwpFinisherCode;
	}

	public String getCwpFinisherName() {
		return cwpFinisherName;
	}

	public void setCwpFinisherName(String cwpFinisherName) {
		this.cwpFinisherName = cwpFinisherName;
	}

	public String getCwpStatusFlag() {
		return cwpStatusFlag;
	}

	public void setCwpStatusFlag(String cwpStatusFlag) {
		this.cwpStatusFlag = cwpStatusFlag;
	}

	public String getCwpReciverName() {
		return cwpReciverName;
	}

	public void setCwpReciverName(String cwpReciverName) {
		this.cwpReciverName = cwpReciverName;
	}

	public Boolean getBwpupdated() {
		return bwpupdated;
	}

	public void setBwpupdated(Boolean bwpupdated) {
		this.bwpupdated = bwpupdated;
	}

	public String getCwpmergenStep() {
		return cwpmergenStep;
	}

	public void setCwpmergenStep(String cwpmergenStep) {
		this.cwpmergenStep = cwpmergenStep;
	}

	public String getCwpRelativePlanCode() {
		return cwpRelativePlanCode;
	}

	public void setCwpRelativePlanCode(String cwpRelativePlanCode) {
		this.cwpRelativePlanCode = cwpRelativePlanCode;
	}

	public String getCwpRePlanCode() {
		return cwpRePlanCode;
	}

	public void setCwpRePlanCode(String cwpRePlanCode) {
		this.cwpRePlanCode = cwpRePlanCode;
	}

	public String getCwpRePlanSource() {
		return cwpRePlanSource;
	}

	public void setCwpRePlanSource(String cwpRePlanSource) {
		this.cwpRePlanSource = cwpRePlanSource;
	}

	public Boolean getBwpPartFinish() {
		return bwpPartFinish;
	}

	public void setBwpPartFinish(Boolean bwpPartFinish) {
		this.bwpPartFinish = bwpPartFinish;
	}

	public Boolean getBwpToolManaged() {
		return bwpToolManaged;
	}

	public void setBwpToolManaged(Boolean bwpToolManaged) {
		this.bwpToolManaged = bwpToolManaged;
	}

	public Double getFwpInCount() {
		return fwpInCount;
	}

	public void setFwpInCount(Double fwpInCount) {
		this.fwpInCount = fwpInCount;
	}

	public String getDwpInDateTime() {
		return dwpInDateTime;
	}

	public void setDwpInDateTime(String dwpInDateTime) {
		this.dwpInDateTime = dwpInDateTime;
	}

	public String getDwpOutDateTime() {
		return dwpOutDateTime;
	}

	public void setDwpOutDateTime(String dwpOutDateTime) {
		this.dwpOutDateTime = dwpOutDateTime;
	}

	public Boolean getBwpPlanNight() {
		return bwpPlanNight;
	}

	public void setBwpPlanNight(Boolean bwpPlanNight) {
		this.bwpPlanNight = bwpPlanNight;
	}

	public String getCwpShr() {
		return cwpShr;
	}

	public void setCwpShr(String cwpShr) {
		this.cwpShr = cwpShr;
	}

	public String getCpucode() {
		return cpucode;
	}

	public void setCpucode(String cpucode) {
		this.cpucode = cpucode;
	}

	public String getCpuname() {
		return cpuname;
	}

	public void setCpuname(String cpuname) {
		this.cpuname = cpuname;
	}
}