package com.twilio.interview.listenup;

import java.util.ArrayList;

public class User {

  public String username;
  public int plays;
  public int friends;
  public Object tracks;
  public String uri;
  private transient ArrayList<String> playsData;

  public User(String username, ArrayList<String> plays, int friends, String uri) {
    this.username = username;
    this.plays = plays.size();
    this.friends = friends;
    this.uri = uri;
    this.tracks = null;
    this.playsData = plays;
  }

  public void setPlays(ArrayList<String> plays) {
    this.plays = plays.size();
    this.playsData = plays;
  }

  public void setFriends(int friends) {
    this.friends = friends;
  }

  public void setTracks(int tracks) {
    this.tracks = new Integer(tracks);
  }

  public ArrayList<String> getPlaysData() {
    return this.playsData;
  }
}
