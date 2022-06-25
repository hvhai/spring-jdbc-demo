package com.codehunter.springjdbcdemo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codehunter.springjdbcdemo.respository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostRepositoryTest {
  @Autowired private PostRepository postRepository;

  @Test
  public void findAll_should_returnAllPost() {
    var actual = postRepository.findAll();
    assertTrue(actual.size() > 0);
  }

  @Test
  public void findAllPostWithComment() {
    var actual = postRepository.findAllPostWithComment();
    assertTrue(actual.size() > 0);
  }

  @Test
  public void createPost() {
    var before = postRepository.findAll();
    postRepository.createPost("Post created from Spring for test");
    var actual = postRepository.findAll();
    assertEquals(before.size() + 1, actual.size());
  }

  @Test
  public void deletePost() {
    postRepository.createPost("Post created from test for delete");
    var before = postRepository.findAll();
    // delete latest post
    postRepository.deletePostById(before.get(before.size() - 1).getId());
    var actual = postRepository.findAll();
    assertEquals(before.size() - 1, actual.size());
  }
}
