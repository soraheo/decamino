package com.skcc.decamino;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;


public class NaverParser {
	private String key1;
	private String target;
	private String sort = "vote";
	
	ArrayList<LocationData> items;
	
	NaverParser(String key, String target){
		this.key1 = key;
		this.target = target;
	}
	public ArrayList<LocationData> getBookData(final String info, final int count, final int start){
		items = new ArrayList<LocationData>();
		
		LocationData item = null;
		
		String m_searchinfo = info;
		
		try{
			m_searchinfo = URLEncoder.encode(info, "UTF8"); // 검색정보 인코딩
		} catch(UnsupportedEncodingException e1){
			e1.printStackTrace();
		}
		
		try{
			Log.i("info : ", info);
			
			URL text = new URL(
							  "http://openapi.naver.com/search?" +
							  "key=" + key1 +
							  "&query=" + m_searchinfo +
							  "&display=" + count +
							  "&start=" + start +
							  "&target=" + target +
							  "&sort=" + sort
							 ); //네이버 api URL 사용
			
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
					
					if(tag.compareTo("title") == 0)
					{
						item = new LocationData();
						String titlesrc = parser.nextText();
						item.title = titlesrc;						
					}
					if(tag.compareTo("description") == 0)
					{
						String descriptionsrc = parser.nextText();
						item.description = descriptionsrc;
					}
					if(tag.compareTo("address") == 0)
					{
						String addresssrc = parser.nextText();
						item.address = addresssrc;
					}
					if(tag.compareTo("mapx") == 0)
					{
						String mapxsrc = parser.nextText();
						item.mapx = mapxsrc;
					}
					if(tag.compareTo("mapy") == 0)
					{
						String mapysrc = parser.nextText();
						item.mapy = mapysrc;
						items.add(item);
					}
					break;
				}
				parseEvent=parser.next();
				// 다음 데이터로 넘어간다.. END_DOCUMENT일때까지..
			}
			Log.i("NET","End... count : " + items.size());
		} catch(Exception e)
		{
			Log.i("NET","Parsing fail");
		}
		return items;
	}
}