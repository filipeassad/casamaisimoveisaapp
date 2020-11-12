package com.stager.casamaisimoveis.utilitarios;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

public class Animacao {
    public void animaEntrada(LinearLayout ll_padrao){
        TranslateAnimation animation = new TranslateAnimation(-1000,0,0,0);
        animation.setDuration(200);
        ll_padrao.startAnimation(animation);
    }

    public void animaSaida(final LinearLayout ll_padrao){

        if(ll_padrao.getVisibility() == View.VISIBLE){
            TranslateAnimation animation = new TranslateAnimation(0,-1000,0,0);
            animation.setDuration(200);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ll_padrao.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            ll_padrao.startAnimation(animation);
        }

    }

    public void fadeInAnimatio(LinearLayout ll_padrao){

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

        ll_padrao.setAnimation(fadeIn);

    }

    public void fadeOutAnimation(final LinearLayout ll_padrao){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(200);
        fadeOut.setDuration(200);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_padrao.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ll_padrao.setAnimation(fadeOut);

    }
}
