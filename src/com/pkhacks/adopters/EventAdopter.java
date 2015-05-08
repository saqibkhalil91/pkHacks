package com.pkhacks.adopters;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkhacks.activities.R;
import com.pkhacks.entities.PkHacksEvent;

public class EventAdopter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater = null;
	viewHolder holder;
	PkHacksEvent tempValues = null;
	private List<PkHacksEvent> data;

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
	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		View view = paramView;
		viewHolder holder;

		if (paramView == null) {
			/******
			 * Inflate event_row_information.xml file for each row ( Defined
			 * below )
			 *******/
			view = inflater.inflate(R.layout.event_row_information, null);
			/****** View Holder Object to contain tabitem.xml file elements ******/
			holder = new viewHolder();
			holder.toggleImage = (ImageView) view
					.findViewById(R.id.Row_toggleimgView);
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
			holder.eventName.setText("No Data");
		}
		{
			/***** Get each Model object from Arraylist ********/
			tempValues = (PkHacksEvent) data.get(paramInt);
			/************ Set Model values in Holder elements ***********/
			holder.eventName.setText(tempValues.getEventName());
			holder.startDate.setText(tempValues.getStartDate());
			holder.endDate.setText(tempValues.getEndDate());
			holder.city.setText(tempValues.getCity());
			holder.website.setText(tempValues.getLink());
			holder.toggleImage.setImageResource(R.drawable.ic_home);

		}
		return view;

	}
	public void updateDataList(List<PkHacksEvent> data)
	{
		 this.data = data;
	}

	public class viewHolder {

		ImageView toggleImage;
		TextView eventName;
		TextView city;
		TextView website;
		TextView startDate;
		TextView endDate;

	}

}
