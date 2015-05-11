package com.pkhacks.fragments;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ActionBar.LayoutParams;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Toast;

import com.pkhacks.activities.R;
import com.pkhacks.adopters.EventAdopter;
import com.pkhacks.entities.PkHacksEvent;

@SuppressLint({ "NewApi", "ValidFragment", "SimpleDateFormat" })
public class CategoriesListFragment extends Fragment {
	ArrayList<PkHacksEvent> eventList;
	public ArrayList<PkHacksEvent> filterList;
	public ArrayList<String> cityList;
	ListView lv;
	EventAdopter adopter;
	int pos = 0;
	private Spinner citySpinner;
	private String selectedCity;
	private ArrayList<PkHacksEvent> cityFilterList,dateFilterList;
	EditText startDate, endDate;
	Date startDateFromDatePicker,endDateFromDatePicker;
	private DatePicker datePickerStartDate;
	private boolean flag_checkDatePicker = false;
	private ArrayList<Date> startDateListValues,endDateListValues;

	@SuppressLint("ValidFragment")
	public CategoriesListFragment(ArrayList<PkHacksEvent> eventList, int pos) {
		// TODO Auto-generated constructor stub

		this.pos = pos;

		this.eventList = eventList;

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		lv = (ListView) rootView.findViewById(R.id.list);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		/*
		 * menutitles = getResources().getStringArray(R.array.titles); menuIcons
		 * = getResources().obtainTypedArray(R.array.icons);
		 * 
		 * rowItems = new ArrayList<EventRowItem>();
		 */

		filterList = new ArrayList<PkHacksEvent>();

		for (int i = 0; i < eventList.size(); i++) {
			if (eventList.get(i).getEventType() == pos) {
				filterList.add(eventList.get(i));

			}
		}
		try {
			adopter = new EventAdopter(getActivity(), getResources(),
					this.filterList);

			lv.setAdapter(adopter);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// setListAdapter(adopter);
		// getListView().setOnItemClickListener(this);

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		getActivity().getMenuInflater().inflate(R.menu.slidingmenu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	public void addItemsOnCitySpinner() {

		cityList = new ArrayList<String>();
		cityList.add("");
		for (int i = 0; i <filterList.size(); i++) {
			cityList.add(filterList.get(i).getCity());
		}
		cityList = removeDuplicates(cityList);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, cityList);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		citySpinner.setAdapter(dataAdapter);
		// }
	}

	private ArrayList<String> removeDuplicates(ArrayList<String> cityList) {
		/*
		 * Set<String> hs = new HashSet<String>(); hs.addAll(cityList);
		 * 
		 * cityList.clear(); cityList.addAll(hs)
		 */;
		int size = cityList.size();
		int duplicates = 0;

		// not using a method in the check also speeds up the execution
		// also i must be less that size-1 so that j doesn't
		// throw IndexOutOfBoundsException
		for (int i = 0; i < size - 1; i++) {
			// start from the next item after strings[i]
			// since the ones before are checked
			for (int j = i + 1; j < size; j++) {
				// no need for if ( i == j ) here

				if (!cityList.get(j).equalsIgnoreCase(cityList.get(i))
						&& !cityList.get(j).equalsIgnoreCase("-"))
					continue;
				duplicates++;
				cityList.remove(j);
				// decrease j because the array got re-indexed
				j--;
				// decrease the size of the array
				size--;
			} // for j
		} // for

		return cityList;
	}
	private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
            /**
             * Set Up Current Date Into dialog
             */
            Calendar calender = Calendar.getInstance();
            Bundle args = new Bundle();
            args.putInt("year", calender.get(Calendar.YEAR));
            args.putInt("month", calender.get(Calendar.MONTH));
            args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
            date.setArguments(args);
            /**
             * Set Call back to capture selected date
             */
            date.setCallBack(new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					String month=getMonthForInt(monthOfYear);
					
					if (flag_checkDatePicker) {
						startDate.setText(String.valueOf(dayOfMonth) + " " + month
				                 + " " + String.valueOf(year));
						try {
							
						 startDateFromDatePicker = getDateObject(startDate.getText().toString());
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} else {
						endDate.setText(String.valueOf(dayOfMonth) + " " + month
				                 + " " + String.valueOf(year));
						
					try {
						endDateFromDatePicker = getDateObject(endDate.getText().toString());
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}
			});
            date.show(getFragmentManager(), "Date Picker");
           }

          

  

	@SuppressLint("InflateParams")
	private void filter() {
		@SuppressWarnings("static-access")
		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);

		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.custom_filteroptions);
	

		citySpinner = (Spinner) dialog.findViewById(R.id.Sppinerbraches);

		citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				selectedCity = String.valueOf(citySpinner.getSelectedItem());
				// selectedCity.toUpperCase();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		addItemsOnCitySpinner();
		startDate = (EditText) dialog.findViewById(R.id.edtDateFrom);
		startDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flag_checkDatePicker = true;
				showDatePicker();
				
			}
		});

		endDate = (EditText) dialog.findViewById(R.id.edtdateTo);
		endDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flag_checkDatePicker = false;
				showDatePicker();
			}
		});

		Button btnDismiss = (Button) dialog.findViewById(R.id.dismiss);
		// datepickerfeildFrom.setText(fromDate);
		btnDismiss.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			//	getFilteredCityList();
				try {
					//getFilteredStartDateList()
					getFilteredListByCityDate();
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			System.out.print(startDate);
				adopter.updateDataList(dateFilterList);
				adopter.notifyDataSetChanged();

				dialog.dismiss();
			}
		});

		dialog.show();

	}
	private void getFilteredListByCityDate() throws java.text.ParseException
	{
		
		dateFilterList = new ArrayList<PkHacksEvent>();
			if(selectedCity!=null && !selectedCity.equals(""))
			{
				if(startDateFromDatePicker!=null)
				{
					if(endDateFromDatePicker!=null)
					{
						for (int i = 0; i < filterList.size(); i++) {
							if (filterList.get(i).getCity().equals(selectedCity)&& (getDateObject(filterList.get(i).getStartDate()).equals(startDateFromDatePicker)||getDateObject(filterList.get(i).getStartDate()).after(startDateFromDatePicker)) &&(getDateObject(filterList.get(i).getEndDate()).equals(endDateFromDatePicker)|| getDateObject(filterList.get(i).getEndDate()).before(endDateFromDatePicker))) {
								dateFilterList.add(filterList.get(i));
	
							}
							
						}
					}
					else
					{
						for (int i = 0; i < filterList.size(); i++) {
							if (filterList.get(i).getCity().equals(selectedCity)&& (getDateObject(filterList.get(i).getStartDate()).equals(startDateFromDatePicker)||getDateObject(filterList.get(i).getStartDate()).after(startDateFromDatePicker))) {
								dateFilterList.add(filterList.get(i));
	
							}
							
						}
					}
					
				}else if(endDateFromDatePicker!=null)
				{
					for (int i = 0; i < filterList.size(); i++) {
						if (filterList.get(i).getCity().equals(selectedCity) &&(getDateObject(filterList.get(i).getEndDate()).equals(endDateFromDatePicker)|| getDateObject(filterList.get(i).getEndDate()).before(endDateFromDatePicker))) {
							dateFilterList.add(filterList.get(i));

						}
						
					}
				}
				else
				{
					for (int i = 0; i < filterList.size(); i++) {
						if (filterList.get(i).getCity().equals(selectedCity)) {
							dateFilterList.add(filterList.get(i));

						}
						
					}
				}
			}
			else if(startDateFromDatePicker!=null){

				if(endDateFromDatePicker!=null)
				{
					for (int i = 0; i < filterList.size(); i++) {
						if ((getDateObject(filterList.get(i).getStartDate()).equals(startDateFromDatePicker)||getDateObject(filterList.get(i).getStartDate()).after(startDateFromDatePicker)) &&(getDateObject(filterList.get(i).getEndDate()).equals(endDateFromDatePicker)|| getDateObject(filterList.get(i).getEndDate()).before(endDateFromDatePicker))) {
							dateFilterList.add(filterList.get(i));

						}
						
					}
				}
				else
				{
					for (int i = 0; i < filterList.size(); i++) {
						if ( (getDateObject(filterList.get(i).getStartDate()).equals(startDateFromDatePicker)||getDateObject(filterList.get(i).getStartDate()).after(startDateFromDatePicker))) {
							dateFilterList.add(filterList.get(i));

						}
						
					}
				}
				
			
			}else if(endDateFromDatePicker!=null)
			{
				for (int i = 0; i < filterList.size(); i++) {
					if ((getDateObject(filterList.get(i).getEndDate()).equals(endDateFromDatePicker)|| getDateObject(filterList.get(i).getEndDate()).before(endDateFromDatePicker))) {
						dateFilterList.add(filterList.get(i));

					}
					
				}
			}else
			{
				for (int i = 0; i < filterList.size(); i++) {
					
						dateFilterList.add(filterList.get(i));

					
					
				}
				
			}
	}
	/**/
	private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
	private Date getDateObject(String strdate) throws java.text.ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		String dateInString = strdate;
	 
		
	 
			Date date = formatter.parse(dateInString);
			System.out.println(date);
			System.out.println(formatter.format(date));
			return date;
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_settings:
			filter();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		// return super.onOptionsItemSelected(item);
	}


}
