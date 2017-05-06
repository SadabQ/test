/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fitbit;

import com.fitbit.model.Activity;
import com.fitbit.model.DailyActivities;
import com.fitbit.model.DailyDataActivity;
import com.fitbit.model.DailySleep;
import com.fitbit.model.LifetimeActivity;
import com.fitbit.model.Sleep;
import com.fitbit.model.Sleep2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
@EnableOAuth2Sso
@RestController
@EnableWebSecurity
public class FitbitOAuthExample extends WebSecurityConfigurerAdapter {

	@Autowired
	OAuth2RestTemplate fitbitOAuthRestTemplate;
	
	@Value("${fitbit.api.resource.activitiesUri}")
	String fitbitActivitiesUri;
	
	@Value("${fitbit.api.resource.dailySummaryUri}")
	String fitbitDailySummaryUri;
	
	@Value("${fitbit.api.resource.dailySleepUri}")
	String fitbitDailySleepUri;
	
	@Value("${fitbit.api.resource.dateRangeSleepUri}")
	String fitbitDateRangeSleepUri;

	@RequestMapping("/lifetime-activity")
	public LifetimeActivity lifetimeActivity() {
		LifetimeActivity lifetimeActivity;

		try {
			Activity a = fitbitOAuthRestTemplate.getForObject(fitbitActivitiesUri, Activity.class);
			lifetimeActivity = a.getLifetime().getTotal();
			}
		catch(Exception e) {
			lifetimeActivity = new LifetimeActivity();
		}

		return lifetimeActivity;
	}
	
	@RequestMapping("/testSleep")
	public Sleep2 checkRest()
	{	
		Sleep2 sl = new Sleep2();
		return sl;
		
	}
	
	@RequestMapping("/Date-Range-Sleep")
	public DailySleep dateRangeSleep(){
		DailySleep dateRangeSleep;
		try {
			Sleep d = fitbitOAuthRestTemplate.getForObject(fitbitDailySleepUri, Sleep.class);
			dateRangeSleep = d.getSummary();
			}
		catch(Exception e) {
			dateRangeSleep = new DailySleep();
			dateRangeSleep.setTotalMinutesAsleep(999);
			e.printStackTrace();
		}
		return dateRangeSleep;
	}
	
	@RequestMapping("/Daily-Summary")
	public DailyActivities dailyActivities(){
		DailyActivities dailyActivities;
		try {
			Activity a = fitbitOAuthRestTemplate.getForObject(fitbitDailySummaryUri, Activity.class);
			dailyActivities = a.getSummary();
			}
		catch(Exception e) {
			dailyActivities = new DailyActivities();
		}
		return dailyActivities;
	}
	
	@RequestMapping("/Daily-Sleep")
	public DailySleep dailySleep(){
		DailySleep dailySleep;
		try {
			Sleep s = fitbitOAuthRestTemplate.getForObject(fitbitDailySleepUri, Sleep.class);
			dailySleep = s.getSummary();
			
			/*String sl  = s.getSleep();
			
			System.out.println(sl);*/
			
			}
		catch(Exception e) {
			dailySleep = new DailySleep();
			dailySleep.setTotalMinutesAsleep(999);
			e.printStackTrace();
		}
		return dailySleep;
	}
	
	@RequestMapping("/dailyData")
	public DailyDataActivity dailyData(){
		DailySleep dailySleep;
//		LifetimeActivity lifetimeActivity;
		DailyDataActivity dailyDataActivity = new DailyDataActivity();
		try {
			
			Date date = new Date();
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -2);
			
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//			https://api.fitbit.com/1.2/user/-/sleep/date/2017-05-02.json
//			https://api.fitbit.com/1.2/user/-/sleep/date/2017-05-03.json
			String reqDate= format1.format(cal.getTime());
			String formatted = "https://api.fitbit.com/1.2/user/-/sleep/date/" 
					+ reqDate + ".json";
			//2017-05-03
			
			Sleep s = fitbitOAuthRestTemplate.getForObject(formatted, Sleep.class);
			dailySleep = s.getSummary();
			Activity a = fitbitOAuthRestTemplate.getForObject(fitbitDailySummaryUri, Activity.class);
//			lifetimeActivity = a.getLifetime().getTotal();
			DailyActivities dailyActivities= a.getSummary();
			dailyDataActivity.setDate(reqDate);
			dailyDataActivity.setCaloriesOut(dailyActivities.getCaloriesOut());
			dailyDataActivity.setTotalMinutesAsleep(dailySleep.getTotalMinutesAsleep());
			}
		catch(Exception e) {
			dailySleep = new DailySleep();
			dailySleep.setTotalMinutesAsleep(999);
			e.printStackTrace();
		}
		return dailyDataActivity;
	}
	
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll().anyRequest()
				.authenticated();
	}

	public static void main(String[] args) {
		SpringApplication.run(FitbitOAuthExample.class, args);
	}

}
