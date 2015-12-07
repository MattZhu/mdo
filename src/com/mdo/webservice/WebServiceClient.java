package com.mdo.webservice;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

import com.mdo.utils.BeanUtils;
import com.mdo.webservice.message.DoTerraDataResponse;
import com.mdo.webservice.message.Oil;
import com.mdo.webservice.message.Symptom;

public class WebServiceClient {

	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://services.lokolapps.com/lokolws.asmx";
	private final String SOAP_ACTION_GETDOTERRA = "http://tempuri.org/getDoTerra";

	private final String SOAP_ACTION_GETDOTERRA_UPDATE = "http://tempuri.org/getDoTerraUpdate";
	public DoTerraDataResponse getDoTerra() {
		SoapObject request = new SoapObject(NAMESPACE, "getDoTerra");
		// Property which holds input parameters
		// PropertyInfo celsiusPI = new PropertyInfo();
		// //Set Name
		// celsiusPI.setName("Celsius");
		// //Set Value
		// celsiusPI.setValue(celsius);
		// //Set dataType
		// celsiusPI.setType(double.class);
		// Add the property to request object
		// request.addProperty(celsiusPI);
		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION_GETDOTERRA, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			String respone = (String) response.getValue();
			JSONObject jsonObj = (JSONObject) new JSONTokener(respone)
			.nextValue();
			DoTerraDataResponse result=new DoTerraDataResponse();
			BeanUtils.convertJsonToBean(jsonObj, result);
			
			printRespon(result);
			return result;

		} catch (Exception e) {
			Log.e("WSClient","Error get data",e);
		}
		return null;
	}
	
	
	public DoTerraDataResponse getDoTerraUpdate(String _lOilDate,String _lSymptomDate) {
		Log.d("WSClient","lastOilDate:"+_lOilDate+", last SymptomDate:"+_lSymptomDate);
		SoapObject request = new SoapObject(NAMESPACE, "getDoTerraUpdate");
		// Property which holds input parameters
		 PropertyInfo lOilDate = new PropertyInfo();
		 //Set Name
		 lOilDate.setName("lOilDate");
		 //Set Value
		 lOilDate.setValue(_lOilDate.replace(" ", "T"));
		 //Set dataType
		 lOilDate.setType(String.class);
//		 Add the property to request object
		 request.addProperty(lOilDate);
		 
		 PropertyInfo lSymptomDate = new PropertyInfo();
		 //Set Name
		 lSymptomDate.setName("lSymptomDate");
		 //Set Value
		 lSymptomDate.setValue(_lSymptomDate.replace(" ", "T"));
		 //Set dataType
		 lSymptomDate.setType(String.class);
//		 Add the property to request object
		 request.addProperty(lSymptomDate);
//		 Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		
		
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION_GETDOTERRA_UPDATE, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			String respone = (String) response.getValue();
			JSONObject jsonObj = (JSONObject) new JSONTokener(respone)
			.nextValue();
			DoTerraDataResponse result=new DoTerraDataResponse();
			BeanUtils.convertJsonToBean(jsonObj, result);
			
			printRespon(result);
			return result;

		} catch (Exception e) {
			Log.e("WSClient","Error get data",e);
		}
		return null;
	}
	
	private void printRespon(DoTerraDataResponse result) {
		System.out.println("getDoTerraDataId:"+result.getDoTerraDataId()+",oil size:"+result.getOils().length+",symptom size:"+result.getSymptoms().length);
		
		for(Oil o:result.getOils()){
			System.out.println(o);
		}
		for(Symptom s:result.getSymptoms()){
			System.out.println(s);
		}
		
	}

	private void printValue(JSONObject jsonObj) throws JSONException{
		Iterator<?> keys=jsonObj.keys();
		System.out.print("{");
		while(keys.hasNext()){
			String key=(String)keys.next();
			System.out.println(key+":");
			Object value=jsonObj.get(key);
			if(value instanceof JSONArray){
				System.out.print("[");
				JSONArray array=(JSONArray)value;
					for(int i=0;i<array.length();i++){
						printValue((JSONObject)array.get(i));
					}
				System.out.println("]");
			}else if(value instanceof JSONObject){
				

				printValue((JSONObject)value);
			}else{
				//System.out.println(value);
			}
			
		}

		System.out.println("}");
		
	}
}
