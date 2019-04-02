package com.twilio.interview.listenup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

  private Map<String, User> users = new HashMap<>();

  public List<User> getAllUsers() {
    return new ArrayList<>(users.values());
  }

  public User getUser(String name) {
    return users.get(name);
  }
}
