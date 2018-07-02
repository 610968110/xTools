
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

    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
        
module的build文件添加：

    //本包的引入
    compile "com.lbx:xtoolslib:2.0.2"
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
    
    android标签下：  

    dataBinding {       
        enabled = true      
    }   
       
二、初始化
====

```Java     
        XTools xTools = new XTools.Builder()        
                        //打印log         
                        .log(true)      
                        //设置log的tag     
                        .logTag("xys")      
                        //设置显示log的级别        
                        .logLevel(xLogUtil.LEVEL_VERBOSE)       
                        //设置crashLog的文件sd卡存储路径     
                        .errLogFilePath("xTools")       
                        //设置crashLog的文件存储名      
                        .errLogFileName("ERR")      
                        //设置crashLog的打印
                        //第一个参数是打印到文件，第二个参数是打印到IDE        
                        .errLogFile(false, true)              
                        // 是否输出log到文件    
                        // 文件在sd卡上的路径       
                        // 第三个参数:加密的key，打印到file的log是否加密(des+base64非对称加密) null为不加密        
                        .logPrintFile(true, "xTools", "lbx")               
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

五、关于log解密
====
仅需要一行代码：
````Java
StringBuilder builder = new SecurityUtil(key).decryptFile(file);
````