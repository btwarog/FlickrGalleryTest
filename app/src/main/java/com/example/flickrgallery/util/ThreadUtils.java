package com.example.flickrgallery.util;

import com.example.flickrgallery.base.Cancelable;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadUtils {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Cancelable postDelayed(final Runnable runnable, final long millis) {

        final AtomicCancelable atomicCancelable = new AtomicCancelable();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadUtils.sleep(millis);
                if(!atomicCancelable.isCanceled()) {
                    runnable.run();
                }
            }
        }).start();

        return atomicCancelable;
    }

    static private class AtomicCancelable implements Cancelable {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        @Override
        public void cancel() {
            atomicBoolean.set(true);
        }

        @Override
        public boolean isCanceled() {
            return atomicBoolean.get();
        }
    }
}
