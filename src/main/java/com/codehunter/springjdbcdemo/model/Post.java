package com.codehunter.springjdbcdemo.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Post {
  private Integer id;
  private String content;
  private Instant reg;
  private List<Comment> comments;

  public Post(Integer id, String content, Instant reg) {
    this(id, content, reg, new ArrayList<>());
  }
}
