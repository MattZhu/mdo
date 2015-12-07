package com.mdo.webservice.message;

public class OilApplication {
	//{
	private String Symbol;//":"T",
	private String Description;//":"Use as directed on individual package labels."
	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return Symbol;
	}
	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		Symbol = symbol;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OilApplication [Symbol=" + Symbol + ", Description="
				+ Description + "]";
	}

	
}
