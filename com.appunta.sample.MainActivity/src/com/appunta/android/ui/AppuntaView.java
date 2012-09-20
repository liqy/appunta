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

package com.appunta.android.ui;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.location.Location;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.appunta.android.orientation.Orientation;
import com.appunta.android.point.Point;
import com.appunta.android.point.PointsUtil;
import com.appunta.android.point.renderer.PointRenderer;

/***
 * <p>This is the base class in order to create Views using the Appunta system.
 * The class has all needed calculations and values to retrieve info from points.</p>
 * 
 * <p>It's important to understand how this will work. All the stuff happens in the onDraw Method.
 * 
 * <p>The {@link #onDraw} method has three phases: <b>preRender</b>, <b>pointRendering</b> & <b>postRender</b>. </p>
 * <ul>
 * <li>The <b>preRender</b> phase triggers the method {@link #preRender}, used to draw all needed elements used in
 * the background.</li>
 * 
 * <li>In the <b>pointRendering</b> phase, the method calculatePointCoordinates(SimplePoint) is invoked per each on of the points, 
 * in order to calculate the screen coordinates for each one of them. Then, they are painted by calling
 * their PaintRenderer.
 * </li>
 * 
 * <li>In the <b>Post render</b> phase, the {@link #postRender(Canvas)} method is invoked in order to paint
 * the foreground layer.</li>
 * </ul>
 * 
 * @author Sergi Martínez
 *
 */
public abstract class AppuntaView extends View {
	
	
	/**
	 * This interface represents an object able to be called when a point is pressed
	 * @author Sergi Martínez
	 *
	 */
	public interface OnPointPressedListener {
		public void onPointPressed(Point p);
	}

	/**
	 * The default max distance that will be shown if not changed
	 */
	private static final double DEFAULT_MAX_DISTANCE = 1000;


	private Location location;

	
	private double maxDistance = DEFAULT_MAX_DISTANCE;

	
	private List<? extends Point> points;

	private OnPointPressedListener onPointPressedListener;

	private Double minorDistance;

	private double distance;

	private Point p;


	private PointRenderer pointRenderer;
	
	private Orientation orientation;
	
	private int phoneRotation=0;

	public AppuntaView(Context context) {
		super(context);
	}

	public AppuntaView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AppuntaView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Given a screen coordinate, returns the nearest point to that coordinate
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 * @return The nearest point to coordinate X,Y
	 */
	protected Point findNearestPoint(float x, float y) {
		p = null;
		minorDistance = (double) Math.max(this.getWidth(),this.getHeight());
		for (Point point : getpoints()) {
			distance = Math.sqrt(Math.pow((point.getX() - x), 2)
					+ Math.pow((point.getY() - y), 2));
			if (distance < minorDistance) {
				minorDistance = distance;
				p = point;
			}
		}
		return p;
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (getOrientation()==null) {
			return;
		}
		PointsUtil.calculateDistance(points, location);
		preRender(canvas);
		if (getpoints() != null) {
			for (Point point : getpoints()) {
				calculatePointCoordinates(point);
				if (point.getDistance()<maxDistance && point.isDrawn()) {
					if (point.getRenderer()!=null){
						point.getRenderer().drawPoint(point, canvas,orientation);
						
					}else{
						if (this.pointRenderer!=null) {
							getPointRenderer().drawPoint(point,canvas,orientation);
	
						}
					}
				}
			}
		}
		postRender(canvas);
	}

	/***
	 * Returns the correct size of the control when needed (Basically
	 * maintaining the ratio)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Point p = findNearestPoint(event.getX(), event.getY());
			
			if (p != null && getOnPointPressedListener() != null) {
				if (Math.abs(p.getX()-event.getX())<50 && Math.abs(p.getY()-event.getY())<50) {
					onPointPressedListener.onPointPressed(p);
				}
			}
		}

		return super.onTouchEvent(event);
	}

	/**
	 * This is the first method called during the painting process. It's used to draw the background layer
	 * @param canvas The canvas where to draw
	 */
	protected abstract void preRender(Canvas canvas);

	/**
	 * This method will be called for each point on the rendering process, in order to determine where this point
	 * should be drawn in the screen
	 * @param point The point to calculate
	 */
	protected abstract void calculatePointCoordinates(Point point);

	/**
	 * This is the last method called during the painting process. It's used to draw the foreground layer
	 * @param canvas The canvas where to draw
	 */
	protected abstract void postRender(Canvas canvas);

	

	protected double getAngle(Point point) {
		return Math.atan2(point.getLocation().getLatitude() - location.getLatitude(),
						  point.getLocation().getLongitude() - location.getLongitude());
	}

	protected double getVerticalAngle(Point point) {
		return Math.atan2(point.getDistance(), location.getAltitude());
	}

	public Location getLocation() {
		return location;
	}


	public double getMaxDistance() {
		return maxDistance;
	}

	public OnPointPressedListener getOnPointPressedListener() {
		return onPointPressedListener;
	}



	protected List<? extends Point> getpoints() {
		return points;
	}


	public void setLocation(Location location) {
		this.location=location;
	}


	public void setMaxDistance(double maxDistance) {
		this.maxDistance = maxDistance;
		this.invalidate();

	}

	public void setOnPointPressedListener(
			OnPointPressedListener onPointPressedListener) {
		this.onPointPressedListener = onPointPressedListener;
	}

	public void setPosition(Location location) {
		this.location=location;
		PointsUtil.calculateDistance(points,location);
	}

	public void setPoints(List<? extends Point> points) {
		this.points=points;
}

	public PointRenderer getPointRenderer() {
		return pointRenderer;
	}

	public void setPointRenderer(PointRenderer pointRenderer) {
		this.pointRenderer = pointRenderer;

	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		
		this.invalidate();

	}

	public int getPhoneRotation() {
		return phoneRotation;
	}

	public void setPhoneRotation(int phoneRotation) {
		this.phoneRotation = phoneRotation;
	}


}
