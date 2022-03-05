# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-optimizationpasses 5  
-dontusemixedcaseclassnames  
-dontskipnonpubliclibraryclasses  
-dontpreverify  
-verbose  
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  

-ignorewarnings 
  
-keepattributes *Annotation*  
-keepattributes Signature

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}


-libraryjars src/main/jniLibs/armeabi/libeasemob_jni.so
-libraryjars src/main/jniLibs/armeabi/libeasemobservice.so
-libraryjars src/main/jniLibs/armeabi/libBase64zzq.so
-libraryjars src/main/jniLibs/armeabi/libtnet-2.0.17.2-agoo.so
-libraryjars src/main/jniLibs/armeabi/libcocklogic.so
-libraryjars src/main/jniLibs/armeabi/liblocSDK6a.so


-libraryjars src/main/jniLibs/armeabi-v7a/libcocklogic.so
-libraryjars src/main/jniLibs/armeabi-v7a/libtnet-2.0.17.2-agoo.so
-libraryjars src/main/jniLibs/armeabi-v7a/libBase64zzq.so


-libraryjars src/main/jniLibs/arm64-v8a/libeasemobservice.so
-libraryjars src/main/jniLibs/arm64-v8a/liblocSDK6a.so
-libraryjars src/main/jniLibs/arm64-v8a/libeasemob_jni.so
-libraryjars src/main/jniLibs/arm64-v8a/libBase64zzq.so




-libraryjars src/main/jniLibs/x86/libeasemobservice.so
-libraryjars src/main/jniLibs/x86/libeasemob_jni.so
-libraryjars src/main/jniLibs/x86/liblocSDK6a.so
-libraryjars src/main/jniLibs/x86/libtnet-2.0.17.2-agoo.so
-libraryjars src/main/jniLibs/x86/libcocklogic.so
-libraryjars src/main/jniLibs/x86/libBase64zzq.so




-libraryjars src/main/jniLibs/x86_64/liblocSDK6a.so
-libraryjars src/main/jniLibs/x86_64/libeasemobservice.so
-libraryjars src/main/jniLibs/x86_64/libBase64zzq.so


#-libraryjars libs/android-support-multidex.jar
#-libraryjars libs/alipaysdk.jar
#-libraryjars libs/alipaysecsdk.jar
#-libraryjars libs/alipayutdid.jar
#-libraryjars libs/AndroidViewAnimations-1.1.3.jar
#-libraryjars libs/apache-commons-lang.jar
#-libraryjars libs/com.umeng.message.lib_v2.5.0.jar
#-libraryjars libs/core.jar
#-libraryjars libs/easemobchat_2.2.7.jar
#-libraryjars libs/fastjson-1.1.41.jar
#-libraryjars libs/httpmime-4.1.2.jar
#-libraryjars libs/nineoldandroids-2.4.0.jar
#-libraryjars libs/SocialSDK_QQZone_1.jar
#-libraryjars libs/SocialSDK_QQZone_2.jar
#-libraryjars libs/SocialSDK_QQZone_3.jar
#-libraryjars libs/SocialSDK_Sina.jar
#-libraryjars libs/SocialSDK_WeiXin_1.jar
#-libraryjars libs/SocialSDK_WeiXin_2.jar
#-libraryjars libs/umeng_social_sdk.jar
















-keep class com.easemob.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
-dontwarn  com.easemob.**
#2.0.9后的不需要加下面这个keep
#-keep class org.xbill.DNS.** {*;}
#另外，demo中发送表情的时候使用到反射，需要keep SmileUtils,注意前面的包名，
#不要SmileUtils复制到自己的项目下keep的时候还是写的demo里的包名
-keep class com.easemob.chatuidemo.utils.SmileUtils {*;}








-keep public class * extends android.app.Fragment  
-keep public class * extends android.app.Activity
-keep public class * extends android.app.FragmentActivity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService

-keep class com.baidu.** { *; } 
-keep class vi.com.gdi.bgl.android.**{*;}

-dontwarn android.support.v4.**  
-dontwarn org.apache.commons.net.** 
-dontwarn com.tencent.** 

-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }
-keep interface webkit.JavascriptInterface.** { *; }

-keepattributes *Annotation*

-keepattributes *JavascriptInterface*

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}



-dontwarn net.poemcode.**

-dontshrink
-dontoptimize

-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**

-keepclassmembers class com.yssj.ui.fragment.MineShopFragment$upLoadImage {
  public *;
}
-keepclassmembers class com.yssj.ui.activity.myshop.ShopBeautifulActivity$upLoadImage {
  public *;
}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keep public class com.yssj.activity.R$*{
public static final int *;
}

