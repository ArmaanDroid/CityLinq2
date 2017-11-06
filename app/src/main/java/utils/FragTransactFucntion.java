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
                R.animator.fade_out).addToBackStack(null).replace(containerId, fragment).commit();
    }

    public static void addFragFromFadeHistory(FragmentManager fragmentManager, Fragment fragment,
                                              int containerId) {
        fragmentManager.beginTransaction().setCustomAnimations(R.animator.fade_in, 0, 0,
                R.animator.fade_out).addToBackStack(null).add(containerId, fragment).commit();
    }

    public static void replaceFragFromRightWithoutHistry(FragmentManager fragmentManager, Fragment fragment,
                                                         int containerId) {
        fragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_right_in, 0, 0,
                R.animator.slide_right_out).replace(containerId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    public static void replaceFragFromRightWithTag(FragmentManager fragmentManager, Fragment fragment,
                                                   int containerId) {
        fragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_right_in, 0, 0,
                R.animator.slide_right_out).addToBackStack(null).replace(containerId, fragment).commit();
    }

}

