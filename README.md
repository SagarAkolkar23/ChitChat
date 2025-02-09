# ChitChats - Real-Time Chat Application

## 📌 Overview

ChitChats is a real-time chat application built using Android Studio with Jetpack Compose. It allows users to authenticate using Firebase Authentication and send real-time messages using Firebase Realtime Database. This project provides a simple and efficient way to connect users through instant messaging.

## ✨ Features

**🔑 Firebase Authentication**: Users can sign up and log in using email/password authentication.

**💬 One-to-One Messaging**: Users can send and receive messages in real-time.

**🚀 Jetpack Compose UI**: A modern and responsive user interface.

**🔥 Firebase Realtime Database**: Ensures instant message synchronization.

**📄 User Registration**: Users are stored in the database with their details.

## 🛠️ Tech Stack

**Programming Language**: Kotlin

**Frontend**: Jetpack Compose

**Backend**: Firebase Authentication, Firebase Realtime Database

**Architecture**: MVVM (Model-View-ViewModel)

**Dagger-Hilt**: Dependency Injection

## 📂 Project Structure

ChitChats/  
│── app/  
│   ├── src/  
│   │   ├── main/  
│   │   │   ├── java/com/example/chitchats/  
│   │   │   │   ├── authentication/         
│   │   │   │   ├── chatbackend/             
│   │   │   │   ├── model/                   
│   │   │   │   ├── ui/                      
│   │   │   │   ├── MainActivity.kt          
│   │   │   ├── AndroidManifest.xml  
│   ├── build.gradle  
│── README.md  

##🔥 Firebase Setup

Create a Firebase project at Firebase Console.

Enable Firebase Authentication and select Email/Password authentication.

Enable Firebase Realtime Database and set rules to:

{  
  "rules": {  
    ".read": "auth != null",  
    ".write": "auth != null"  
  }  
}  

Download the google-services.json file and place it in the app/ directory.

**🚀 Future Enhancements**

**✅ Group Chat Functionality**

**✅ Push Notifications**

**✅ Image & File Sharing**

**✅ Dark Mode UI**

**🤝 Contributing**

Feel free to fork this repository and submit a pull request! Contributions are always welcome.

