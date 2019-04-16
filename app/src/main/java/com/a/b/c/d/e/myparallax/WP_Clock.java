package com.a.b.c.d.e.myparallax;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;


/**
 * Created by KCS on 3/14/2018.
 */

public class WP_Clock extends WallpaperService {

    @Override
    public void onDestroy() {
        super.onDestroy();

//        GlobalFunc.saveData(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public Engine onCreateEngine() {
        GlobalData.engine = new clockEngine();
        return GlobalData.engine;
    }

    public class clockEngine extends WallpaperService.Engine implements SensorEventListener {

        private final int frameDuration = 5;
        private final int SKY_COLOR = Color.parseColor("#36B4DD");
        private final int EARTH_COLOR = Color.parseColor("#865B4B");
        private final int MIN_PLANE_COLOR = Color.parseColor("#E8D4BB");
        private final float TOTAL_VISIBLE_PITCH_DEGREES = 45 * 2; // � 45�
        private SurfaceHolder holder;
        private boolean visible;
        private Handler handler;
        private SensorManager mSensorManager;
        private Sensor mRotationSensor;
        private PorterDuffXfermode mXfermode;
        private Paint mBitmapPaint;
        private Paint mEarthPaint;
        private Paint mPitchLadderPaint;
        private Paint mMinPlanePaint;
        private Paint mBottomPitchLadderPaint;
        // These are created once and reused in subsequent onDraw calls.
        private Bitmap mSrcBitmap;
        private Canvas mSrcCanvas;
        private Bitmap mDstBitmap;
        private int mWidth;
        private int mHeight;
        private float mPitch = 0; // Degrees
        private float mRoll = 0; // Degrees, left roll is positive


        private Runnable drawClock = new Runnable() {
            public void run() {
                draw();
            }
        };

        public clockEngine() {
            handler = new Handler();
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            this.holder = surfaceHolder;
            // initialize your android device sensor capabilities
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            if (mRotationSensor == null)
                Toast.makeText(WP_Clock.this, "Sensor Error", Toast.LENGTH_LONG).show();
            setTouchEventsEnabled(true);


            mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
            mBitmapPaint = new Paint();
            mBitmapPaint.setFilterBitmap(false);

            mEarthPaint = new Paint();
            mEarthPaint.setAntiAlias(true);
            mEarthPaint.setColor(EARTH_COLOR);

            mPitchLadderPaint = new Paint();
            mPitchLadderPaint.setAntiAlias(true);
            mPitchLadderPaint.setColor(Color.WHITE);
            mPitchLadderPaint.setStrokeWidth(3);

            mBottomPitchLadderPaint = new Paint();
            mBottomPitchLadderPaint.setAntiAlias(true);
            mBottomPitchLadderPaint.setColor(Color.WHITE);
            mBottomPitchLadderPaint.setStrokeWidth(3);
            mBottomPitchLadderPaint.setAlpha(128);

            mMinPlanePaint = new Paint();
            mMinPlanePaint.setAntiAlias(true);
            mMinPlanePaint.setColor(MIN_PLANE_COLOR);
            mMinPlanePaint.setStrokeWidth(5);
            mMinPlanePaint.setStyle(Paint.Style.STROKE);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            GlobalData.m_nWidth = width;
            GlobalData.m_nHeight = height;
            if (GlobalData.m_nWidth < GlobalData.m_nHeight) {
                GlobalData.port_land = true;
            } else {
                GlobalData.port_land = false;
            }

            mWidth = GlobalData.m_nWidth;
            mHeight = GlobalData.m_nHeight;

            GlobalData.center_x = mWidth / 2;
            GlobalData.center_y = mHeight / 2;
            init();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                //canvas = holder.lockCanvas();
                //canvas.save();
                mSensorManager.registerListener(this, mRotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
                handler.post(drawClock);
            } else {
                //canvas.restore();
                //holder.unlockCanvasAndPost(canvas);
                handler.removeCallbacks(drawClock);
                mSensorManager.unregisterListener(this);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawClock);
        }

