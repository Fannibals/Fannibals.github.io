## iOS Bugs

---

### 1. Unknown Class *** in interface builder file

+ **Replication workflow**
  + Create a new VC, let's say MyViewController.swift
  + Connect it with a view in Interface Buider
  + Directly delete MyViewController.swift
  + Restart the app
+ **Causes**
  + the connection between deleted VCs and IB parts has not been deleted
  + This error can also happen in other senarios such as Module of the custom class is not defined yet.

+ **Fix** 
  + Delete / Add relevant parts in storyboard
  + In this case, delete the old VC and add the new one



### 2. Weird error printed in Xcode console

+ description

  ```swo
  W0704 16:09:00.471663       1 commandlineflags.cc:1026] Ignoring RegisterValidateFunction() for flag pointer 0x1038af380: no flag found at that address
  ```

+ **Replication workflow**
  + pod intall goolgemap

+ **Causes**
  + STILL UNKNOWN

