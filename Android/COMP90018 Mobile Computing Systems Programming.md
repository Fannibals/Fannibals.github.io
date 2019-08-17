### COMP90018 Mobile Computing Systems Programming

---

**WEEK 1 - Introduction, Android Studio, Git**

1. **Android Application Components**
   + Activities 
   + Services
   + Content Providers
   + Broadcast Receiver



#### Activities

+ entry point for interacting with the user.
+ It represents a single screen with a UI.



#### Services:

+ a general-purpose entry point for 
  + Keeping an app running in the background for all kinds of reasons.
+ runns in the background to perfrom long-running operations or to perfrom work for remote processes.
+ A service does not provide a UI.



#### Broadcast receivers

+ enables the system to deliver events to the app outside of a regular user flow
+ allowing the app to **respond system-wide broadcast announcements.**



#### Content providers

+ Manages a shared set of app data that you can store in the file system, in a SQLite db, on the web, or on any other persistent storage location that your app can access.





2. **Debug: ** 从最简略到最详细

| Code                       | Meaning      |
| -------------------------- | ------------ |
| **Log.e (String, String)** | Error 错误   |
| **Log.w (String, String)** | Warn 警告    |
| **Log.i (String, String)** | Info 信息    |
| **Log.d (String, String)** | Debug 调试   |
| **Log.v (String, String)** | Verbase 详细 |



3. **Add button**

+ Step1: draw the button in the xml file

+ Step2: delcare the Button in the Activity Page

  ```java
  private Button button;
  
  onCreate() {
  	button = findViewById (R.id.button);
  }
  ```

  

4. Achieving click 

   + ```java
     button.setOnClickListener () {
       //
     }
     ```

5. Button butter knife

   + ```Sync project with gradle files```
   + there are two gradle files?
     + mobile app gradle ?
     + Project app gradle ?

+ Why we use Butter Knife?
  + eliminate ```findViewById``` by using ```@BindView```
  + **<font color=orange>Group multiple views in a list or array ?</font>**
  + ```@OnClick```.



6. **Intent** 

   + **Explicit Intent**
   + **Implicit Intent** 
     + https://blog.csdn.net/xiao__gui/article/details/11392987
     + 如何实现？

   **Intent-filter**

   + Specifies the types of intents that an activity, service, or broadcast receiver can respond to. An intent filter declares the capabilities of its parent component — what an activity or service can do and what types of broadcasts a receiver can handle
     + **Action**
     + **Data** URI
     + **Category**

7. **Callback Method** ！！！
   + 

#### Related Resources

+ Android Components
+ Layouts
+ LifeCycles
+ Launch Modes