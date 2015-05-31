# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/banxi/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
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


### for retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

### for picasso
-dontwarn com.squareup.okhttp.**

### for Module:app
-keep com.banxi1988.v2exgeek.model.**{ *;}
-keep com.banxi1988.v2exgeek.api.**{ *;}
