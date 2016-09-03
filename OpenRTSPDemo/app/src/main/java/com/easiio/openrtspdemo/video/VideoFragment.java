package com.easiio.openrtspdemo.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easiio.openrtspdemo.MainActivity;
import com.easiio.openrtspdemo.R;
import com.easiio.openrtspdemo.RtspAPP;
import com.easiio.openrtspdemo.RtspConstans;
import com.easiio.openrtspdemo.inter.OnBackPressed;
import com.easiio.openrtspdemo.mp4parser.Mp4ParserUtils;
import com.easiio.openrtspdemo.utils.FileUtils;
import com.easiio.openrtspdemo.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VideoFragment extends Fragment implements OnBackPressed {

    private final String TAG = this.getClass().getSimpleName();
    private VideoFolderObserver folderObserver;
    private onFileCreateListener fileListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fileListener = (onFileCreateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        folderObserver = new VideoFolderObserver(
                FileUtils.getAppStroagePath() + RtspConstans.EXTERNAL_STROAGE_VIDEO_SUBDIR
                , FileObserver.CREATE | FileObserver.MOVED_TO);
        folderObserver.startWatching();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = bindView(inflater, container);
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
        loadFileData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        folderObserver.stopWatching();
    }

    private RecyclerView videoContentView;
    private Button btnDownload;
    private ViewGroup editTopLayout;
    private Button btnCancleEdit;
    private Button btnCheckAll;
    private TextView tvEditMessage;

    private View bindView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        videoContentView = (RecyclerView) view.findViewById(R.id.recycle_content);
        btnDownload = (Button) view.findViewById(R.id.download);
        editTopLayout = (ViewGroup) view.findViewById(R.id.layout_top_edit);
        btnCancleEdit = (Button) view.findViewById(R.id.btn_cancle_edit);
        btnCheckAll = (Button) view.findViewById(R.id.btn_all_check);
        tvEditMessage = (TextView) view.findViewById(R.id.tv_check_message);
        return view;
    }

    private String videoDir = FileUtils.getAppStroagePath()
            + RtspConstans.EXTERNAL_STROAGE_VIDEO_SUBDIR + File.separator;
    private List<VideoFileBean> videoFileList = new ArrayList<>();
    private List<VideoFileBean> editFileList = new ArrayList<>();
    private CommonAdapter<VideoFileBean> commonAdapter;

    private static final int FILE_MANAGEMENT_STATE_NORMAL = 1;
    private static final int FILE_MANAGEMENT_STATE_EDIT = 2;
    private static final int FILE_MANAGEMENT_STATE_DEFAULE = FILE_MANAGEMENT_STATE_NORMAL;

    private int fileManagementState = FILE_MANAGEMENT_STATE_DEFAULE;


    private void initView() {
        videoContentView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        commonAdapter = new CommonAdapter<VideoFileBean>(getActivity(), R.layout.item_video, videoFileList) {

            @Override
            protected void convert(final ViewHolder holder, VideoFileBean s, final int position) {
                holder.setText(R.id.tv_video, s.getName());
                if (s.getThumbnail() != null) {
                    holder.setImageBitmap(R.id.img_video, s.getThumbnail());
                } else {
                    holder.setImageDrawable(R.id.img_video, getResources().getDrawable(R.drawable.video_defauly));
                }
                if (fileManagementState == FILE_MANAGEMENT_STATE_NORMAL) {
                    holder.getConvertView().setSelected(false);
                } else if (fileManagementState == FILE_MANAGEMENT_STATE_EDIT) {
                    holder.getConvertView().setSelected(videoFileList.get(position).isSelected());
                }

                holder.setOnClickListener(R.id.layout_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fileManagementState == FILE_MANAGEMENT_STATE_NORMAL) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            Uri uri = Uri.parse("file://" + videoFileList.get(position).getPath());
                            intent.setDataAndType(uri, RtspConstans.MEDIA_TYPE_VIDEO_MP4);
                            getActivity().startActivity(intent);
//                            Mp4ParserUtils.exportRaw(videoFileList.get(position).getPath());
                        } else if (fileManagementState == FILE_MANAGEMENT_STATE_EDIT) {
                            VideoFileBean bean = videoFileList.get(position);
                            bean.setSelected(!bean.isSelected());
                            v.setSelected(bean.isSelected());
                            if (bean.isSelected()){
                                editFileList.add(bean);
                            }else {
                                editFileList.remove(bean);
                            }
                            tvEditMessage.setText(getString(R.string.edit_top_textview_message,editFileList.size()));
                        }
                    }
                });

                holder.setOnLongClickListener(R.id.layout_content, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (fileManagementState == FILE_MANAGEMENT_STATE_EDIT) {
                            return false;
                        }
                        return enterEditMode(position, v);
                    }
                });
            }
        };
        videoContentView.setAdapter(commonAdapter);

        btnCancleEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitEditMode();
            }
        });

        btnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoContentView.setSelected(!videoContentView.isSelected());
                btnCheckAll.setText(
                        videoContentView.isSelected()
                                ? getString(R.string.edit_top_button_cancle_all)
                                : getString(R.string.edit_top_button_select_all)
                );
                for (VideoFileBean bean:videoFileList){
                    bean.setSelected(videoContentView.isSelected());
                }
                if (videoContentView.isSelected()){
                    editFileList.clear();
                    editFileList.addAll(videoFileList);
                    tvEditMessage.setText(getString(R.string.edit_top_textview_message,editFileList.size()));
                }else {
                    editFileList.clear();
                    tvEditMessage.setText(getString(R.string.edit_top_textview_message,editFileList.size()));
                }
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpDownload();
            }
        });

        tvEditMessage.setText(getString(R.string.edit_top_textview_message,5));
    }


    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse(RtspConstans.MEDIA_TYPE_VIDEO_MP4);

    private void httpDownload() {
        final OkHttpClient client = RtspAPP.getOkHttpClient();
        String filePath = FileUtils.getAppStroagePath() + "b.mp4";
        File file = new File(filePath);
        Log.d(TAG, "update file: " + filePath);

        final Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();


        new Thread() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    System.out.println(response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    private void loadFileData() {
        if (videoFileList == null || videoFileList.size() < 1) {
            if (videoFileList == null) {
                videoFileList = new ArrayList<>();
            }
            VideoFileManager.getInstance().loadVideoFiles(commonAdapter, videoFileList);
        }
    }

    private boolean enterEditMode(int position, View v) {
        ToastUtils.showToast("进入编辑模式");
        editTopLayout.setVisibility(View.VISIBLE);
        fileManagementState = FILE_MANAGEMENT_STATE_EDIT;
        VideoFileBean bean = videoFileList.get(position);
        bean.setSelected(!bean.isSelected());
        v.setSelected(bean.isSelected());
        if (bean.isSelected()){
            editFileList.add(bean);
        }else {
            editFileList.remove(bean);
        }
        tvEditMessage.setText(getString(R.string.edit_top_textview_message,editFileList.size()));
        return true;
    }

    private boolean exitEditMode() {
        if (fileManagementState == FILE_MANAGEMENT_STATE_EDIT) {
            ToastUtils.showToast("取消编辑");
            editTopLayout.setVisibility(View.GONE);
            fileManagementState = FILE_MANAGEMENT_STATE_NORMAL;
            commonAdapter.notifyDataSetChanged();
            editFileList.clear();
            tvEditMessage.setText(getString(R.string.edit_top_textview_message,editFileList.size()));
            for (VideoFileBean bean:videoFileList){
                bean.setSelected(false);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onBackPressed() {
        return exitEditMode();
    }


    public class VideoFolderObserver extends FileObserver {

        public VideoFolderObserver(String path, int mask) {
            super(path, mask);
        }

        @Override
        public void onEvent(int event, String path) {
            VideoFileBean bean = null;
            switch (event) {
                case FileObserver.CREATE:
                case FileObserver.MOVED_TO:
                    bean = new VideoFileBean(path, FileUtils.getAppStroagePath()
                            + RtspConstans.EXTERNAL_STROAGE_VIDEO_SUBDIR + File.separator + path);
                    break;
                default:
                    break;
            }
            if (bean != null) {
                videoFileList.add(0, bean);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commonAdapter.notifyDataSetChanged();
                        if (fileListener != null) {
                            fileListener.onFileCreate();
                        }
                    }
                });
            }
        }
    }

    public interface onFileCreateListener {
        void onFileCreate();
    }
}
