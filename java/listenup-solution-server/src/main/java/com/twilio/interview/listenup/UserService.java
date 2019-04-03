package com.twilio.interview.listenup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

  private static String getJsonResponse(String url) throws ClientProtocolException, IOException {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse response1 = httpclient.execute(httpGet);
    try {
      HttpEntity entity1 = response1.getEntity();
      String json = EntityUtils.toString(entity1);
      return json;
    } finally {
      response1.close();
    }
  }

  private static ServiceResponse getServiceRequest(String url) throws ClientProtocolException, IOException {
    String json = getJsonResponse(url);
    Gson gson = new Gson();
    ServiceResponse response = gson.fromJson(json, ServiceResponse.class);
    return response;
  }

  private static ServiceDetailResponse getServiceDetailRequest(String url) throws ClientProtocolException, IOException {
    String json = getJsonResponse(url);
    Gson gson = new Gson();
    ServiceDetailResponse response = gson.fromJson(json, ServiceDetailResponse.class);
    return response;
  }

  private static int filterDuplicates(ArrayList<String> list) {
    Set<String> set = new HashSet<>(list);
    return set.size();
  }

  private void handleFriendsService(String username) throws ClientProtocolException, IOException {
    final String uri = "/friends/" + username;
    ServiceDetailResponse friendsDetailResponse = getServiceDetailRequest(friendsURLPrefix + uri);
    User u = users.get(username);
    if (u == null) {
      users.put(username, new User(username, new ArrayList<String>(), friendsDetailResponse.data.size(), "/users/" + username));
    } else {
      u.setFriends(friendsDetailResponse.data.size());
    }
  }

  private void getFriendsService() throws ClientProtocolException, IOException {
    ServiceResponse friendsResponse = getServiceRequest(friendsURLPrefix + "/friends");
    for (User i : friendsResponse.users) {
      handleFriendsService(i.username);
    }
  }

  private void handlePlaysService(String username) throws ClientProtocolException, IOException {
    final String uri = "/plays/" + username;
    ServiceDetailResponse playsDetailResponse = getServiceDetailRequest(playsURLPrefix + uri);
    User u = users.get(username);
    if (u == null) {
      users.put(username, new User(username, playsDetailResponse.data, 0, "/users/" + username));
    } else {
      u.setPlays(playsDetailResponse.data);
    }
  }

  private void getPlaysService() throws ClientProtocolException, IOException {
    ServiceResponse playsResponse = getServiceRequest(playsURLPrefix + "/plays");
    for (User i : playsResponse.users) {
      handlePlaysService(i.username);
    }
  }

  public ServiceResponse getAllUsers() throws ClientProtocolException, IOException {
    getFriendsService();
    getPlaysService();
    return new ServiceResponse(new ArrayList<>(users.values()), "/users");
  }

  public User getUser(String name) throws ClientProtocolException, IOException {
    User u = users.get(name);
    if (u == null) {
      handleFriendsService(name);
      handlePlaysService(name);
      u = users.get(name);
    }
    ArrayList<String> plays = u.getPlaysData();
    int tracks = filterDuplicates(plays);
    u.setTracks(tracks);
    return u;
  }
}
