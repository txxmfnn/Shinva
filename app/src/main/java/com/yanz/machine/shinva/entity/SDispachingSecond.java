package com.yanz.machine.shinva.entity;





public class SDispachingSecond implements java.io.Serializable {

	// Fields

	private Integer iautoId;
	private String cplanCode;
	private String cpartCode;
	private String cpartName;
	private Integer igxh;
	private String cgxnr;
	private Double fquantity;
	private Double fmhtime;
	private Double ffinishQuantity;
	private Double ffailedQuantity;
	private String cfinisherCode;
	private String cfinisherName;
	private String dtPlanEdate;
	private String cmemo;
	private String cplannerCode;
	private String cplannerName;
	private String cstatusFlag;
	private String cdepartmentCode;
	private String cdepartmentName;
	private String cmakerCode;
	private String cmakerName;
	private String dtMakeDate;
	private Boolean bsubmitCheck;
	private String dtSubmitDate;
	private String creporterCode;
	private String creporterName;
	private String dtReportDate;
	private Double fpopFinishQuantity;
	private Double fpopFailedQuantity;
	private String cpopQuality;
	private String cboxNo;

	// Constructors

	/** default constructor */
	public SDispachingSecond() {
	}

	/** minimal constructor */
	public SDispachingSecond(Integer iautoId) {
		this.iautoId = iautoId;
	}

	/** full constructor */
	public SDispachingSecond(Integer iautoId, String cplanCode,
			String cpartCode, String cpartName, Integer igxh, String cgxnr,
			Double fquantity, Double fmhtime, Double ffinishQuantity,
			Double ffailedQuantity, String cfinisherCode, String cfinisherName,
			String dtPlanEdate, String cmemo, String cplannerCode,
			String cplannerName, String cstatusFlag, String cdepartmentCode,
			String cdepartmentName, String cmakerCode, String cmakerName,
			String dtMakeDate, Boolean bsubmitCheck, String dtSubmitDate,
			String creporterCode, String creporterName, String dtReportDate,
			Double fpopFinishQuantity, Double fpopFailedQuantity,
			String cpopQuality, String cboxNo) {
		this.iautoId = iautoId;
		this.cplanCode = cplanCode;
		this.cpartCode = cpartCode;
		this.cpartName = cpartName;
		this.igxh = igxh;
		this.cgxnr = cgxnr;
		this.fquantity = fquantity;
		this.fmhtime = fmhtime;
		this.ffinishQuantity = ffinishQuantity;
		this.ffailedQuantity = ffailedQuantity;
		this.cfinisherCode = cfinisherCode;
		this.cfinisherName = cfinisherName;
		this.dtPlanEdate = dtPlanEdate;
		this.cmemo = cmemo;
		this.cplannerCode = cplannerCode;
		this.cplannerName = cplannerName;
		this.cstatusFlag = cstatusFlag;
		this.cdepartmentCode = cdepartmentCode;
		this.cdepartmentName = cdepartmentName;
		this.cmakerCode = cmakerCode;
		this.cmakerName = cmakerName;
		this.dtMakeDate = dtMakeDate;
		this.bsubmitCheck = bsubmitCheck;
		this.dtSubmitDate = dtSubmitDate;
		this.creporterCode = creporterCode;
		this.creporterName = creporterName;
		this.dtReportDate = dtReportDate;
		this.fpopFinishQuantity = fpopFinishQuantity;
		this.fpopFailedQuantity = fpopFailedQuantity;
		this.cpopQuality = cpopQuality;
		this.cboxNo = cboxNo;
	}

	public Integer getIautoId() {
		return iautoId;
	}

