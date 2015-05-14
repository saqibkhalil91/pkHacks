package com.pkhacks.adopters;

import java.util.List;

import com.pkhacks.activities.R;
import com.pkhacks.entities.EventRowItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavAdopter extends BaseAdapter {

	Context context;
	List<EventRowItem> rowItem;

	public NavAdopter(Context context, List<EventRowItem> rowItem) {
		this.context = context;
		this.rowItem = rowItem;

	}

	@Override
	public int getCount() {

		return rowItem.size();
	}

	@Override
	public Object getItem(int position) {

		return rowItem.get(position);
	}

	@Override
	public long getItemId(int position) {

		return rowItem.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_item, null);
		}

		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

		EventRowItem row_pos = rowItem.get(position);
		// setting the image resource and title
		imgIcon.setImageResource(row_pos.getIcon());
		txtTitle.setText(row_pos.getTitle());

		return convertView;

	}

}
