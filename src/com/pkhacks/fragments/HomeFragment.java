package com.pkhacks.fragments;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

import com.pkhacks.activities.R;
import com.pkhacks.adopters.EventAdopter;
import com.pkhacks.entities.PkHacksEvent;

public class HomeFragment extends Fragment   {
	ArrayList<PkHacksEvent> eventList;
	public ArrayList<PkHacksEvent> filterList;
	ListView lv;
	EventAdopter adopter;
	int pos=1;
	private Spinner citySpinner;
	public HomeFragment(ArrayList<PkHacksEvent> eventList, int pos) {
		// TODO Auto-generated constructor stub
		if(pos!=0)
		{
			this.pos = pos;
		}else{
			pos= 1;
		}
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
			if (eventList.get(i).getEventType()==pos) {
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

		List<String> list = new ArrayList<String>();
		list.add("list 1");
		list.add("list 2");
		list.add("list 3");
		//ArrayList<PkHacksEvent> data = fragment.filterList;
		//
		//	Log.d("slidingevent naem", data.get(0).getCity());
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			citySpinner.setAdapter(dataAdapter);
		//}
	}

	@SuppressLint("InflateParams")
	private void filter() {
		@SuppressWarnings("static-access")
		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
		/*View filterView = layoutInflater.inflate(R.layout.custom_filteroptions,
				null);*/
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.custom_filteroptions);
		/*final Dialog popupWindow = new PopupWindow(filterView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);*/
		citySpinner = (Spinner) dialog.findViewById(R.id.Sppinerbraches);
		
	/*	citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		*/
		addItemsOnCitySpinner();
		
		 
		Button btnDismiss = (Button) dialog.findViewById(R.id.dismiss);
		// datepickerfeildFrom.setText(fromDate);
		btnDismiss.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				dialog.dismiss();
			}
		});

		
		 
		dialog.show();

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
	//	return super.onOptionsItemSelected(item);
	}




	

	
}
