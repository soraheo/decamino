package com.skcc.decamino;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

class DaumConvertAddress {
	
	HashMap<String, String> hsItem;
	
	private String x;	
	private String y;
		
	final static String DAUM_API_KEY = "6289bb3fc391c90c4dfc3f073e9d2a5bc9ee364f";

	public DaumConvertAddress(HashMap<String, String> hsItem) {
		// TODO Auto-generated constructor stub
		this.hsItem = hsItem;	
	}

	public GeoCode convert() {
		// TODO Auto-generated method stub		
		
		GeoCode item = new GeoCode();
				
		x = hsItem.get("mapx");
		y = hsItem.get("mapy");
		
		String m_x = "";
		String m_y = "";
		
		try{
			m_x = URLEncoder.encode(x, "UTF8"); // 검색정보 인코딩
			m_y = URLEncoder.encode(y, "UTF8"); // 검색정보 인코딩
		} catch(UnsupportedEncodingException e1){
			e1.printStackTrace();
		}
		
		try{
			URL text= new URL(
							  "http://apis.daum.net/local/geo/transcoord?" +
							  "apikey=" + DAUM_API_KEY +
							  "&x=" + m_x +
							  "&y=" + m_y +
							  "&fromCoord=" + "KTM" +
							  "&toCoord=" + "WGS84" +
							  "&output=" + "xml"
							 ); //다음 api URL 사용
			
			Log.i("URL : ", text.toString());
			
			//개발자 센터 참조
			XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserCreator.newPullParser();
			
			parser.setInput(text.openStream(), null);
			
			Log.i("NET", "Parsing...");
			
			// 읽어온 정보를 파싱하여 데이터를 만든다.
			int parseEvent = parser.getEventType();
			while(parseEvent != XmlPullParser.END_DOCUMENT){
				
				switch(parseEvent){
				
				case XmlPullParser.START_TAG:					
					Log.i("NET","START...");
					
					String tag = parser.getName();
											
					if(tag.compareTo("result") == 0)
					{
						String result = parser.nextText();
						item.result = result;
						Log.i("result", result);
					}
					if(tag.compareTo("x") == 0)
					{
						String x = parser.nextText();
						item.longitude = x;
						Log.i("x", x);
					}
					if(tag.compareTo("y") == 0)
					{
						String y = parser.nextText();
						item.latitude = y;
						Log.i("y", y);
					}
					break;
				}
				parseEvent = parser.next();
				// 다음 데이터로 넘어간다.. END_DOCUMENT일때까지..
			}
			Log.i("NET", "End...");
		} catch(Exception e)
		{
			Log.i("NET", "Parsing fail");
		}
		
		return item;
	}

}


