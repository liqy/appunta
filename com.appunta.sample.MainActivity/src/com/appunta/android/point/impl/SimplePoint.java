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

package com.appunta.android.point.impl;

import android.location.Location;

import com.appunta.android.point.Point;
import com.appunta.android.point.renderer.PointRenderer;

/***
 * A single point representing a place, it contains information on where it's
 * located in space, in screen, it's id and name and the name of the renderer to
 * use to draw it.
 * 
 * @author Sergi Martínez
 *
 */
public class SimplePoint implements Point {

	public static int MILLION = 1000000;

	
	private int id;
	private Location location;
	private double distance;
	private String name;
	private PointRenderer renderer;
	private float x;
	private float y;
	private boolean selected;


	private boolean drawn=true;
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public SimplePoint(int id, Location location, PointRenderer renderer, String name) {
		super();
		this.setLocation(location);
		this.renderer=renderer;
		this.name=name;
	}
	public SimplePoint(int id, Location location, PointRenderer renderer) {
		this(id,location,renderer,"");
	}

	public SimplePoint(int id, Location location) {
		this(id,location,null);
	}
	
	
	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#getDistance()
	 */
	@Override
	public double getDistance() {
		return distance;
	}
	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#setDistance(double)
	 */
	@Override
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#getRenderer()
	 */
	@Override
	public PointRenderer getRenderer() {
		return renderer;
	}
	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#setRenderer(com.appunta.android.point.renderer.PointRenderer)
	 */
	@Override
	public void setRenderer(PointRenderer renderer) {
		this.renderer = renderer;
	}

	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#getId()
	 */
	@Override
	public int getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#setId(int)
	 */
	@Override
	public void setId(int id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#getX()
	 */
	@Override
	public float getX() {
		return x;
	}

	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#setX(float)
	 */
	@Override
	public void setX(float x) {
		this.x = x;
	}
	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#getY()
	 */
	@Override
	public float getY() {
		return y;
	}

	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#setY(float)
	 */
	@Override
	public void setY(float y) {
		this.y = y;
	}
	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#getLocation()
	 */
	@Override
	public Location getLocation() {
		return location;
	}
	/* (non-Javadoc)
	 * @see com.appunta.android.point.Point#setLocation(android.location.Location)
	 */
	@Override
	public void setLocation(Location location) {
		this.location = location;
	}
	@Override
	public boolean isDrawn() {
		return drawn;
	}
	@Override
	public void setDrawn(boolean drawn) {
		this.drawn=drawn;
	}

	
}
