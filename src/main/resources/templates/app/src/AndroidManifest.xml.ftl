<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="${packageName}">

    <application
        android:name=".App"
        android:allowBackup="true"
        android:icon="@mipmap/logo"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/logo"
        android:supportsRtl="false"
        android:theme="@style/AppTheme"
        tools:replace="android:supportsRtl">

        <!-- 启动页 -->
        <activity
            android:name=".SplashActivity"
            android:configChanges="locale|keyboardHidden|screenSize|fontScale|smallestScreenSize|density|screenLayout"
            android:screenOrientation="portrait"
            android:theme="@style/SplashTheme">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- 主页面 -->
        <activity
            android:name=".MainActivity"
            android:configChanges="locale|orientation|keyboardHidden|screenSize|fontScale|smallestScreenSize|density|screenLayout"
            android:launchMode="singleTask"
            android:screenOrientation="portrait"
            android:windowSoftInputMode="adjustResize"
            android:supportsPictureInPicture="true"/>
    </application>

</manifest>