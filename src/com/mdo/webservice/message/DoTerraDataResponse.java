package com.mdo.webservice.message;

public class DoTerraDataResponse {
	private Integer doTerraDataId;
	
	private Oil[] oils;
	
	private Symptom[] symptoms;

	/**
	 * @return the doTerraDataId
	 */
	public Integer getDoTerraDataId() {
		return doTerraDataId;
	}

	/**
	 * @param doTerraDataId the doTerraDataId to set
	 */
	public void setDoTerraDataId(Integer doTerraDataId) {
		this.doTerraDataId = doTerraDataId;
	}

	/**
	 * @return the oils
	 */
	public Oil[] getOils() {
		return oils;
	}

	/**
	 * @param oils the oils to set
	 */
	public void setOils(Oil[] oils) {
		this.oils = oils;
	}

	/**
	 * @return the symptoms
	 */
	public Symptom[] getSymptoms() {
		return symptoms;
	}

	/**
	 * @param symptoms the symptoms to set
	 */
	public void setSymptoms(Symptom[] symptoms) {
		this.symptoms = symptoms;
	}
	
	
}
