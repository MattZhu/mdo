package com.mdo.webservice.message;

import java.util.Arrays;

public class Oil {
	private Integer id;
	private String Name;
	private String ImageURL;//":"http://admin.lokolapps.com/doterra/images/image102.jpg",
	private String ScientificName;//":"",
	private String Database;//:"G",
	private String Description;//":"Lavender, jasmine, geranium, and frankincense in an anti-aging moisturizer. Anti-Aging Moisturizer will target the visible signs of aging--improving skin elasticity and tone, reducing the appearance and number of wrinkles as well as helping to prevent the future signs of mechanical aging.",
	private String Uses;//":"Blemishes\r\nMoisturizer\r\nRough skin\r\nIrritated skin\r\nWrinkles",
	private String SystemsAffected;//":"Emotional\r\nHemolytic\r\nIntegumentary",
	private String AromaticAffects;//":"",
	private String Classifications;//":"Enhancer\r\nEqualizer",
	private String SafetyPrecautions;//":"Avoid direct contact with eyes.  Not all oils or products are safe during pregnancy, on young children, or animals.  Please consult the specific Pregnancy, Child, or Animal references for specific treatments and individual oil safety.\r\n",
	private Integer IsFree;//":0,
	private OilApplication[] GeneralApplications;//":[
	private OilApplication[] PregnancyApplications;//":[],
	private OilApplication[] ChildrenApplications;//":[]
	private OilApplication[] AnimalApplications;//":[],
	private String GeneralAdditionalSymbols;//":"",
	private String PregnancyAdditionalSymbols;//":"",
	private String ChildrenAdditionalSymbols;//":"",
	private String AnimalAdditionalSymbols;//":"",
	private IndicatedSymptom[]	IndicatedSymptoms;//":[],
	private String TimeStamp;//":"2012-11-09 19:08:23"
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the imageURL
	 */
	public String getImageURL() {
		return ImageURL;
	}
	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		ImageURL = imageURL;
	}
	/**
	 * @return the scientificName
	 */
	public String getScientificName() {
		return ScientificName;
	}
	/**
	 * @param scientificName the scientificName to set
	 */
	public void setScientificName(String scientificName) {
		ScientificName = scientificName;
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
	 * @return the uses
	 */
	public String getUses() {
		return Uses;
	}
	/**
	 * @param uses the uses to set
	 */
	public void setUses(String uses) {
		Uses = uses;
	}
	/**
	 * @return the systemsAffected
	 */
	public String getSystemsAffected() {
		return SystemsAffected;
	}
	/**
	 * @param systemsAffected the systemsAffected to set
	 */
	public void setSystemsAffected(String systemsAffected) {
		SystemsAffected = systemsAffected;
	}
	/**
	 * @return the aromaticAffects
	 */
	public String getAromaticAffects() {
		return AromaticAffects;
	}
	/**
	 * @param aromaticAffects the aromaticAffects to set
	 */
	public void setAromaticAffects(String aromaticAffects) {
		AromaticAffects = aromaticAffects;
	}
	/**
	 * @return the classifications
	 */
	public String getClassifications() {
		return Classifications;
	}
	/**
	 * @param classifications the classifications to set
	 */
	public void setClassifications(String classifications) {
		Classifications = classifications;
	}
	/**
	 * @return the safetyPrecautions
	 */
	public String getSafetyPrecautions() {
		return SafetyPrecautions;
	}
	/**
	 * @param safetyPrecautions the safetyPrecautions to set
	 */
	public void setSafetyPrecautions(String safetyPrecautions) {
		SafetyPrecautions = safetyPrecautions;
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
	/**
	 * @return the generalApplications
	 */
	public OilApplication[] getGeneralApplications() {
		return GeneralApplications;
	}
	/**
	 * @param generalApplications the generalApplications to set
	 */
	public void setGeneralApplications(OilApplication[] generalApplications) {
		GeneralApplications = generalApplications;
	}
	/**
	 * @return the pregnancyApplications
	 */
	public OilApplication[] getPregnancyApplications() {
		return PregnancyApplications;
	}
	/**
	 * @param pregnancyApplications the pregnancyApplications to set
	 */
	public void setPregnancyApplications(OilApplication[] pregnancyApplications) {
		PregnancyApplications = pregnancyApplications;
	}
	/**
	 * @return the childrenApplications
	 */
	public OilApplication[] getChildrenApplications() {
		return ChildrenApplications;
	}
	/**
	 * @param childrenApplications the childrenApplications to set
	 */
	public void setChildrenApplications(OilApplication[] childrenApplications) {
		ChildrenApplications = childrenApplications;
	}
	/**
	 * @return the animalApplications
	 */
	public OilApplication[] getAnimalApplications() {
		return AnimalApplications;
	}
	/**
	 * @param animalApplications the animalApplications to set
	 */
	public void setAnimalApplications(OilApplication[] animalApplications) {
		AnimalApplications = animalApplications;
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
	 * @return the indicatedSymptoms
	 */
	public IndicatedSymptom[] getIndicatedSymptoms() {
		return IndicatedSymptoms;
	}
	/**
	 * @param indicatedSymptoms the indicatedSymptoms to set
	 */
	public void setIndicatedSymptoms(IndicatedSymptom[] indicatedSymptoms) {
		IndicatedSymptoms = indicatedSymptoms;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Oil [id=" + id + ", Name=" + Name + ", ImageURL=" + ImageURL
				+ ", ScientificName=" + ScientificName + ", Database="
				+ Database + ", Description=" + Description + ", Uses=" + Uses
				+ ", SystemsAffected=" + SystemsAffected + ", AromaticAffects="
				+ AromaticAffects + ", Classifications=" + Classifications
				+ ", SafetyPrecautions=" + SafetyPrecautions + ", IsFree="
				+ IsFree + ", GeneralApplications="
				+ Arrays.toString(GeneralApplications)
				+ ", PregnancyApplications="
				+ Arrays.toString(PregnancyApplications)
				+ ", ChildrenApplications="
				+ Arrays.toString(ChildrenApplications)
				+ ", AnimalApplications=" + Arrays.toString(AnimalApplications)
				+ ", GeneralAdditionalSymbols=" + GeneralAdditionalSymbols
				+ ", PregnancyAdditionalSymbols=" + PregnancyAdditionalSymbols
				+ ", ChildrenAdditionalSymbols=" + ChildrenAdditionalSymbols
				+ ", AnimalAdditionalSymbols=" + AnimalAdditionalSymbols
				+ ", IndicatedSymptoms=" + Arrays.toString(IndicatedSymptoms)
				+ ", TimeStamp=" + TimeStamp + "]";
	}
	
	
	
}
