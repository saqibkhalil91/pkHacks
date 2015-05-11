package com.pkhacks.activities;

import java.util.ArrayList;









import com.pkhacks.asynctasks.EventAsyncTask;
import com.pkhacks.entities.PkHacksEvent;
import com.pkhacks.interfaces.EventListListener;
import com.pkhacks.utilities.ConnectionDetector;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	ArrayList<PkHacksEvent> eventList;
	TextView isLoading;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		eventList= new ArrayList<PkHacksEvent>();
		isLoading =(TextView)findViewById(R.id.textView1);
		ConnectionDetector cd = new ConnectionDetector(this);
		boolean checkingConnection =cd.isConnectingToInternet();
		if (checkingConnection==true)	{
			EventAsyncTask api =  new EventAsyncTask(this);
			isLoading.setText("PkHacks is loading");
			
			api.execute(null, null, null);
			api.setApiResulListener(new EventListListener() {
				
				@Override
				public void setList(ArrayList<PkHacksEvent> list) {
					// TODO Auto-generated method stub
					eventList=list;
					Intent slidingMenu = new Intent(getApplicationContext(),SlidingMenuActivity.class);
					slidingMenu.putExtra("eventList", eventList);
					startActivity(slidingMenu);
					finish();
					
				}
			});
			/*api.setApiResulListener(new ListListener() {
				
			

				private OffersAdopter adapter;

				@Override
				public void setList(ArrayList<Offer> list) {
					// TODO Auto-generated method stub
					adapter=new OffersAdopter( CustomListView,res,list );
					
			        lv.setAdapter(adapter);
				}
			});*/
		
			
		}
		else{
			internetNotAvailable();
			
		}
	}
	private void internetNotAvailable() {

	   	 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
	   	 helpBuilder.setTitle("Internet Issue");
	   	 helpBuilder.setMessage("Your internet is not available");
	   	
	   	 helpBuilder.setPositiveButton("Thank you",
	   	   new DialogInterface.OnClickListener() {
	   		
	   	    public void onClick(DialogInterface dialog, int which) {
	   	    	
	   	    	isLoading.setText("Internet is not available");
	   	    }
	   	   });
	   	 // Remember, create doesn't show the dialog
	   	 AlertDialog helpDialog = helpBuilder.create();
	   	 helpDialog.show();

	   	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
