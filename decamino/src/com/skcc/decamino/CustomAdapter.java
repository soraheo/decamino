package com.skcc.decamino;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Object> {
private ArrayList<LocationData> items;
	
	View v;
	public CustomAdapter(Context context, int textViewResourceId, ArrayList items){
		super(context, textViewResourceId, items);
		this.items = items;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub

		v = convertView;
		
		if(v == null)
		{
			LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listitem, null);
		}
		
		LocationData item = items.get(position);
		if(item != null)
		{
			TextView v_title = (TextView)v.findViewById(R.id.title);
			TextView v_description = (TextView)v.findViewById(R.id.description);
			TextView v_address = (TextView)v.findViewById(R.id.address);

			v_title.setText(item.title);
			v_description.setText("설명 : " + item.description);
			v_address.setText("주소 : " + item.address);
		}
		return v;
	}
}
