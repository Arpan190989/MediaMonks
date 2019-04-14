package com.aem.core.mediamonks.core.bean;

import java.util.Calendar;

public class CanalDataBean implements Comparable<CanalDataBean>{

	private Integer waterHeight;

	private Calendar readingDate;
	
	public Integer getWaterHeight() {
		return waterHeight;
	}

	public void setWaterHeight(Integer waterHeight) {
		this.waterHeight = waterHeight;
	}

	public Calendar getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(Calendar readingDate) {
		this.readingDate = readingDate;
	}
	
	  public int compareTo(CanalDataBean o) {
	    return o.getReadingDate().compareTo(getReadingDate());
	  }
	  

	
}
