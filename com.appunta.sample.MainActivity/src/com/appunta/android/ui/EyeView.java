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

import com.appunta.android.math3d.Math3dUtil;
import com.appunta.android.math3d.Trig1;
import com.appunta.android.math3d.Trig3;
import com.appunta.android.math3d.Vector1;
import com.appunta.android.math3d.Vector2;
import com.appunta.android.math3d.Vector3;
import com.appunta.android.point.Point;

public class EyeView extends AppuntaView {

	private static final int SCREEN_DEPTH = 1;

	
	
	private Vector3 camRot=new Vector3();
	private Trig3   camTrig=new Trig3();
	private Vector3 camPos=new Vector3();
	private Vector3 pointPos=new Vector3();
	private Vector3 relativePos=new Vector3();
	private Vector3 relativeRotPos=new Vector3();

	private Vector3 screenRatio=new Vector3();
	
	private Vector2 screenPos=new Vector2();
	private Vector2 screenSize=new Vector2();

	private Vector1 screenRot=new Vector1();
	private Trig1 	screenRotTrig=new Trig1();



	private boolean drawn;
	


	
	public EyeView(Context context) {
		super(context);
		
		init();
	}

	public EyeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	EyeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		screenRatio.z=SCREEN_DEPTH;



	}

	@Override
    protected void preRender(Canvas canvas) {
			// For the moment we stablish a square as ratio. Size is arithmetic mean of width and height
            screenRatio.y=(getWidth()+getHeight())/2;
            screenRatio.x=(getWidth()+getHeight())/2;
            // Get the current size of the window
            screenSize.y=getHeight();
            screenSize.x=getWidth();	
            //Obtain the current camera rotation and related calculations based on phone orientation and rotation
			Math3dUtil.getCamRotation(getOrientation(), getPhoneRotation(), camRot, camTrig, screenRot, screenRotTrig);
			//Transform current camera location into a position object;
            Math3dUtil.convertLocationToPosition(getLocation(),  camPos);

            
	       
    }

	@Override
	protected void calculatePointCoordinates(Point point) {
		//Transform point Location into a Position object
		Math3dUtil.convertLocationToPosition(point.getLocation(), pointPos);
		//Calculate relative position to the camera. Transforms angles of latitude and longitude into meters of distance.
		Math3dUtil.getRelativeTranslationInMeters(pointPos, camPos, relativePos);
		//Rotates the point around the camera in order to stablish the camera rotation to <0,0,0>
        Math3dUtil.getRelativeRotation(relativePos, camTrig, relativeRotPos);
        //Converts a 3d position into a 2d position on screen
        drawn=Math3dUtil.convert3dTo2d(relativeRotPos, screenSize, screenRatio,  screenRotTrig, screenPos);
        //If drawn is false, the point is behind us, so no need to paint
        if (drawn) {
	        point.setX((float) screenPos.x);
		    point.setY((float) screenPos.y);
        }
        point.setDrawn(drawn);
}

	@Override
	protected void postRender(Canvas canvas) {
		// TODO Auto-generated method stub

	}



}