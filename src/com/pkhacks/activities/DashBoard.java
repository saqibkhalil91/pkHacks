package com.pkhacks.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.usage.UsageEvents.Event;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import com.entities.event.*;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
public class DashBoard extends Activity {
	private List<com.entities.event.Event> posts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_board);
		 posts = new ArrayList<com.entities.event.Event>();
		    ArrayAdapter<com.entities.event.Event> adapter = new ArrayAdapter<com.entities.event.Event>(this, R.layout.list_item_layout, posts);
		   //setListAdapter(adapter);
		 
		    refreshPostList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dash_board, menu);
		return true;
	}
	private void refreshPostList() {
		 
	    ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
	 
	    query.findInBackground(new FindCallback<ParseObject>() {
	 
	        @Override
	        public void done(List<ParseObject> postList, ParseException e) {
	            if (e == null) {
	                // If there are results, update the list of posts
	                // and notify the adapter
	                posts.clear();
	                for (ParseObject post : postList) {
	                	com.entities.event.Event event= new com.entities.event.Event();   ;//(post.getObjectId(), post.getString("title"), post.getString("content"));
	                	
	                	String eventName=post.getString("event_name");
	                	String eventCity =post.getString("City");
	                	String eventTypeId=post.getString("event_type_id");
	                	String start_date=post.getString("start_date");
	                	String end_date =post.getString("end_date");
	                	String link =post.getString("link");
	                	String event_Type_Id=post.getString("event_type_id");
	                	String event_id=post.getString("event_id");
	                	Log.d("eventName", eventName);
	                	Log.d("eventCity", eventCity);
	                	Log.d("eventTypeId", eventTypeId);
	                	Log.d("start_date", start_date);
	                	Log.d("end_date", end_date);
	                	Log.d("link", link);
	                	event.setCity(eventCity);
	                	event.setEventName(eventName);
	                	event.setStartDate(start_date);
	                	event.setEndDate(end_date);
	                	event.setLink(link);
	                    posts.add(event);
	                }
	               // ((ArrayAdapter<Note>) getListAdapter()).notifyDataSetChanged();
	            } else {
	                Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
	            }
	        }
	    });
	}
	

	
}
