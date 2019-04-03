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

  private String getJsonResponse(String url) throws ClientProtocolException, IOException {
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

  private ServiceResponse getServiceRequest(String url) throws ClientProtocolException, IOException {
    String json = getJsonResponse(url);
    Gson gson = new Gson();
    ServiceResponse response = gson.fromJson(json, ServiceResponse.class);
    return response;
  }

  private ServiceDetailResponse getServiceDetailRequest(String url) throws ClientProtocolException, IOException {
    String json = getJsonResponse(url);
    Gson gson = new Gson();
    ServiceDetailResponse response = gson.fromJson(json, ServiceDetailResponse.class);
    return response;
  }

  public int filterDuplicates(ArrayList<String> list) {
    Set<String> set = new HashSet<>(list);
    return set.size();
  }

  private void insertUser(String username, ArrayList<String> plays, int friends) {
    users.put(username, new User(username, plays, friends, "/users/" + username));
  }

  public User getUser(String username) {
    return users.get(username);
  }

  public void handleFriendsService(String username, int friendsCount) {
    User u = getUser(username);
    if (u == null) {
      insertUser(username, new ArrayList<String>(), friendsCount);
    } else {
      u.setFriends(friendsCount);
    }
  }

  private void getFriendsService() throws ClientProtocolException, IOException {
    ServiceResponse friendsResponse = getServiceRequest(friendsURLPrefix + "/friends");
    for (User i : friendsResponse.users) {
      final String uri = "/friends/" + i.username;
      ServiceDetailResponse friendsDetailResponse = getServiceDetailRequest(friendsURLPrefix + uri);
      handleFriendsService(i.username, friendsDetailResponse.data.size());
    }
  }

  public void handlePlaysService(String username, ArrayList<String> plays) {
    User u = getUser(username);
    if (u == null) {
      insertUser(username, plays, 0);
    } else {
      u.setPlays(plays);
    }
  }

  private void getPlaysService() throws ClientProtocolException, IOException {
    ServiceResponse playsResponse = getServiceRequest(playsURLPrefix + "/plays");
    for (User i : playsResponse.users) {
      final String uri = "/plays/" + i.username;
      ServiceDetailResponse playsDetailResponse = getServiceDetailRequest(playsURLPrefix + uri);
      handlePlaysService(i.username, playsDetailResponse.data);
    }
  }

  public ServiceResponse getAllUsers() throws ClientProtocolException, IOException {
    getFriendsService();
    getPlaysService();
    return new ServiceResponse(new ArrayList<>(users.values()), "/users");
  }

  public User getUserDetail(String name) throws ClientProtocolException, IOException {
    User u = getUser(name);
    if (u == null) {
      final String friendsUri = "/friends/" + name;
      final String playsUri = "/plays/" + name;
      ServiceDetailResponse friendsDetailResponse = getServiceDetailRequest(friendsURLPrefix + friendsUri);
      ServiceDetailResponse playsDetailResponse = getServiceDetailRequest(playsURLPrefix + playsUri);
      handleFriendsService(name, friendsDetailResponse.data.size());
      handlePlaysService(name, playsDetailResponse.data);
      u = getUser(name);
    }
    ArrayList<String> plays = u.getPlaysData();
    int tracks = filterDuplicates(plays);
    u.setTracks(tracks);
    return u;
  }
}
