package com.dhxgzs.goodluck.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;
import android.widget.Toast;

public class LimitEditText extends EditText{
	
	 public LimitEditText(Context context) {
	        super(context);
	    }

	    public LimitEditText(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }

	    public LimitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	    }

	    /**
	     * ���뷨
	     * @param outAttrs
	     * @return
	     */
	    @Override
	    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
	        return new mInputConnecttion(super.onCreateInputConnection(outAttrs),
	                false);
	    }
	}

	class mInputConnecttion extends InputConnectionWrapper implements
	        InputConnection {

	    private Context context;

		public mInputConnecttion(InputConnection target, boolean mutable) {
	        super(target, mutable);
	    }

	    /**
	     * ����������ݽ�������
	     *
	     * @param text
	     * @param newCursorPosition
	     * @return
	     */
	    @Override
	    public boolean commitText(CharSequence text, int newCursorPosition) {
	        // ֻ�����뺺��
	        if (!text.toString().matches("[\u4e00-\u9fa5]+")) {
	            return false;
	            
	        }
	        return super.commitText(text, newCursorPosition);
	    }

	    @Override
	    public boolean sendKeyEvent(KeyEvent event) {
	        return super.sendKeyEvent(event);
	    }

	    @Override
	    public boolean setSelection(int start, int end) {
	        return super.setSelection(start, end);
	    }


}
