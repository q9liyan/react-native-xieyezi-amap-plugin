import { NativeModules, NativeEventEmitter, Platform } from 'react-native';
import type { AppKey } from './types';

const XiyeziAmapPlugin = NativeModules.XieyeziAmapLocationModule;
const eventEmitter = new NativeEventEmitter(XiyeziAmapPlugin);

export function init(key: AppKey) {
  XiyeziAmapPlugin.init(Platform.select(key));
}

export function start() {
  XiyeziAmapPlugin.start();
}

export function stop() {
  XiyeziAmapPlugin.stop();
}

export function isStarted() {
  return XiyeziAmapPlugin.isStarted();
}

/**
 * 添加定位监听函数
 *
 * @param listener
 */
export function addLocationListener(listener: (location: any) => void) {
  return eventEmitter.addListener('onLocationChanged', listener);
}

export function getCurrentPosition(
  successCallback: (position: any) => void,
  errorCallBack?: (err: string) => void
) {
  const listener = addLocationListener((location) => {
    console.log(location);
    if (location.errorCode !== 0) {
      errorCallBack && errorCallBack(location.errorCode + location.errorInfo);
      stop();
      return listener.remove();
    }
    successCallback(location);
    stop();
    return listener.remove();
  });
  start();
}
