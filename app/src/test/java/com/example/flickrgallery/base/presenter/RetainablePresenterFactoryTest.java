package com.example.flickrgallery.base.presenter;

import com.example.flickrgallery.util.ThreadUtils;

import junit.framework.Assert;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class RetainablePresenterFactoryTest {
    @Test
    public void idIsAlwaysUnique() {
        int idsPerThread = 100;
        int threads = 10;
        int totalCount = idsPerThread*threads;
        long[] ids = new long[totalCount];
        AtomicInteger syncObj = new AtomicInteger(0);

        for (int i = 0; i < threads; i++) {
            int first = i*idsPerThread;
            int last = (i+1)*idsPerThread;
            Runnable testRunnable = new TestRunnable(first, last, ids, syncObj);
            Thread thread = new Thread(testRunnable);
            thread.start();
        }

        while (syncObj.get() < threads) {
            ThreadUtils.sleep(1);
        }

        for (int i = 0; i < totalCount; i++) {
            for (int j = i+1; j < totalCount; j++) {
                Assert.assertFalse(ids[i] == ids[j]);
            }
        }
    }

    private class TestRunnable implements Runnable {
        final int firstIndex;
        final int lastIndex;
        final long[] idsToWrite;
        final AtomicInteger syncObj;
        TestRunnable(int firstIndex, int lastIndex, long[] idsToWrite, AtomicInteger syncObj) {
            this.firstIndex = firstIndex;
            this.lastIndex = lastIndex;
            this.idsToWrite = idsToWrite;
            this.syncObj = syncObj;
        }

        @Override
        public void run() {
            for (int i = firstIndex; i < lastIndex; i++) {
                idsToWrite[i] = RetainablePresenterFactory.getNextUniqueId();
            }

            syncObj.incrementAndGet();

        }
    }
}
