package com.pkhacks.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;

@SuppressLint("NewApi")
public class DatePickerFragment extends DialogFragment
implements DatePickerDialog.OnDateSetListener {
	  OnDateSetListener ondateSet;

	     public DatePickerFragment() {
	     }

	     public void setCallBack(OnDateSetListener ondate) {
	      ondateSet = ondate;
	     }

	     private int year, month, day;

	     @SuppressLint("NewApi")
	    @Override
	     public void setArguments(Bundle args) {
	      super.setArguments(args);
	      year = args.getInt("year");
	      month = args.getInt("month");
	      day = args.getInt("day");
	     }

	     @Override
	     public Dialog onCreateDialog(Bundle savedInstanceState) {
	      return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
	     }

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			
		}
}
