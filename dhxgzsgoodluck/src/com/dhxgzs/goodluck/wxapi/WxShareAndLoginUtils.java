package com.dhxgzs.goodluck.wxapi;
 
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.Constant;
import com.dhxgzs.goodluck.app.XyApplication;
import com.dhxgzs.goodluck.util.Utils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;
 

/**
 * Created by xmg on 2016/11/29.
 */

public class WxShareAndLoginUtils {

    public static int WECHAT_FRIEND = 0;  //分享好友
    public static int WECHAT_MOMENT = 1;  //分享朋友圈
    private static IWXAPI iwxapi;

    public static IWXAPI getWXAPI(){
        if (iwxapi == null){
            //通过WXAPIFactory创建IWAPI实例
            iwxapi = WXAPIFactory.createWXAPI(XyApplication.appContext, Constant.WX_APPID, true);
            //将应用的appid注册到微信
            iwxapi.registerApp(Constant.WX_APPID);
        }
        return iwxapi;
    }

    /**
     * 微信登录
     */
    public static void WxLogin() {
        if (!judgeCanGo()){
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        //授权域 获取用户个人信息则填写snsapi_userinfo
        req.scope = "snsapi_userinfo";
        //用于保持请求和回调的状态 可以任意填写
        req.state = "test_login";
        iwxapi.sendReq(req);
        Log.d("test", "WxLogin");
    }

    /**
     * 分享文本至朋友圈
     * @param text  文本内容
     * @param judge 类型选择 好友-WECHAT_FRIEND 朋友圈-WECHAT_MOMENT
     */
    public static void WxTextShare(String text, int judge){
        if (!judgeCanGo()){
            return;
        }
        //初始化WXTextObject对象，填写对应分享的文本内容
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;
        //初始化WXMediaMessage消息对象，
        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = textObject;
        message.description = text;
        //构建一个Req请求对象
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());   //transaction用于标识请求
        req.message = message;
        req.scene = judge;      //分享类型 好友==0 朋友圈==1
        //发送请求
        iwxapi.sendReq(req);
    }

    /**
     *  分享图片
     * @param bitmap 图片bitmap,建议别超过32k
     * @param judge 类型选择 好友-WECHAT_FRIEND 朋友圈-WECHAT_MOMENT
     */
    public static void WxBitmapShare(Bitmap bitmap, int judge){
        if (!judgeCanGo()){
            return;
        }
        WXImageObject wxImageObject = new WXImageObject(bitmap);
        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = wxImageObject;

        Bitmap thunmpBmp = Bitmap.createScaledBitmap(bitmap,50,50,true);
        bitmap.recycle();
        message.thumbData = Utils.bmpToByteArray(thunmpBmp,true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = message;
        req.scene = judge;

        iwxapi.sendReq(req);

    }

    /**
     * 网页分享
     * @param url  地址
     * @param title 标题
     * @param description  描述
     * @param judge  类型选择 好友-WECHAT_FRIEND 朋友圈-WECHAT_MOMENT
     */
    public static void WxUrlShare(String url, String title, String description, int judge){
        if (!judgeCanGo()){
            return;
        }
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = url;

        WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
        wxMediaMessage.title = title;
        wxMediaMessage.description = description;
        Bitmap bmp = BitmapFactory.decodeResource(XyApplication.appContext.getResources(),
                R.drawable.pcdd_logo);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        // 缩略图 不能超过32k
        wxMediaMessage.thumbData = Utils.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMediaMessage;
        req.scene = judge;

        iwxapi.sendReq(req);
    }


    private static boolean judgeCanGo(){
        getWXAPI();
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(XyApplication.appContext, "请先安装微信应用", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!iwxapi.isWXAppSupportAPI()) {
            Toast.makeText(XyApplication.appContext, "请先更新微信应用", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
