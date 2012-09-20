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

package com.appunta.android.point.renderer.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appunta.android.orientation.Orientation;
import com.appunta.android.point.Point;
import com.appunta.android.point.renderer.PointRenderer;

/***
 * A simple Point renderer used as default by the compass
 * @author Sergi Martínez
 *
 */
public class SimplePointRenderer implements PointRenderer {

	private static Bitmap b = null;
	/***
	 * Draws a small circle in the point coordinates
	 */
	@Override
	public void drawPoint(Point point, Canvas canvas, Orientation orientation) {
		if (b == null) {
			Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
			p.setColor(0x44FFFFFF);
			canvas.drawCircle(point.getX(), point.getY(), 5, p);
			p.setColor(0x33FFFFFF);
			canvas.drawCircle(point.getX(), point.getY(), 4, p);
			p.setColor(0x66FFFFFF);
			canvas.drawCircle(point.getX(), point.getY(), 3, p);
			p.setColor(0x99FFFFFF);
			canvas.drawCircle(point.getX(), point.getY(), 2, p);
			p.setColor(0xFFFFFFFF);
			canvas.drawCircle(point.getX(), point.getY(), 1, p);
		}
		
	}
	
	

}
