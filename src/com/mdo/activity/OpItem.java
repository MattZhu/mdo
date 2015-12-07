package com.mdo.activity;

public class OpItem {
	public OpItem(int icon, int text) {
		this.icon = icon;
		this.text = text;
	}

	public OpItem(int icon, int text, Class<?> activityClass) {
		this(icon, text);
		this.activityClass = activityClass;
	}

	/** icon id */
	private int icon;
	/** String id */
	private int text;

	private Class<?> activityClass;

	/**
	 * @return the icon
	 */
	public int getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(int icon) {
		this.icon = icon;
	}

	/**
	 * @return the text
	 */
	public int getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(int text) {
		this.text = text;
	}

	/**
	 * @return the activityClass
	 */
	public Class<?> getActivityClass() {
		return activityClass;
	}

	/**
	 * @param activityClass
	 *            the activityClass to set
	 */
	public void setActivityClass(Class<?> activityClass) {
		this.activityClass = activityClass;
	}

}