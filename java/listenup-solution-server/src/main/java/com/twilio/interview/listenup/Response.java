package com.twilio.interview.listenup;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Response {
  @SerializedName(value="users", alternate={"friends"})
  public ArrayList<User> users;
  public String uri;

  public Response(ArrayList<User> users, String uri) {
    this.users = users;
    this.uri = uri;
  }
}
