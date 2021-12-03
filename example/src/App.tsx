import * as React from 'react';
import {
  StyleSheet,
  View,
  Text,
  Button,
  PermissionsAndroid,
} from 'react-native';
import XiyeziAmapPlugin from 'react-native-xieyezi-amap-plugin';

export default function App() {
  const [location, setLocation] = React.useState(null);
  const { init, start, getCurrentLocation } = XiyeziAmapPlugin;

  const amapInit = async () => {
    await PermissionsAndroid.requestMultiple([
      PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
      PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION,
    ]);
    await init('67fa3daaa4d6ad8f88d4d8713b362d30');
    console.log('已经初始化...');
  };

  const getLocation = async () => {
    const target = await getCurrentLocation();
    console.log(target);
    setLocation(target);
  };

  const startPostion = async () => {
    try {
      await start();
      // console.log('开始定位...');
    } catch (error) {
      console.log(error);
    }
  };

  React.useEffect(() => {
    amapInit();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <View style={styles.container}>
      <Button title="开始定位" onPress={startPostion} />
      <Button title="获取location" onPress={getLocation} />
      <Text>{location}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
