package com.easiio.openrtspdemo.rtsp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.easiio.openrtspdemo.R;
import com.easiio.openrtspdemo.RtspConstans;
import com.easiio.openrtspdemo.widget.FlowRadioGroup;

public class RtspRecordFragment extends Fragment implements View.OnClickListener{

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = bindView(inflater,container);
        initView();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        RtspRecordManager.getInstance().varifyFile(getActivity(), RtspRecordManager.OPENRTSP_BINARY_FILE);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Button btnRecord;
    private ViewGroup btnRtspSource;
    private TextView tvRtspSourceName;
    private FlowRadioGroup resolutionGroup;
    private RadioGroup fpsGroup;
    private RadioGroup videoFormatGroup;
    private TextView tvRecordDuration;
    private SeekBar seekBarRecordDuration;

    private OpenRtspBean openRtspBean = new OpenRtspBean();

    private View bindView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_rtsp_record,container,false);
        btnRecord = (Button) view.findViewById(R.id.btn_record);
        btnRtspSource = (ViewGroup) view.findViewById(R.id.rtsp_source_layout);
        tvRtspSourceName = (TextView) view.findViewById(R.id.tv_rtsp_source);
        resolutionGroup = (FlowRadioGroup) view.findViewById(R.id.resolution_group);
        fpsGroup = (RadioGroup) view.findViewById(R.id.fps_group);
        videoFormatGroup = (RadioGroup) view.findViewById(R.id.video_format_group);
        tvRecordDuration = (TextView) view.findViewById(R.id.tv_record_duration);
        seekBarRecordDuration = (SeekBar) view.findViewById(R.id.seekbar_record_duration);
        return view;
    }

    private void initView() {
        btnRecord.setOnClickListener(this);
        btnRtspSource.setOnClickListener(this);
        resolutionGroup.setOnCheckedChangeListener(new FlowRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(FlowRadioGroup group, int checkedId) {
                checkResolution(checkedId);
            }
        });
        fpsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkFps(checkedId);
            }
        });
        videoFormatGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkVideoFormat(checkedId);
            }
        });
        seekBarRecordDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvRecordDuration.setText(progress+"s");
                openRtspBean.setD(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnRtspSource.requestFocus();
        resolutionGroup.check(R.id.resolution_item_vga);
        fpsGroup.check(R.id.fps_item_15);
        videoFormatGroup.check(R.id.video_format_item_mp4);
        seekBarRecordDuration.setProgress(60);

        btnRecord.setText(getString(R.string.start_record));
        tvRtspSourceName.setText(rtspUrls[0]);
        openRtspBean.setRtspUrl(rtspUrls[0]);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_record:
                startOrStopRecord();
                break;
            case R.id.rtsp_source_layout:
                selectRtspSource();
                break;
            default:
                break;
        }
    }

    /**
     * RTSP测试地址
     * rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp
     * rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov
     * rtsp://218.204.223.237:554/live/1/67A7572844E51A64/f68g2mj7wjua3la7.sdp
     */

    private void startOrStopRecord() {
        if (RtspRecordManager.getInstance().getRecordState() == RtspRecordManager.RECORD_STATE_STOP){
            btnRecord.setText(getString(R.string.stop_record));
            RtspRecordManager.getInstance().startRecord(openRtspBean);
        }else {
            btnRecord.setText(getString(R.string.start_record));
            RtspRecordManager.getInstance().stopRecord();
        }
    }

    private String[] rtspUrls = {
            "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp",
            "rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov",
            "rtsp://218.204.223.237:554/live/1/67A7572844E51A64/f68g2mj7wjua3la7.sdp"
    };

    private void selectRtspSource() {
        ListAdapter adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,rtspUrls);

        new AlertDialog.Builder(getActivity()).setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which < 0 || which >= rtspUrls.length){
                    return;
                }
                tvRtspSourceName.setText(rtspUrls[which]);
                openRtspBean.setRtspUrl(rtspUrls[which]);
            }
        }).show();
    }

    private void checkResolution(int resId){
        switch (resId){
            case R.id.resolution_item_qvga:
                openRtspBean.setW(RtspConstans.VIDEO_RESOLUTION_QVGA[0]);
                openRtspBean.setH(RtspConstans.VIDEO_RESOLUTION_QVGA[1]);
                break;
            case R.id.resolution_item_vga:
                openRtspBean.setW(RtspConstans.VIDEO_RESOLUTION_VGA[0]);
                openRtspBean.setH(RtspConstans.VIDEO_RESOLUTION_VGA[1]);
                break;
            case R.id.resolution_item_wxga:
                openRtspBean.setW(RtspConstans.VIDEO_RESOLUTION_WXGA[0]);
                openRtspBean.setH(RtspConstans.VIDEO_RESOLUTION_WXGA[1]);
                break;
            case R.id.resolution_item_wuxga:
                openRtspBean.setW(RtspConstans.VIDEO_RESOLUTION_WUXGA[0]);
                openRtspBean.setH(RtspConstans.VIDEO_RESOLUTION_WUXGA[1]);
                break;
            default:
                openRtspBean.setW(RtspConstans.VIDEO_RESOLUTION_VGA[0]);
                openRtspBean.setH(RtspConstans.VIDEO_RESOLUTION_VGA[1]);
                break;
        }
    }

    private void checkFps(int resId){
        switch (resId){
            case R.id.fps_item_15:
                openRtspBean.setFps(15);
                break;
            case R.id.fps_item_30:
                openRtspBean.setFps(30);
                break;
            case R.id.fps_item_60:
                openRtspBean.setFps(60);
                break;
            default:
                openRtspBean.setFps(15);
                break;
        }
    }

    private void checkVideoFormat(int resId){
        switch (resId){
            case R.id.video_format_item_avi:
                openRtspBean.setFileType(".avi");
                openRtspBean.setFormat('i');
                break;
            case R.id.video_format_item_mov:
                openRtspBean.setFileType(".mov");
                openRtspBean.setFormat('H');
                break;
            case R.id.video_format_item_mp4:
                openRtspBean.setFileType(".mp4");
                openRtspBean.setFormat('4');
                break;
            default:
                openRtspBean.setFileType(".mp4");
                openRtspBean.setFormat('4');
                break;
        }
    }
}
