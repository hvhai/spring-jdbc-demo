package com.codehunter.springjdbcdemo.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment {
  private Integer id;
  private String text;
  private Instant reg_date;
}