        private void draw() {
            if (visible) {
//                if (GlobalData.initFlag) {
//                    init();
//                    GlobalData.initFlag = false;
//                }
//
//                try {
//                    Canvas canvas = holder.lockCanvas();
//                    canvas.save();
//
//                    drawBackground(canvas);
//                    drawCompass(canvas);
//
//
//                    canvas.restore();
//                    holder.unlockCanvasAndPost(canvas);
//                    handler.removeCallbacks(drawClock);
//                    handler.postDelayed(drawClock, frameDuration);
//                } catch (Exception ex) {
//
//                }


                try {

                    Canvas canvas = holder.lockCanvas();
                    canvas.save();
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                    drawBackground(canvas);

                    canvas.save();

                    canvas.translate((GlobalData.mRoll * 2), 0);

                    canvas.drawBitmap(GlobalData.background_3, new Rect(0, 0, GlobalData.background_3.getWidth(), GlobalData.background_3.getHeight()), new Rect(-150, (int) (GlobalData.m_nHeight / 2.5), GlobalData.m_nWidth + 150, GlobalData.m_nHeight), new Paint());

                    canvas.translate((float) (GlobalData.mRoll / 2), GlobalData.mPitch);

                    canvas.drawBitmap(GlobalData.background_4, new Rect(0, 0, GlobalData.background_4.getWidth(), GlobalData.background_4.getHeight()), new Rect((int) ((GlobalData.m_nWidth / 10) * 2.5), (int) (GlobalData.m_nHeight / 1.5), (int) ((GlobalData.m_nWidth / 10) * 7.5), (GlobalData.m_nHeight / 10) * 9), new Paint());

                    canvas.translate(GlobalData.mRoll, 0);

                    canvas.drawBitmap(GlobalData.background_2, new Rect(0, 0, GlobalData.background_2.getWidth(), GlobalData.background_2.getHeight()), new Rect(250, 250, (int) (GlobalData.m_nWidth / 2.5), (int) (GlobalData.m_nWidth / 2.5)), new Paint());
                    canvas.restore();
                    holder.unlockCanvasAndPost(canvas);
                    handler.removeCallbacks(drawClock);
                    handler.postDelayed(drawClock, frameDuration);

                } catch (Exception e) {

                }


            }
        }

