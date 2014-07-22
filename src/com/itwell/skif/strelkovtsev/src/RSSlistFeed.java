package com.itwell.skif.strelkovtsev.src;
import java.util.List;

import android.view.View;
import android.widget.TextView;

public class RSSlistFeed {
	private String rssName; //название юлога
	private String rssURL; // URL блога
	private List<RSSljFeed> rssContainer; // Содержимое блога, берётся из класса
	private View pageFeed;
	private List<TextView> titleList;
	private List<TextView> dateList;
	private List<TextView> urlList;
	private List<TextView> descList;
	private TextView blogName;
	private TextView blogURL;
	
	public String getRssName() {
		return rssName;
	}
	public void setRssName(String rssName) {
		this.rssName = rssName;
	}
	public String getRssURL() {
		return rssURL;
	}
	public void setRssURL(String rssURL) {
		this.rssURL = rssURL;
	}
	public List<RSSljFeed> getRssContainer() {
		return rssContainer;
	}
	public void setRssContainer(List<RSSljFeed> rssContainer) {
		this.rssContainer = rssContainer;
	}
	public View getPageFeed() {
		return pageFeed;
	}
	public void setPageFeed(View pageFeed) {
		this.pageFeed = pageFeed;
	}
	public List<TextView> getTitleList() {
		return titleList;
	}
	public void setTitleList(List<TextView> titleList) {
		this.titleList = titleList;
	}
	public List<TextView> getDateList() {
		return dateList;
	}
	public void setDateList(List<TextView> dateList) {
		this.dateList = dateList;
	}
	public List<TextView> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<TextView> urlList) {
		this.urlList = urlList;
	}
	public List<TextView> getDescList() {
		return descList;
	}
	public void setDescList(List<TextView> descList) {
		this.descList = descList;
	}
	public TextView getBlogName() {
		return blogName;
	}
	public void setBlogName(TextView blogName) {
		this.blogName = blogName;
	}
	public TextView getBlogURL() {
		return blogURL;
	}
	public void setBlogURL(TextView blogURL) {
		this.blogURL = blogURL;
	}
	
	
}
