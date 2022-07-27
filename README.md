# Practice Spring JDBC with MySQL(Docker)

1. Get mysql image:
    ``` shell
    docker run --name demo-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=pw -d mysql
    ```
2. [Config DBeaver for connect to DB](https://stackoverflow.com/a/61939827/18859462)

   Add two properties: "useSSL" and "allowPublicKeyRetrieval"

   jdbc:mysql://localhost:3306/db?allowPublicKeyRetrieval=true&useSSL=false

3. Create 'forum' database with script [init.sql](./src/main/resources/sql/script/init.sql)

4. Update **application.properties**
   ``` 
   spring.datasource.url=jdbc:mysql://localhost:3306/forum?allowPublicKeyRetrieval=true&useSSL=false
   spring.datasource.username=root
   spring.datasource.password=pw
   ```
5. Create simple bean ApplicationRunner to verify DB connection
    ``` java
      @Bean
      public ApplicationRunner runner(JdbcTemplate jdbcTemplate) {
        return arguments -> {
            String query = """
            select content from post where id = ?
                    """;
            String post = jdbcTemplate.queryForObject(query,String.class, 1);
            log.info("post : " + post);
        };
      }
    ```
6. Create CRUD with JdbcTemplate
    - create/delete -> use JdbcTemplate.update()
    - read -> use JdbcTemplate.query()
# Working with Liquidbase

1. Add dependency
``` xml
    <dependency>
      <groupId>org.liquibase</groupId>
      <artifactId>liquibase-core</artifactId>
    </dependency>
```

2. Add plugin

``` xml
   <plugin>
       <groupId>org.liquibase</groupId>
       <artifactId>liquibase-maven-plugin</artifactId>
       <configuration>
           <propertyFileWillOverride>true</propertyFileWillOverride>
           <propertyFile>src/main/resources/liquibase.properties</propertyFile>
       </configuration>
   </plugin>
```

3. Add config

Create **[liquibase.properties](src/main/resources/liquibase.properties)** file

``` properties 
#contexts: ${liquibase.contexts}
changeLogFile=src/main/resources/db/changelog/changelog.xml
diffChangeLogFile=src/main/resources/db/changelog/${maven.build.timestamp}changelog.xml
driver=com.mysql.cj.jdbc.Driver
username=root
password=pw
url=jdbc:mysql://localhost:3306/forum?allowPublicKeyRetrieval=true&useSSL=false
verbose=true
#dropFirst: false
```

4. Bootstrap database
 
5. Execute changelog command
``` shell
mvn liquibase:generateChangeLog -Dliquibase.outputChangeLogFile=src/main/resources/db/changelog/generate.xml
```
