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