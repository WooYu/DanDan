package com.dhxgzs.goodluck.util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class MyUtil {

	/**
	 * ��ȡ�ֻ�����ϵͳ�汾
	 * 
	 * @return
	 * @author SHANHY
	 * @date 2015��12��4��
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
     * ��ȡ�ֻ�IMEI��
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
