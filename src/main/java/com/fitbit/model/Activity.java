package com.fitbit.model;

public class Activity {

    private LifetimeActivities lifetime;
    private DailyActivities summary;
    public DailyActivities getSummary() {
		return summary;
	}

	public void setSummary(DailyActivities summary) {
		this.summary = summary;
	}

	public LifetimeActivities getLifetime() {
        return lifetime;
    }

    public void setLifetime(LifetimeActivities lifetime) {
        this.lifetime = lifetime;
    }
}
