####2019.9.30 记录

1. Fragment 和 Activity 之间不能相互传的坑

   + solution

     a. using Fragment's startactivityforresult rather than getActivity().start...

     b. the activity includes fragments should have:

     ​	```super.onActivityResult(requestCode, resultCode, data);```



### RecyclerView.ItemDecoration

+ An ItemDecoration allows the application to **add a special drawing and layout** **offset** to specific item views from the adapter's data set.



**动态设置控件长宽**

+ LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) textView.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20  

+ linearParams.width = 30;// 控件的宽强制设成30   

+ textView.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
  

