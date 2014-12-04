package com.barriosnahuel.expandonscroll.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by nbarrios on 27/11/14.
 */
public class ResetAnimation extends Animation {

    int targetHeight;
    int originalHeight;
    int extraHeight;
    View mView;

    public ResetAnimation(View view, int targetHeight) {
        this.mView = view;
        this.targetHeight = targetHeight;
        originalHeight = view.getHeight();
        extraHeight = this.targetHeight - originalHeight;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        mView.getLayoutParams().height = (int) (targetHeight - extraHeight * (1 - interpolatedTime));
        mView.requestLayout();
    }
}
