package com.sxq.clockdemo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    private List<String> mStrings;

    @Before
    public void setUp(){
        mStrings = new ArrayList<>();
        mStrings.add("adsf");
        mStrings.add("adsf1");
        mStrings.add("adsf2");
        mStrings.add("adsf3");
        mStrings.add("adsf4");
        mStrings.add("adsf5");
        mStrings.add("adsf6");
    }

    @Test
    public void testRemove1() throws Exception {
        for (String s:mStrings){
            mStrings.remove(s);
        }
        assertEquals(mStrings.size(),0);
    }

    @Test
    public void testRemove2() throws Exception{
        for (String s:mStrings){
            mStrings.remove(s);
            break;
        }
        assertEquals(mStrings.size(),6);
    }
}