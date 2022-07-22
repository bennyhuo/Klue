/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import type {Node} from 'react';
import React from 'react';
import {Text, NativeModules} from 'react-native';
import {Utils} from 'SampleBridge';

const onPress = async () => {
    try {
        console.log(NativeModules)
        console.log(NativeModules.KlueModule)
         const result = await Utils.platform()
         console.log(`>>>> ${result}`)

        const result2 = await Utils.testParameters(
            0,
            1234567890,
            1.1,
            2.2,
            "Hello World"
        )
        console.log(`>>>> ${result2}`)
    } catch (e) {
        console.error(e)
    }
}

const App: () => Node = () => {
  return (
    <Text style={style} onPress={onPress}>Hello World</Text>
  );
};

const style = {
    fontSize: 50,
    color: 'blue'
}

export default App;
