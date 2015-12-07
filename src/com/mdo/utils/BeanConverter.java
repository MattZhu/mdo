package com.mdo.utils;

import java.util.ArrayList;
import java.util.List;

import com.mdo.domainobject.ApplicationType;
import com.mdo.domainobject.OilApplication;
import com.mdo.domainobject.SymptomApplication;
import com.mdo.webservice.message.IndicatedOil;
import com.mdo.webservice.message.Oil;
import com.mdo.webservice.message.Symptom;

public class BeanConverter {

	public static void convertOil(Oil oil, com.mdo.domainobject.Oil entity) {
		entity.setId(oil.getId().longValue());
		entity.setAdditionalSymbol(oil.getAnimalAdditionalSymbols());
		// entity.setApplication(oil.);
		entity.setAromaticAffects(oil.getAromaticAffects());
		entity.setClassification(oil.getClassifications());
		entity.setDatabase(oil.getDatabase());
		entity.setDescription(oil.getDescription());
		entity.setImageURL(oil.getImageURL());
		entity.setIsFree(oil.getIsFree().byteValue());
		entity.setName(oil.getName());
		entity.setSafetyPrecautions(oil.getSafetyPrecautions());
		entity.setScientificName(oil.getScientificName());
		entity.setSystemsAffected(oil.getSystemsAffected());
		entity.setTimestamp(oil.getTimeStamp());
		entity.setUses(oil.getUses());

	}

	public static List<OilApplication> getOilApplication(Oil oil) {
		List<OilApplication> result = new ArrayList<OilApplication>();
		convertOilApplication(oil, oil.getAnimalApplications(), result,
				ApplicationType.A, oil.getAnimalAdditionalSymbols());
		convertOilApplication(oil, oil.getChildrenApplications(), result,
				ApplicationType.C, oil.getChildrenAdditionalSymbols());
		convertOilApplication(oil, oil.getGeneralApplications(), result,
				ApplicationType.G, oil.getGeneralAdditionalSymbols());
		convertOilApplication(oil, oil.getPregnancyApplications(), result,
				ApplicationType.P, oil.getPregnancyAdditionalSymbols());
		return result;
	}

	private static void convertOilApplication(Oil oil,
			com.mdo.webservice.message.OilApplication[] applications,
			List<OilApplication> result, ApplicationType t,
			String additionalSymbols) {
		for (com.mdo.webservice.message.OilApplication app : applications) {
			OilApplication oa = new OilApplication();
			oa.setDescription(app.getDescription());
			oa.setSymbolCode(app.getSymbol());
			oa.setAdditionalSymbols(additionalSymbols);
			oa.setOilId(oil.getId());
			oa.setApplicationType(t.getVal());
			result.add(oa);
		}
	}

	public static void converSymptom(Symptom symptom,
			com.mdo.domainobject.Symptom entity) {
		entity.setAdditionalProducts(symptom.getAdditionalProducts());
		entity.setAdditionalSymbols(symptom.getAnimalAdditionalSymbols());
		entity.setBlends(symptom.getBlends());
		entity.setContraindications(symptom.getContraindications());
		entity.setDatabase(symptom.getDatabase());
		entity.setDescription(symptom.getDescription());
		entity.setId(symptom.getId().longValue());
		entity.setIsFree(symptom.getIsFree().byteValue());
		entity.setName(symptom.getName());
		entity.setPrimaryOils(getOilIds(symptom.getPrimaryIndicatedOils()));
		entity.setSecondaryOils(getOilIds(symptom.getSecondaryIndicatedOils()));
		entity.setTertiaryOils(getOilIds(symptom.getTertiaryIndicatedOils()));
		entity.setTimestamp(symptom.getTimeStamp());
	}

	private static String getOilIds(IndicatedOil[] indicatedOils) {
		String result = "";
		for (IndicatedOil oil : indicatedOils) {
			result += oil.getOilId() + ",";
		}
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static List<SymptomApplication> getSymptomApplication(Symptom symptom) {
		List<SymptomApplication> result = new ArrayList<SymptomApplication>();

		convertSymptomApplication(symptom, symptom.getAnimalApplications(),
				result, ApplicationType.A, symptom.getAnimalAdditionalSymbols());
		
		convertSymptomApplication(symptom, symptom.getChildrenApplications(),
				result, ApplicationType.C, symptom.getChildrenAdditionalSymbols());
		
		convertSymptomApplication(symptom, symptom.getGeneralApplications(),
				result, ApplicationType.G, symptom.getGeneralAdditionalSymbols());
		
		convertSymptomApplication(symptom, symptom.getPregnancyApplications(),
				result, ApplicationType.P, symptom.getPregnancyAdditionalSymbols());
		
		return result;
	}

	private static void convertSymptomApplication(Symptom symptom,
			com.mdo.webservice.message.SymptomApplication[] applications,
			List<SymptomApplication> result, ApplicationType t,
			String additionalSymbols) {
		for (com.mdo.webservice.message.SymptomApplication app : applications) {
			SymptomApplication oa = new SymptomApplication();
			oa.setDescription(app.getDescription());
			oa.setSymbolCode(app.getSymbol());
			oa.setAdditionalSymbols(additionalSymbols);
			oa.setSymId(symptom.getId());
			oa.setApplicationType(t.getVal());
			result.add(oa);
		}
	}
}
