package com.skcc.decamino;

import java.io.*;
import java.util.*;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.location.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.*;
import android.widget.*;

public class GeoCoding extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		Double longitude = intent.getExtras().getDouble("longitude");
		Double latitude = intent.getExtras().getDouble("latitude");	
	}
	
	public Address GetAddress(Context context, Double latitude, Double longitude){

		Geocoder mCoder = new Geocoder(context);	
		List<Address> addr;
		try {
			addr = mCoder.getFromLocation(latitude, longitude, 5);
			
			if (addr == null) {
				return null;
			}
			
			for(int i=0; i<addr.size(); i++){
				if(addr.get(i).getPostalCode() != null){
					return addr.get(i);
				}
			}
			
			return addr.get(0);			
		} catch (IOException e) {
			return null; 
		}
	}	
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
	}
}
