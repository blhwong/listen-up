package com.twilio.interview.listenup;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import spark.Spark;

public class UserServiceTest {
  final UserService service = new UserService();

  @BeforeClass
  public static void beforeClass() {
    Main.main(null);
  }

  @AfterClass
  public static void afterClass() {
    Spark.stop();
  }

  @Test
  public void canFilterDuplicates() {
    ArrayList<String> list = new ArrayList<String>(
      Arrays.asList("ABC", "ABC", "DEF", "DEF", "HIJ")
    );
    int count = service.filterDuplicates(list);
    assertEquals(count, 3);
    list.clear();
    count = service.filterDuplicates(list);
    assertEquals(count, 0);
  }

  @Test
  public void canHandlePlaysService() {
    String username = "brandon_wong";
    ArrayList<String> plays = new ArrayList<String>(
      Arrays.asList("song1", "song1", "song1", "song2")
    );
    service.handlePlaysService(username, plays);
    User u = service.getUser(username);
    assertEquals(u.username, username);
    assertEquals(u.plays, 4);

    plays.add("song3");
    service.handlePlaysService(username, plays);
    u = service.getUser(username);
    assertEquals(u.plays, 5);
  }

  @Test
  public void canHandleFriendsService() {
    String username = "test_test";
    int friendsCount = 10;
    service.handleFriendsService(username, friendsCount);
    User u = service.getUser(username);
    assertEquals(u.username, username);
    assertEquals(u.friends, friendsCount);

    service.handleFriendsService(username, friendsCount + 1);
    u = service.getUser(username);
    assertEquals(u.friends, friendsCount + 1);
  }

  @Test
  public void canGetUserDetailIfPopulated() {
    String username = "populated_user";
    int friendsCount = 15;
    ArrayList<String> plays = new ArrayList<String>(
      Arrays.asList("song1", "song1", "song1", "song2", "song3")
    );
    service.handleFriendsService(username, friendsCount);
    service.handlePlaysService(username, plays);

    try {
      User u = service.getUserDetail(username);
      assertEquals(u.username, username);
      assertEquals(u.friends, friendsCount);
      assertEquals(u.plays, plays.size());
      assertEquals(u.tracks, 3);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
