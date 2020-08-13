## How to create a new backend server

1. <a href="https://www.jetbrains.com/help/idea/getting-started-with-gradle.html"> **Create a new project** </a>
   - Choose Gradle as our build tool
   - choose java and web then click next
   - then you there are two ids
     - GroupId: uses when we need to upload to maven repo(id of comp)
     - ArtifactId: name of the project
2. **Add tomcat server**
   
   - 
3. **Add Mysql**
   - need to download driver at the first time we use it
   - if forget the password, reset it (<a href="https://blog.csdn.net/czb_Corbin/article/details/72718781">how to stop mysql</a>)
   
   + DB name: IMBack
   + user & password: root & 123456
   + new url:
     + jdbc:mysql://localhost:3306/IMBack?useSSL=false&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT

4. Add dependencies in build.gradle