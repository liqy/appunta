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

import android.location.Location;

import com.appunta.android.point.renderer.PointRenderer;

public interface Point {

	/***
	 * Distance to a point
	 * @return the distance in Km if previously set
	 */
	public abstract double getDistance();

	/***
	 * Allows to store the distance to a point
	 * @param distance Distance to a point in Km
	 */
	public abstract void setDistance(double distance);

	/***
	 * Name of the point. Created in order to make your life easier
	 * @return the name of the point
	 */
	public abstract String getName();

	/***
	 * Allows to store a name for the point
	 * @param name the intended name
	 */
	public abstract void setName(String name);

	/***
	 * Gets the name of the renderer to use to draw this point
	 * @return The renderer
	 */
	public abstract PointRenderer getRenderer();

	/***
	 * To assign a renderer to the current point
	 * @param renderer
	 */
	public abstract void setRenderer(PointRenderer renderer);

	/***
	 * A unique id
	 * @return an id
	 */
	public abstract int getId();

	/***
	 * A unique id
	 * @param id
	 */
	public abstract void setId(int id);

	/***
	 * Last X coordinate where the point should be drawn
	 * @return X coordinate of the canvas
	 */
	public abstract float getX();

	public abstract void setX(float x);

	/***
	 * Last Y coordinate where the point should be drawn
	 * @return Y coordinate of the canvas
	 */
	public abstract float getY();

	public abstract void setY(float y);

	public abstract Location getLocation();

	public abstract void setLocation(Location location);

	public abstract boolean isSelected();
	
	public abstract void setSelected(boolean selected);
	
	public abstract boolean isDrawn();
	
	public abstract void setDrawn(boolean drawn);
	
}