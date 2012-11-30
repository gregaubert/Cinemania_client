package com.cinemania.camera;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.input.touch.detector.PinchZoomDetector.IPinchZoomDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import static com.cinemania.constants.AllConstants.*;

import com.cinemania.activity.Base;

import android.util.Log;

public class CameraManager implements IOnSceneTouchListener, IScrollDetectorListener, IPinchZoomDetectorListener{

	private ZoomCamera mZoomCamera;
	private Base mActivity;

	private SurfaceScrollDetector mScrollDetector;
	private PinchZoomDetector mPinchZoomDetector;
	private float mPinchZoomStartedCameraZoomFactor;
	
	public CameraManager(ZoomCamera zoomCamera) {
		mZoomCamera = zoomCamera;
		mActivity = Base.getSharedInstance();
		
		if(MultiTouch.isSupported(mActivity)) {
			if(MultiTouch.isSupportedDistinct(mActivity)) {
				Log.i("MULTITOUCH", "MultiTouch detected --> Both controls will work properly!");
			} else {
				Log.i("MULTITOUCH", "MultiTouch detected, but your device has problems distinguishing between fingers.\n\nControls are placed at different vertical locations.");
			}
		} else {
			Log.i("MULTITOUCH", "Sorry your device does NOT support MultiTouch!\n\n(Falling back to SingleTouch.)\n\nControls are placed at different vertical locations.");
		}
		
		mScrollDetector = new SurfaceScrollDetector(this);
		mPinchZoomDetector = new PinchZoomDetector(this);
		
		mZoomCamera.setBounds(0, 0, zoomCamera.getWidth() * 2, zoomCamera.getHeight() * 2);
		mZoomCamera.setBoundsEnabled(true);
		mZoomCamera.setZoomFactor(1f);
	}

	@Override
	public void onPinchZoomStarted(PinchZoomDetector pPinchZoomDetector,
			TouchEvent pSceneTouchEvent) {
		mPinchZoomStartedCameraZoomFactor = mZoomCamera.getZoomFactor();
	}

	@Override
	public void onPinchZoom(PinchZoomDetector pPinchZoomDetector,
			TouchEvent pTouchEvent, float pZoomFactor) {
		if (pZoomFactor != 1)
	    {
	        // check bounds
	        float newZoomFactor = mPinchZoomStartedCameraZoomFactor * pZoomFactor;
	        if (newZoomFactor <= BOARD_ZOOM_MIN)
	            mZoomCamera.setZoomFactor(BOARD_ZOOM_MIN);
	        else if (newZoomFactor >= BOARD_ZOOM_MAX)
	        	mZoomCamera.setZoomFactor(BOARD_ZOOM_MAX);
	        else
	        	mZoomCamera.setZoomFactor(newZoomFactor);
	    }
	}

	@Override
	public void onPinchZoomFinished(PinchZoomDetector pPinchZoomDetector,
			TouchEvent pTouchEvent, float pZoomFactor) {
		//mZoomCamera.setZoomFactor(this.mPinchZoomStartedCameraZoomFactor * pZoomFactor);
	}

	@Override
	public void onScrollStarted(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {
		final float zoomFactor = this.mZoomCamera.getZoomFactor();
		mZoomCamera.offsetCenter(-pDistanceX / zoomFactor, -pDistanceY / zoomFactor);
	}

	@Override
	public void onScroll(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {
		final float zoomFactor = this.mZoomCamera.getZoomFactor();
		mZoomCamera.offsetCenter(-pDistanceX / zoomFactor, -pDistanceY / zoomFactor);
	}

	@Override
	public void onScrollFinished(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {
		final float zoomFactor = this.mZoomCamera.getZoomFactor();
		mZoomCamera.offsetCenter(-pDistanceX / zoomFactor, -pDistanceY / zoomFactor);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		mPinchZoomDetector.onTouchEvent(pSceneTouchEvent);

		if(mPinchZoomDetector.isZooming()) {
			mScrollDetector.setEnabled(false);
		} else {
			if(pSceneTouchEvent.isActionDown()) {
				mScrollDetector.setEnabled(true);
			}
			mScrollDetector.onTouchEvent(pSceneTouchEvent);
		}

		return true;
	}

}
