package com.easiio.openrtspdemo.mp4parser;

import android.nfc.Tag;
import android.util.Log;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.util.Path;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class Mp4ParserUtils {

    private static final String TAG = "mp4";

    public static void exportRaw(String file) {
        String path = file.substring(0,file.lastIndexOf("/"));
        try {
            List<Track> audioTracks = new ArrayList<>();
            List<Track> videoTracks = new ArrayList<>();
            Movie movie = MovieCreator.build(file);
            for (Track t : movie.getTracks()) {
                Log.d(TAG,t.getHandler());
                if (t.getHandler().equals("vide")) {
                    videoTracks.add(t);
                }
                if (t.getHandler().equals("soun")){
                    audioTracks.add(t);
                }
            }
            Movie vresult = new Movie();
            Movie aresult = new Movie();
            Log.d(TAG,"audio: " + audioTracks.size() + " video: " + videoTracks.size());
            if (audioTracks.size() > 0) {
                aresult.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
                aresult.addTrack(audioTracks.get(0));
            }
            if (videoTracks.size()>0){
//                vresult.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
                vresult.addTrack(videoTracks.get(0));
            }
            Container aout = new DefaultMp4Builder().build(aresult);
            Container vout = new DefaultMp4Builder().build(vresult);
            try {
                FileChannel fc = new RandomAccessFile(path + "/video1.mp4", "rw").getChannel();
                vout.writeContainer(fc);
                fc.close();
                FileChannel afc = new RandomAccessFile(path + "/audio2.mp4","rw").getChannel();
                aout.writeContainer(afc);
                afc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
