package in.ac.iitb.poem.beans;

import java.util.List;

public class Comments {
	private String username;
	private String text;
	private List<String> likes;
	private String date_ts;
	private String display_date;
	
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the likes
	 */
	public List<String> getLikes() {
		return likes;
	}
	/**
	 * @param likes the likes to set
	 */
	public void setLikes(List<String> likes) {
		this.likes = likes;
	}
	/**
	 * @return the date_ts
	 */
	public String getDate_ts() {
		return date_ts;
	}
	/**
	 * @param date_ts the date_ts to set
	 */
	public void setDate_ts(String date_ts) {
		this.date_ts = date_ts;
	}
	/**
	 * @return the display_date
	 */
	public String getDisplay_date() {
		return display_date;
	}
	/**
	 * @param display_date the display_date to set
	 */
	public void setDisplay_date(String display_date) {
		this.display_date = display_date;
	}
	
	
}
