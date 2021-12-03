package com.reactnativexieyeziamapplugin;


import androidx.annotation.NonNull;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(name = XieyeziAmapPluginModule.NAME)
public class XieyeziAmapPluginModule extends ReactContextBaseJavaModule {
    public static final String NAME = "XieyeziAmapPlugin";
    private final ReactApplicationContext context;

    private AMapLocationClient client;
    private AMapLocationClientOption option = new AMapLocationClientOption();


    public XieyeziAmapPluginModule(ReactApplicationContext reactContext) {

      super(reactContext);
      this.context = reactContext;
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    @ReactMethod
    public  void init(String key, Promise promise) {
      if(client != null) {
        client.onDestroy();
      }
      AMapLocationClient.setApiKey(key);
      client = new AMapLocationClient(context);
      promise.resolve(null);
    }


    @ReactMethod
    public  void start() {
      client.startLocation();
    }

    @ReactMethod
    public void stop() {
      client.stopLocation();
    }

    @ReactMethod
    public void isStarted(Promise promise) {
      promise.resolve(client.isStarted());
    }

    @ReactMethod
    public void setLocationMode(String mode) {
      option.setLocationMode(AMapLocationClientOption.AMapLocationMode.valueOf(mode));
      client.setLocationOption(option);
    }


}
