package com.codehunter.springjdbcdemo;

import com.codehunter.springjdbcdemo.respository.PostRepository;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Log4j2
public class SpringJdbcDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringJdbcDemoApplication.class, args);
  }

  @Bean
  public ApplicationRunner runner(JdbcTemplate jdbcTemplate) {
    return arguments -> {
			String query = """
					select content from post
					""";
			List<String> post = jdbcTemplate.queryForList(query, String.class);
      log.info("post : " + post);
		};
  }
  @Bean ApplicationRunner readPost(PostRepository postRepository) {
    return args -> {
      var allPost = postRepository.findAll();
      log.info("all posts : " + allPost);
    };
  }
}
