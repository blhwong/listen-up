package com.twilio.interview.listenup;

import static spark.Spark.get;
import static spark.Spark.port;
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
      User user = userService.getUser(name);
      if (user == null) {
        res.status(404);
        return new ResponseError("No user with name '%s' found", name);
      }
      return user;
    }, json());

    after((req, res) -> {
      res.type("application/json");
    });

    exception(IllegalArgumentException.class, (err, req, res) -> {
      res.status(400);
      res.body(toJson(new ResponseError(err)));
    });
  }
}
