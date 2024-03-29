# Klue

A unified bridge implementation with Kotlin multiplatform for JavaScript running on Android and iOS. WebView and React
Native are supported.

## Introduction

**Klue** is short for **Kotlin Glue**, and it is pronounced the same as **clue**. **Klue** is helpful when you want to
call native methods from JavaScript in mobile Apps.

## Usage

Create a bridge project according to **sample-bridge**, add dependencies to your project:

```kts
// runtime
kotlin {
    ...
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project("com.bennyhuo.kotlin:klue-runtime-common:1.0-Beta"))
                // for WebView
                api(project("com.bennyhuo.kotlin:klue-runtime-webview:1.0-Beta"))
                // for React Native project
                api(project("com.bennyhuo.kotlin:klue-runtime-reactnative:1.0-Beta"))
            }
        }
        ...
    }
}

// compile time
dependencies {
    "kspJvm"("com.bennyhuo.kotlin:klue-compiler:1.0-Beta") 
    "kspIosX64"("com.bennyhuo.kotlin:klue-compiler:1.0-Beta") 
    // see https://kotlinlang.org/docs/ksp-multiplatform.html
}

```

You will never need to define a method name and its parameter types in a document, then tell the WebView/React
Native/Android/iOS developers how to invoke or implement this method.

All you need is a Kotlin interface like this:

```kotlin
// common code
import com.bennyhuo.klue.annotations.KlueBridge

@KlueBridge
interface Utils {

    fun platform(): String

}
```

And its implementation on Android/iOS:

```kotlin
// Android
class UtilsImpl : Utils {
    override fun platform() = "Android!!"
}

// iOS
class UtilsImpl : Utils {
    override fun platform() = "iOS!!"
}
```

Register Klue bridge in your apps, then simply use it in your JavaScript code:

[WebView]
```html 
<script type="text/javascript" src="/SampleBridge.js"></script>
<script type="text/javascript" charset="UTF-8">
...
    SampleBridge.Utils.platform()
        .then((value) => {
            ...
        }).catch((err) => {
            ...
        })
...
</script>
```
[React Native]
```javascript
import {Utils} from 'SampleBridge';

const onPress = async () => {
    try {
        const result = await Utils.platform()
        console.log(`>>>> ${result}`)
    } catch (e) {
        console.error(e)
    }
}
```