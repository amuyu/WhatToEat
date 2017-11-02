package com.amuyu.whattoeat.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

public class ActivityUtils {



    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static <T extends Fragment> T replaceFragment(Context context, FragmentManager fragmentManager,
                                                         FragmentOperation<T> operation, int frameId) {
        Fragment fragment = Fragment.instantiate(context, operation.fragmentName, operation.arguments);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (operation.animationSet != null) {
            if (operation.animationSet.length == 2) {
                transaction.setCustomAnimations(
                        operation.animationSet[0], operation.animationSet[1]);
            } else if (operation.animationSet.length == 4) {
                transaction.setCustomAnimations(
                        operation.animationSet[0], operation.animationSet[1],
                        operation.animationSet[2], operation.animationSet[3]);
            }

        }
        transaction.replace(frameId, fragment);
        if (operation.addToBackStack) transaction.addToBackStack(operation.fragmentName);
        transaction.commit();
        return (T) fragment;
    }

    public static class FragmentOperation<T extends Fragment> {
        String fragmentName;
        Bundle arguments;
        boolean addToBackStack;
        int[] animationSet;

        private FragmentOperation() {
        }

        public static <T extends Fragment> FragmentOperation<T> create(Class<T> fragment) {
            FragmentOperation fragmentOperation = new FragmentOperation();
            fragmentOperation.fragmentName = fragment.getName();
            return fragmentOperation;
        }

        public FragmentOperation<T> arguments(Bundle arguments) {
            this.arguments = arguments;
            return this;
        }

        public FragmentOperation<T> addToBackStack(boolean addToBackStack) {
            this.addToBackStack = addToBackStack;
            return this;
        }

        public FragmentOperation<T> animationSet(int... animationSet) {
            this.animationSet = animationSet;
            return this;
        }
    }
}
