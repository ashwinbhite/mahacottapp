package com.cotton.mahacott.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SaveSharedPreference
{
	
	
	
	static final String PREF_PASSWORD= "password";
	static final String PREF_USER_CODE ="usercode";
	static final String SEASON_MST_ID="seasonMstId";

	static final String PREF_WORKINGDATE ="workingdate";
	
	static final String PREF_BRANCHNAME ="branchname";
	static final String PREF_BRANCHCODE = "branchcode";
	static final String PREF_USERNAME = "username";
	static final String CENTERCODELIST = "centercodelist";
	static final String PREF_CUSTOMERTYPE = "customerType";
	static final String ZONECODE = "zonecode";
    static final String PINCODE = "pincode";

    static SharedPreferences getSharedPreferences(Context ctx) 
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPassword(Context ctx, String password) 
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PASSWORD, password);
        editor.commit();
    }
    
    public static void setUserCode(Context ctx, String usercode) 
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_CODE, usercode);
        editor.commit();
    }

    public static void setSeasonMstId(Context ctx, String seasonMstId)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(SEASON_MST_ID, seasonMstId);
        editor.commit();
    }
    
  
    
    public static void setWorkingDate(Context ctx, String workingDate) 
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_WORKINGDATE, workingDate);
        editor.commit();
    }
    
  
   
    
  
    public static void setBranchName(Context ctx, String branchName) 
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_BRANCHNAME, branchName);
        editor.commit();
    }
    
    public static void setBranchCode(Context ctx, String branchCode) 
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_BRANCHCODE, branchCode);
        editor.commit();
    }
    
    public static void setUserName(Context ctx, String username) 
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USERNAME, username);
        editor.commit();
    }
    
    public static void setcentercodelist(Context ctx, String centercodelist)
    {
    	Editor editor = getSharedPreferences(ctx).edit();
    	editor.putString(CENTERCODELIST, centercodelist);
    	editor.commit();
    }
    
    public static void setCustomerType(Context ctx, String customerType)
    {
    	Editor editor = getSharedPreferences(ctx).edit();
    	editor.putString(PREF_CUSTOMERTYPE, customerType);
    	editor.commit();
    }
    
    public static void setzonecode(Context ctx, String zonecode)
    {
    	Editor editor = getSharedPreferences(ctx).edit();
    	editor.putString(ZONECODE, zonecode);
    	editor.commit();
    }

    public static void setPincode(Context ctx, String pincode)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PINCODE, pincode);
        editor.commit();
    }
    
    // methods to get the detail

   
    
    
    public static String getPassword(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_PASSWORD, "");
    }
    
    public static String getUserCode(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_CODE, "");
    }

    public static String getSeasonMstId(Context ctx)
    {
        return getSharedPreferences(ctx).getString(SEASON_MST_ID, "");
    }


   
    
    public static String getWorkingDate(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_WORKINGDATE, "");
    }
    
  
    
    public static String getBranchCode(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_BRANCHCODE, "");
    }
    public static String getBranchName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_BRANCHNAME, "");
    }
  
    
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USERNAME, "");
    }
    
    
    public static String getcentercodelist(Context ctx)
    {
    	return getSharedPreferences(ctx).getString(CENTERCODELIST,"");
    }
    
    public static String getCustomerType(Context ctx)
    {
    	return getSharedPreferences(ctx).getString(PREF_CUSTOMERTYPE, "");
    }
    
    public static String getzonecode(Context ctx)
    {
    	return getSharedPreferences(ctx).getString(ZONECODE, "");
    }

    public static String getPincode(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PINCODE, "");
    }


    public static void clearUserName(Context ctx) 
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

}
