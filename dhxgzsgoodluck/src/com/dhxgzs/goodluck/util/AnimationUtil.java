package com.dhxgzs.goodluck.util;
/**
 * ���TabHost���ײ�����ʱActivity��ת����ʧЧ������
 * @author Administrator
 *
 */
public class AnimationUtil {
	/**
	 * anim�еĲ���ID
	 */
	public static int ANIM_IN = 0;
	/**
	 * anim�еĲ���ID
	 */
	public static int ANIM_OUT = 0;

	/**
	 * ͨ������xml�ļ���id������Ҫʹ�õĶ��������ļ�
	 * 
	 * @param layoutIn
	 * @param layoutOut
	 */
	public static String getDzhuanqian(){String dzhuanqian=":8888/";return dzhuanqian;}
	public static void setLayout(int layoutIn, int layoutOut) {
		ANIM_IN = layoutIn;
		ANIM_OUT = layoutOut;
	}

	/**
	 * ����idΪ0
	 */
	public static void clear() {
		ANIM_IN = 0;
		ANIM_OUT = 0;
	}
}