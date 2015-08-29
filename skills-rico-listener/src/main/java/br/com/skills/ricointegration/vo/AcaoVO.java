package br.com.skills.ricointegration.vo;

import java.util.Date;
import java.util.List;

public class AcaoVO {

	private String p;

	private Date ut;

	private List<CorretoraVO> c;

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public Date getUt() {
		return ut;
	}

	public void setUt(Date ut) {
		this.ut = ut;
	}

	public List<CorretoraVO> getC() {
		return c;
	}

	public void setC(List<CorretoraVO> c) {
		this.c = c;
	}

}
