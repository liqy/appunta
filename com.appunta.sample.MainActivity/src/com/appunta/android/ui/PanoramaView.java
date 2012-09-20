/*
   Copyright  Sergi Martínez (@sergiandreplace)

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

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.appunta.android.point.Point;

public class PanoramaView extends AppuntaView {

	private static final double VISIBLE_DEGREES = Math.PI / 3;
	private static final double MAX_DEGREES = Math.PI * 2;

	public PanoramaView(Context context) {
		super(context);
	}

	public PanoramaView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	PanoramaView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

		
	@Override
	protected void preRender(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void calculatePointCoordinates(Point point) {
		double angularDistance = angleDifference(Math.toRadians(getOrientation().getX()), MAX_DEGREES
				/ 4 - getAngle(point));
		// double angularDistance= getAngle(point);
		point.setX((float) ((angularDistance + VISIBLE_DEGREES / 2) * getWidth() / VISIBLE_DEGREES));
		point.setY((float) (getHeight()-getHeight() * point.getDistance()/ getMaxDistance()));
	
	}
	
	@Override
	protected void postRender(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}
	private double angleDifference(double centered, double moved) {
		double cwDiff = cwDifference(centered, moved);
		double ccwDiff = ccwDiference(centered, moved);
		if (cwDiff < ccwDiff) {
			return cwDiff;
		} else {
			return -ccwDiff;
		}
	}

	private double cwDifference(double centered, double moved) {
		double cw = 0;
		cw = moved - centered;
		if (cw < 0) {
			cw += MAX_DEGREES;
		}
		return cw;
	}

	private double ccwDiference(double centered, double moved) {
		double ccw = 0;
		ccw = centered - moved;
		if (ccw < 0) {
			ccw += MAX_DEGREES;
		}
		return ccw;
	}

}