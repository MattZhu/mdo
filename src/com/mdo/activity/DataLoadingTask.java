package com.mdo.activity;

import java.util.List;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.mdo.activity.fragment.DisclaimerDialogFragment;
import com.mdo.activity.fragment.LoadingDialogFragment;
import com.mdo.domainobject.DaoSession;
import com.mdo.domainobject.OilApplication;
import com.mdo.domainobject.OilApplicationDao;
import com.mdo.domainobject.OilDao;
import com.mdo.domainobject.SymptomApplication;
import com.mdo.domainobject.SymptomApplicationDao;
import com.mdo.domainobject.SymptomDao;
import com.mdo.utils.AsyncTaskEx;
import com.mdo.utils.BeanConverter;
import com.mdo.utils.DatabaseHelper;
import com.mdo.webservice.WebServiceClient;
import com.mdo.webservice.message.DoTerraDataResponse;
import com.mdo.webservice.message.Oil;
import com.mdo.webservice.message.Symptom;

public class DataLoadingTask {

	public static void loadData(final DaoSession session,final BaseActivity activity,final boolean showTerms){
		final LoadingDialogFragment fragment=new LoadingDialogFragment();
		fragment.show(activity.getSupportFragmentManager(), "loading");
		new AsyncTaskEx<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... params) {

				OilDao oilDao = session.getOilDao();
				Oil[] oils = null;

				WebServiceClient client = new WebServiceClient();
				if (oilDao.loadAll().size() > 0) {
					Log.d("Main", "oil already exist in DB, not call service.");
			
					return 0;
				}
				else{
					
				}
				DoTerraDataResponse result = client.getDoTerra();
				
				if(result==null){
					return -1;
				}
				
				oils= result.getOils();

				OilApplicationDao applicationDao = session
						.getOilApplicationDao();
				oilDao.deleteAll();
				applicationDao.deleteAll();
				for (int i = 0; i < oils.length; i++) {
					com.mdo.domainobject.Oil entity = new com.mdo.domainobject.Oil();
					BeanConverter.convertOil(oils[i], entity);
					oilDao.insertInTx(entity);

					List<OilApplication> oilApplications = BeanConverter
							.getOilApplication(oils[i]);
					applicationDao.insertInTx(oilApplications);
				}

				Symptom[] symptoms = result.getSymptoms();

				SymptomDao symptomDao = session.getSymptomDao();

				SymptomApplicationDao symptomApplicationDao = session
						.getSymptomApplicationDao();
				symptomDao.deleteAll();
				symptomApplicationDao.deleteAll();
				for (int i = 0; i < symptoms.length; i++) {
					com.mdo.domainobject.Symptom entity = new com.mdo.domainobject.Symptom();
					BeanConverter.converSymptom(symptoms[i], entity);
					symptomDao.insertInTx(entity);

					List<SymptomApplication> entities = BeanConverter
							.getSymptomApplication(symptoms[i]);
					symptomApplicationDao.insertInTx(entities);
				}
				return 0;
			}

			/* (non-Javadoc)
			 * @see com.doterra.utils.AsyncTaskEx#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(Integer result) {
				super.onPostExecute(result);
				fragment.dismiss();
				
				if(result==-1){
					showLoadError(activity.getApplicationContext());
				}
				
				if(showTerms){

					DialogFragment newFragment = new DisclaimerDialogFragment(true);
					newFragment.show(activity.getSupportFragmentManager(), "disclaimer");
				}
			}

		}.execute();
	
	} 
	
	public static void refresh(final BaseActivity activity){
		final LoadingDialogFragment fragment=new LoadingDialogFragment();
		fragment.show(activity.getSupportFragmentManager(), "loading");
		new AsyncTaskEx<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... params) {
				 DaoSession session = DatabaseHelper.getDaoSession(activity);
				OilDao oilDao = session.getOilDao();
				SymptomDao symptomDao = session.getSymptomDao();
				

				WebServiceClient client = new WebServiceClient();
				String _lOilDate=null;
				String _lSymptomDate=null;
				List<com.mdo.domainobject.Oil>lastOils=oilDao.queryBuilder().orderDesc(OilDao.Properties.Timestamp).limit(1).list();
				if(lastOils.size()>0){
				 _lOilDate=lastOils.get(0).getTimestamp();
				}
				List<com.mdo.domainobject.Symptom> lastSymptom=symptomDao.queryBuilder().orderDesc(SymptomDao.Properties.Timestamp).limit(1).list();
				if(lastSymptom.size()>0){
					_lSymptomDate=lastSymptom.get(0).getTimestamp();
				}
				DoTerraDataResponse result;
				if(_lOilDate==null&&_lSymptomDate==null){
					result=client.getDoTerra();
				}else{
					result= client.getDoTerraUpdate(_lOilDate, _lSymptomDate);
				}
				if(result==null){
					return -1;
				}
				Oil[] oils = result.getOils();

				OilApplicationDao applicationDao = session
						.getOilApplicationDao();
				for (int i = 0; i < oils.length; i++) {
					com.mdo.domainobject.Oil entity = new com.mdo.domainobject.Oil();
					BeanConverter.convertOil(oils[i], entity);
					oilDao.insertOrReplaceInTx(entity);

					List<OilApplication> oilApplications = BeanConverter
							.getOilApplication(oils[i]);
					applicationDao.insertOrReplaceInTx(oilApplications);
				}

				Symptom[] symptoms = result.getSymptoms();


				SymptomApplicationDao symptomApplicationDao = session
						.getSymptomApplicationDao();

				for (int i = 0; i < symptoms.length; i++) {
					com.mdo.domainobject.Symptom entity = new com.mdo.domainobject.Symptom();
					BeanConverter.converSymptom(symptoms[i], entity);
					symptomDao.insertOrReplaceInTx(entity);

					List<SymptomApplication> entities = BeanConverter
							.getSymptomApplication(symptoms[i]);
					symptomApplicationDao.insertOrReplaceInTx(entities);
				}
				return 0;
			}

			/* (non-Javadoc)
			 * @see com.doterra.utils.AsyncTaskEx#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(Integer result) {
				super.onPostExecute(result);
				fragment.dismiss();
				if(result==-1){
					showLoadError(activity.getApplicationContext());
				}
				activity.refresh();
			}

		}.execute();
	}
	
	private static void showLoadError(Context context){
		Toast.makeText(
				context,
				"Error get data from server,Please make sure the network is available and try again.", Toast.LENGTH_SHORT)
				.show();
		
	}
}
