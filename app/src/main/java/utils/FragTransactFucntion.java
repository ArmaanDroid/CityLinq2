package utils;

import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 07-10-2017.
 */

public class FragTransactFucntion {
    public static void addFragmentFromRight(FragmentManager fragmentManager, Fragment fragment,
                                            int containerId) {
        fragmentManager.beginTransaction().
                setCustomAnimations(R.animator.slide_right_in, 0,
                        0, R.animator.slide_right_out)
                .add(containerId, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName()).commit();
    }

    public static void addFragmentFromRightWithTag(FragmentManager fragmentManager, Fragment fragment,
                                                   int containerId, String tag) {
        fragmentManager.beginTransaction().
                setCustomAnimations(R.animator.slide_right_in, 0,
                        0, R.animator.slide_right_out)
                .add(containerId, fragment, tag)
                .addToBackStack(null).commit();
    }

    public static void replaceFragFromFadeWithoutHistory(FragmentManager fragmentManager, Fragment fragment,
                                                         int containerId) {
        fragmentManager.beginTransaction().setCustomAnimations(R.animator.fade_in, 0, 0,
                R.animator.fade_out).replace(containerId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    public static void replaceFragFromFadeHistory(FragmentManager fragmentManager, Fragment fragment,
                                                  int containerId) {
        fragmentManager.beginTransaction().setCustomAnimations(R.animator.fade_in, 0, 0,
                R.animator.fade_out).addToBackStack(null).replace(containerId, fragment,fragment.getClass().getName()).commit();
    }

    public static void replaceFragFromRightHistory(FragmentManager fragmentManager, Fragment fragment,
                                                  int containerId) {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_to_left, R.anim.fade_out, R.anim.left_to_right,
                R.anim.back_left_to_right).addToBackStack(null).replace(containerId, fragment,fragment.getClass().getName()).commit();
    }

    public static void addFragFromFadeHistory(FragmentManager fragmentManager, Fragment fragment,
                                              int containerId) {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in,
                R.anim.fade_out).addToBackStack(null).add(containerId, fragment).commit();
    }

    public static void addFragFromBottomFadeHistory(FragmentManager fragmentManager, Fragment fragment,
                                                    int containerId) {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.bt_bottom_sheet_dialog_open, R.anim.fade_out, R.anim.fade_in,
                R.anim.bt_bottom_sheet_dialog_close).addToBackStack(null).add(containerId, fragment).commit();
    }

    public static void addFragFromRightFadeHistory(FragmentManager fragmentManager, Fragment fragment,
                                                   int containerId) {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_to_left, R.anim.fade_out, R.anim.left_to_right,
                R.anim.back_left_to_right).addToBackStack(null).add(containerId, fragment).commit();
    }

    public static void addFragFromFadeWithoutHistory(FragmentManager fragmentManager, Fragment fragment,
                                                     int containerId) {
        fragmentManager.beginTransaction().setCustomAnimations(R.animator.fade_in, 0, 0,
                R.animator.fade_out).add(containerId, fragment).commit();
    }

}

