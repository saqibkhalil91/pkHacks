package com.pkhacks.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.pkhacks.activities.R;
import com.pkhacks.adopters.EventAdopter;
import com.pkhacks.entities.PkHacksEvent;

public class HomeFragment extends Fragment implements OnItemClickListener {
	ArrayList<PkHacksEvent> eventList;
	ArrayList<PkHacksEvent> filterList;
	ListView lv;
	EventAdopter adopter;
	int pos = 0;

	public HomeFragment(ArrayList<PkHacksEvent> eventList, int pos) {
		// TODO Auto-generated constructor stub
		filterList = new ArrayList<PkHacksEvent>();
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
		for (int i = 0; i < eventList.size(); i++) {
			if (eventList.get(i).getEventType().equals(""+pos)) {
				filterList.add(eventList.get(i));
			}
		}

		adopter = new EventAdopter(getActivity(), getResources(),
				this.filterList);
		lv.setAdapter(adopter);
		// setListAdapter(adopter);
		// getListView().setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	/*
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) {
	 * 
	 * Toast.makeText(getActivity(), menutitles[position], Toast.LENGTH_SHORT)
	 * .show();
	 * 
	 * }
	 */

}
