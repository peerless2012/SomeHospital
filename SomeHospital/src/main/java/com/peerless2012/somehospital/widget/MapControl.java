package com.peerless2012.somehospital.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.ZoomButton;
import android.widget.ZoomControls;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.peerless2012.somehospital.R;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/12 23:22
 * @Version V1.0
 * @Description : 地图控制控件
 */
public class MapControl extends LinearLayout implements View.OnClickListener{

    private ZoomButton mLocation;
    private ZoomButton mZoomIn;
    private ZoomButton mZoomOut;

    private AMap mAMap;

    public MapControl(Context context) {
        this(context, null);
    }

    public MapControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(false);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.map_control, this, // we are the parent
                true);

        mLocation = (ZoomButton) findViewById(R.id.location);
        mZoomIn = (ZoomButton) findViewById(R.id.zoom_in);
        mZoomOut = (ZoomButton) findViewById(R.id.zoom_out);
        mLocation.setOnClickListener(this);
        mZoomIn.setOnClickListener(this);
        mZoomOut.setOnClickListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupMargin();
    }

    private void setupMargin() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && getFitsSystemWindows()) {
            TypedArray typedArray = getContext().obtainStyledAttributes(new int[]{android.R.attr.windowTranslucentNavigation});
            boolean transNavigationBar = typedArray.getBoolean(0, false);
            if (transNavigationBar){
                LinearLayout.LayoutParams p = (LayoutParams) mZoomIn.getLayoutParams();
                p.bottomMargin += getNavigationBarHeight();
            }
            typedArray.recycle();

            typedArray = getContext().obtainStyledAttributes(new int[]{android.R.attr.windowTranslucentStatus});
            boolean statusBar = typedArray.getBoolean(0, false);
            if (statusBar){
                LinearLayout.LayoutParams p = (LayoutParams) mLocation.getLayoutParams();
                p.topMargin += getStatusBarHeight();
            }
            typedArray.recycle();
        }

    }

    /*
     * Sets how fast you get zoom events when the user holds down the
     * zoom in/out buttons.
     */
    public void setZoomSpeed(long speed) {
        mZoomIn.setZoomSpeed(speed);
        mZoomOut.setZoomSpeed(speed);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /* Consume all touch events so they don't get dispatched to the view
         * beneath this view.
         */
        return true;
    }

    public void show() {
        fade(View.VISIBLE, 0.0f, 1.0f);
    }

    public void hide() {
        fade(View.GONE, 1.0f, 0.0f);
    }

    private void fade(int visibility, float startAlpha, float endAlpha) {
        AlphaAnimation anim = new AlphaAnimation(startAlpha, endAlpha);
        anim.setDuration(500);
        startAnimation(anim);
        setVisibility(visibility);
    }

    public void setIsZoomInEnabled(boolean isEnabled) {
        mZoomIn.setEnabled(isEnabled);
    }

    public void setIsZoomOutEnabled(boolean isEnabled) {
        mZoomOut.setEnabled(isEnabled);
    }

    @Override
    public boolean hasFocus() {
        return mZoomIn.hasFocus() || mZoomOut.hasFocus();
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ZoomControls.class.getName();
    }


    public void attachMap(AMap aMap){
        mAMap = aMap;
        refreshZoomButton();
    }

    private void refreshZoomButton() {
        float maxZoomLevel = mAMap.getMaxZoomLevel();
        float minZoomLevel = mAMap.getMinZoomLevel();
        float zoom = mAMap.getCameraPosition().zoom;
        mZoomOut.setEnabled(zoom > minZoomLevel);
        mZoomIn.setEnabled(zoom < maxZoomLevel);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void detachMap(){
        mAMap = null;
    }

    @Override
    public void onClick(View v) {
        if (mAMap == null ) return;
        switch (v.getId()){
            case R.id.zoom_in:
                mAMap.animateCamera(CameraUpdateFactory.zoomIn());
                refreshZoomButton();
                break;

            case R.id.zoom_out:
                mAMap.animateCamera(CameraUpdateFactory.zoomOut());
                refreshZoomButton();
                break;
            case R.id.location:
                mAMap.setMyLocationEnabled(true);
                break;
        }
    }

    private int getStatusBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        return resources.getDimensionPixelSize(resourceId);
    }

    private int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
