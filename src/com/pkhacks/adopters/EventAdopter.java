package com.pkhacks.adopters;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkhacks.activities.R;
import com.pkhacks.activities.WebViewActivity;
import com.pkhacks.entities.PkHacksEvent;
import com.pkhacks.fragments.CategoriesListFragment;


public class EventAdopter extends BaseAdapter  {

	private Context mContext;
	private LayoutInflater inflater = null;
	viewHolder holder;
	PkHacksEvent tempValues = null;
	private List<PkHacksEvent> data;
	private Calendar cal;

	public EventAdopter(Context a, Resources resLocal,
			List<PkHacksEvent> pkevents) {
		/********** Take passed values **********/
		mContext = a;
		this.data = pkevents;
		holder = new viewHolder();
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (data.size() <= 0) {
			return 1;
		} else {
			return data.size();
		}
	}

	@Override
	public Object getItem(int paramInt) {
		// TODO Auto-generated method stub
		return this.data.get(paramInt);
	}

	@Override
	public long getItemId(int paramInt) {
		// TODO Auto-generated method stub
		return paramInt;
	}

	@Override
	public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup) {
		View view = paramView;
		//viewHolder holder;

		if (paramView == null) {
			/******
			 * Inflate event_row_information.xml file for each row ( Defined
			 * below )
			 *******/
			view = inflater.inflate(R.layout.event_row_information, null);
			/****** View Holder Object to contain tabitem.xml file elements ******/
			holder = new viewHolder();
			holder.toggleImage = (CheckBox) view
					.findViewById(R.id.row_toggleimgView);
			holder.eventName = (TextView) view.findViewById(R.id.row_eventName);
			holder.startDate = (TextView) view.findViewById(R.id.row_startTime);
			holder.endDate = (TextView) view.findViewById(R.id.row_endTime);
			holder.city = (TextView) view.findViewById(R.id.row_cityName);
			holder.website = (TextView) view.findViewById(R.id.row_website);
			/************ Set holder with LayoutInflater ************/
			view.setTag(holder);
		} else
			holder = (viewHolder) view.getTag();
		if (null == data || data.size() == 0) {
			holder.eventName.setText("Record not found");
			holder.startDate.setText("");
			holder.endDate.setText("");
			holder.city.setText("");
			holder.website.setText("");
			holder.toggleImage.setVisibility(view.INVISIBLE);
		}
		else{
			/***** Get each Model object from Arraylist ********/
			tempValues = (PkHacksEvent) data.get(paramInt);
			/************ Set Model values in Holder elements ***********/
			holder.eventName.setText(tempValues.getEventName());
			holder.startDate.setText(tempValues.getStartDate());
			holder.endDate.setText(tempValues.getEndDate());
			holder.city.setText(tempValues.getCity());
			holder.website.setText("");
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					 Intent intent = new Intent(mContext, WebViewActivity.class);
					 intent.putExtra("url", data.get(paramInt).getLink());
						
						mContext.startActivity(intent);
				}
			});
			holder.toggleImage.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(isChecked)
					{
						try {
							Log.d("ok done", "done");
							setReminder();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			});
			
		}
		return view;

	}
	public void updateDataList(List<PkHacksEvent> data)
	{
		 this.data = data;
	}

	public class viewHolder {

		CheckBox toggleImage;
		TextView eventName;
		TextView city;
		TextView website;
		TextView startDate;
		TextView endDate;

	}
	private void setReminder() throws ParseException
	{
		 cal = Calendar.getInstance();  
		Intent intent = new Intent(Intent.ACTION_EDIT);
		intent.setType("vnd.android.cursor.item/event");
		//String startMonth=getMonthForInt(holder.startDate.getText());
		intent.putExtra("eventName", holder.eventName.getText().toString());
		intent.putExtra("startDate", convertDateToMiliSeconds( holder.startDate.getText().toString()).getTime());
		intent.putExtra("endDate",convertDateToMiliSeconds( holder.endDate.getText().toString()).getTime());
		intent.putExtra("city", holder.city.getText().toString());
		intent.putExtra("website", holder.website.getText().toString());
		mContext.startActivity(intent);
	}
	
	@SuppressLint("SimpleDateFormat")
	private Date convertDateToMiliSeconds(String strDate) throws ParseException
	{
	

		String someDate = strDate;
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		Date date = sdf.parse(someDate);
		return date;
	
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
	

	

}
