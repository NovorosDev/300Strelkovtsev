package com.itwell.skif.strelkovtsev.src;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.example.slidepageandroid.R;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import java.util.jar.Attributes;
import org.xml.sax.Attributes;

//import android.R;
import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class News extends ActionBarActivity {
	String rssResult = "";
	//static List<RSSljFeed> rssList;
	static List<RSSlistFeed> rssListFeed;
	static int indexList;
	SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();
        indexList = 0;
        //rssList = new ArrayList<RSSljFeed>();
        rssListFeed = new ArrayList<RSSlistFeed>();
        
        View page = inflater.inflate(R.layout.page, null);
        //TextView textView = (TextView) page.findViewById(R.id.text_view);
        //textView.setText("Страница 1");
        
        rssListFeed.add(new RSSlistFeed());
        rssListFeed.get(0).setRssName("El Murid");
        rssListFeed.get(0).setRssURL("http://el-murid.livejournal.com/data/rss");
        rssListFeed.get(0).setPageFeed(page);
        rssListFeed.get(0).setBlogName((TextView) page.findViewById(R.id.tvBlogName));
        rssListFeed.get(0).setBlogURL((TextView) page.findViewById(R.id.tvBlogURL));
        rssListFeed.get(0).setTitleList(setTitleList(page));
        rssListFeed.get(0).setDateList(setDateList(page));
        rssListFeed.get(0).setUrlList(setUrlList(page));
        rssListFeed.get(0).setDescList(setDescList(page));
        pages.add(page);
        
        page = inflater.inflate(R.layout.page, null);
        //textView = (TextView) page.findViewById(R.id.text_view);
        //textView.setText("Страница 2");
        rssListFeed.add(new RSSlistFeed());
        
        rssListFeed.get(1).setRssName("Voice Sevastopol");
        rssListFeed.get(1).setRssURL("http://voicesevas.ru/rss.xml");
        rssListFeed.get(1).setPageFeed(page);
        rssListFeed.get(1).setBlogName((TextView) page.findViewById(R.id.tvBlogName));
        rssListFeed.get(1).setBlogURL((TextView) page.findViewById(R.id.tvBlogURL));
        rssListFeed.get(1).setTitleList(setTitleList(page));
        rssListFeed.get(1).setDateList(setDateList(page));
        rssListFeed.get(1).setUrlList(setUrlList(page));
        rssListFeed.get(1).setDescList(setDescList(page));
        pages.add(page);
        
        SlideNewsPage pagerAdapter = new SlideNewsPage(pages);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        
        setContentView(viewPager);
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		getRSSdata();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 public void getRSSdata() {
	    	//
	    	DownloadDataTask ddt = new DownloadDataTask();
			ddt.execute();
	    }
	    
	    public void onClickUpdate(View v) {
	    	getRSSdata();
	    }
	    
	    
	    static private class RSSHandler extends DefaultHandler {
			boolean _Item=false;
			boolean _Title=false;
			boolean _PubDate=false;
			boolean _Link=false;
			boolean _Description=false;
			String description = "";
			RSSljFeed rssAtom;
			static List<RSSljFeed> rssList;
			public void startDocument() throws SAXException {
		        System.out.println("start document   : ");
		        rssList = new ArrayList<RSSljFeed>();
		    }

		    public void endDocument() throws SAXException {
		        System.out.println("end document     : ");
		        rssListFeed.get(indexList).setRssContainer(rssList);
		    }

		    public void startElement(String uri, String localName,
		        String qName, Attributes attributes)
		    throws SAXException {

		        //System.out.println("start element    : " + qName);
		        if (qName.equals("item")){
		        	_Item=true;
		        	rssAtom = new RSSljFeed();
		        	Log.d("startElemnt", qName);
		        	//System.out.println("start element    : " + qName);
		        }
		        else if (qName.equals("title")){
		        	_Title=true;
		        	Log.d("startElemnt", qName);
		        	//System.out.println("start element    : " + qName);
		        }
		        else if (qName.equals("pubDate")){
		        	_PubDate=true;
		        	Log.d("startElemnt", qName);
		        	//System.out.println("start element    : " + qName);
		        }
		        else if (qName.equals("link")){
		        	_Link=true;
		        	Log.d("startElemnt", qName);
		        	//System.out.println("start element    : " + qName);
		        }
		        else if (qName.equals("description")){
		        	_Description=true;
		        	Log.d("startElemnt", "Desc");
		        	//System.out.println("start element    : " + qName);
		        }
		    }

		    public void endElement(String uri, String localName, String qName)
		    throws SAXException {
		        //System.out.println("end element      : " + qName);
		        if (qName.equals("item")){
		        	Log.d("endElemnt", qName);
		        	_Item=false;
		        	Log.d("rssList", "Add item");
		        	rssList.add(rssAtom);
		        	Log.d("rssList", String.valueOf(rssList.size()));
		        	//Log.d("rssList", rssList.get(0).getItemTitle());
		        	//System.out.println("end element      : " + qName);
		        }
		        else if (qName.equals("title")){
		        	Log.d("endElemnt", qName);
		        	_Title=false;
		        	//System.out.println("end element      : " + qName);
		        }
		        else if (qName.equals("pubDate")){
		        	Log.d("endElemnt", qName);
		        	_PubDate=false;
		        	//System.out.println("end element      : " + qName);
		        }
		        else if (qName.equals("link")){
		        	Log.d("endElemnt", qName);
		        	_Link=false;
		        	//System.out.println("end element    : " + qName);
		        }
		        else if (qName.equals("description")){
		        	Log.d("endElemnt", "Desc");
		        	_Description=false;
		        	//if (_Item==true) rssAtom.setItemDescription(description);
		        	//System.out.println("end element    : " + qName);
		        }
		    }

		    public void characters(char ch[], int start, int length)
		    throws SAXException {
		    	if (_Item==true){
		    		/*if (_Title||_PubDate||_Link||_Description){
		    			System.out.println("start characters : " +
			    	            new String(ch, start, length));
		    		}*/
		    		if(_Title){
		    			if (rssAtom.getItemTitle()!=null) {
		    				rssAtom.setItemTitle(rssAtom.getItemTitle() + (new String(ch, start, length)));
		    			}
		    			else {
		    				//rssAtom.setItemDescription(description);
		    				rssAtom.setItemTitle(new String(ch, start, length));
		    			}
		    			Log.d("TITLE", rssAtom.getItemTitle());
		    		}
		    		else if(_PubDate){
		    			rssAtom.setItemPubDate(new String(ch, start, length));
		    		}
		    		else if(_Link){
		    			rssAtom.setItemLink(new String(ch, start, length));
		    		}
		    		else if(_Description){
		    			//description = (new String(ch, start, length));
		    			if (rssAtom.getItemDescription()!=null) {
		    				rssAtom.setItemDescription(rssAtom.getItemDescription() + (new String(ch, start, length)));
		    			}
		    			else {
		    				//rssAtom.setItemDescription(description);
		    				rssAtom.setItemDescription(new String(ch, start, length));
		    			}
		    		}
		    		
		    	}
		    	else {
		    		if (_Title){
			    		rssListFeed.get(indexList).setRssName(new String(ch, start, length));
		    		}
		    		else if(_Link){
		    			rssListFeed.get(indexList).setRssURL(new String(ch, start, length));
		    		}
		    	}
		        
		    }

	    }

	    
	    protected class DownloadDataTask  extends AsyncTask<Void, Void, Void>  {

	        
	        
			@Override
			protected void onPreExecute() {
				super.onPreExecute();				
			}
			
			protected void onProgressUpdate(Integer... progress) {
		         setProgress(progress[0]);
		     }

		     protected void onPostExecute(Void result) {
		    	 super.onPostExecute(result);
		    	 
		    	
		    	 //titleList.size();
		    	 Log.d("onPostExecute", rssResult);
		    	 String rssText = "";
		    	 //rss.setText(rssResult);
		    	 for (int ind=0; ind<rssListFeed.size(); ind++){
		    		List<TextView> titleList = rssListFeed.get(ind).getTitleList();
		 	    	List<TextView> dateList = rssListFeed.get(ind).getDateList();
		 	    	List<TextView> urlList = rssListFeed.get(ind).getUrlList();
		 	    	List<TextView> descList = rssListFeed.get(ind).getDescList();
		    		List<RSSljFeed> rssList = rssListFeed.get(ind).getRssContainer();
		    		rssListFeed.get(ind).getBlogName().setText(rssListFeed.get(ind).getRssName());
		    		rssListFeed.get(ind).getBlogURL().setText(rssListFeed.get(ind).getRssURL());
		    		if (rssList!=null && rssList.size()>0){
			    		 int count = rssList.size();
			    		 //rss.setText(String.valueOf(count));
			    		 
			    		 for (int i=0;i<count;i++){
			    			Date date = new Date();
			    			try {
								date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z").parse(rssList.get(i).getItemPubDate());
			    				 
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								Log.d("DATE PARCE", e.toString());
								e.printStackTrace();
								
							}
			    			SimpleDateFormat fDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			    			String rssDate = fDate.format(date);
			    			String description = "";
			    			description = rssList.get(i).getItemDescription();
			    			//description = description.replaceAll("<[a-zA-Z\\s/]+>", "");
			    			description = description.replaceAll("<.*?>","");
			    			description = description.replaceAll("&quot;","\"");
			    			description = description.replaceAll("&nbsp;","");
			    			description = description.replaceAll("&laquo;","«");
			    			description = description.replaceAll("&raquo;","»");
			    			description = description.replaceAll("&ndash;","-");
			    			//description = description.replaceAll("&nbsp;","");
			    			
			    			String title = rssList.get(i).getItemTitle();
			    			title = title.replaceAll("<.*?>","");
			    			title = title.replaceAll("&quot;","\"");
			    			title = title.replaceAll("&nbsp;","");
			    			title = title.replaceAll("&laquo;","«");
			    			title = title.replaceAll("&raquo;","»");
			    			title = title.replaceAll("&ndash;","-");
			    			

			    			if (description.length()>80) {
			    				description = description.substring(0,80);
			    						};
			    			Log.d ("COUNT ELEMENT", String.valueOf(i));
			    			/*rssListFeed.get(ind).getTitleList().get(i).setEnabled(true);
			    			rssListFeed.get(ind).getDateList().get(i).setEnabled(true);
			    			rssListFeed.get(ind).getUrlList().get(i).setEnabled(true);
			    			rssListFeed.get(ind).getDescList().get(i).setEnabled(true);
			    			rssListFeed.get(ind).getTitleList().get(i).setVisibility(0);
			    			rssListFeed.get(ind).getDateList().get(i).setVisibility(0);
			    			rssListFeed.get(ind).getUrlList().get(i).setVisibility(0);
			    			rssListFeed.get(ind).getDescList().get(i).setVisibility(0);*/
			    			rssListFeed.get(ind).getTitleList().get(i).setText(title);
			    			rssListFeed.get(ind).getDateList().get(i).setText(rssDate);
			    			rssListFeed.get(ind).getUrlList().get(i).setText(rssList.get(i).getItemLink());
			    			rssListFeed.get(ind).getDescList().get(i).setText(description);
			    			
			    		 }
			    		 Log.d ("COUNT ELEMENT", "---------------------");
			    		 if (count<25){
			    			 Log.d ("COUNT ELEMENT", String.valueOf(count));
			    			 for (int in=(count);in<25;in++){
			    				 Log.d ("COUNT ELEMENT", String.valueOf(in)+ "--->");
			    				 rssListFeed.get(ind).getTitleList().get(in).setText("");
			    				 rssListFeed.get(ind).getDateList().get(in).setText("");
			    				 rssListFeed.get(ind).getUrlList().get(in).setText("");
			    				 rssListFeed.get(ind).getDescList().get(in).setText("");
			    				 /*rssListFeed.get(ind).getTitleList().get(in).setEnabled(false);
			    				 rssListFeed.get(ind).getDateList().get(in).setEnabled(false);
			    				 rssListFeed.get(ind).getUrlList().get(in).setEnabled(false);
			    				 rssListFeed.get(ind).getDescList().get(in).setEnabled(false);
			    				 
			    				 rssListFeed.get(ind).getTitleList().get(in).setVisibility(1);
			    				 rssListFeed.get(ind).getDateList().get(in).setVisibility(1);
			    				 rssListFeed.get(ind).getUrlList().get(in).setVisibility(1);
			    				 rssListFeed.get(ind).getDescList().get(in).setVisibility(1);*/
			    			 }
			    		 }
			    		 
			    	 }
			    	 else {
			    		 Log.d("onPostExecute", "rssList is null!");
			    	 }
			    	 rssList = new ArrayList<RSSljFeed>();
			    	 //rss.setText(rssText);
		    	 }
		    	 

		     }

			
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				for (int i=0; i<rssListFeed.size();i++){
					indexList=i;
					try {
			    		//String url = "http://el-murid.livejournal.com/data/rss";
						String url = rssListFeed.get(i).getRssURL();
			            URL rssUrl = new URL(url);
			            rssResult= url;
			            SAXParserFactory factory = SAXParserFactory.newInstance();
			            SAXParser saxParser = factory.newSAXParser();
			            XMLReader xmlReader = saxParser.getXMLReader();            
			            RSSHandler rssHandler = new RSSHandler();
			            xmlReader.setContentHandler(rssHandler);
			            InputSource inputSource = new InputSource(rssUrl.openStream());
			            xmlReader.parse(inputSource);
			            //rssResult= inputSource.toString();			            
			    	 } 
			    	//catch (IOException| SAXException |ParserConfigurationException e) {
					catch (Exception e) {
			    		rssResult= (e.getMessage());
			         } 	
				}
						
				return null;
			}
			
		}
	
	    
	    private List<TextView> setTitleList(View page) {
	    	List<TextView> titleList = new ArrayList<TextView>();
	    	titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead0));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead1));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead2));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead3));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead4));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead5));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead6));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead7));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead8));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead9));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead10));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead11));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead12));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead13));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead14));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead15));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead16));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead17));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead18));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead19));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead20));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead21));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead22));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead23));
			titleList.add((TextView) page.findViewById(R.id.tvRSSitemHead24));
	        return titleList;
	    }
	    private List<TextView> setDateList(View page){
	    	List<TextView> dateList = new ArrayList<TextView>();
	    	dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate0));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate1));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate2));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate3));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate4));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate5));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate6));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate7));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate8));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate9));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate10));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate11));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate12));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate13));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate14));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate15));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate16));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate17));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate18));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate19));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate20));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate21));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate22));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate23));
			dateList.add((TextView) page.findViewById(R.id.tvRSSitemDate24));
			return dateList;
	    }
	    private List<TextView> setUrlList(View page){
	    	List<TextView> urlList= new ArrayList<TextView>();
	    	urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL0));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL1));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL2));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL3));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL4));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL5));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL6));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL7));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL8));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL9));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL10));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL11));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL12));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL13));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL14));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL15));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL16));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL17));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL18));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL19));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL20));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL21));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL22));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL23));
			urlList.add((TextView) page.findViewById(R.id.tvRSSitemURL24));
			return urlList;
	    }
	    private List<TextView> setDescList(View page) {
	    	List<TextView> descList= new ArrayList<TextView>();
	    	descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription0));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription1));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription2));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription3));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription4));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription5));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription6));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription7));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription8));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription9));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription10));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription11));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription12));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription13));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription14));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription15));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription16));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription17));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription18));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription19));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription20));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription21));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription22));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription23));
			descList.add((TextView) page.findViewById(R.id.tvRSSitemDescription24));
			return descList;
	    }
}
