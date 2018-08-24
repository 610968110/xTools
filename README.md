        
xTools是一个在三方框架上和androidApi上进行二次封装的框架，里面包含很多工具类，如检查apk的
deBug版本、判断主线程弹出Toast、log打印管理、Http网络请求（RxAndroid2+Retrofit2）、
BaseActivity/BaseFragment、一下常用的自定义控件、MVVM等。封装了很多简化的工具类
可以满足大部分需求。      
        
使用很简单：XTools.xxxUtil().method(); 
        
             
一、依赖包和相关配置
====
            
        
需要权限：
````Xml
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.USE_FINGERPRINT" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
````
       
                
                
项目的build文件添加：

````Xml    
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
 ````
        
        
        
module的build文件添加：

````Xml 
    //本包的引入
    compile "com.lbx:xTools:2.1.2"
````
        
        
        
android标签下：  

````Xml 
    dataBinding {       
        enabled = true      
    } 
````



xTools依赖的三方框架，pom会自动引用，若有版本变化需求请自行引用覆盖并更改版本号：

````Xml 
    compile 'com.jakewharton:butterknife:8.5.1'
    //这里注意，因为和dataBinding冲突，所以不能用apt        
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.5.1'            
    compile 'com.android.support:recyclerview-v7:26+'           
    compile 'com.google.code.gson:gson:2.7'             
    compile 'io.reactivex.rxjava2:rxjava:2.0.1'
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
    compile 'com.squareup.retrofit2:retrofit:2.2.0'
    compile 'com.squareup.okhttp3:logging-interceptor:3.6.0'
    compile 'com.squareup.retrofit2:converter-gson:2.2.0'
    compile 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0'
    compile 'com.android.support:design:26.+'
    compile 'com.google.dagger:dagger:2.+'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.+'
    compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
````

       
二、初始化
====

```Java     
        boolean debug = XTools.ApkUtil().isApkInDebug(this);
        XTools xTools = new XTools.Builder()
                        .log(/*是否打印log*/true)
                        .logTag(/*设置log的tag*/"xys")
                        .logLevel(/*设置显示log的级别*/debug ? LEVEL_VERBOSE : LEVEL_NONE)
                        .errLogFilePath(/*设置crashLog的文件存储路径*/"xTools")
                        .errLogFileName(/*设置crashLog的文件存储名*/"crash")
                        .errLogFile(
                                /*是否打印到文件*/true,
                                /*是否打印到log*/debug)
                        .logPrintFile(
                                /*是否打印log到文件*/true,
                                /*打印log文件在sd卡下的路径*/"xTools/log", 
                                /*打印到file的log是否加密(des+base64对称加密), null为不加密*/ "lbx")
                        .build(this);         
        //初始化           
        xTools.init();          
```     
        
三、XWebView相关配置：
===
````Xml
        <!-- 定义FileProvider -->
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="{packageName}.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_provider" />
        </provider>
````
四、XTabLayout的相关属性：
===
````Xml
 <!--底部分隔栏的颜色-->
        <attr name="bottomLineColor" format="color" />
        <!--竖直分隔线的颜色-->
        <attr name="dividerColor" format="color" />
        <!--底部分隔栏的高度-->
        <attr name="bottomLineHeight" format="dimension" />
        <!--竖直分隔线的宽与高-->
        <attr name="dividerWidth" format="dimension" />
        <attr name="dividerHeight" format="dimension" />
        <attr name="bottomLineMode">
            <!--不显示底部分割栏-->
            <enum name="none" value="0" />
            <!--显示在滑动栏外面-->
            <enum name="outer" value="1" />
            <!--显示在滑动栏里面-->
            <enum name="inner" value="2" />
        </attr>
        <!--下划线的宽度-->
        <attr name="tabLineWidth" format="dimension" />

           <lbx.xtoollib.view.tablayout.XTabLayout
                android:id="@+id/tl_police_news"
                android:layout_width="match_parent"
                android:layout_height="30dp"
                android:background="@color/news_two_level_bg_color"
                app:tabBackground="@color/news_two_level_bg_color"
                app:tabIndicatorColor="@color/news_two_level_indicator_color"
                app:tabSelectedTextColor="@color/news_two_level_select_color"
                app:tabTextAppearance="@style/TabNewsTwoLevelTextStyle"
                app:tabGravity="fill"
                app:tabMinWidth="45dp"
                app:tabMode="scrollable"
                app:tabIndicatorHeight="3dp"
                app:tabLineWidth="10dp"
                app:tabTextColor="@color/news_two_level_normal_color">

            </lbx.xtoollib.view.tablayout.XTabLayout>
````

五、关于XBottomBar
====
在项目中覆盖以下属性：
````Xml
<dimen name="bottom_bar_textview_height">16dp</dimen>
<dimen name="bottom_bar_text_size">10dp</dimen>
<dimen name="bottom_bar_img_size">22dp</dimen>
<dimen name="bottom_bar_point_size">8dp</dimen>
<dimen name="bottom_bar_point_text_size">6dp</dimen>
<dimen name="bottom_bar_point_offset_x">2dp</dimen>
<dimen name="bottom_bar_point_offset_y">2dp</dimen>
<color name="bottombar_item_textcolor_normal">#cccccc</color>
<color name="bottombar_item_textcolor_select">#A00000</color>
````

API：
````Java
mBottomBar = new BottomBar(this);
//设置文字颜色
mBottomBar.setTextStateColor(R.color.bottombar_item_textcolor_normal, R.color.bottombar_item_textcolor_select);
//绑定viewpager  默认跳到HOME页面  TextView不为null会显示设置FragmentInfo的名字 boolean滚动动画
mBottomBar.bind(mBinding.vpMain, (TextView) null, false, FragmentType.HOME.getPos());
//显示消息点
mBottomBar.showNoticePoint(pos);
````

六、关于log解密
====
仅需要一行代码：
````Java
StringBuilder builder = new SecurityUtil(key).decryptFile(file);
````

#### 版本升级说明
##### 2.1.0
1、升级Log库，使用方法 `xLogUtil.e(this,"****");`重载方法的时候，会在Logcat
中定位到打印log的位置;