	public void setIautoId(Integer iautoId) {
		this.iautoId = iautoId;
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

	public Double getFquantity() {
		return fquantity;
	}

	public void setFquantity(Double fquantity) {
		this.fquantity = fquantity;
	}

	public Double getFmhtime() {
		return fmhtime;
	}

	public void setFmhtime(Double fmhtime) {
		this.fmhtime = fmhtime;
	}

	public Double getFfinishQuantity() {
		return ffinishQuantity;
	}

	public void setFfinishQuantity(Double ffinishQuantity) {
		this.ffinishQuantity = ffinishQuantity;
	}

	public Double getFfailedQuantity() {
		return ffailedQuantity;
	}

	public void setFfailedQuantity(Double ffailedQuantity) {
		this.ffailedQuantity = ffailedQuantity;
	}

	public String getCfinisherCode() {
		return cfinisherCode;
	}

	public void setCfinisherCode(String cfinisherCode) {
		this.cfinisherCode = cfinisherCode;
	}

	public String getCfinisherName() {
		return cfinisherName;
	}

	public void setCfinisherName(String cfinisherName) {
		this.cfinisherName = cfinisherName;
	}

	public String getDtPlanEdate() {
		return dtPlanEdate;
	}

	public void setDtPlanEdate(String dtPlanEdate) {
		this.dtPlanEdate = dtPlanEdate;
	}

	public String getCmemo() {
		return cmemo;
	}

	public void setCmemo(String cmemo) {
		this.cmemo = cmemo;
	}

	public String getCplannerCode() {
		return cplannerCode;
	}

	public void setCplannerCode(String cplannerCode) {
		this.cplannerCode = cplannerCode;
	}

	public String getCplannerName() {
		return cplannerName;
	}

	public void setCplannerName(String cplannerName) {
		this.cplannerName = cplannerName;
	}

	public String getCstatusFlag() {
		return cstatusFlag;
	}

	public void setCstatusFlag(String cstatusFlag) {
		this.cstatusFlag = cstatusFlag;
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

	public String getCmakerCode() {
		return cmakerCode;
	}

	public void setCmakerCode(String cmakerCode) {
		this.cmakerCode = cmakerCode;
	}

	public String getCmakerName() {
		return cmakerName;
	}

	public void setCmakerName(String cmakerName) {
		this.cmakerName = cmakerName;
	}

	public String getDtMakeDate() {
		return dtMakeDate;
	}

	public void setDtMakeDate(String dtMakeDate) {
		this.dtMakeDate = dtMakeDate;
	}

	public Boolean getBsubmitCheck() {
		return bsubmitCheck;
	}

	public void setBsubmitCheck(Boolean bsubmitCheck) {
		this.bsubmitCheck = bsubmitCheck;
	}

	public String getDtSubmitDate() {
		return dtSubmitDate;
	}

	public void setDtSubmitDate(String dtSubmitDate) {
		this.dtSubmitDate = dtSubmitDate;
	}

	public String getCreporterCode() {
		return creporterCode;
	}

	public void setCreporterCode(String creporterCode) {
		this.creporterCode = creporterCode;
	}

	public String getCreporterName() {
		return creporterName;
	}

	public void setCreporterName(String creporterName) {
		this.creporterName = creporterName;
	}

	public String getDtReportDate() {
		return dtReportDate;
	}

	public void setDtReportDate(String dtReportDate) {
		this.dtReportDate = dtReportDate;
	}

	public Double getFpopFinishQuantity() {
		return fpopFinishQuantity;
	}

	public void setFpopFinishQuantity(Double fpopFinishQuantity) {
		this.fpopFinishQuantity = fpopFinishQuantity;
	}

	public Double getFpopFailedQuantity() {
		return fpopFailedQuantity;
	}

	public void setFpopFailedQuantity(Double fpopFailedQuantity) {
		this.fpopFailedQuantity = fpopFailedQuantity;
	}

	public String getCpopQuality() {
		return cpopQuality;
	}

	public void setCpopQuality(String cpopQuality) {
		this.cpopQuality = cpopQuality;
	}

	public String getCboxNo() {
		return cboxNo;
	}

	public void setCboxNo(String cboxNo) {
		this.cboxNo = cboxNo;
	}
}