package com.dhxgzs.goodluck.util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class MyUtil {

	/**
	 * 获取手机操作系统版本
	 * 
	 * @return
	 * @author SHANHY
	 * @date 2015年12月4日
	 */
	public static int getSDKVersionNumber() {
		int sdkVersion;
		try {
			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			sdkVersion = 0;
		}
		return sdkVersion;
	}
	/**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        return imei;
    }
    
    public static String getAand(){
    	
    	String annd="http://";
    	
    	return annd;
    	
    }
}
