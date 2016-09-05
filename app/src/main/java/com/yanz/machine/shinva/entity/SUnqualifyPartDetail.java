package com.yanz.machine.shinva.entity;

import java.io.Serializable;

/**
 * Created by yanz on 2016-09-01.
 */
public class SUnqualifyPartDetail implements Serializable {
    private Integer iautoid;
    private String cwlcode;
    private String cplanCode;
    private String cunqualifyDealResult;
    private String cunQualifyContent;
    private String cdealSuggest;
    private String ccorrectAction;
    private String cdealAdvise;

    public Integer getIautoid() {
        return iautoid;
    }

    public void setIautoid(Integer iautoid) {
        this.iautoid = iautoid;
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

    public String getCunqualifyDealResult() {
        return cunqualifyDealResult;
    }

    public void setCunqualifyDealResult(String cunqualifyDealResult) {
        this.cunqualifyDealResult = cunqualifyDealResult;
    }

    public String getCunQualifyContent() {
        return cunQualifyContent;
    }

    public void setCunQualifyContent(String cunQualifyContent) {
        this.cunQualifyContent = cunQualifyContent;
    }

    public String getCdealSuggest() {
        return cdealSuggest;
    }

    public void setCdealSuggest(String cdealSuggest) {
        this.cdealSuggest = cdealSuggest;
    }

    public String getCcorrectAction() {
        return ccorrectAction;
    }

    public void setCcorrectAction(String ccorrectAction) {
        this.ccorrectAction = ccorrectAction;
    }

    public String getCdealAdvise() {
        return cdealAdvise;
    }

    public void setCdealAdvise(String cdealAdvise) {
        this.cdealAdvise = cdealAdvise;
    }
}
