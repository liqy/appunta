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

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.appunta.android.orientation.Orientation;
import com.appunta.android.point.Point;
import com.appunta.android.point.renderer.PointRenderer;

/***
 * This class is used to generate a PointRenderer using a drawable
 * resource
 * @author Sergi Martínez
 *
 */
public class DrawablePointRenderer implements PointRenderer {
	private Bitmap b=null;
	private Resources res;
	private int id;
	private int xOff;
	private int yOff;
	private Paint pText;
	/***
	 * Creates and object able to draw a drawable resource in a Canvas
	 * @param res A resources object in order to retrieve the drawable
	 * @param id Id of the drawable
	 */
	public DrawablePointRenderer(Resources res, int id) {
		this.id=id;
		this.res=res;	
	}
	/***
	 * This methods paints the drawable received in constructor and writes the point name beside it
	 */
	@Override
	public void drawPoint(Point point, Canvas canvas, Orientation orientation) {
		if (b==null) {
			
			//Initialize drawing objects
			
			b=BitmapFactory.decodeResource(res, id);
			xOff = b.getWidth()/2;
			yOff = b.getHeight()/2;
		

			pText = new Paint(Paint.ANTI_ALIAS_FLAG);
			pText.setStyle(Paint.Style.FILL);
			pText.setTextAlign(Paint.Align.LEFT);
			pText.setTextSize(20);
			pText.setTypeface(Typeface.SANS_SERIF);
			pText.setColor(Color.WHITE);
				
		}
		
		canvas.drawBitmap(b, point.getX()-xOff, point.getY()- yOff, null);
		canvas.drawText(point.getName(), point.getX() + xOff,point.getY()+8, pText);
		
	}

}
