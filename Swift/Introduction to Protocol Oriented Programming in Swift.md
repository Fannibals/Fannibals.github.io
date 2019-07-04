##Introduction to Protocol Oriented Programming in Swift

Written by Ethan Shin, 2/7/2019

#### Declaration 

+ The original article is: <a href="https://blog.bobthedeveloper.io/introduction-to-protocol-oriented-programming-in-swift-b358fe4974f">Introduction to Protocol Oriented Programming in Swift</a>

+ I'm just going to make a conclusion of the above article.

#### Motivation

+ Understand the diffference between ```Classes``` and ```Structs```
+ Make clear about protocol



#### Classes and Structs

+ Take the following code as an example, as you can see, the class object ```classyHuman``` is initiated with name "Ethan", however, when pass it to a new object ```newClass``` and change the name to "Kitty", the original object's name has changed as well.

```swift
class HumanClass {
    var name: String
    init(name: String) {
        self.name = name
    }
}

var classyHuman = HumanClass(name:"Ethan")
print(classyHuman.name) // Ethan

var newClassyHuman = classyHuman // create a "copied" object
newClassyHuman.name = "Kitty"
print(classyHuman.name) // Kitty
```

+ Now, let's take a look at Structs

```swift
struct HumanStruct {
    var name: String
}

var humanStruct = HumanStruct(name: "Ethan")
print(humanStruct.name) // Ethan
var newHumanStruct = humanStruct
newHumanStruct.name = "Kitty"
print(humanStruct.name) // Ethan
```

+ The change to the name property of the copied object hasn't affected the original humanStruct object/

#### <font color=red>Conclusion</font>

+ In **Classes**, when you make a copy of a variable, both variables are referring to **<u>the same object in memory.</u>**		Reference Type
+ In **Structs**, you simply copy and paste variables by **<u>creating a seperate object</u>.**        Value Type



####OOP vs POP

**A protocol is like a basketball coach. He tells his players what to do, but he doesn’t know how to dunk a basketball.**

+ Firstly, let's make a blueprint for a human.

```swift
protocol Human {
    var name: String {get set}
    var race: String {get set}
    func sayHi()
}

struct Korean: Human {
    var name: String = "Yuming Shin"
    var race: String = "Asian"
    func sayHi() {
        print("Hi, my name is \(name)")
    }
}
```

+ You should note that the struct object should completely conforms to the protocol
  + let's say, if there is no name var in Korean struct, then it will not compile successfully.
+ And then, we are going to create a new ```SuperHuman``` protocol inherits from the ```Human``` protocol.

```swift
protocol SuperHuman: Human {
    var canFly: Bool {get set}
    func punch()
}

struct SuperSaiyan: SuperHuman {
    var name: String = "悟空"
    var race: String = "赛亚人"
    var canFly: Bool = true
    
    func sayHi() {
        print("Hi, my name is \(name)")
    }
    
    func punch() {
        print("one punch man = =||")
    }
}
```



#### Protocol Extension

```swift
protocol SuperAnimal {
    func speakEnglish()
}

extension SuperAnimal {
    func speakEnglish() { print("I speak English, pretty cool, huh?")}
}

class Donkey: SuperAnimal {
}
var ramon = Donkey()
ramon.speakEnglish() // "I speak English, pretty cool, huh?"
```

+ If you use an extension, you can add default functions and properties to class, struct, and enum.



#### Protocol as Type

```swift
protocol Fightable {
    func legKick()
}

struct StructKangaroo: Fightable {
    func legKick() {
        print("Puuook")
    }
}

class ClassKangaroo: Fightable {
    func legKick() {
        print("Pakkkk")
    }
}

let structKang = StructKangaroo()
let classKang = ClassKangaroo()

var Kangaroos:[Fightable] = [structKang, classKang]

for kang in Kangaroos{
    kang.legKick()
}
```

