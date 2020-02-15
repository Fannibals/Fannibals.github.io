### Android Layout
---

#### Adapter

+ android.widget.Adapter
+ An Adapter object acts as a bridge between an `AdapterView` and the <u>underlying data for that view</u>. The Adapter provides access to the data items. The Adapter is also responsible for making a `View` for each item in the data set.

#### AdapterView

+ An AdapterView is a view whose children are determined by an `Adapter`.

+ | [java.lang.Object](https://developer.android.com/reference/java/lang/Object.html) |                                                              |                                                              |                                                              |
  | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | ↳                                                            | [android.view.View](https://developer.android.com/reference/android/view/View.html) |                                                              |                                                              |
  |                                                              | ↳                                                            | [android.view.ViewGroup](https://developer.android.com/reference/android/view/ViewGroup.html) |                                                              |
  |                                                              |                                                              | ↳                                                            | android.widget.AdapterView<T extends [android.widget.Adapter](https://developer.android.com/reference/android/widget/Adapter.html)> |

| Parameters |                                                              |
| ---------- | ------------------------------------------------------------ |
| `context`  | `Context`: The current context. This value must never be `null`. |
| `resource` | `int`: The resource ID for a layout file containing a TextView to use when instantiating views. |
| `objects`  | `T`: The objects to represent in the ListView. This value must never be `null`. |





#### LayoutInflater

+ Instantiates a layout XML file into its corresponding `View` objects.

+ 在实际开发中LayoutInflater这个类还是非常有用的，它的作用类似于findViewById()。
  不同点是LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化；而findViewById()是找xml布局文件下的具体widget控件（如Button，TextView等等）

+ 使用场景：

  ①对于一个没有被载入或者想要动态载入的界面，都需要使用`LayoutInflater.inflater()`来载入。

  ②对于一个已经载入的界面，可以使用`Activity.findViewById()`方法来获取其中的界面元素。



---

### Fragment

+ Can be thought of a fragment as a modular section of an activity, which has its own lifecycle, receives ites own input events.



+ **Lifecycle**

  | Activity Status | Fragment Callbacks                                           |
  | --------------- | ------------------------------------------------------------ |
  | Created         | onAttach() -> onCreated() -> onCreatedView()-> OnActivityCreated() |
  | Started         | onStarted()                                                  |
  | Resumed         | onResumed()                                                  |
  | Paused          | onPaused()                                                   |
  | Stopped         | onStop()                                                     |
  | Destroyed       | onDestroyView() -> onDestroy() -> onDetach()                 |

  

+ 将`Fragment` 添加到 ```Activity``` 中一般有两种方法：  
  1. 在`Activity`的`layout.xml`布局文件中静态添加
  2. 在`Activity`的`.java`文件中动态添加