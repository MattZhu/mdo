package com.mdo.utils;

import android.content.Context;

import com.mdo.domainobject.DaoMaster;
import com.mdo.domainobject.DaoSession;
import com.mdo.domainobject.DaoMaster.OpenHelper;

public class DatabaseHelper {

	private static DaoMaster daoMaster;

	private static DaoSession daoSession;
	
	private final static String DATABASE_NAME="doterra";

	public static DaoMaster getDaoMaster(Context context)
	{
	    if (daoMaster == null)
	    {
	        OpenHelper helper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
	        daoMaster = new DaoMaster(helper.getWritableDatabase());
	    }
	    return daoMaster;
	}
	
	
	public static DaoSession getDaoSession(Context context)
	{
	    if (daoSession == null)
	    {
	        if (daoMaster == null)
	        {
	            daoMaster = getDaoMaster(context);
	        }
	        daoSession = daoMaster.newSession();
	    }
	    return daoSession;
	}
}
