package com.example.topnews.screens.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.example.topnews.R;
import javax.annotation.Nullable;

/**
 * @author xix
 */

public class LoadingView extends FrameLayout {
	
	public static final int IMAGE = 1;
	public static final int TEXT = 0;
	private static final String LOG_TAG = LoadingView.class.getSimpleName();
	
	private static final int MIN_SHOW_TIME = 500; // ms
	private static final int MIN_DELAY = 500; // ms

	long mStartTime = -1;
	
	boolean mPostedHide = false;
	
	boolean mPostedShow = false;
	
	boolean mDismissed = false;
	@Nullable private Listener listener;
	
	public LoadingView(Context context) {
		this(context, null);
	}
	
	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}
	
	private void initialize() {
		/* Inflate Layout and set Attributes. */
		LayoutInflater.from(getContext()).inflate(R.layout.progress_bar, this, true);
		/* Initialize Views. */
		setClickable(true);
		setVisibility(VISIBLE);
	}
	
	private void hideRun() {
		mPostedHide = false;
		mStartTime = -1;
		setVisibility(View.GONE);
		if (listener != null) {
			listener.onHide();
		}
	}
	
	private void showRun() {
		mPostedShow = false;
		Log.d(LOG_TAG, "delay show: ");
		if (!mDismissed) {
			mStartTime = System.currentTimeMillis();
			setVisibility(View.VISIBLE);
			if (listener != null) {
				listener.onShow();
			}
		}
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		removeCallbacks();
	}
	
	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		removeCallbacks();
	}
	
	private void removeCallbacks() {
		removeCallbacks(null);
	}
	
	/**
	 * Hide the progress view if it is visible. The progress view will not be
	 * hidden until it has been shown for at least a minimum show time. If the
	 * progress view was not yet visible, cancels showing the progress view.
	 */
	
	public synchronized void hide() {
		mDismissed = true;
		removeCallbacks();
		mPostedShow = false;
		long diff = System.currentTimeMillis() - mStartTime;
		if (diff >= MIN_SHOW_TIME || mStartTime == -1) {
			// The progress spinner has been shown long enough
			// OR was not shown yet. If it wasn't shown yet,
			// it will just never be shown.
			setVisibility(View.GONE);
			if (listener != null) {
				listener.onHide();
			}
		} else {
			// The progress spinner is shown, but not long enough,
			// so put a delayed message in to hide it when its been
			// shown long enough.
			if (!mPostedHide) {
				postDelayed(this::hideRun, MIN_SHOW_TIME - diff);
				mPostedHide = true;
			}
		}
	}
	
	/**
	 * Show the progress view after waiting for a minimum delay. If
	 * during that time, hide() is called, the view is never made visible.
	 */
	public synchronized void show() {
		// Reset the start time.
		mStartTime = -1;
		mDismissed = false;
		removeCallbacks();
		mPostedHide = false;
		if (!mPostedShow) {
			postDelayed(this::showRun, MIN_DELAY);
			mPostedShow = true;
		}
	}
	
	public interface Listener {
		void onShow();
		
		void onHide();
	}
}
