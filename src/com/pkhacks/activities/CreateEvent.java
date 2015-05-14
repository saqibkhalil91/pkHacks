package com.pkhacks.activities;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.pkhacks.adopters.EventAdopter;
import com.pkhacks.entities.PkHacksEvent;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateEvent extends Activity implements OnClickListener, OnItemSelectedListener {
	EditText eventName, city, startDate, endDate,url;
	Button createEvent;
	ArrayList<PkHacksEvent> eventList;
	private ImageView startDateImg, endDateImg;
	private DatePicker datePickerStartDate;
	static final int DATE_DIALOG_ID = 999;
	static final int DATE_DIALOG = 99;
	private int year;
	private int month;
	private int day;
	private boolean flag_checkDatePicker = false;
	int sum1 = 0;
	private int categoriesPosition=1;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		eventList = new ArrayList<PkHacksEvent>();
		eventList = (ArrayList<PkHacksEvent>) getIntent().getSerializableExtra(
				"eventList");
		setViews();
		setCurrentDateOnView();
		
	}

	private void setViews() {
		addItemsOnSpinner();
		eventName = (EditText) findViewById(R.id.edtEventName);
		city = (EditText) findViewById(R.id.edtCity);
		startDate = (EditText) findViewById(R.id.edtStartDate);
		endDate = (EditText) findViewById(R.id.edtEndDate);
		url = (EditText) findViewById(R.id.edtUrl);
		createEvent = (Button) findViewById(R.id.btnCreateEvent);
		createEvent.setOnClickListener(this);
		startDateImg = (ImageView) findViewById(R.id.btnStartDate);
		
		startDateImg.setOnClickListener(this);
		endDateImg = (ImageView) findViewById(R.id.btnEndDate);
		endDateImg.setOnClickListener(this);
		
	}

	public void setCurrentDateOnView() {

		// tvDisplayDate = (EditText) findViewById(R.id.tvDate);
		datePickerStartDate = (DatePicker) findViewById(R.id.dpStartDate);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		datePickerStartDate.init(year, month, day, null);

	}
	 public void addItemsOnSpinner() {
		 
			Spinner categories = (Spinner) findViewById(R.id.spinnerCategories);
			categories.setOnItemSelectedListener(this);
			List<String> list = new ArrayList<String>();
			list.add("Hackathons");
			list.add("Competitive Programming");
			list.add("Startup Weekends");
			list.add("Conferences");
			list.add("Tech Talks / Sessions");
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			categories.setAdapter(dataAdapter);
		  }
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			String fstDatepickerAns = new StringBuilder().append(day)
					.append(month + 1).append(year).toString();
		
			// set selected date into textview
			if (flag_checkDatePicker) {
				String strMonth=getMonthForInt(month);
				startDate.setText(new StringBuilder().append(day).append(" ").append(strMonth + 1)
						.append(" ").append(year)
						.append(" "));
			} else {
				endDate.setText(new StringBuilder().append(day).append(" ").append(month + 1)
						.append(" ").append(year)
						.append(" "));
			}
			// set selected date into datepicker alsoF
			datePickerStartDate.init(year, month, day, null);

		}
	};

	
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
		case DATE_DIALOG:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);

		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int key = v.getId();
		switch (key) {
		case R.id.btnStartDate:
			showDialog(DATE_DIALOG_ID);
			flag_checkDatePicker = true;

			break;
		case R.id.btnEndDate:
			showDialog(DATE_DIALOG_ID);
			flag_checkDatePicker = false;

			break;
		case R.id.btnCreateEvent:

			// Retrieve the text entered from the EditText
			String getEventName = eventName.getText().toString();
			String getCity = city.getText().toString();
			String getStartDate = startDate.getText().toString();
			String getEndDate = endDate.getText().toString();
			String geturl = url.getText().toString();
			// Force user to fill up the form
			if (getEventName.equals("") || getCity.equals("")
					|| getStartDate.equals("") ||getEndDate.equals("")) {
				Toast.makeText(getApplicationContext(),
						"Please complete the sign up form", Toast.LENGTH_LONG)
						.show();

			} else {
				// Save new user data into Parse.com Data Storage

				ParseObject Event = new ParseObject("Event");
				Event.put("event_name", getEventName);
				Event.put("City", getCity);
				Event.put("start_date", getStartDate);
				Event.put("end_date", getEndDate);
				Event.put("event_type_id", categoriesPosition);
				Event.put("link", geturl);
				 Event.saveInBackground();
				// Event.saveInBackground(new EventAdopter(a, resLocal,
				// pkevents));
				/*
				 * ParseUser user = new ParseUser();
				 * user.setUsername(getEventName); user.setPassword(getCity);
				 * user.setUsername(getStartDate); user.setPassword(getEndDate);
				 * 
				 * Event.signUpInBackground(new SignUpCallback() { public void
				 * done(ParseException e) { if (e == null) { // Show a simple
				 * Toast message upon successful registration
				 * Toast.makeText(getApplicationContext(),
				 * "Successfully Signed up, please log in.",
				 * Toast.LENGTH_LONG).show(); } else {
				 * Toast.makeText(getApplicationContext(), "Sign up Error",
				 * Toast.LENGTH_LONG) .show(); } } });
				 */
			}

			break;

		default:
			break;
		}

	}
	private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent slidingMenu = new Intent(this,SlidingMenuActivity.class);
		slidingMenu.putExtra("eventList", eventList);
		startActivity(slidingMenu);
		finish();
		super.onBackPressed();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		categoriesPosition=arg2+1;
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
