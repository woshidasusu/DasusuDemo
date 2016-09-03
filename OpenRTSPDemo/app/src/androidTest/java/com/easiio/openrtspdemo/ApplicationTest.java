package com.easiio.openrtspdemo;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

import java.io.IOException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        String path = "/storage/sdcard0/OpenRTSPDemo/video/normal.mp4";
        try {
            Movie movie = MovieCreator.build(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}