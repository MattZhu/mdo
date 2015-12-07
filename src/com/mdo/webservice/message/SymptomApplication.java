package com.mdo.webservice.message;

public class SymptomApplication {
	private String Description;
	private String Symbol;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SymptomApplication [Description=" + Description + ", Symbol="
				+ Symbol + "]";
	}
	
	
}
