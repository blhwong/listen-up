package com.twilio.interview.listenup;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ServiceResponse extends Response {

  @SerializedName(value="friends", alternate={"users"})
  public ArrayList<User> users;

  public ServiceResponse(ArrayList<User> users, String uri) {
    this.users = users;
    this.uri = uri;
  }
}