-keepattributes Exceptions,InnerClasses,Signature,EnclosingMethod

-keepattributes SourceFile,LineNumberTable

-keepclassmembers class com.yssj.ui.activity.myshop.ChooseModelActivity{
   public *;
}
-keepclassmembers class com.yssj.ui.activity.myshop.ShopBeautifulActivity{
   public *;
}

-keepclassmembers   class com.yssj.ui.fragment.MineShopFragment$*{
    *;
}


-keepclassmembers   class com.yssj.ui.activity.myshop.ChooseModelActivity$*{
    *;
}
-keepclassmembers   class com.yssj.ui.fragment.myshop.ShopBeautifulActivity$*{
    *;
}

-keepclassmembers public class * extends android.view.View {
   *;
}
-keepclassmembers public class * extends android.database.sqlite.SQLiteOpenHelper{
   *;
}

 -keepclassmembers public class com.yssj.model.ComModel2{
 	*; 
 }
 
 -keepclassmembers public class com.yssj.model.ComModel{
 	*; 
 }
 
  -keepclassmembers public class com.yssj.utils.EntityFactory{
 	*; 
 }
 
 
 
 
-keepclassmembers   class com.yssj.utils.YCache{
    *;
}

-keepclassmembers   class com.yssj.ui.activity.MainMenuActivity{
    *;
}

-keepclassmembers   class com.yssj.network.YConn{
    *;
}

-keepclassmembers   class com.yssj.utils.SharedPreferencesUtil{
    *;
}




# 保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {                       
   public void *(android.view.View);
}

# 保持自定义控件类不被混淆
-keepclassmembers class * extends android.os.AsyncTask {                       
  *;
}

# 
-keepclassmembers class * extends android.app.PopupWindow {                       
  *;
}



# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {                                                     
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {                             
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# http client
-keep class org.apache.http.** {*; }


-dontwarn android.support.**
-dontwarn com.alibaba.fastjson.**
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses
-keep class com.alibaba.fastjson.** { *; }
-keepclassmembers class * {
public <methods>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    public <fields>;
}
-keepattributes Signture


# keep 泛型
-keepattributes Signature


-keepclasseswithmembers class * {                                               
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);     
}

-keepclasseswithmembers class * {
    void onClick*(...);
}
-keepclasseswithmembers class * {
    *** *Callback(...);
}

-keepclasseswithmembernames class com.yssj.entity.Business
{
*;
}
	
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}


#UMeng推送
-keep class com.umeng.message.* {
        public <fields>;
        public <methods>;
}

-keep class com.umeng.message.protobuffer.* {
        public <fields>;
        public <methods>;
}

-keep class com.squareup.wire.* {
        public <fields>;
        public <methods>;
}

-keep class com.umeng.message.local.* {
        public <fields>;
        public <methods>;
}
-keep class org.android.agoo.impl.*{
        public <fields>;
        public <methods>;
}

-keep public class [your_pkg].R$*{  
    public static final int *;  
}  


-keep class org.android.agoo.service.* {*;}

-keep class org.android.spdy.**{*;}

-keep public class com.yssj.activity.R$*{
    public static final int *;
}



-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;} 
-dontwarn okio.**
 
-dontwarn com.handmark.pulltorefresh.library.**
-keep class com.handmark.pulltorefresh.library.** { *;}
-dontwarn com.handmark.pulltorefresh.library.extras.**
-keep class com.handmark.pulltorefresh.library.extras.** { *;}
-dontwarn com.handmark.pulltorefresh.library.internal.**
-keep class com.handmark.pulltorefresh.library.internal.** { *;}







-keep class com.easemob.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
-dontwarn  com.easemob.**
#2.0.9后的不需要加下面这个keep
#-keep class org.xbill.DNS.** {*;}
#另外，demo中发送表情的时候使用到反射，需要keep SmileUtils
-keep class com.yssj.huanxin.utils.SmileUtils {*;}
#注意前面的包名，如果把这个类复制到自己的项目底下，比如放在com.example.utils底下，应该这么写(实际要去掉#)
#-keep class com.example.utils.SmileUtils {*;}
#如果使用easeui库，需要这么写
-keep class com.easemob.easeui.utils.EaseSmileUtils {*;}

#2.0.9后加入语音通话功能，如需使用此功能的api，加入以下keep
-dontwarn ch.imvs.**
-dontwarn org.slf4j.**
-keep class org.ice4j.** {*;}
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}

