package com.mdo.webservice.message;

public class IndicatedSymptom {
	private Integer SymptomId;
	private String SymptomName;
	/**
	 * @return the symptomId
	 */
	public Integer getSymptomId() {
		return SymptomId;
	}
	/**
	 * @param symptomId the symptomId to set
	 */
	public void setSymptomId(Integer symptomId) {
		SymptomId = symptomId;
	}
	/**
	 * @return the symptomName
	 */
	public String getSymptomName() {
		return SymptomName;
	}
	/**
	 * @param symptomName the symptomName to set
	 */
	public void setSymptomName(String symptomName) {
		SymptomName = symptomName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndicatedSymptom [SymptomId=" + SymptomId + ", SymptomName="
				+ SymptomName + "]";
	}
	
}
