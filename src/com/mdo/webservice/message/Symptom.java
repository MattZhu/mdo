package com.mdo.webservice.message;

import java.util.Arrays;

public class Symptom {
	private String Description;
	private SymptomApplication[] PregnancyApplications;

	private SymptomApplication[] GeneralApplications;

	private String AdditionalProducts;
	private String Database;
	private SymptomApplication[] ChildrenApplications;
	private String ChildrenAdditionalSymbols;
	private String GeneralAdditionalSymbols;
	private String TimeStamp;
	private SymptomApplication[] AnimalApplications;
	private String Blends;
	private String Name;
	private IndicatedOil[] PrimaryIndicatedOils;
	private String PregnancyAdditionalSymbols;
	private IndicatedOil[] SecondaryIndicatedOils;
	private Integer Id;
	private IndicatedOil[] TertiaryIndicatedOils;

	private String AnimalAdditionalSymbols;
	private String Contraindications;
	private Integer IsFree;
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
	 * @return the pregnancyApplications
	 */
	public SymptomApplication[] getPregnancyApplications() {
		return PregnancyApplications;
	}
	/**
	 * @param pregnancyApplications the pregnancyApplications to set
	 */
	public void setPregnancyApplications(SymptomApplication[] pregnancyApplications) {
		PregnancyApplications = pregnancyApplications;
	}
	/**
	 * @return the generalApplications
	 */
	public SymptomApplication[] getGeneralApplications() {
		return GeneralApplications;
	}
	/**
	 * @param generalApplications the generalApplications to set
	 */
	public void setGeneralApplications(SymptomApplication[] generalApplications) {
		GeneralApplications = generalApplications;
	}
	/**
	 * @return the additionalProducts
	 */
	public String getAdditionalProducts() {
		return AdditionalProducts;
	}
	/**
	 * @param additionalProducts the additionalProducts to set
	 */
	public void setAdditionalProducts(String additionalProducts) {
		AdditionalProducts = additionalProducts;
	}
	/**
	 * @return the database
	 */
	public String getDatabase() {
		return Database;
	}
	/**
	 * @param database the database to set
	 */
	public void setDatabase(String database) {
		Database = database;
	}
	/**
	 * @return the childrenApplications
	 */
	public SymptomApplication[] getChildrenApplications() {
		return ChildrenApplications;
	}
	/**
	 * @param childrenApplications the childrenApplications to set
	 */
	public void setChildrenApplications(SymptomApplication[] childrenApplications) {
		ChildrenApplications = childrenApplications;
	}
	/**
	 * @return the childrenAdditionalSymbols
	 */
	public String getChildrenAdditionalSymbols() {
		return ChildrenAdditionalSymbols;
	}
	/**
	 * @param childrenAdditionalSymbols the childrenAdditionalSymbols to set
	 */
	public void setChildrenAdditionalSymbols(String childrenAdditionalSymbols) {
		ChildrenAdditionalSymbols = childrenAdditionalSymbols;
	}
	/**
	 * @return the generalAdditionalSymbols
	 */
	public String getGeneralAdditionalSymbols() {
		return GeneralAdditionalSymbols;
	}
	/**
	 * @param generalAdditionalSymbols the generalAdditionalSymbols to set
	 */
	public void setGeneralAdditionalSymbols(String generalAdditionalSymbols) {
		GeneralAdditionalSymbols = generalAdditionalSymbols;
	}
	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return TimeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}
	/**
	 * @return the animalApplications
	 */
	public SymptomApplication[] getAnimalApplications() {
		return AnimalApplications;
	}
	/**
	 * @param animalApplications the animalApplications to set
	 */
	public void setAnimalApplications(SymptomApplication[] animalApplications) {
		AnimalApplications = animalApplications;
	}
	/**
	 * @return the blends
	 */
	public String getBlends() {
		return Blends;
	}
	/**
	 * @param blends the blends to set
	 */
	public void setBlends(String blends) {
		Blends = blends;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return the primaryIndicatedOils
	 */
	public IndicatedOil[] getPrimaryIndicatedOils() {
		return PrimaryIndicatedOils;
	}
	/**
	 * @param primaryIndicatedOils the primaryIndicatedOils to set
	 */
	public void setPrimaryIndicatedOils(IndicatedOil[] primaryIndicatedOils) {
		PrimaryIndicatedOils = primaryIndicatedOils;
	}
	/**
	 * @return the pregnancyAdditionalSymbols
	 */
	public String getPregnancyAdditionalSymbols() {
		return PregnancyAdditionalSymbols;
	}
	/**
	 * @param pregnancyAdditionalSymbols the pregnancyAdditionalSymbols to set
	 */
	public void setPregnancyAdditionalSymbols(String pregnancyAdditionalSymbols) {
		PregnancyAdditionalSymbols = pregnancyAdditionalSymbols;
	}
	/**
	 * @return the secondaryIndicatedOils
	 */
	public IndicatedOil[] getSecondaryIndicatedOils() {
		return SecondaryIndicatedOils;
	}
	/**
	 * @param secondaryIndicatedOils the secondaryIndicatedOils to set
	 */
	public void setSecondaryIndicatedOils(IndicatedOil[] secondaryIndicatedOils) {
		SecondaryIndicatedOils = secondaryIndicatedOils;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		Id = id;
	}
	/**
	 * @return the tertiaryIndicatedOils
	 */
	public IndicatedOil[] getTertiaryIndicatedOils() {
		return TertiaryIndicatedOils;
	}
	/**
	 * @param tertiaryIndicatedOils the tertiaryIndicatedOils to set
	 */
	public void setTertiaryIndicatedOils(IndicatedOil[] tertiaryIndicatedOils) {
		TertiaryIndicatedOils = tertiaryIndicatedOils;
	}
	/**
	 * @return the animalAdditionalSymbols
	 */
	public String getAnimalAdditionalSymbols() {
		return AnimalAdditionalSymbols;
	}
	/**
	 * @param animalAdditionalSymbols the animalAdditionalSymbols to set
	 */
	public void setAnimalAdditionalSymbols(String animalAdditionalSymbols) {
		AnimalAdditionalSymbols = animalAdditionalSymbols;
	}
	/**
	 * @return the contraindications
	 */
	public String getContraindications() {
		return Contraindications;
	}
	/**
	 * @param contraindications the contraindications to set
	 */
	public void setContraindications(String contraindications) {
		Contraindications = contraindications;
	}
	/**
	 * @return the isFree
	 */
	public Integer getIsFree() {
		return IsFree;
	}
	/**
	 * @param isFree the isFree to set
	 */
	public void setIsFree(Integer isFree) {
		IsFree = isFree;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Symptom [Description=" + Description
				+ ", PregnancyApplications="
				+ Arrays.toString(PregnancyApplications)
				+ ", GeneralApplications="
				+ Arrays.toString(GeneralApplications)
				+ ", AdditionalProducts=" + AdditionalProducts + ", Database="
				+ Database + ", ChildrenApplications="
				+ Arrays.toString(ChildrenApplications)
				+ ", ChildrenAdditionalSymbols=" + ChildrenAdditionalSymbols
				+ ", GeneralAdditionalSymbols=" + GeneralAdditionalSymbols
				+ ", TimeStamp=" + TimeStamp + ", AnimalApplications="
				+ Arrays.toString(AnimalApplications) + ", Blends=" + Blends
				+ ", Name=" + Name + ", PrimaryIndicatedOils="
				+ Arrays.toString(PrimaryIndicatedOils)
				+ ", PregnancyAdditionalSymbols=" + PregnancyAdditionalSymbols
				+ ", SecondaryIndicatedOils="
				+ Arrays.toString(SecondaryIndicatedOils) + ", Id=" + Id
				+ ", TertiaryIndicatedOils="
				+ Arrays.toString(TertiaryIndicatedOils)
				+ ", AnimalAdditionalSymbols=" + AnimalAdditionalSymbols
				+ ", Contraindications=" + Contraindications + ", IsFree="
				+ IsFree + "]";
	}

	
}
