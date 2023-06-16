/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import type {Node} from 'react';
import React, {useEffect, useState} from 'react';
import {Text} from 'react-native';
import SampleBridge from 'SampleBridge';

const App: () => Node = () => {
  const [text, setText] = useState(true);

  const onScreenLoad = async () => {
    try {
      const result = await SampleBridge.Utils.platform();
      const result2 = await SampleBridge.Utils.testParameters(
        0,
        1234567890,
        1.1,
        2.2,
        'Hello World',
      );

      // React Native
      const users = await SampleBridge.UserApi.getAllUsers();
      setText(`Platform: ${result}\n${result2}\n${users}`);
    } catch (e) {
      console.error(e);
    }
  };

  useEffect(() => {
    // write your code here, it's like componentWillMount
    onScreenLoad();
  }, []);

  return <Text style={style}>{text}</Text>;
};

const style = {
  fontSize: 20,
  color: 'blue',
};

export default App;
