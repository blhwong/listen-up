package com.twilio.interview.listenup;

import java.util.ArrayList;

public class User {

  public String username;
  public int plays;
  public int friends;
  public Object tracks;
  public String uri;
  private transient ArrayList<String> playsData;

  public User(final String username, final ArrayList<String> plays, final int friends, final String uri) {
    this.username = username;
    this.plays = plays.size();
    this.friends = friends;
    this.uri = uri;
    this.tracks = null;
    this.playsData = plays;
  }

  public void setPlays(final ArrayList<String> plays) {
    this.plays = plays.size();
    this.playsData = plays;
  }

  public void setFriends(final int friends) {
    this.friends = friends;
  }

  public void setTracks(final int tracks) {
    this.tracks = new Integer(tracks);
  }

  public ArrayList<String> getPlaysData() {
    return this.playsData;
  }
}
