## As As? As!

---

### Motivation

+ In order to understand the use of  ```as``` in swift

+ Improve comprehension of the ```class```



### Animal and Dog Example

+ Let's say we have two classes : ```Animal``` and ```Dog```, the ```Dog``` is a subclass of ```Animal```

+ ```swift
  class Animal{
      func cry () {
          print("animal cry")
      }
  }
  
  class Dog: Animal{
      override func cry() {
          print("dog cry")
      }
  }
  ```

+ In the dog class, the cry function has been overrided



### Upcasting

+ This time let us create a new object named ```a```, this object is type annotated as Animal.

+ **a as! Dog** 

  ```swift
  let a:Animal = Dog()   // Dog
  a as! Dog
  a.cry() 	// dog cry 
  ```

+ **d as Animal**

  ```swift
  let d = Dog()	 // Dog
  d as Animal
  d.cry()		// dog cry
  ```

+ Can you find that what happened?
  + Both cry functions print dog cry ! even though the object d
+ WHY?
  + this is because both a and d are of class ```Dog```

##### The above example is about <font color=red>guranteed conversion</font>

### Forced Conversion

```swift
// forced conversion
let b = Animal()
b.cry()		// Animal cry
b as? Dog
b as! Dog // error
```

