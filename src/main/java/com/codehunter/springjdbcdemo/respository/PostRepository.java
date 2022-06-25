package com.codehunter.springjdbcdemo.respository;

import com.codehunter.springjdbcdemo.model.Comment;
import com.codehunter.springjdbcdemo.model.Post;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostRepository {
  private final JdbcTemplate jdbcTemplate;

  public void createPost(String content) {
    String query = """
					INSERT INTO post(content) VALUES (?)
					""";
    var value = jdbcTemplate.update(query,content);
    log.info("create post result " +  value);
  }

  public void deletePostById(int id) {
    var query = """
        DELETE FROM post WHERE id = ?
        """;
    var value = jdbcTemplate.update(query, id);
    log.info("delete post result " +  value);
  }

  public List<Post> findAll() {
    String query = """
					SELECT id, content, reg_date FROM post
					""";
    List<Post> post = jdbcTemplate.query(query,new PostMapper());
    log.info("post : " + post);
    return post;
  }

  public List<Post> findAllPostWithComment(){
    String query = """
        SELECT p.*, c.id comment_id, c.post_id, c.value from post p
        LEFT JOIN comment c
        ON p.id = c.post_id
        """;
    return jdbcTemplate.query(query, rs -> {
      Map<Integer, Post> map = new HashMap<>();
      Post post;
      while (rs.next()) {
        var id = rs.getInt("id");
        post = map.get(id);
        if (post == null) {
          post = new Post(rs.getInt("id"),
              rs.getString("content"),
              rs.getTimestamp("reg_date").toInstant());
          map.put(id, post);
        }
        if (rs.getInt("post_id")> 0){
          Comment comment = new Comment(rs.getInt("comment_id"),
              rs.getString("value"),
              rs.getTimestamp("reg_date").toInstant());
          post.getComments().add(comment);
        }
      }
      return new ArrayList<>(map.values());
    });
  }

  private static final class PostMapper implements RowMapper<Post>{
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Post(rs.getInt("id"),
          rs.getString("content"),
          rs.getTimestamp("reg_date").toInstant());
    }
  }
}
