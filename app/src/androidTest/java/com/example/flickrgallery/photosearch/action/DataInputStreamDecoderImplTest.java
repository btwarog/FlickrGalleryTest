package com.example.flickrgallery.photosearch.action;

import android.support.test.runner.AndroidJUnit4;

import com.example.flickrgallery.screen.photosearch.action.DataInputStreamDecoderImpl;
import com.example.flickrgallery.screen.photosearch.model.Photo;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DataInputStreamDecoderImplTest {

    @Test
    public void jsonDecodeTest() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("json_response");
        DataInputStreamDecoderImpl dataInputStreamDecoder = new DataInputStreamDecoderImpl();
        boolean isExceptionThrown = false;
        List<Photo> result = null;
        try {
            result = dataInputStreamDecoder.decode(inputStream);
        } catch (Exception e) {
            isExceptionThrown = true;
            e.printStackTrace();
        }

        Assert.assertFalse(isExceptionThrown);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 20);
    }

    @Test
    public void jsonDecodeFailsTest() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("json_response_failing");
        DataInputStreamDecoderImpl dataInputStreamDecoder = new DataInputStreamDecoderImpl();
        boolean isExceptionThrown = false;

        try {
            dataInputStreamDecoder.decode(inputStream);
        } catch (Exception e) {
            isExceptionThrown = true;
            e.printStackTrace();
        }

        Assert.assertTrue(isExceptionThrown);
    }
}
