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

package com.appunta.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
    private View btnRadar;
	private View btnAR;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        setListeners();
        
    }
    
    private void findViews() {
    	btnRadar=findViewById(R.id.btnRadar);
    	btnAR=findViewById(R.id.btnAR);
    }
    
    private void setListeners() {
    	btnRadar.setOnClickListener(this);
    	btnAR.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		if (v.getId()==btnRadar.getId()) {
			Intent i=new Intent(this, RadarActivity.class);
			startActivity(i);
		}
		if (v.getId()==btnAR.getId()) {
			Intent i=new Intent(this, EyeViewActivity.class);
			startActivity(i);
		}
	}
}