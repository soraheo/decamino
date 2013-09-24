package com.skcc.decamino;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import android.app.Activity;
import android.view.Menu;
import android.widget.AdapterView.OnItemClickListener;

public class SearchLocation extends Activity {

/** Called when the activity is first created. */
	
	final int MENU_back=Menu.FIRST;    
	final int MENU_forward=Menu.FIRST+1;
	
	final static String NAVER_API_KEY = "d7037a44135829aa4b4aec8d8f3e8dc8";
	private String target = "local";
	
	private ProgressDialog dialog;
	
	private ListView myList;
	private NaverParser Nparser;
	private CustomAdapter adapter;
	
	ArrayList<LocationData> items;
	private String info;
	
	final int count=11;
	int start=1;
	
	private final Handler handler=new Handler(){
    	public void handleMessage(final Message msg){
    		dialog.dismiss();
    		
    		adapter = new CustomAdapter(SearchLocation.this, R.layout.listitem, items); //adapter 생성
    		myList.setAdapter(adapter);
    		myList.setOnItemClickListener(mItemClickListener);
    	}
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
                
        Intent intent = getIntent(); // 검색창에 입력한 정보
	    Bundle myBundle = intent.getExtras(); // 번들을  이용하여 얻어옴
		info = myBundle.getString("keyword");
		
		Log.i(this.getClass().getName(), info);
		
		myList = (ListView)findViewById(R.id.listview);
		Nparser = new NaverParser(NAVER_API_KEY, target);
		
		getNewList(info, count, start);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_back, 0, "이전 페이지로");
		menu.add(0, MENU_forward, 1, "다음 페이지로");
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case MENU_back:{
			if(start <= 11)
			{
				Toast.makeText(this, "이전 페이지가 없습니다", Toast.LENGTH_SHORT).show();
			}
			else
			{
				start-=11;
				getNewList(info,count,start);
			}
			break;
		}
		case MENU_forward:{
			start+=11;
			getNewList(info,count,start);
			break;
		}
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
    public void getNewList(final String inform, final int count, final int starts){    	
		dialog = ProgressDialog.show(this, "Loading..", "리스트를 불러옵니다",true,false);
		new Thread(){
			public void run(){
				items = Nparser.getBookData(inform, count, starts); // 네이버 api를 이용해 정보를 얻어옴
				handler.sendEmptyMessage(0);
			}
		}.start();
	}
    
    AdapterView.OnItemClickListener mItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View view, int position, long id){
						
			HashMap<String, String> hsItem = new HashMap<String, String>();;
			
			hsItem.put("mapx", items.get(position).mapx);
			hsItem.put("mapy", items.get(position).mapy);
			
			GeoCode point = new GeoCode();
			DaumConvertAddress dca = new DaumConvertAddress(hsItem);
			point = dca.convert();
						
//			Intent intent = new Intent(getBaseContext(), MainActivity.class);	
//			intent.putExtra("latitude", point.latitude);
//			intent.putExtra("longitude", point.longitude);
//			startActivity(intent);
		}
	};	
}