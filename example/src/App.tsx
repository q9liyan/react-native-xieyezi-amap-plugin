import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { multiply } from 'react-native-xieyezi-amap-plugin';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  const getResult = async () => {
    const returnResult = await multiply(3, 8);
    setResult(returnResult);
  };

  React.useEffect(() => {
    getResult();
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
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
