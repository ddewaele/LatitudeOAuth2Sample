package com.ecs.sample.oauth2.latitude.cmdline;

import java.io.IOException;
import java.util.List;

import com.google.api.services.latitude.Latitude;
import com.google.api.services.latitude.model.LatitudeCurrentlocationResourceJson;
import com.google.api.services.latitude.model.Location;
import com.google.api.services.latitude.model.LocationFeed;

public class LatitudeActions {

	static void showCurrentLocation(Latitude latitude) throws IOException {
		View.header("Show CurrentLocation ");
		LatitudeCurrentlocationResourceJson latitudeCurrentlocationResourceJson = latitude.currentLocation.get().execute();
		View.display(latitudeCurrentlocationResourceJson);
	}

	static void showLocationHistory(Latitude latitude) throws IOException {
		View.header("Show CurrentLocation ");
		com.google.api.services.latitude.Latitude.Location.List list = latitude.location.list();
		list.maxResults = "10";
		LocationFeed locationFeed = list.execute();
		List<Location> locations = locationFeed.items;
		for (Location location : locations) {
			View.display(location);
		}
	}

	static void updateCurrentLocation(Latitude latitude) throws IOException {
		View.header("Show CurrentLocation ");
		LatitudeCurrentlocationResourceJson latitudeCurrentlocationResourceJson = new LatitudeCurrentlocationResourceJson();
		latitudeCurrentlocationResourceJson.put("latitude", "32.40");
		latitudeCurrentlocationResourceJson.put("longitude", "12.40");
		LatitudeCurrentlocationResourceJson response = latitude.currentLocation
				.insert(latitudeCurrentlocationResourceJson).execute();
		View.display(response);
	}

}
