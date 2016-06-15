package com.yanz.machine.shinva.entity;


public class SLogisticsPlan implements java.io.Serializable {

	// Fields

	private Integer iautoId;
	private Integer ipdid;
	private String cplanCode;
	private Integer igxh;
	private String cstatusFlag;
	private String cpartCode;
	private String cpartName;
	private Double fquantity;
	private String creporterCode;
	private String creporterName;
	private String dtReportDate;
	private String cdepartmentCode;
	private String cdepartmentName;
	private String creciveDepartmentCode;
	private String creciveDepartmentName;
	private Double fdistance;
	private Double fmodulus;
	private Double fmz;
	private String cdeliverCode;
	private String cdeliverName;
	private String dtDeliveDate;
	private String creciverCode;
	private String creciverName;
	private String dtReciveDate;
	private String cplanerCode;
	private String cplanerName;
	private Double ftotalCount;
	private String cactReciveDepartmentCode;
	private String cactReciveDepartmentName;
	private String cboxNo;

	// Constructors

	/** default constructor */
	public SLogisticsPlan() {
	}

	/** minimal constructor */
	public SLogisticsPlan(Integer iautoId) {
		this.iautoId = iautoId;
	}

	/** full constructor */
	public SLogisticsPlan(Integer iautoId, Integer ipdid, String cplanCode,
			Integer igxh, String cstatusFlag, String cpartCode,
			String cpartName, Double fquantity, String creporterCode,
			String creporterName, String dtReportDate,
			String cdepartmentCode, String cdepartmentName,
			String creciveDepartmentCode, String creciveDepartmentName,
			Double fdistance, Double fmodulus, Double fmz, String cdeliverCode,
			String cdeliverName, String dtDeliveDate, String creciverCode,
			String creciverName, String dtReciveDate, String cplanerCode,
			String cplanerName, Double ftotalCount,
			String cactReciveDepartmentCode, String cactReciveDepartmentName,
			String cboxNo) {
		this.iautoId = iautoId;
		this.ipdid = ipdid;
		this.cplanCode = cplanCode;
		this.igxh = igxh;
		this.cstatusFlag = cstatusFlag;
		this.cpartCode = cpartCode;
		this.cpartName = cpartName;
		this.fquantity = fquantity;
		this.creporterCode = creporterCode;
		this.creporterName = creporterName;
		this.dtReportDate = dtReportDate;
		this.cdepartmentCode = cdepartmentCode;
		this.cdepartmentName = cdepartmentName;
		this.creciveDepartmentCode = creciveDepartmentCode;
		this.creciveDepartmentName = creciveDepartmentName;
		this.fdistance = fdistance;
		this.fmodulus = fmodulus;
		this.fmz = fmz;
		this.cdeliverCode = cdeliverCode;
		this.cdeliverName = cdeliverName;
		this.dtDeliveDate = dtDeliveDate;
		this.creciverCode = creciverCode;
		this.creciverName = creciverName;
		this.dtReciveDate = dtReciveDate;
		this.cplanerCode = cplanerCode;
		this.cplanerName = cplanerName;
		this.ftotalCount = ftotalCount;
		this.cactReciveDepartmentCode = cactReciveDepartmentCode;
		this.cactReciveDepartmentName = cactReciveDepartmentName;
		this.cboxNo = cboxNo;
	}

	public Integer getIautoId() {
		return iautoId;
	}

	public void setIautoId(Integer iautoId) {
		this.iautoId = iautoId;
	}

	public Integer getIpdid() {
		return ipdid;
	}

	public void setIpdid(Integer ipdid) {
		this.ipdid = ipdid;
	}

	public String getCplanCode() {
		return cplanCode;
	}

	public void setCplanCode(String cplanCode) {
		this.cplanCode = cplanCode;
	}

	public Integer getIgxh() {
		return igxh;
	}

	public void setIgxh(Integer igxh) {
		this.igxh = igxh;
	}

	public String getCstatusFlag() {
		return cstatusFlag;
	}

	public void setCstatusFlag(String cstatusFlag) {
		this.cstatusFlag = cstatusFlag;
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

	public Double getFquantity() {
		return fquantity;
	}

	public void setFquantity(Double fquantity) {
		this.fquantity = fquantity;
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

	public String getCreciveDepartmentCode() {
		return creciveDepartmentCode;
	}

	public void setCreciveDepartmentCode(String creciveDepartmentCode) {
		this.creciveDepartmentCode = creciveDepartmentCode;
	}

	public String getCreciveDepartmentName() {
		return creciveDepartmentName;
	}

	public void setCreciveDepartmentName(String creciveDepartmentName) {
		this.creciveDepartmentName = creciveDepartmentName;
	}

	public Double getFdistance() {
		return fdistance;
	}

	public void setFdistance(Double fdistance) {
		this.fdistance = fdistance;
	}

	public Double getFmodulus() {
		return fmodulus;
	}

	public void setFmodulus(Double fmodulus) {
		this.fmodulus = fmodulus;
	}

	public Double getFmz() {
		return fmz;
	}

	public void setFmz(Double fmz) {
		this.fmz = fmz;
	}

	public String getCdeliverCode() {
		return cdeliverCode;
	}

	public void setCdeliverCode(String cdeliverCode) {
		this.cdeliverCode = cdeliverCode;
	}

	public String getCdeliverName() {
		return cdeliverName;
	}

	public void setCdeliverName(String cdeliverName) {
		this.cdeliverName = cdeliverName;
	}

	public String getDtDeliveDate() {
		return dtDeliveDate;
	}

	public void setDtDeliveDate(String dtDeliveDate) {
		this.dtDeliveDate = dtDeliveDate;
	}

	public String getCreciverCode() {
		return creciverCode;
	}

	public void setCreciverCode(String creciverCode) {
		this.creciverCode = creciverCode;
	}

	public String getCreciverName() {
		return creciverName;
	}

	public void setCreciverName(String creciverName) {
		this.creciverName = creciverName;
	}

	public String getDtReciveDate() {
		return dtReciveDate;
	}

	public void setDtReciveDate(String dtReciveDate) {
		this.dtReciveDate = dtReciveDate;
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

	public Double getFtotalCount() {
		return ftotalCount;
	}

	public void setFtotalCount(Double ftotalCount) {
		this.ftotalCount = ftotalCount;
	}

	public String getCactReciveDepartmentCode() {
		return cactReciveDepartmentCode;
	}

	public void setCactReciveDepartmentCode(String cactReciveDepartmentCode) {
		this.cactReciveDepartmentCode = cactReciveDepartmentCode;
	}

	public String getCactReciveDepartmentName() {
		return cactReciveDepartmentName;
	}

	public void setCactReciveDepartmentName(String cactReciveDepartmentName) {
		this.cactReciveDepartmentName = cactReciveDepartmentName;
	}

	public String getCboxNo() {
		return cboxNo;
	}

	public void setCboxNo(String cboxNo) {
		this.cboxNo = cboxNo;
	}
}