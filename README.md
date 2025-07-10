# GameOfLife

Pet project to experiment with Kotlin Multiplatform

## Structure

* `/shared` the business logic shared across all the applications.
  - `commonMain` is for code that's common for all targets.
  - `androidMain`, `iosMain` and `jvmMain` for target specific implementation
* `/composeApp` the Android and Jvm application using Jebrains Compose for its user interface.
* `/iosApp` the iOS application using SwiftUI for its user interface.

## Screenshots

### Android
<p align="middle">
    <img src="img/screenshot_android_1.png" alt="Android screenshot 1" width="30%" />
    <img src="img/screenshot_android_2.png" alt="Android screenshot 2" width="30%" /> 
    <img src="img/screenshot_android_3.png" alt="Android screenshot 3" width="30%" /> 
</p>

<p align="middle">
    <img src="img/screenshot_android_4.png" alt="Android screenshot 4" width="100%" /> 
</p>

### Desktop
<p align="middle">
    <img src="img/screenshot_desktop.png" alt="Desktop screenshot" width="100%" /> 
</p>

### iPhone
<p align="middle">
    <img src="img/screenshot_iphone_1.png" alt="iPhone screenshot 1" width="30%" />
    <img src="img/screenshot_iphone_2.png" alt="iPhone screenshot 2" width="30%" /> 
</p>
