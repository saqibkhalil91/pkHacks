package com.pkhacks.activities;

import java.util.Calendar;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.pkhacks.adopters.EventAdopter;







import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateEvent extends Activity implements OnClickListener{
	EditText eventName,city,startDate,endDate;
	Button CreateEvent;
	private DatePicker datePickerStartDate;
	private ImageView startDateImg;
	private final int DATE_DIALOG_ID = 999;
	private final int DATE_DIALOG = 99;
	private int year;
	private int month;
	private int day;
	int sum1=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		setViews();
		setCurrentDateOnView();
		addListenerOnButton();
	}
	private void setViews()
	{
		eventName =(EditText)findViewById(R.id.edtEventName);
		city= (EditText)findViewById(R.id.edtCity);
		startDate=(EditText)findViewById(R.id.edtStartDate);
		endDate= (EditText)findViewById(R.id.edtEndDate);
		CreateEvent=(Button)findViewById(R.id.btnCreateEvent);
		CreateEvent.setOnClickListener(this);
	}
	public void addListenerOnButton() {

		startDateImg = (ImageView) findViewById(R.id.btnStartDate);

		startDateImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				showDialog(DATE_DIALOG_ID);
				

			}

		});

	}
	public void setCurrentDateOnView() {

		//tvDisplayDate = (EditText) findViewById(R.id.tvDate);
		datePickerStartDate = (DatePicker) findViewById(R.id.dpStartDate);

		
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);


		datePickerStartDate.init(year, month, day, null);

	}
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			String fstDatepickerAns = new StringBuilder().append(day).append(month+1).append(year).toString();
			calculations1(fstDatepickerAns);
			// set selected date into textview
			startDate.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

			// set selected date into datepicker also
			datePickerStartDate.init(year, month, day, null);

		}
	};
	public void calculations1(String a)
	{
		int sum = 0;char c;int bb=0;
		for(int i=0; i<a.length();i++){
			c= a.charAt(i);
			bb =Character.getNumericValue(c);
			sum = sum+bb;
		
		}
		sum1 = sum;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_event, menu);
		return true;
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
			
		
		}
		return null;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int key= v.getId();
		switch (key) {
		case R.id.btnStartDate:
			
			break;
		case R.id.btnCreateEvent:
		

			// Retrieve the text entered from the EditText
			String getEventName = eventName.getText().toString();
			String getCity = city.getText().toString();
			String getStartDate = startDate.getText().toString();
			String getEndDate = endDate.getText().toString();
			
			// Force user to fill up the form
			if (getEventName.equals("") && getCity.equals("") && getStartDate.equals("") && getEndDate.equals("")) {
				Toast.makeText(getApplicationContext(),
						"Please complete the sign up form",
						Toast.LENGTH_LONG).show();

			} else {
				// Save new user data into Parse.com Data Storage
				
				ParseObject Event = new ParseObject("Event");
				Event.put("event_name", getEventName);
				Event.put("City", getCity);
				Event.put("start_date", getStartDate);
				Event.put("end_date", getEndDate);
				//Event.saveInBackground();
			//	Event.saveInBackground(new EventAdopter(a, resLocal, pkevents));
		/*	
		  ParseUser user = new ParseUser();
				user.setUsername(getEventName);
				user.setPassword(getCity);
				user.setUsername(getStartDate);
				user.setPassword(getEndDate);
		 
				Event.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						if (e == null) {
							// Show a simple Toast message upon successful registration
							Toast.makeText(getApplicationContext(),
									"Successfully Signed up, please log in.",
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(getApplicationContext(),
									"Sign up Error", Toast.LENGTH_LONG)
									.show();
						}
					}
				});*/
			}

		
			
			break;

		default:
			break;
		}
		
		
	}

}
