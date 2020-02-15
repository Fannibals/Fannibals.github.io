### COMP90018 Tutorial 2
---

#### 1. Callback Method

+ 

#### 2. Collecting a result from an activity

+ 启动另一个Activity不一定是单向操作的
+ startActivityForResult()
  + Intent
  + request code

+ when the user is done with the subsequent activity and returns, the systems calls your activity's ```onActivityResult()``` method.
  + the request code you passed to ```startActivityForResult()```
  + reuslt code
  + An intent that carries the result data



+ Step 1: start activity for result

  ```java
  Intent intent = new Intent();
  intent.setAction("SecondActivity");
  intent.putExtra(MESSAGE, "Hello from the first activity.");
  startActivityForResult(intent, MESSAGE_RECEIVE);
  ```

+ Step 2: back pressed function

  ```java
  @Override
  public void onBackPressed() {
    Intent returnIntent = new Intent();
    returnIntent.putExtra(RECEIVED_MESSAGE, "Hello from the second activity.");
    setResult(RESULT_OK, returnIntent);
  
  	super.onBackPressed();
  }
  ```

+ Step 3  Receive Message from Called Activities

  ```java
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  	// Check which request we're responding to
    if (requestCode == MESSAGE_RECEIVE) {
    	if (resultCode==RESULT_OK){              	message.setText(data.getStringExtra(Main2Activity.RECEIVED_MESSAGE));
              }
          }
      }
  ```

  

#### 2. Software Development Kit (SDK)

+ A set of software development tools that allows hte creation of applications for a certain software package ...



#### 3. Abstract Class

+ Interface vs. Abstract

+ Interface需要实现，要用implements，而abstract class需要继承，要用extends。

---



1. **User Interface — Layout**
   + A layout defines the structure for a UI in your app, such as in an activity.
2. **View** 
   + A view usually draws sth the user can see and interact with.
   + The view objects are usually called "widgets"
3. **ViewGroup => Layout**

4. **View vs. ViewGroup**

   View是Android中所有控件的基类。 (ImageView, TextView)

   ViewGroup继承自View，控件组，可以包含若干个View。

   + such as LinearLayout,RelativeLayout

   View本身既可以是单个控件，也可以是由多个控件组成的一组控件。

5. **Declare a layout** 

   + in XML

   + at runtime 

6. **Adapter**

   + behaves as a middleman between the data source and the AdapterView Layout
   + Retrieves the data
   + Converts each entry into a view

+ **WHY** do we need an adapter
  + When the content for the layout is dynamic or not pre-determined
    + subclasses AdapterView
  + 



7. **List View**
   + **Declare** ListView in layout (XML)
   + **Create** Object
   + **Create** Adapter and ViewHolder
   + **Bind** Adapter with ListView in the Activity

8. **GetView () ???**

+ 疑问



**TODO List**

+ fragment
+ ListView