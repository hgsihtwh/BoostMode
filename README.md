# BoostMode - Formula 1 Mobile App

## Требования

- JDK 17+
- Android SDK (API 26+)
- Gradle 8+

## Сборка APK через командную строку

### 1. Клонировать репозиторий

git clone https://github.com/ТВО_ИМЯ/BoostMode.git
cd BoostMode

### 2. Собрать debug APK

./gradlew assembleDebug

На Windows:
gradlew.bat assembleDebug

### 3. Найти собранный APK

После успешной сборки APK находится по пути:
app/build/outputs/apk/debug/app-debug.apk

### 4. Установить APK на устройство

adb install app/build/outputs/apk/debug/app-debug.apk

## Сборка release APK

./gradlew assembleRelease

APK будет по пути:
app/build/outputs/apk/release/app-release-unsigned.apk
