package com.hyphenate.easeui.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;

public class EaseUserUtils {
    
    static EaseUserProfileProvider userProvider;
    
    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }
    
    /**
     * get EaseUser according username
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
        	
            return userProvider.getUser(username);
        
        return null;
    }
    
    /**
     * set user avatar
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	EaseUser user = getUserInfo(username);
    	System.out.println("util用户名"+username);
//    	if(!username.equals("8001")){
    		
    	
        if(user != null && user.getAvatar() != null){
            try {
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) { 
                //use default avatar         .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar)
                Glide.with(context).load(user.getAvatar()).into(imageView);
            }
        }else{
            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
        }
//    	}else{
//    		
//    	}
    }
    public static void setUserGbVip(Context context, String username, ImageView imageView){
    	EaseUser user = getUserInfo(username);
    	System.out.println("util用户名"+username);
//    	if(!username.equals("8001")){
    		
    	
        if(user != null && user.getDgvip() != null){
            try {
                int avatarResId = Integer.parseInt(user.getDgvip());
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) { 
                //use default avatar         .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar)
                Glide.with(context).load(user.getDgvip()).into(imageView);
            }
        }else{
        	if(username.equals("8001")||username.equals("8002")||username.equals("8003")){
        		
        	}else{
        		
//        		Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
        	}
        }
//    	}else{
//    		
//    	}
    }
    
    /**
     * set user's nickname
     */
    public static void setUserNick(String username,TextView textView){
    	
    	if(textView != null){
        	EaseUser user = getUserInfo(username);
        	if(user != null && user.getNick() != null){
        		textView.setText(user.getNick());
        	}else{
        		textView.setText(username);
        	}
        }
    	
    }
    
}
