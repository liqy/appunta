/*
   Copyright Sergi Martínez (@sergiandreplace)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.appunta.android.point;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

/***
 * A simple utility class intented to  perform basic operations
 * 
 * @author Sergi Martínez
 * 
 */
public class PointsUtil {
	private static int EARTH_RADIUS_METERS = 6371000;

	/***
	 * Calculate the distance from a given point to all the points stored and
	 * sets the distance property for all them
	 * 
	 * @param location
	 *            Latitude and longitude of the given point
	 */
	public static void calculateDistance(List<? extends Point> points, Location location) {
		for (Point poi : points) {
			poi.setDistance(distanceInMeters(poi.getLocation(),location));
		}
	}

	/***
	 * Returns a subset of points that are below a distance of a given point
	 * 
	 * @param location
	 *            Latitude and longitude of the given point
	 * @param distance
	 *            Distance to filter
	 * @return The subset list
	 */
	public static List<Point> getNearPoints(List<Point> points, Location location,
			double distance) {
		calculateDistance(points, location);
		List<Point> subPoints = new ArrayList<Point>();
		for (Point poi : points) {
			if (poi.getDistance() <= distance) {
				subPoints.add(poi);
			}
		}
		return subPoints;
	}

	/**
	 * Computes the distance in meters between two points on Earth.
	 * 
	 * @param location
	 *            Latitude and longitude of the first point
	 * @param otherLocation
	 *            Latitude and longitude of the second point
	 * @return Distance between the two points in kilometers.
	 */
	private static double distanceInMeters(Location location, Location otherLocation) {
		double lat1Rad = Math.toRadians(otherLocation.getLatitude());
		double lat2Rad = Math.toRadians(location.getLatitude());
		double deltaLonRad = Math.toRadians(location.getLongitude()
				- otherLocation.getLongitude());

		return Math
				.acos(Math.sin(lat1Rad) * Math.sin(lat2Rad) + Math.cos(lat1Rad)
						* Math.cos(lat2Rad) * Math.cos(deltaLonRad))
				* EARTH_RADIUS_METERS;
	}

}
