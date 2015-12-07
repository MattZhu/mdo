package com.mdo.webservice.message;

public class IndicatedOil {
	private Integer OilId;
	private String OilName;
	/**
	 * @return the oilId
	 */
	public Integer getOilId() {
		return OilId;
	}
	/**
	 * @param oilId the oilId to set
	 */
	public void setOilId(Integer oilId) {
		OilId = oilId;
	}
	/**
	 * @return the oilName
	 */
	public String getOilName() {
		return OilName;
	}
	/**
	 * @param oilName the oilName to set
	 */
	public void setOilName(String oilName) {
		OilName = oilName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndicatedOil [OilId=" + OilId + ", OilName=" + OilName + "]";
	}
	
	
}
