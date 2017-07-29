package com.example.flickrgallery.util;

import junit.framework.Assert;

import org.junit.Test;

public class StringUtilsTest {
    @Test
    public void joinWithDelimiterTest() {
        String[] strings = new String[] {"aa", "bb", "cc"};
        String delimiter = ",";
        String required = "aa,bb,cc";

        String result = StringUtils.joinWithDelimiter(strings, delimiter);

        Assert.assertEquals(required, result);
    }

    @Test
    public void splitBySpaceTest() {
        String input = "  aa \t bb   \n cc";
        String[] result = StringUtils.splitBySpace(input);

        Assert.assertEquals(result.length , 3);
    }
}
