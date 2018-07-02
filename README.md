# scan-speech

This is a small sample unifing Text to speech and QR Reader. This application has been made to help a student to present his project in front of a evaluation tribunal.

I'm not going to explain the idea but I'm going to talk about how is the application made.

## Libraries

The application uses the ZXing library, basically is a loop that allows users to capture qr codes.

Added

  implementation 'com.journeyapps:zxing-android-embedded:3.6.0'

inside gradle.

## Functionality

For every QR Code captured the library fires an event using [Text To Speech](https://developer.android.com/reference/android/speech/tts/TextToSpeech) that in spanish speak's the text stored inside the QR code.

## Usage

Only download the repository and press play inside Android Studio.
