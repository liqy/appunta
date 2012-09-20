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

/**
 *  The list of trigonometric values (sin and cos) of a Vector3
 * 
 * @author Sergi Martínez
 *
 */
public class Trig1 {
	public double sin;
	public double cos;

	
	public Trig1(){
		
	}
	
	/**
	 * 
	 * @param point
	 */
	public Trig1(Vector1 point){
		setVector1(point);
	}
	
	
	
	public void setVector1(Vector1 point) {
		sin=Math.sin(point.v);
		cos=Math.cos(point.v);
	}
}
