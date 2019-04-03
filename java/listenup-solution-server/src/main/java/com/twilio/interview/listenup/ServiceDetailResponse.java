package com.twilio.interview.listenup;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ServiceDetailResponse extends Response {

  @SerializedName(value = "friends", alternate = { "plays" })
  public ArrayList<String> data;
}
