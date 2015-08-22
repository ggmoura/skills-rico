package br.com.skills.ricointegration.vo;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resume {

	private Date updatetime;

	private List<Entrie> entries;

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public List<Entrie> getEntries() {
		return entries;
	}

	public void setEntries(List<Entrie> entries) {
		this.entries = entries;
	}

}
