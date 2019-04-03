package com.twilio.interview.listenup;

import java.util.ArrayList;

public class Response {
  public ArrayList<User> friends;
  public String uri;

  public Response(ArrayList<User> friends, String uri) {
    this.friends = friends;
    this.uri = uri;
  }
}
