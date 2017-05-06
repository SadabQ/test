package com.fitbit.model;

/**
 * @author athar
 *
 */
public class DailyActivities 
{
	private int caloriesOut = 0;
	private int activityCalories = 0;
	private int caloriesBMR = 0;
	
	public int getActivityCalories() {
		return activityCalories;
	}

	public void setActivityCalories(int activityCalories) {
		this.activityCalories = activityCalories;
	}

	public int getCaloriesBMR() {
		return caloriesBMR;
	}

	public void setCaloriesBMR(int caloriesBMR) {
		this.caloriesBMR = caloriesBMR;
	}

	public int getCaloriesOut() {
		return caloriesOut;
	}

	public void setCaloriesOut(int caloriesOut) {
		this.caloriesOut = caloriesOut;
	}

	
	
}
