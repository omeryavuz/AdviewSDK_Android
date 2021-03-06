package com.kyview.adapters;

import android.app.Activity;
import android.content.Context;
//import android.graphics.Color;
import android.util.Log;


import com.donson.momark.AdManager;
import com.donson.momark.view.view.AdView;
import com.donson.momark.util.AdViewListener;


import com.kyview.AdViewLayout;
import com.kyview.AdViewTargeting;
import com.kyview.AdViewLayout.ViewAdRunnable;
import com.kyview.AdViewTargeting.RunMode;
import com.kyview.obj.Ration;
import com.kyview.util.AdViewUtil;


public class MomarkAdapter extends AdViewAdapter implements AdViewListener{
	AdView adView;
		
	public MomarkAdapter(AdViewLayout adViewLayout, Ration ration) {
		// TODO Auto-generated constructor stub

		super(adViewLayout, ration);
		String key=new String(ration.key);
		String key2=new String(ration.key2);
		
		AdManager.init(key2, key);
		adView = null;
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Momark");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}
	
	    Activity activity = adViewLayout.activityReference.get();
		  if(activity == null) {
			  return;
		  }  	     		
	    adView = new AdView((Context)activity, "adview");		
		adView.setAdViewListener(this);
	}

	@Override
	public void onConnectFailed(AdView paramAdView) {
		// TODO Auto-generated method stub
		
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Momark failure");
	    
		 paramAdView.setAdViewListener(null);

		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			 return;
		  }
		 adViewLayout.adViewManager.resetRollover_pri();
		  adViewLayout.rotateThreadedPri();
		
	}

	@Override
	public void onAdViewSwitchedAd(AdView paramAdView) {
		// TODO Auto-generated method stub
	
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Momark success");

		paramAdView.setAdViewListener(null);
		
		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			  return;
		  }

		  adViewLayout.adViewManager.resetRollover();
		  adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, paramAdView));
		  adViewLayout.rotateThreadedDelayed();
		
	}


}
