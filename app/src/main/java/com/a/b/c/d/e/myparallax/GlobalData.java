package com.a.b.c.d.e.myparallax;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by KCS on 3/14/2018.
 */

public class GlobalData {
    public static boolean initFlag = false;
    public static WP_Clock.clockEngine engine = null;
    public static boolean port_land = true; //true:potrait, false: landscape
    public static int center_x;
    public static int center_y;
    public static int left = 0;
    public static int top = 0;
    public static float w_Rate;
    public static float h_Rate;
    public static int m_nWidth;
    public static int m_nHeight;
    public static Point cc1_center;
    public static Point cc2_center;
    public static float scaleRate = 0;

    public static float chicha_Degree = 0;
    public static int g9_Degree = 0;
    public static boolean g9_direct = true;
    public static int pin1_Degree = 0;
    public static boolean pin1_direct = true;


    public static TimerTask mTask;
    public static Timer mTimer;

    public static Bitmap background_1;
    public static Bitmap background_2;
    public static Bitmap background_3;
    public static Bitmap background_4;
    public static Bitmap clockBG;
    public static Bitmap bgClock;
    public static Bitmap chicha_g1;
    public static Bitmap chicha_g2;
    public static Bitmap chicha_g9;
    public static Bitmap chicha_g4;
    public static Bitmap chicha_g5;
    public static Bitmap chicha_g6;
    public static Bitmap chicha_g3;
    public static Bitmap chicha_g8;
    public static Bitmap pin1;
    public static Bitmap metal_bar;
    public static Bitmap metal_bar_small;
    public static Bitmap nutt;
    public static Bitmap clock_n1;
    public static Bitmap clock_n2;
    public static Bitmap clock_n3;
    public static Bitmap nut_bg;
    public static Bitmap compass_bg;
    public static Bitmap compass_pin;


    public static Point g2_realPos;
    public static Point g1_realPos;
    public static Point g9_realPos;
    public static Point g4_realPos;
    public static Point g5_realPos;
    public static Point g6_realPos;
    public static Point g3_realPos;
    public static Point g8_realPos;
    public static Point pin1_realPos;
    public static Point metal_bar_realPos;
    public static Point metal_bar_small_realPos;
    public static Point nutt1_realPos;
    public static Point nutt2_realPos;
    public static Point nutt3_realPos;
    public static Point nutt4_realPos;
    public static Point nutt5_realPos;
    public static Point nutt6_realPos;
    public static float n1_realRadio = 0;
    public static float n2_realRadio = 0;
    public static float n3_realRadio = 0;
    public static Point compass_realPos;
    public static float mAzimut = 0;
    public static float mChange = 0;
    public static float mPitch = 0;
    public static float mRoll = 0;

    public static Point PM_AM_realPos;
    public static Point hour_realPos;
    public static Point comma1_realPos;
    public static Point minute_realPos;
    public static Point comma2_realPos;
    public static Point second_realPos;
    public static Point week_realPos;
    public static Point date_realPos;

    public static int realtextSize_1;
    public static int realtextSize_2;
    public static int realtextSize_3;

    public static int background_id = 1;
    public static int face_id = 1;
    public static int needle_id = 1;
    public static int format = 1; //bg_1: 12H, bg_2: 24H
    public static int type = 1; //bg_1: D, bg_2: A
    public static int transparency = 255;
    public static float size = 1f;
    public static float speed = 5.0f;
    public static boolean enable_compass = true; //true: enable compass. false: disable compass
    public static boolean enable_sound = true; //true: enable compass. false: disable compass
    public static int power_id = 1;
}
