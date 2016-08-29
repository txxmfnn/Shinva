package com.yanz.machine.shinva.entity;





public class SRdRecord implements java.io.Serializable {

	// Fields


	private String cplanCode;
	private String cwhCode;
	private String cwhName;
	private String ccode;
	private String cpartCode;
	private String cpartName;
	private String cpartStd;
	private String cposition;
	private Double fquantity;
	private Double fcurrentStock;
	private String ddate;
	private String capplyCode;

	public String getCapplyCode() {
		return capplyCode;
	}

	public void setCapplyCode(String capplyCode) {
		this.capplyCode = capplyCode;
	}

	public String getCpartStd() {
		return cpartStd;
	}

	public void setCpartStd(String cpartStd) {
		this.cpartStd = cpartStd;
	}

	private String cdepartmentCode;
	private String cdepartmentName;
	private String cpersonCode;
	private String cpersonName;
	private String cmakerCode;
	private String cmakerName;
	private String cmemo;

	// Constructors

	/** default constructor */
	public SRdRecord() {
	}

	public String getCplanCode() {
		return cplanCode;
	}

	public void setCplanCode(String cplanCode) {
		this.cplanCode = cplanCode;
	}

	public String getCwhCode() {
		return cwhCode;
	}

	public void setCwhCode(String cwhCode) {
		this.cwhCode = cwhCode;
	}

	public String getCwhName() {
		return cwhName;
	}

	public void setCwhName(String cwhName) {
		this.cwhName = cwhName;
	}

	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
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

	public String getCposition() {
		return cposition;
	}

	public void setCposition(String cposition) {
		this.cposition = cposition;
	}

	public Double getFquantity() {
		return fquantity;
	}

	public void setFquantity(Double fquantity) {
		this.fquantity = fquantity;
	}

	public Double getFcurrentStock() {
		return fcurrentStock;
	}

	public void setFcurrentStock(Double fcurrentStock) {
		this.fcurrentStock = fcurrentStock;
	}

	public String getDdate() {
		return ddate;
	}

	public void setDdate(String ddate) {
		this.ddate = ddate;
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

	public String getCpersonCode() {
		return cpersonCode;
	}

	public void setCpersonCode(String cpersonCode) {
		this.cpersonCode = cpersonCode;
	}

	public String getCpersonName() {
		return cpersonName;
	}

	public void setCpersonName(String cpersonName) {
		this.cpersonName = cpersonName;
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

	public String getCmemo() {
		return cmemo;
	}

	public void setCmemo(String cmemo) {
		this.cmemo = cmemo;
	}
}