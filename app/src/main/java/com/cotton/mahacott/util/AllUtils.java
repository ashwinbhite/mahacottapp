/**
 * NetBanking
 * Copyright (C) 2013 Virtual Galaxy Infotech Pvt. Ltd.
 * This program is copyrighted; you cannot redistribute it or modify it without prior permission of VGIPL.
 * You cannot keep copy or use it for commercial purpose.
 * @AUTHOR Chetana Meshram
 * @DATE 08-May-2013
 * @VERSION 1.1
 */
/**

 * This Class is for Formatting Date
 * @param parameter_name1 parameter_desc1
 * @param parameter_name1 parameter_desc1
 * @param parameter_name1 parameter_desc1
 * @return return_param_value_desc
 */
package com.cotton.mahacott.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.Intent;

public class AllUtils {

	public static String getFormattedDate(String strDate){

		try 
		{
			strDate = strDate.replaceAll("-", "/");

		} catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in Formatting Date "+e);
		}
		return  strDate;
	}

	public static String getFormattedDateForXML(String strDate)
	{

		try
		{
			strDate = strDate.replaceAll("/", "-");

		} catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Eception in Formatting Date "+e);
		}
		return  strDate;
	}

	public static String getFormattedDateForSql(String strDate)
	{
		String sDate = null;

		try 
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
			sDate = myformat.format(fromformat.parse(strDate));
			System.out.println("Date received "+sDate);

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception in Formatting Date "+e);
		}
		return sDate;
	}

	public static String getFormattedDateForSummary(String strDate)
	{
		String sDate = null;

		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
			sDate = myformat.format(fromformat.parse(strDate));
			System.out.println("Date received "+sDate);

		} 
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in Formatting Date "+e);
		}
		return sDate;
	}

	public static String getFormattedDateForSqlForWorkingdate(String strDate)
	{
		String sDate = null;

		try 
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
			sDate = myformat.format(fromformat.parse(strDate));
			//System.out.println("Date is "+sDate);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in Formatting Date "+e);
		}
		return sDate;
	}
	public static String getFormattedDateForSqlForSeasonDate(String strDate)
	{
		String sDate = null;

		try 
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
			sDate = myformat.format(fromformat.parse(strDate));
			//System.out.println("Date is "+sDate);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in Formatting Date "+e);
		}
		return sDate;
	}


	public static String getFormattedDateForSqlnew(String strDate)
	{
		String sDate = null;

		try 
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("M/d/yyyy");
			sDate = myformat.format(fromformat.parse(strDate));
			System.out.println("Date is "+sDate);

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception in Formatting Date "+e);
		}
		return sDate;
	}

	public static String getCustomFormattedDateForSql(String strDate,String format)
	{
		String sDate = null;

		try 
		{
			SimpleDateFormat fromformat = new SimpleDateFormat(format);//"yyyy-MM-dd"
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
			sDate = myformat.format(fromformat.parse(strDate));
			System.out.println("Date is "+sDate);

		} catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in Formatting Date "+e);
		}
		return sDate;
	}

	public static String getroundvalue(Double value)
	{
		String formate = "0.00";

		try 
		{
			DecimalFormat df = new DecimalFormat("0.00");
			formate = df.format(value); 
			System.out.println("Format is "+formate);

		}
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("Check Exception in rounding value "+e.getMessage());
		} 
		return formate;
	}
	public static String getroundvalueforFloat(float value)
	{
		String formate = "0.00";

		try 
		{
			DecimalFormat df = new DecimalFormat("0.00");
			formate = df.format(value); 
			System.out.println("Format is "+formate);

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("Check Exception in rounding value "+e.getMessage());
		} 
		return formate;
	}

	public static String getroundvalueforFloatCustom(float value)
	{
		String formate = "0.00";

		try 
		{
			DecimalFormat df = new DecimalFormat("0.000");
			formate = df.format(value); 
			System.out.println("Format is "+formate);

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("Check Exception in rounding value "+e.getMessage());
		} 
		return formate;
	}
	
	// NEW Method for number formate 
	
	public static String getroundvalueforFloatCustomNew(double value)
	{
		String formate = "0.000";

		try 
		{
			DecimalFormat df = new DecimalFormat("0.000");
			formate = df.format(value); 
			System.out.println("Format is "+formate);

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("Check Exception in rounding value "+e.getMessage());
		} 
		return formate;
	}
	
	public static String round(double price) 
	{
		NumberFormat numFormat = NumberFormat.getInstance();
		numFormat.setMinimumFractionDigits(2);
		numFormat.setMaximumFractionDigits(2);
		return numFormat.format(price);
	}
	public static String getFormattedDateForSqlDashnew(String strDate)
	{
		String sDate = null;

		try 
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
			sDate = myformat.format(fromformat.parse(strDate));
			System.out.println("Date is "+sDate);

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception in Formatting Date "+e);
		}
		return sDate;
	}
	
	public static String getFormattedDatenew(String strDate)
	{
		String sDate = null;

		try 
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
			sDate = myformat.format(fromformat.parse(strDate));
			System.out.println("Date is "+sDate);

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception in Formatting Date "+e);
		}
		return sDate;
	}
	
	public static String getFormattedDateForAuction(String strDate)
	{
		String sDate = null;

		try 
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
			sDate = myformat.format(fromformat.parse(strDate));
			System.out.println("Date is "+sDate);

		} 
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in Formatting Date "+e);
		}
		return sDate;
	}
	
	
	// date formation for bill receipt 
	public static String getFormattedDateForBillReport(String strDate)
	{
	String sDate = null;
	try 
	{
		SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
		sDate = myformat.format(fromformat.parse(strDate));
		System.out.println("Date is "+sDate);

	} 
	catch(Exception e) 
	{
		e.printStackTrace();
		System.out.println("Exception in Formatting Date "+e);
	}
	return sDate;
 }
   /*formatting date for refreshing current date */
	public static String getFormattedDateForCurrentDate(String strDate)
	{
	String sDate = null;
	try 
	{
		SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat myformat = new SimpleDateFormat("dd-MMM-yyyy");
		sDate = myformat.format(fromformat.parse(strDate));
		System.out.println("Date is------- "+sDate);
		
		
	} 
	catch(Exception e) 
	{
		e.printStackTrace();
		System.out.println("Exception in Formatting Date "+e);
	}
	
	return sDate;
	
	
	
	
 }

	/*formatting date for refreshing current date */
	public static String getFormattedDateForWeighment(String strDate)
	{
	String sDate = null;
	try 
	{
		SimpleDateFormat fromformat = new SimpleDateFormat("dd/M/yyyy");
		SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
		sDate = myformat.format(fromformat.parse(strDate));
		System.out.println("Date is------- "+sDate);
		
		
	} 
	catch(Exception e) 
	{
		e.printStackTrace();
		System.out.println("Exception in Formatting Date "+e);
	}
	
	return sDate;
	
	
	
	
 }
	
	public  static String getUserFormatedDateForDisplay(String strDate)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("dd-MMM-yyyy");
			sDate = myformat.format(fromformat.parse(strDate));
			System.out.println("date received "+sDate);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}
	
	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return false;
		return true;
	}
	
}
