package com.twilio.interview.listenup;

import static spark.Spark.get;
import static spark.Spark.port;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import static spark.Spark.after;
import static spark.Spark.exception;

import static com.twilio.interview.listenup.JsonUtil.json;
import static com.twilio.interview.listenup.JsonUtil.toJson;

public class UserController {

  public UserController(final UserService userService) {
    port(8005);

    get("/users", (req, res) -> userService.getAllUsers(), json());

    get("/users/:name", (req, res) -> {
      String name = req.params(":name");
      User user = userService.getUserDetail(name);
      if (user == null) {
        res.status(404);
        return new ResponseError("No user with name %s found", name);
      }
      return user;
    }, json());

    after((req, res) -> {
      res.type("application/json");
    });

    exception(IllegalArgumentException.class, (err, req, res) -> {
      res.status(400);
      res.type("application/json");
      res.body(toJson(new ResponseError(err)));
    });

    exception(ClientProtocolException.class, (err, req, res) -> {
      res.status(500);
      res.type("application/json");
      res.body(toJson(new ResponseError(err)));
    });

    exception(IOException.class, (err, req, res) -> {
      res.status(500);
      res.type("application/json");
      res.body(toJson(new ResponseError(err)));
    });
  }
}
