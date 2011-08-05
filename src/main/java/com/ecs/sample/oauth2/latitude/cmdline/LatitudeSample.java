package com.ecs.sample.oauth2.latitude.cmdline;

import com.ecs.sample.oauth2.latitude.cmdline.oauth.FileBasedCredentialStore;
import com.ecs.sample.oauth2.latitude.cmdline.oauth.LocalServerReceiver;
import com.ecs.sample.oauth2.latitude.cmdline.oauth.OAuth2Native;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonError.ErrorInfo;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.latitude.Latitude;

public class LatitudeSample {

  private static void run(JsonFactory jsonFactory) throws Exception {
    HttpTransport transport = new NetHttpTransport();
    GoogleAccessProtectedResource accessProtectedResource =
        OAuth2Native.authorize(transport, jsonFactory, new LocalServerReceiver(), new FileBasedCredentialStore(),
            "google-chrome", OAuth2ClientCredentials.CLIENT_ID,
            OAuth2ClientCredentials.CLIENT_SECRET, OAuth2ClientCredentials.SCOPE);
    
    final Latitude latitude = new Latitude(transport, accessProtectedResource, jsonFactory);
    
    latitude.apiKey=OAuth2ClientCredentials.API_KEY;
    latitude.setApplicationName("LatitudeSample/1.0");
    latitude.prettyPrint = true;
    
    LatitudeActions.showCurrentLocation(latitude);
    LatitudeActions.updateCurrentLocation(latitude);
    LatitudeActions.showCurrentLocation(latitude);
    LatitudeActions.showLocationHistory(latitude);
  }

  public static void main(String[] args) {
    JsonFactory jsonFactory = new JacksonFactory();
    try {
      try {
        if (OAuth2ClientCredentials.CLIENT_ID == null
            || OAuth2ClientCredentials.CLIENT_SECRET == null) {
          System.err.println("Please enter your client ID and secret in "
              + OAuth2ClientCredentials.class);
        } else {
          run(jsonFactory);
        }
        // success!
        return;
      } catch (HttpResponseException e) {
        if (!e.response.contentType.equals(Json.CONTENT_TYPE)) {
          System.err.println(e.response.parseAsString());
        } else {
          GoogleJsonError errorResponse = GoogleJsonError.parse(jsonFactory, e.response);
          System.err.println(errorResponse.code + " Error: " + errorResponse.message);
          for (ErrorInfo error : errorResponse.errors) {
            System.err.println(jsonFactory.toString(error));
          }
        }
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
    System.exit(1);
  }
}
