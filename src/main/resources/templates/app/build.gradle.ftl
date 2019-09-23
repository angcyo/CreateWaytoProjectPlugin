ext {
    V_CODE = "1"
    V_NAME = "1.0.0"
    schema = "${schema}"

    application_id = "${packageName}"
    plugin_name = "${packageName}"
}

apply from: allGradle.key

android {
    defaultConfig {
        buildConfigField "String", "CUSTOM_URL", '"http://116.7.249.36:17100/"'

        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }

    productFlavors {
        //develop
        _dev {
            //buildConfigField "String", "CUSTOM_URL", '"http://api.dev.wayto.com.cn:8080/"'
        }
        //preview
        pre {
            //buildConfigField "String", "CUSTOM_URL", '"http://api.dev.wayto.com.cn:8080/"'
        }
        //apk
        apk {
            //buildConfigField "String", "CUSTOM_URL", '"http://47.107.208.147:38500/"'
        }
    }
    packagingOptions {
        exclude 'META-INF/LGPL2.1'
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/NOTICE'
    }
}

dependencies {
    implementation project(allModule.plugin)
    implementation project(allModule.objectbox)
//    implementation project(allModule.Camera)
//    implementation project(allModule.tesstwo)
//    implementation project(allModule.opencv)
//    implementation project(allModule.ex)
//    implementation project(allModule.mapbox)
    implementation project(allModule.map)
    implementation project(allModule.rcode)
    implementation project(allModule.mqtt)
//    implementation project(allModule.websocket)
    implementation project(allModule.agora)

    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
}

apply from: allGradle.plugin
apply from: allGradle.v7a
apply plugin: 'kotlin-kapt'
apply plugin: 'io.objectbox'