package com.fitbit.model;

import java.util.Date;

public class DailySleep 
{
//	private Date dateOfSleep ;
	private int totalMinutesAsleep ;
	
	private String datesleep;

	public String getDatesleep() {
		return datesleep;
	}

	public void setDatesleep(String datesleep) {
		this.datesleep = datesleep;
	}

	public int getTotalMinutesAsleep() {
		return totalMinutesAsleep;
	}

	public void setTotalMinutesAsleep(int totalMinutesAsleep) {
		this.totalMinutesAsleep = totalMinutesAsleep;
	}
	
}
