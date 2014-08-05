package pfm.storm.only.basic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Tweet implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Date createdAt;
	private String screenName;
	private String message;
	private double longitude;
	private double latitude;
	
	private List<String> hashtags;
	
	public Tweet(Long id, Date createdAt, String screenName, String message, List<String> hashtags,double longitude, double latitude) {
		this.id = id;
		this.createdAt = createdAt;
		this.screenName = screenName;
		this.message = message;
		this.hashtags = hashtags;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Tweet(Long id, Date createdAt, String screenName, String message, String hashtags,double longitude, double latitude) {
		this.id = id;
		this.createdAt = createdAt;
		this.screenName = screenName;
		this.message = message;
		this.longitude = longitude;
		this.latitude = latitude;
		this.hashtags = new ArrayList<String>();
		String[] ht = StringUtils.split(hashtags, ",");
		for (int i = 0; i<ht.length; i++) {
			this.hashtags.add(ht[i]);
		}
	}

	public double getLongitude() {
		return longitude;
	}


	public double getLatitude() {
		return latitude;
	}


	public Long getId() {
		return id;
	}

	public String getScreenName() {
		return screenName;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getHashtags() {
		return hashtags;
	}
	
}