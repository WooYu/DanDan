package com.dhxgzs.goodluck.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**��ֹViewPager���һ�������*/
public class MyViewPager extends ViewPager{
	  private boolean enabled = false;

	    public MyViewPager(Context context) {
	        super(context);
	    }

	    public MyViewPager(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }

	    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	        // �����¼�������
	        if (this.enabled) {
	            return super.onTouchEvent(event);
	        }
	        return false;
	    }

	    @Override
	    public boolean onInterceptTouchEvent(MotionEvent event) {
	        // �������������¼�
	        if (this.enabled) {
	            return super.onInterceptTouchEvent(event);
	        }
	        return false;
	    }

	    @Override
	    public boolean dispatchTouchEvent(MotionEvent event) {
	        // �ַ��¼�������Ǳ���Ҫ�ģ������������������ˣ���ôViewPager����View�ͽ��ղ����¼���
	        if (this.enabled) {
	            return super.dispatchTouchEvent(event);
	        }
	        return super.dispatchTouchEvent(event);
	    }

	    public void setPagingEnabled(boolean enabled) {
	        this.enabled = enabled;
	    }
}
