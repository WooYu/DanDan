package com.dhxgzs.goodluck.util;
/**
 * 解决TabHost做底部导航时Activity跳转动画失效工具类
 * @author Administrator
 *
 */
public class AnimationUtil {
	/**
	 * anim中的布局ID
	 */
	public static int ANIM_IN = 0;
	/**
	 * anim中的布局ID
	 */
	public static int ANIM_OUT = 0;

	/**
	 * 通过动画xml文件的id设置需要使用的动画布局文件
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
	 * 设置id为0
	 */
	public static void clear() {
		ANIM_IN = 0;
		ANIM_OUT = 0;
	}
}