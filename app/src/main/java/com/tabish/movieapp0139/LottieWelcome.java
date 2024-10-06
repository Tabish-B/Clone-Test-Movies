package com.tabish.movieapp0139;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.tabish.movieapp0139.home.profile.RegisterActivity;

public class LottieWelcome extends AppCompatActivity {

    Boolean Flag = true;
    LottieAnimationView lottieAnimationLoad , lottieAnimationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_lottie);


        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        lottieAnimationLoad = findViewById(R.id.lottie_main);
        lottieAnimationText = findViewById(R.id.lottie_text);


       lottieAnimationLoad.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e("Animation:","start");
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("Animation:","end");

                try {
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    finish();
                } catch(Exception ex) {
                    ex.toString();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e("Animation:","cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e("Animation:","repeat");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        lottieAnimationLoad.pauseAnimation();
        lottieAnimationText.pauseAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lottieAnimationLoad.pauseAnimation();
        lottieAnimationText.pauseAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lottieAnimationLoad.resumeAnimation();
        lottieAnimationText.resumeAnimation();
    }



    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}