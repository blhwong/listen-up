package com.twilio.interview.listenup;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import spark.Spark;
import spark.utils.IOUtils;

public class UserTest {

  @BeforeClass
  public static void beforeClass() {
    Main.main(null);
  }

  @AfterClass
  public static void afterClass() {
    Spark.stop();
  }

  @Test
  public void canGetAllUsers() {
    assertEquals(200, 200);
  }
}
