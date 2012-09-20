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

package com.appunta.android.math3d;

import android.location.Location;
import android.view.Surface;

import com.appunta.android.orientation.Orientation;

public class Math3dUtil {

	//Check http://gis.stackexchange.com/questions/2951/algorithm-for-offsetting-a-latitude-longitude-by-some-amount-of-meters
	private static final double METERS_IN_A_DEGREE = 111111;
	
	private static double QUADRANT = Math.PI / 2;
	private static Vector2 viewPortPos=new Vector2();

	/**
	 * This method transforms the camera orientation angles into 3D space angles. It also calculates angle Z separately, as it
	 * is a local angle, not an universal one (We want to move our head, not the whole universe)
	 * @param inOrientation Input parameter. The orientation of the camera
	 * @param inPhoneRotation Input parameter. The current camera rotation. This is a int constant from Surface class with four possible values
	 * @param outCamRot Output parameter. The real camera rotation in our 3D Space
	 * @param outCamTrig Output parameter. The trigonometric calculations of all three angles
	 * @param outScreenRot Output parameter. The rotation angle of the screen (local Z angle)
	 * @param outScreenRotTrig Output parameter. The trigonometric calculations of the angle.
	 */
	public static void getCamRotation(Orientation inOrientation, 
									  int inPhoneRotation, 
									  Vector3 outCamRot, 
									  Trig3 outCamTrig, 
									  Vector1 outScreenRot, 
									  Trig1 outScreenRotTrig) {
		// X goes the other way
		outCamRot.x = -inOrientation.getX();
		// 0 value is south, so turn around
		outCamRot.y = inOrientation.getY()+Math.PI;
		//The universal rotation angle for Z is always 0
		outCamRot.z = 0;
		
		//The value of the z angle (screen rotation) 
		if (inPhoneRotation == Surface.ROTATION_0) {
			outScreenRot.v = -inOrientation.getZ();
		}
		if (inPhoneRotation == Surface.ROTATION_180) {
			outScreenRot.v = -inOrientation.getZ() + Math.PI;
		}

		if (inPhoneRotation == Surface.ROTATION_90) {
			outScreenRot.v = -inOrientation.getZ()-QUADRANT;
		}
		if (inPhoneRotation == Surface.ROTATION_270) {
			outScreenRot.v = -inOrientation.getZ()+QUADRANT;
		}
		outCamTrig.setVector3(outCamRot);
		outScreenRotTrig.setVector1(outScreenRot);

	}

	/**
	 * Transforms a Location type into a position in space (currently is direct attribution) just to move from lat/lon/alt to 
	 * z,x,y
	 * @param inLocation Input parameter. A location where the point is currently located.
	 * @param outPos A position object with same values.
	 */
	public static void convertLocationToPosition(Location inLocation, 
								   Vector3 outPos) {
		outPos.z = inLocation.getLatitude();
		outPos.x = inLocation.getLongitude();
		outPos.y = inLocation.getAltitude();
	}

	/***
	 * Calculates de relative position of a point getting the camera position as 0,0,0. Returns the result in meters
	 * Check this article: http://gis.stackexchange.com/questions/2951/algorithm-for-offsetting-a-latitude-longitude-by-some-amount-of-meters
	 * @param inPointPos Input parameter. Current position of the point, z is latitude in degrees, y is altitude in meters, x is longitude in degrees
	 * @param inCamPos Input parameter. Current position of the camera, z is latitude in degrees, y is in meters, x is longitude in degrees
	 * @param outRelativePosInMeters Output parameter. Relative distance of the point to the camera. X and Z are converted to meters
	 */
	public static void getRelativeTranslationInMeters(Vector3 inPointPos, Vector3 inCamPos,  Vector3 outRelativePosInMeters) {
		outRelativePosInMeters.z=(inCamPos.z-inPointPos.z) * METERS_IN_A_DEGREE;
		outRelativePosInMeters.y=(inCamPos.y-inPointPos.y);
		outRelativePosInMeters.x=(inCamPos.x-inPointPos.x) * Math.cos(inCamPos.z-inPointPos.z) * METERS_IN_A_DEGREE;
	}
	
	/***
	 * Check this article before trying to only understand a simple comma   
	 * http://en.wikipedia.org/wiki/3D_projection#Perspective_projection    
	 * In fact, I don't care too much about the formula. Just C&P it.       
	 * @param inRelativePos Input parameter. The coordinates of a point relative to camera position in meters
	 * @param inCamTrig Input parameter. The set of trigonometric calculations of the camera angles
	 * @param outRelativeRotPos Output parameter. The final coordinates of the point after rotating the space in order to set the camera to angles 0,0,0
	 */
	public static void getRelativeRotation(Vector3 inRelativePos,
											  Trig3 inCamTrig, 
											  Vector3 outRelativeRotPos) {
        // Check this article before trying to only understand a simple comma
        // http://en.wikipedia.org/wiki/3D_projection#Perspective_projection
        // In fact, I don't care too much about the formula. Just C&P it.
        outRelativeRotPos.x = inCamTrig.yCos * (inCamTrig.zSin * inRelativePos.y + inCamTrig.zCos * inRelativePos.x) 
        		         - inCamTrig.ySin * inRelativePos.z;
        outRelativeRotPos.y = inCamTrig.xSin * (inCamTrig.yCos * inRelativePos.z + inCamTrig.ySin * (inCamTrig.zSin * inRelativePos.y + inCamTrig.zCos * inRelativePos.x)) 
        		         + inCamTrig.xCos * (inCamTrig.zCos * inRelativePos.y - inCamTrig.zSin * inRelativePos.x);
        outRelativeRotPos.z = inCamTrig.xCos * (inCamTrig.yCos * inRelativePos.z + inCamTrig.ySin * (inCamTrig.zSin * inRelativePos.y + inCamTrig.zCos * inRelativePos.x)) 
        		         - inCamTrig.xSin * (inCamTrig.zCos * inRelativePos.y - inCamTrig.zSin * inRelativePos.x);
		
	}

	/**
	 * 
	 * @param inRelativePos Input parameter. 
	 * @param inScreenSize Input parameter. 
	 * @param inScreenRatio Input parameter. 
	 * @param inScreenRotTrig Input parameter. 
	 * @param outScreenPos Output parameter. 
	 * @return
	 */
	public static boolean convert3dTo2d(Vector3 inRelativePos,
										Vector2 inScreenSize, 
										Vector3 inScreenRatio, 
										Trig1 inScreenRotTrig, 
										Vector2 outScreenPos) {
		if (inRelativePos.z > 0) {
	    	viewPortPos.x = (inRelativePos.x * inScreenRatio.x) / (inScreenRatio.z * inRelativePos.z);
	    	viewPortPos.y =  (inRelativePos.y * inScreenRatio.y) / (inScreenRatio.z * inRelativePos.z);
		    outScreenPos.x=inScreenSize.x / 2 +  viewPortPos.x * inScreenRotTrig.cos - viewPortPos.y * inScreenRotTrig.sin;
		    outScreenPos.y=inScreenSize.y /2 +  viewPortPos.y * inScreenRotTrig.cos + viewPortPos.x * inScreenRotTrig.sin;
		    return true;
		} else {
			return false;
		}		
	}


	
}
