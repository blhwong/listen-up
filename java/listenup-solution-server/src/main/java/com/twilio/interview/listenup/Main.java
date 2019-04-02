package com.twilio.interview.listenup;

public class Main {
  // public static void getRequest(String url) throws ClientProtocolException, IOException {
  //     CloseableHttpClient httpclient = HttpClients.createDefault();
  //     HttpGet httpGet = new HttpGet(url);
  //     CloseableHttpResponse response1 = httpclient.execute(httpGet);
  //     try {
  //       HttpEntity entity1 = response1.getEntity();
  //       System.out.println(EntityUtils.toString(entity1));
  //       EntityUtils.consume(entity1);
  //     } finally {
  //       response1.close();
  //     }
  //   }
  public static void main(String[] args) {
    new UserController(new UserService());
  }

}
