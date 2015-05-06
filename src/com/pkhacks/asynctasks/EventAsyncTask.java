package com.pkhacks.asynctasks;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pkhacks.entities.PkHacksEvent;
import com.pkhacks.interfaces.EventListListener;

public class EventAsyncTask extends AsyncTask<Void, Void, Void> {
	private ProgressDialog mProgressDialog;
	private Context context;
	private List<ParseObject> events;
	private ArrayList<PkHacksEvent> eventList;
	private String eventName, eventCity, start_date, end_date, link;
	private int eventTypeId, event_id;
	private EventListListener eventListner;

	public EventAsyncTask(Context context) {
		this.context = context;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Create a progressdialog
		mProgressDialog = new ProgressDialog(context);

		// Set progressdialog message
		mProgressDialog.setMessage("Loading...");
		mProgressDialog.setIndeterminate(false);
		// Show progressdialog
		mProgressDialog.show();
	}

	@Override
	protected Void doInBackground(Void... params) {
		// Locate the class table named "Country" in Parse.com
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Event");
		query.orderByDescending("_created_at");
		try {
			events = query.find();
		} catch (ParseException e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		/*
		 * // Locate the listview in listview_main.xml listview = (ListView)
		 * findViewById(R.id.listview); // Pass the results into an ArrayAdapter
		 * listview.setAdapter(adapter); adapter = new
		 * ArrayAdapter<String>(MainActivity.this, this is in loop
		 * adapter.add((String) Event.get("event_name"));
		 * R.layout.listview_item);
		 */
		// Retrieve object "name" from Parse.com database
		mProgressDialog.dismiss();
		this.eventList = new ArrayList<PkHacksEvent>();
		try {
			for (ParseObject event : events) {
				PkHacksEvent aEvent = new PkHacksEvent();
				aEvent.createObjectFromParsePbj(event);
				// event_id = Event.getInt("event_id");
				// eventTypeId = Event.getInt("event_type_id");

				eventList.add(aEvent);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		this.eventListner.setList(eventList);
		// Binds the Adapter to the ListView

		// Close the progressdialog

		// Capture button clicks on ListView items
		/*
		 * listview.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { // Send single item click data to
		 * SingleItemView Class Intent i = new Intent(MainActivity.this,
		 * SingleItemView.class); // Pass data "name" followed by the position
		 * i.putExtra("name", ob.get(position).getString("event_name")
		 * .toString()); // Open SingleItemView.java Activity startActivity(i);
		 * } });
		 */
	}

	public void setApiResulListener(EventListListener listner) {
		this.eventListner = listner;

	}

}
