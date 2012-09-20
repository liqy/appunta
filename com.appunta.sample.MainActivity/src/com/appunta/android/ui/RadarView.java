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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.appunta.android.point.Point;

public class RadarView extends AppuntaView {

	private int rotableBackground = 0;
	private float center;
	private Bitmap rotableBacgkroundBitmap;
	private double compassAngle=0;
	public RadarView(Context context) {
		super(context);
	}

	public RadarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	RadarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/***
	 * Returns the correct size of the control when needed (Basically
	 * maintaining the ratio)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = getDefaultSize(getSuggestedMinimumWidth(),
				widthMeasureSpec);
		int measuredHeight = getDefaultSize(getSuggestedMinimumHeight(),
				heightMeasureSpec);

		int size = Math.max(measuredWidth, measuredHeight);
		center = size / 2;
		setMeasuredDimension(size, size);
	}

	@Override
	protected void calculatePointCoordinates(Point point) {

		double pointAngle = getAngle(point) + compassAngle;
		double pixelDistance = point.getDistance() * center / getMaxDistance();
		double pointy = center - pixelDistance * Math.sin(pointAngle);
		double pointx = center + pixelDistance * Math.cos(pointAngle);
		point.setX((float) pointx);
		point.setY((float) pointy);
	}

	@Override
	protected void preRender(Canvas canvas) {
		drawBackground(canvas);
		compassAngle=getOrientation().getY();

	}

	@Override
	protected void postRender(Canvas canvas) {
		Paint pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pointPaint.setColor(0xff00a0d2);
		canvas.drawCircle(center, center, 5, pointPaint);

	}

	private void drawBackground(Canvas canvas) {
		if (getRotableBackground() != 0 && getOrientation()!=null) {

			Matrix transform = new Matrix();
			transform.setRectToRect(
					new RectF(0, 0, rotableBacgkroundBitmap.getWidth(),
							rotableBacgkroundBitmap.getHeight()), 
							new RectF(0, 0, getWidth(), getWidth()),
					Matrix.ScaleToFit.CENTER);
			transform.preRotate((float) -(Math.toDegrees(compassAngle)),
					rotableBacgkroundBitmap.getWidth() / 2, rotableBacgkroundBitmap.getHeight() / 2);
			canvas.drawBitmap(rotableBacgkroundBitmap, transform, null);
		}
	}

	
	public int getRotableBackground() {
		return rotableBackground;
	}

	public void setRotableBackground(int rotableBackground) {
		this.rotableBackground = rotableBackground;
		rotableBacgkroundBitmap = BitmapFactory.decodeResource(
				this.getResources(), rotableBackground);
	}

}