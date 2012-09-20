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
public class EyeViewRenderer implements PointRenderer {
	private Bitmap selectedBitmap=null;
	private Resources res;
	private int selectedId;
	private int xSelectedOff;
	private int ySelectedOff;
	private int unselectedId;
	private Bitmap unselectedBitmap;
	private int xUnselectedOff;
	private int yUnselectedOff;
	private Paint pText;
	private Paint pBlackLine;
	/***
	 * Creates and object able to draw a drawable resource in a Canvas
	 * @param res A resources object in order to retrieve the drawable
	 * @param selectedId Id of the drawable
	 */
	public EyeViewRenderer(Resources res, int selectedId, int unselectedId) {
		this.selectedId=selectedId;
		this.unselectedId=unselectedId;
		this.res=res;	
	}
	/***
	 * This methods paints the drawable received in constructor and writes the point name beside it
	 */
	@Override
	public void drawPoint(Point point, Canvas canvas, Orientation orientation) {
		if (selectedBitmap==null) {
			
			//Initialize drawing objects
			
			selectedBitmap=BitmapFactory.decodeResource(res, selectedId);
			unselectedBitmap=BitmapFactory.decodeResource(res, unselectedId);
			
			xSelectedOff = selectedBitmap.getWidth()/2;
			ySelectedOff = selectedBitmap.getHeight()/2;
		
			xUnselectedOff = unselectedBitmap.getWidth()/2;
			yUnselectedOff = unselectedBitmap.getHeight()/2;
			
			pText = new Paint(Paint.ANTI_ALIAS_FLAG);
			pText.setStyle(Paint.Style.STROKE);
			pText.setTextAlign(Paint.Align.LEFT);
			pText.setTextSize(20);
			pText.setTypeface(Typeface.SANS_SERIF);
			pText.setColor(Color.WHITE);
			
			pBlackLine=new Paint(Paint.ANTI_ALIAS_FLAG);
			pBlackLine.setColor(Color.BLACK);
			pBlackLine.setTextSize(20);
			pBlackLine.setTypeface(Typeface.SANS_SERIF);
			pBlackLine.setTextAlign(Paint.Align.LEFT);
						
		}
		if (point.isSelected()) {
			canvas.drawBitmap(selectedBitmap, point.getX()-xSelectedOff, point.getY()- ySelectedOff, null);
		}else{
			canvas.drawBitmap(unselectedBitmap, point.getX()-xUnselectedOff, point.getY()- yUnselectedOff, null);
		}
		canvas.rotate(315, point.getX(), point.getY());
		canvas.drawText(point.getName(), point.getX()+35,point.getY(), pBlackLine);
		canvas.drawText(point.getName(), point.getX()+34 ,point.getY()-2, pText);
		canvas.rotate(-315, point.getX(), point.getY());
	}
	

}
