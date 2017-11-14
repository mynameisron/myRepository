package com.company.study.domain;
 
public class Dictionary {
     
	private int dicId;
	private String reqCont;
	private String ansType;
	private String ansCont;
	private String ansApp;
	private String regDttm;

	public int getDicId() {
		return dicId;
	}

	public void setDicId(int dicId) {
		this.dicId = dicId;
	}

	public String getReqCont() {
		return reqCont;
	}

	public void setReqCont(String reqCont) {
		this.reqCont = reqCont;
	}

	public String getAnsType() {
		return ansType;
	}

	public void setAnsType(String ansType) {
		this.ansType = ansType;
	}

	public String getAnsCont() {
		return ansCont;
	}

	public void setAnsCont(String ansCont) {
		this.ansCont = ansCont;
	}

	public String getAnsApp() {
		return ansApp;
	}

	public void setAnsApp(String ansApp) {
		this.ansApp = ansApp;
	}

	public String getRegDttm() {
		return regDttm;
	}

	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}
     
}
