package com.reactnativexieyeziamapplugin.modules;


import androidx.annotation.NonNull;

import com.amap.api.location.AMapLocationListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(name = LocationModule.NAME)
public class LocationModule extends ReactContextBaseJavaModule implements AMapLocationListener{
    public static final String NAME = "XieyeziAmapLocationModule";
    private ReactApplicationContext reactContext;
    private DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter;
    private AMapLocationClient client;
    private AMapLocationClientOption option = new AMapLocationClientOption();


    public LocationModule(ReactApplicationContext reactContext) {

      super(reactContext);
      this.reactContext = reactContext;
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    @Override
    public void onLocationChanged(AMapLocation location) {
      if (location != null) {
        eventEmitter.emit("onLocationChanged", toJSON(location));
      }
    }

    @ReactMethod
    public  void init(String key, Promise promise) {
      if(client != null) {
        client.onDestroy();
      }
      try {
        client.updatePrivacyShow(reactContext, true, true);
        client.updatePrivacyAgree(reactContext, true);
        AMapLocationClient.setApiKey(key);
        client = new AMapLocationClient(reactContext);
        client.setLocationListener(this);
        eventEmitter = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        promise.resolve(null);
      }catch (Exception e) {
        promise.reject(e);
      }
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


    private ReadableMap toJSON(AMapLocation location) {
      if (location == null) {
        return null;
      }
      WritableMap map = Arguments.createMap();
      map.putInt("errorCode", location.getErrorCode());
      map.putString("errorInfo", location.getErrorInfo());
      map.putString("locationDetail", location.getLocationDetail());
      if (location.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
        map.putDouble("timestamp", location.getTime());
        map.putDouble("accuracy", location.getAccuracy());
        map.putDouble("latitude", location.getLatitude());
        map.putDouble("longitude", location.getLongitude());
        map.putDouble("altitude", location.getAltitude());
        map.putDouble("speed", location.getSpeed());
        map.putDouble("heading", location.getBearing());
        map.putInt("locationType", location.getLocationType());
        map.putString("coordinateType", location.getCoordType());
        map.putInt("gpsAccuracy", location.getGpsAccuracyStatus());
        map.putInt("trustedLevel", location.getTrustedLevel());
        if (!location.getAddress().isEmpty()) {
          map.putString("address", location.getAddress());
          map.putString("description", location.getDescription());
          map.putString("poiName", location.getPoiName());
          map.putString("country", location.getCountry());
          map.putString("province", location.getProvince());
          map.putString("city", location.getCity());
          map.putString("cityCode", location.getCityCode());
          map.putString("district", location.getDistrict());
          map.putString("street", location.getStreet());
          map.putString("streetNumber", location.getStreetNum());
          map.putString("adCode", location.getAdCode());
        }
      }
      return map;
    }
}