        private void init() {
            GlobalData.background_1 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_1);
            GlobalData.background_2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_2);
            GlobalData.background_3 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_3);
            GlobalData.background_4 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_4);

        }

        private void drawBackground(Canvas canvas) {

            canvas.drawBitmap(GlobalData.background_1, new Rect(0, 0, GlobalData.background_1.getWidth(), GlobalData.background_1.getHeight()), new Rect(0, 0, GlobalData.m_nWidth, GlobalData.m_nHeight), new Paint());

        }

        protected void onPause() {
            mSensorManager.unregisterListener(this);
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {

            float[] rotationVector = event.values;
            float[] rotationMatrix = new float[9];
            SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector);

            final int worldAxisForDeviceAxisX;
            final int worldAxisForDeviceAxisY;

            worldAxisForDeviceAxisX = SensorManager.AXIS_X;
            worldAxisForDeviceAxisY = SensorManager.AXIS_Y;


            float[] adjustedRotationMatrix = new float[9];
            SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisForDeviceAxisX,
                    worldAxisForDeviceAxisY, adjustedRotationMatrix);

            // Transform rotation matrix into azimuth/pitch/roll
            float[] orientation = new float[3];
            SensorManager.getOrientation(adjustedRotationMatrix, orientation);

            // Convert radians to degrees
            GlobalData.mPitch = orientation[1] * -57;
            GlobalData.mRoll = orientation[2] * -57;
            if (GlobalData.mRoll > 80) {
                GlobalData.mRoll = 80;
            } else if (GlobalData.mRoll < -80) {
                GlobalData.mRoll = -80;
            }


            Log.i("Salman", "Pitch: " + GlobalData.mPitch + " :: " + "Roll: " + GlobalData.mRoll);

        }


        private Bitmap getSrc(Canvas canvas) {
            if (mSrcBitmap == null) {
                mSrcBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            }

            float centerX = mWidth / 2;
            float centerY = mHeight / 2;

            // Background
            canvas.drawColor(SKY_COLOR);

            // Save the state without any rotation/translation so
            // we can revert back to it to draw the fixed components.
            canvas.save();

            // Orient the earth to reflect the pitch and roll angles
            canvas.rotate(mRoll, centerX, centerY);
            canvas.translate(0, (mPitch / TOTAL_VISIBLE_PITCH_DEGREES) * mHeight);

            // Draw the earth as a rectangle, well beyond the view bounds
            // to account for large nose-down pitch.
            canvas.drawRect(-mWidth, centerY, mWidth * 2, mHeight * 2, mEarthPaint);

            // Draw white horizon and top pitch ladder
            float ladderStepY = mHeight / 12;
            canvas.drawLine(-mWidth, centerY, mWidth * 2, centerY, mPitchLadderPaint);
            for (int i = 1; i <= 4; i++) {
                float y = centerY - ladderStepY * i;
                float width = mWidth / 8;
                canvas.drawLine(centerX - width / 2, y, centerX + width / 2, y, mPitchLadderPaint);
            }

            // Draw the bottom pitch ladder
            float bottomLadderStepX = mWidth / 12;
            float bottomLadderStepY = mWidth / 12;
            canvas.drawLine(centerX, centerY, centerX - bottomLadderStepX * 3.5f, centerY
                    + bottomLadderStepY * 3.5f, mBottomPitchLadderPaint);
            canvas.drawLine(centerX, centerY, centerX + bottomLadderStepX * 3.5f, centerY
                    + bottomLadderStepY * 3.5f, mBottomPitchLadderPaint);
            for (int i = 1; i <= 3; i++) {
                float y = centerY + bottomLadderStepY * i;
                canvas.drawLine(centerX - bottomLadderStepX * i, y, centerX + bottomLadderStepX * i, y,
                        mBottomPitchLadderPaint);
            }

            // Return to normal to draw the miniature plane
            canvas.restore();

            // Draw the nose dot
            canvas.drawPoint(centerX, centerY, mMinPlanePaint);

            // Half-circle of miniature plane
            float minPlaneCircleRadiusX = mWidth / 6;
            float minPlaneCircleRadiusY = mHeight / 6;
            RectF wingsCircleBounds = new RectF(centerX - minPlaneCircleRadiusX, centerY
                    - minPlaneCircleRadiusY, centerX + minPlaneCircleRadiusX, centerY + minPlaneCircleRadiusY);
            canvas.drawArc(wingsCircleBounds, 0, 180, false, mMinPlanePaint);

            // Wings of miniature plane
            float wingLength = mWidth / 6;
            canvas.drawLine(centerX - minPlaneCircleRadiusX - wingLength, centerY, centerX
                    - minPlaneCircleRadiusX, centerY, mMinPlanePaint);
            canvas.drawLine(centerX + minPlaneCircleRadiusX, centerY, centerX + minPlaneCircleRadiusX
                    + wingLength, centerY, mMinPlanePaint);

            // Draw vertical post
            canvas.drawLine(centerX, centerY + minPlaneCircleRadiusY, centerX, centerY
                    + minPlaneCircleRadiusY + mHeight / 3, mMinPlanePaint);

            return mSrcBitmap;
        }

        private Bitmap getDst(Canvas canvas) {
            if (mDstBitmap == null) {
                mDstBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);

                canvas.drawColor(Color.TRANSPARENT);

                Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
                p.setColor(Color.RED);
                canvas.drawOval(new RectF(0, 0, mWidth, mHeight), p);
            }
            return mDstBitmap;
        }


        private void drawCompass(Canvas canvas) {
            Matrix matrix = new Matrix();
            Bitmap bmp_compassBg = Bitmap.createBitmap(GlobalData.compass_bg, 0, 0, GlobalData.compass_bg.getWidth(), GlobalData.compass_bg.getHeight(), matrix, true);
            canvas.drawBitmap(bmp_compassBg, new Rect(0, 0, bmp_compassBg.getWidth(), bmp_compassBg.getHeight()),
                    new Rect(GlobalData.compass_realPos.x - bmp_compassBg.getWidth() / 2, GlobalData.compass_realPos.y - bmp_compassBg.getHeight() / 2, GlobalData.compass_realPos.x + bmp_compassBg.getWidth() / 2, GlobalData.compass_realPos.y + bmp_compassBg.getHeight() / 2),
                    new Paint());

            canvas.drawBitmap(GlobalData.compass_pin, new Rect(0, 0, GlobalData.compass_pin.getWidth(), GlobalData.compass_pin.getHeight()),
                    new Rect(GlobalData.compass_realPos.x - GlobalData.compass_pin.getWidth() / 2, GlobalData.compass_realPos.y - GlobalData.compass_pin.getHeight() / 2, GlobalData.compass_realPos.x + GlobalData.compass_pin.getWidth() / 2, GlobalData.compass_realPos.y + GlobalData.compass_pin.getHeight() / 2),
                    new Paint());
            //bmp_g2.recycle();
        }

    }
}
