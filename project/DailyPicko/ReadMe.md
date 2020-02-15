# Daily Picko - COMP90018 Project

---

This is an android application used for helping people to make a choice in their daily lives.



## Table of contents

* [General info](#general-info)
* [Screenshots for Compilation](#screenshots-for-compilation)
* [Technologies](#technologies)
* [Setup](#setup)
* [Meaning of icons](#meaning-of-icons)



## General info

+ The core value of this app is to help people who are experiencing hard time making choice during daily life.
+ An Android mobile app that is powered by Group 68 members
+ This is the front end part of the project

**File Structure**

![File Struture](/Users/Ethan/Desktop/fileStructure.png)

## Screenshots for Compilation

![compile screenshorts](/Users/Ethan/Desktop/compileScreenShot.png)

## Technologies

**Project is created with:**

+ Java version: 8
+ SDK version: 29 (Android 10.0 Q)

**Project Structure**

![project_strucure](/Users/Ethan/Desktop/project_strucure.png)

**Other technologies/libraries used:**

+ Google API for map(markers) and location detection
+ QRcode(Zxing library)

## Setup

**For the backend section**

Before going to run the project, you need to pull down and run the newest [backend code](https://github.com/LuChenyang3842/recom-flask-back-end) .

1. Using the terminal to run the backend code: 

   ```python
   python3 server.py
   ```

**For the frontend section**
After running the backend code, put your IP address into the following two parts:

1. ***Project name***/app/src/main/res/xml/network_security_config.xml

2. **Project name**/app/src/main/java/com/example/uberzomato/utils/Utils.java

   ```java
   public final static String BASE_URL = "add your ip here";
   ```

## Features

Already implemented features:

+ User signup, log in/out 
+ Add friend by scanning QR code
+ Broadcast voting system
+ Selection-based restaurant recommendation
+ Algorithm-based restaurant recommendation
+ Check-in function
+ Notification, Image update and so forth



To-do list:

+ chat part





## Meaning of icons 

+ You may find there are different icons showing on the map when you click the recommendation button. 

+ ![icon_description](/Users/Ethan/Desktop/icon_description.png)

