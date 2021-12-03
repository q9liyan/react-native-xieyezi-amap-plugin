import { NativeModules } from 'react-native';

export interface XiyeziAmapPluginType {
  init: (key: string) => Promise<null>;
  start: () => Promise<any>;
  stop: () => void;
  isStarted: () => Promise<boolean>;
  getCurrentLocation: () => Promise<any>;
}

const XiyeziAmapPlugin =
  NativeModules.XieyeziAmapPlugin as XiyeziAmapPluginType;

export default XiyeziAmapPlugin;
