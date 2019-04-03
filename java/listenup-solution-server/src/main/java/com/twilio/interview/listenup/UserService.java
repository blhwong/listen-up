package com.twilio.interview.listenup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class UserService {

  private static final String friendsURLPrefix = "http://localhost:8000";
  private static final String playsURLPrefix = "http://localhost:8001";
  private static final Gson gson = new Gson();
  private static Map<String, User> users = new HashMap<>();

  private String getJsonResponse(final String url) throws ClientProtocolException, IOException {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse response1 = httpclient.execute(httpGet);
    StatusLine sl = response1.getStatusLine();
    int statusCode = sl.getStatusCode();
    if (statusCode == 404) {
      return null;
    }
    try {
      HttpEntity entity1 = response1.getEntity();
      String json = EntityUtils.toString(entity1);
      return json;
    } finally {
      response1.close();
    }
  }

  private ServiceResponse getServiceRequest(final String url) throws ClientProtocolException, IOException {
    String json = getJsonResponse(url);
    ServiceResponse response = gson.fromJson(json, ServiceResponse.class);
    return response;
  }

  private ServiceDetailResponse getServiceDetailRequest(final String url) throws ClientProtocolException, IOException {
    String json = getJsonResponse(url);
    ServiceDetailResponse response = gson.fromJson(json, ServiceDetailResponse.class);
    return response;
  }

  public int filterDuplicates(final ArrayList<String> list) {
    Set<String> set = new HashSet<>(list);
    return set.size();
  }

  private void insertUser(final String username, final ArrayList<String> plays, final int friends) {
    users.put(username, new User(username, plays, friends, "/users/" + username));
  }

  public User getUser(final String username) {
    return users.get(username);
  }

  public void handleFriendsService(final String username, final int friendsCount) {
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

  public void handlePlaysService(final String username, final ArrayList<String> plays) {
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

  public User getUserDetail(final String name) throws ClientProtocolException, IOException {
    User u = getUser(name);
    if (u == null) {
      final String friendsUri = "/friends/" + name;
      final String playsUri = "/plays/" + name;
      ServiceDetailResponse friendsDetailResponse = getServiceDetailRequest(friendsURLPrefix + friendsUri);
      ServiceDetailResponse playsDetailResponse = getServiceDetailRequest(playsURLPrefix + playsUri);
      if (friendsDetailResponse == null || playsDetailResponse == null) {
        return null;
      }
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
