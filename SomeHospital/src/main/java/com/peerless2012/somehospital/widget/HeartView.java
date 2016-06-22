package com.peerless2012.somehospital.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;

import com.peerless2012.somehospital.R;

public class HeartView extends View {

	// width="645"
	// height="585"

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * View缺省宽度
	 */
	private int defaultWidth;

	/**
	 * View缺省高度
	 */
	private int defaultHeight;

	/**
	 * 是否需要重新计算各个点、矩形等的坐标
	 */
	private boolean isPointsDirty = false;

	private int mContentWidth = 0;

	private Path mHeartPath = new Path();

	private Path mCirclePath = new Path();

	private Bitmap mBitmap;

	public HeartView(Context context) {
		super(context);
		init(context, null);
	}

	public HeartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs);
	}

	public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, null);
	}


	private void init(Context context, AttributeSet attrs) {
		defaultWidth = defaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splash);
		setWillNotDraw(false);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		//只要是不是精确指定的，就设置为默认宽高。
		if (widthMode == MeasureSpec.UNSPECIFIED) {
			width = defaultWidth;
		}
		if (heightMode == MeasureSpec.UNSPECIFIED) {
			height = defaultHeight;
		}
		mContentWidth = Math.min(width, height);
		setMeasuredDimension(mContentWidth + getPaddingLeft() + getPaddingRight()
				, mContentWidth + getPaddingTop() + getPaddingBottom());
		isPointsDirty = true;
	}

	private LinearGradient linearGradient;

	private Matrix mMatrix = new Matrix();

	private Matrix mBitmapMatrix = new Matrix();

	private Xfermode xfermode = new PorterDuffXfermode(Mode.SRC_ATOP);

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(getWidth() / 2, getHeight() / 2);
		if (isPointsDirty) {
			initData();
			isPointsDirty = false;
		}
		int width = getWidth() / 2;



		if (progress <= -2) {
			float p = progress + 3;
			mPaint.setColor(Color.RED);
			mCirclePath.reset();
			mCirclePath.addCircle(0, 0, width * p * 1.5f, Path.Direction.CCW);
			canvas.clipPath(mCirclePath);
			canvas.drawPath(mHeartPath, mPaint);
		}else if (progress <= 1){
			float startX = width * progress;
			float startY = startX;
			if (linearGradient == null) {
				linearGradient = new LinearGradient(0,0,width,width, Color.BLACK, Color.RED, TileMode.CLAMP);
			}
			linearGradient.getLocalMatrix(mMatrix);
			mMatrix.setTranslate(startX, startY);
			linearGradient.setLocalMatrix(mMatrix);

			mPaint.setShader(linearGradient);
			canvas.drawPath(mHeartPath, mPaint);
			mPaint.setShader(null);
		}else if (progress <= 2) {
			float p = 1 - (progress - 1);
			mPaint.setColor(Color.BLACK);
			mCirclePath.reset();
			mCirclePath.addCircle(0, 0, width * p, Path.Direction.CCW);
			canvas.clipPath(mCirclePath);
			canvas.drawPath(mHeartPath, mPaint);
		}else {
			float p = progress - 2;
			mCirclePath.reset();
			mCirclePath.addCircle(0, 0, width * p * 1.5f, Path.Direction.CCW);
			canvas.clipPath(mCirclePath);
			canvas.drawBitmap(mBitmap, mBitmapMatrix, mPaint);
		}

	}
	// 645 322.5
	// 585
	private void initData() {

		int width = getMeasuredWidth() / 2;

		mHeartPath.reset();

		mHeartPath.moveTo(-0.078147426f * width ,0.7081185f * width );
		mHeartPath.cubicTo(-0.12086068f * width ,0.6602539f * width ,-0.22751379f * width ,0.56694126f * width ,-0.31515422f * width ,0.5007569f * width );
		mHeartPath.cubicTo(-0.5748258f * width ,0.30465826f * width ,-0.6101672f * width ,0.2762788f * width ,-0.71559924f * width ,0.17919657f * width );
		mHeartPath.cubicTo(-0.9099699f * width ,2.1925342E-4f * width ,-0.99251586f * width ,-0.1795942f * width ,-0.99223304f * width ,-0.42340702f * width );
		mHeartPath.cubicTo(-0.99209505f * width ,-0.5424267f * width ,-0.98398256f * width ,-0.5882813f * width ,-0.950652f * width ,-0.6584373f * width );
		mHeartPath.cubicTo(-0.8941041f * width ,-0.7774627f * width ,-0.8108063f * width ,-0.865908f * width ,-0.70430994f * width ,-0.9200016f * width );
		mHeartPath.cubicTo(-0.6288823f * width ,-0.95831424f * width ,-0.5916816f * width ,-0.97533584f * width ,-0.46572256f * width ,-0.9760322f * width );
		mHeartPath.cubicTo(-0.33396038f * width ,-0.9767604f * width ,-0.30622295f * width ,-0.9613974f * width ,-0.22871567f * width ,-0.91881716f * width );
		mHeartPath.cubicTo(-0.13437614f * width ,-0.86698985f * width ,-0.037274122f * width ,-0.7562259f * width ,-0.017201789f * width ,-0.67754406f * width );
		mHeartPath.lineTo(-0.004804555f * width ,-0.62894744f * width );
		mHeartPath.lineTo(0.025767908f * width ,-0.6958762f * width );
		mHeartPath.cubicTo(0.19853118f * width ,-1.0740868f * width ,0.7501039f * width ,-1.0684283f * width ,0.9420541f * width ,-0.6864759f * width );
		mHeartPath.cubicTo(1.0029461f * width ,-0.5653098f * width ,1.0096313f * width ,-0.30659008f * width ,0.95563585f * width ,-0.16085461f * width );
		mHeartPath.cubicTo(0.8852009f * width ,0.02925202f * width ,0.75290716f * width ,0.17418581f * width ,0.44708905f * width ,0.39628217f * width );
		mHeartPath.cubicTo(0.24652837f * width ,0.5419368f * width ,0.019539483f * width ,0.76231587f * width ,0.0037380958f * width ,0.79326814f * width );
		mHeartPath.cubicTo(-0.014604435f * width ,0.82919866f * width ,0.0028632586f * width ,0.79889894f * width ,-0.078147426f * width ,0.7081185f * width );

		mHeartPath.close();

		mBitmapMatrix.reset();
		int dwidth = mBitmap.getWidth();
		int dheight = mBitmap.getHeight();

		int vwidth = getWidth();
		int vheight = getHeight();

		float scale;
		float dx;
		float dy;

		if (dwidth <= vwidth && dheight <= vheight) {
			scale = 1.0f;
		} else {
			scale = Math.min((float) vwidth / (float) dwidth,
					(float) vheight / (float) dheight);
		}

		dx = (int) ((vwidth - dwidth * scale) * 0.5f + 0.5f);
		dy = (int) ((vheight - dheight * scale) * 0.5f + 0.5f);
		mBitmapMatrix.setScale(scale, scale);
		mBitmapMatrix.postTranslate(dx - getWidth() / 2, dy - getHeight() / 2);

	}

	private ValueAnimator valueAnimator;
	private float progress = -3f;
	private Animator.AnimatorListener mAnimatorListener;
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		valueAnimator = ValueAnimator.ofFloat(-3.0f,3.0f);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				progress = (float) animation.getAnimatedValue();
				invalidate();
			}
		});
		valueAnimator.addListener(mAnimatorListener);
		valueAnimator.setDuration(5000);
		valueAnimator.start();
	}

	@Override
	protected void onDetachedFromWindow() {
		valueAnimator.removeAllListeners();
		valueAnimator.end();
		super.onDetachedFromWindow();
	}

	public void setAnimListener(Animator.AnimatorListener animatorListener){
		this.mAnimatorListener = animatorListener;
	}
}
