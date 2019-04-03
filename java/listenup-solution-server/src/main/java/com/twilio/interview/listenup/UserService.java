package com.twilio.interview.listenup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class UserService {

  private final String friendsURLPrefix = "http://localhost:8000";
  private final String playsURLPrefix = "http://localhost:8001";
  private Map<String, User> users = new HashMap<>();


  private static Response getRequest(String url) throws ClientProtocolException, IOException {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse response1 = httpclient.execute(httpGet);
    try {
      HttpEntity entity1 = response1.getEntity();
      String json = EntityUtils.toString(entity1);
      System.out.println(json);
      Gson gson = new Gson();
      Response response = gson.fromJson(json, Response.class);
      EntityUtils.consume(entity1);
      return response;
    } finally {
      response1.close();
    }
  }

  public List<User> getAllUsers() throws ClientProtocolException, IOException {
    Response friendsResponse = getRequest(friendsURLPrefix + "/friends");
    Response playsResponse = getRequest(playsURLPrefix + "/plays");
    System.out.println("friendsResponse");
    System.out.println(friendsResponse.toString());
    System.out.println("playsResponse");
    System.out.println(playsResponse.toString());
    return new ArrayList<>(users.values());
  }

  public User getUser(String name) {
    return users.get(name);
  }
}
