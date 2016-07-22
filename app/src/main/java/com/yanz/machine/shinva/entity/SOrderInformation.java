package com.yanz.machine.shinva.entity;

public class SOrderInformation implements java.io.Serializable {

	// Fields


	private String corderType;
	private String corderCode;
	private String ccustomerCode;
	private String ccustomerName;
	private String cfactoryCode;
	private String cfactoryName;
	private String cpartCode;
	private String cpartName;
	private Double fquantity;
	private String dtDeliveryDate;
	private String cmakeMemo;
	private String cpaperDrawing;
	private String cmakerName;
	private String dtMakeDate;
	private String dtSubmitDate;
	private String cauditerName;
	private String dtAuditDate;
	private String cauditMemo;
	private Double ffinishQuantity;
	private Integer iflowFlag;
	private Integer iworkNeedDays;
	private Integer ideliveryRemainDays;
	private String dtPlanEdate;

	public String getDtPlanEdate() {
		return dtPlanEdate;
	}

	public void setDtPlanEdate(String dtPlanEdate) {
		this.dtPlanEdate = dtPlanEdate;
	}

	public Integer getIworkNeedDays() {
		return iworkNeedDays;
	}

	public void setIworkNeedDays(Integer iworkNeedDays) {
		this.iworkNeedDays = iworkNeedDays;
	}

	public Integer getIdeliveryRemainDays() {
		return ideliveryRemainDays;
	}

	public void setIdeliveryRemainDays(Integer ideliveryRemainDays) {
		this.ideliveryRemainDays = ideliveryRemainDays;
	}
	// Constructors

	/** default constructor */
	public SOrderInformation() {
	}

	public String getCorderType() {
		return corderType;
	}

	public void setCorderType(String corderType) {
		this.corderType = corderType;
	}

	public String getCorderCode() {
		return corderCode;
	}

	public void setCorderCode(String corderCode) {
		this.corderCode = corderCode;
	}

	public String getCcustomerCode() {
		return ccustomerCode;
	}

	public void setCcustomerCode(String ccustomerCode) {
		this.ccustomerCode = ccustomerCode;
	}

	public String getCcustomerName() {
		return ccustomerName;
	}

	public void setCcustomerName(String ccustomerName) {
		this.ccustomerName = ccustomerName;
	}

	public String getCfactoryCode() {
		return cfactoryCode;
	}

	public void setCfactoryCode(String cfactoryCode) {
		this.cfactoryCode = cfactoryCode;
	}

	public String getCfactoryName() {
		return cfactoryName;
	}

	public void setCfactoryName(String cfactoryName) {
		this.cfactoryName = cfactoryName;
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

	public String getDtDeliveryDate() {
		return dtDeliveryDate;
	}

	public void setDtDeliveryDate(String dtDeliveryDate) {
		this.dtDeliveryDate = dtDeliveryDate;
	}

	public String getCmakeMemo() {
		return cmakeMemo;
	}

	public void setCmakeMemo(String cmakeMemo) {
		this.cmakeMemo = cmakeMemo;
	}

	public String getCpaperDrawing() {
		return cpaperDrawing;
	}

	public void setCpaperDrawing(String cpaperDrawing) {
		this.cpaperDrawing = cpaperDrawing;
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

	public String getDtSubmitDate() {
		return dtSubmitDate;
	}

	public void setDtSubmitDate(String dtSubmitDate) {
		this.dtSubmitDate = dtSubmitDate;
	}

	public String getCauditerName() {
		return cauditerName;
	}

	public void setCauditerName(String cauditerName) {
		this.cauditerName = cauditerName;
	}

	public String getDtAuditDate() {
		return dtAuditDate;
	}

	public void setDtAuditDate(String dtAuditDate) {
		this.dtAuditDate = dtAuditDate;
	}

	public String getCauditMemo() {
		return cauditMemo;
	}

	public void setCauditMemo(String cauditMemo) {
		this.cauditMemo = cauditMemo;
	}

	public Double getFfinishQuantity() {
		return ffinishQuantity;
	}

	public void setFfinishQuantity(Double ffinishQuantity) {
		this.ffinishQuantity = ffinishQuantity;
	}

	public Integer getIflowFlag() {
		return iflowFlag;
	}

	public void setIflowFlag(Integer iflowFlag) {
		this.iflowFlag = iflowFlag;
	}
}