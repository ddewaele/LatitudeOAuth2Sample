package com.ecs.sample.oauth2.latitude.cmdline;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.api.services.latitude.model.LatitudeCurrentlocationResourceJson;
import com.google.api.services.latitude.model.Location;

class View {

  static void header(String name) {
    System.out.println();
    System.out.println("============== " + name + " ==============");
    System.out.println();
  }

  static void display(LatitudeCurrentlocationResourceJson latitudeCurrentlocationResourceJson) {
	  String timestampMs = (String) latitudeCurrentlocationResourceJson.get("timestampMs");
	  DateFormat df= new  SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	  Date d = new Date(Long.valueOf(timestampMs));
	  System.out.println(latitudeCurrentlocationResourceJson.get("latitude") + " - " + latitudeCurrentlocationResourceJson.get("longitude") + " - " + df.format(d));
  }
  
  static void display(Location location) {
	  String timestampMs = location.timestampMs.toString();
	  DateFormat df= new  SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	  Date d = new Date(Long.valueOf(timestampMs));
	  System.out.println(location.latitude + " - " + location.longitude + " - " + df.format(d));
  }  

}
