package com.twilio.interview.listenup;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {

    public static void main(String[] args) {
        /* See Spark documentation at http://sparkjava.com/documentation.html */
        port(8005);
        get("/hello/:name", (request, response) -> {
            return "Hello " + request.params("name")+ "!";
        });
    }

}
