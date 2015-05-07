package com.pkhacks.activities;

import java.util.ArrayList;
import java.util.List;

import com.pkhacks.adopters.NavDrawerListAdapter;
import com.pkhacks.entities.NavDrawerItem;
import com.pkhacks.entities.PkHacksEvent;

import com.pkhacks.fragments.HomeFragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ActionBar.LayoutParams;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;

public class SlidingMenuActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	ArrayList<PkHacksEvent> eventList;
	private Spinner citySpinner;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@SuppressWarnings("unchecked")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidingmenuactivity);
		eventList = new ArrayList<PkHacksEvent>();
		eventList = (ArrayList<PkHacksEvent>) getIntent().getSerializableExtra(
				"eventList");
		
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(2, -1)));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		/*
		 * navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
		 * .getResourceId(2, -1)));
		 */
		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	/*public void addItemsOnCitySpinner() {

		List<String> list = new ArrayList<String>();
		list.add("list 1");
		list.add("list 2");
		list.add("list 3");
		//ArrayList<PkHacksEvent> data = fragment.filterList;
		//
		//	Log.d("slidingevent naem", data.get(0).getCity());
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			citySpinner.setAdapter(dataAdapter);
		//}
	}*/

	/*@SuppressLint("InflateParams")
	private void filter() {
		LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View filterView = layoutInflater.inflate(R.layout.custom_filteroptions,
				null);
		final PopupWindow popupWindow = new PopupWindow(filterView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		citySpinner = (Spinner) filterView.findViewById(R.id.Sppinerbraches);
		citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		
		addItemsOnCitySpinner();
		
		 * final Spinner
		 * spiner_projects=(Spinner)popupView.findViewById(R.id.SppinerProjects
		 * ); final Spinner
		 * spiner_projects=(Spinner)popupView.findViewById(R.id.
		 * SppinerProjects); //
		 * popupWindow.showAtLocation(R.layout.custom_filteroptions,
		 * Gravity.NO_GRAVITY,35 + 50,20 + 50); datepickerfeildTodate =
		 * (EditText)popupView.findViewById(R.id.edtdateTo); final Spinner
		 * spiner_brnacher=(Spinner)popupView.findViewById(R.id.Sppinerbraches);
		 * final Spinner
		 * spiner_projects=(Spinner)popupView.findViewById(R.id.SppinerProjects
		 * ); datepickerfeildFrom =
		 * (EditText)popupView.findViewById(R.id.edtDateFrom);
		 
		Button btnDismiss = (Button) filterView.findViewById(R.id.dismiss);
		// datepickerfeildFrom.setText(fromDate);
		btnDismiss.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				popupWindow.dismiss();
			}
		});

		// datefrom.setOnClickListener(this);
		
		 * datepickerfeildFrom.setOnClickListener(new OnClickListener(){
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub flag=true; mydatePicker(); //
		 * datepickerfeildFrom.setText(fromDate); }});
		 * 
		 * 
		 * datepickerfeildTodate.setOnClickListener(new OnClickListener(){
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub flag =false; mydatePicker(); //
		 * datepickerfeildFrom.setText(fromDate); }});
		 
		popupWindow.showAtLocation(btnDismiss, 100, 100, 100);

	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.slidingmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return false;
		}
		// Handle action bar actions click bn
		switch (item.getItemId()) {
		case R.id.action_settings:
			//filter();
			return false;
		default:
			return false;
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	HomeFragment fragment = null;
	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		
		fragment = new HomeFragment(eventList, position);
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
