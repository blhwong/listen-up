package com.twilio.interview.listenup;

public class User {

  public String username;
  public int plays;
  public int friends;
  public String uri;

  public User(String username, int plays, int friends, String uri) {
    this.username = username;
    this.plays = plays;
    this.friends = friends;
    this.uri = uri;
  }

  public void setPlays(int plays) {
    this.plays = plays;
  }

  public void setFriends(int friends) {
    this.friends = friends;
  }
}
