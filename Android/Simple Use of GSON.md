### Simple Use of GSON

---

1. **Serialization** means to convert an object into that string, 

   ```
   Object -> String
   ```
   
   **Deserialization** is its inverse operation.

2. **@SerializedName("")**

   ```java
   @SerializedName("id")
   @Expose
   private Long id;
   ```

3. **@SerializedName(value="", alternate={} / "")**

   + value is the default parameter
   + but it will still trans if backend passed paras in alternate







### Simple Use of ROOM
---

1. **DAO**

```java
@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertMovies(List<MovieEntity> movies);  
```

+ OnConflict strategy constant to **replace the old data and continue the transaction.**
+ @Insert => OnConlict () do if a conflict happens



2. 

 



### RxJava
---

1. Build blocks for RxJava code are the following

+ **Observables** representing sources of data
+ **Subscribers(observers )** listening to the observables
+ a set of methods for modifying and composing the data













### Annotation
---

1. **@MainThread / @WorkerThread**
   + Denotes that the annotated method should only be called on a main / worker thread.

2. **@interface and interface**
   + The interface keyword indicates that you are declaring a traditional interface class in Java
   + The @interface keyword is used to declare a new annotation type.