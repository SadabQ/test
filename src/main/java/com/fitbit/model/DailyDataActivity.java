package com.fitbit.model;

import java.util.Date;

public class DailyDataActivity 
{
//	private Date dateOfSleep ;
	private int totalMinutesAsleep ;
	
	private int caloriesOut = 0;
	
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCaloriesOut() {
		return caloriesOut;
	}

	public void setCaloriesOut(int caloriesOut) {
		this.caloriesOut = caloriesOut;
	}

	public int getTotalMinutesAsleep() {
		return totalMinutesAsleep;
	}

	public void setTotalMinutesAsleep(int totalMinutesAsleep) {
		this.totalMinutesAsleep = totalMinutesAsleep;
	}
	
}
