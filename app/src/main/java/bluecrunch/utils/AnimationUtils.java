package bluecrunch.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import io.reactivex.functions.Action;

/**
 * Created by amrahmed on 3/13/19.
 */

public class AnimationUtils {

    static int parentH = 0;
    static int parentW = 0;

    public static void slideUpView(View target, long duration) {

        if (target == null) {
            return;
        }
        if (target.getTag() instanceof Animator) {
            ((Animator) target.getTag()).cancel();
        }

        ViewTreeObserver observer= target.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parentH = ((View)target.getParent()).getHeight();
                target.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                AnimationSet as = new AnimationSet(true);
                TranslateAnimation animation = new TranslateAnimation(
                        Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f,
                        Animation.ABSOLUTE,  parentH,
                        Animation.ABSOLUTE, 0.0f);
                animation.setDuration(duration);
                as.addAnimation(animation);
                target.startAnimation(as);
            }
        });
    }


    public static void slideLeft(View target, long duration) {

        if (target == null) {
            return;
        }
        if (target.getTag() instanceof Animator) {
            ((Animator) target.getTag()).cancel();
        }

        ViewTreeObserver observer= target.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parentW = ((View)target.getParent()).getWidth();
                target.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                AnimationSet as = new AnimationSet(true);
                TranslateAnimation animation = new TranslateAnimation(
                        Animation.ABSOLUTE, parentW, Animation.ABSOLUTE, 0.0f,
                        Animation.ABSOLUTE,  0.0f,
                        Animation.ABSOLUTE, 0.0f);
                animation.setDuration(duration);
                as.addAnimation(animation);
                target.startAnimation(as);
            }
        });
    }

    public static void slideRight(View target, long duration) {

        if (target == null) {
            return;
        }
        if (target.getTag() instanceof Animator) {
            ((Animator) target.getTag()).cancel();
        }

        ViewTreeObserver observer= target.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parentW = ((View)target.getParent()).getWidth();
                target.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                AnimationSet as = new AnimationSet(true);
                TranslateAnimation animation = new TranslateAnimation(
                        Animation.ABSOLUTE, -parentW, Animation.ABSOLUTE, 0.0f,
                        Animation.ABSOLUTE,  0.0f,
                        Animation.ABSOLUTE, 0.0f);
                animation.setDuration(duration);
                as.addAnimation(animation);
                target.startAnimation(as);
            }
        });
    }

    public static void slideDown(View target, long duration) {

        if (target == null) {
            return;
        }
        if (target.getTag() instanceof Animator) {
            ((Animator) target.getTag()).cancel();
        }

        ViewTreeObserver observer= target.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parentH = target.getHeight();
                target.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                AnimationSet as = new AnimationSet(true);
                TranslateAnimation animation = new TranslateAnimation(
                        Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f,
                        Animation.ABSOLUTE,  -parentH,
                        Animation.ABSOLUTE, 0.0f);
                animation.setDuration(duration);
                as.addAnimation(animation);
                target.startAnimation(as);
            }
        });
    }

    public static void circularTransition(View view){

        int x = ((View)view.getParent()).getRight();
        int y = ((View)view.getParent()).getBottom();
        int startRadius = 0;
        int endRadius = (int) Math.hypot(((View)view.getParent()).getWidth(), ((View)view.getParent()).getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view, 0, 0, startRadius, endRadius);
        anim.setDuration(1700);
        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    public static void circularReverseTransition(View view, Action callback){

        int x = ((View)view.getParent()).getRight();
        int y = ((View)view.getParent()).getBottom();
        int startRadius = (int) Math.max(((View)view.getParent()).getWidth(), ((View)view.getParent()).getHeight());
        int endRadius = 0;
        Animator anim = ViewAnimationUtils.createCircularReveal(view, x , y , startRadius, endRadius);
        anim.setDuration(1800);
//        view.setVisibility(View.VISIBLE);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                try {
                    callback.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        anim.start();
    }




}
