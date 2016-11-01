package com.dilidili.of.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dilidili.of.R;
import com.dilidili.of.base.J_BaseActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.player.data.panoramas.PanoramaData;
import com.player.panoplayer.IPanoPlayerListener;
import com.player.panoplayer.IPanoPlayerVideoPluginListener;
import com.player.panoplayer.PanoPlayer;
import com.player.panoplayer.PanoPlayerUrl;
import com.player.panoplayer.Plugin;
import com.player.panoplayer.plugin.VideoPlugin;
import com.player.renderer.PanoPlayerSurfaceView;

import org.opencv.android.OpenCVLoader;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by mary on 2016/11/1.
 */

public class DetuAcitivity extends J_BaseActivity implements IPanoPlayerListener,
        IPanoPlayerVideoPluginListener {

    private PanoPlayer panoplayer_renderer;
    /*播放器*/
    private PanoPlayerSurfaceView glview;
    /*播放进度条*/
    private SeekBar sb_progress;
    /*播放按钮*/
    private Button btn_play;

    private TextView maxtimelable;
    private TextView curtimelable;
    protected Handler handler = new Handler();
    private PanoPlayer.PanoVideoPluginStatus playerStatus = PanoPlayer.PanoVideoPluginStatus.VIDEO_STATUS_STOP;
    private boolean isSeekBarDragging;
    private VideoPlugin videoplugin;
    /*当前是否直播*/
    private boolean isplaylive = false;
    /*罗盘*/
    private boolean isGyroEnable = false;
    static {
        if (!OpenCVLoader.initDebug()) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Vitamio.initialize(MainActivity.this);
        setContentView(R.layout.activity_main_detu);
        //初始化ImageLoader
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.NONE).cacheInMemory()
                .cacheOnDisc().build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .writeDebugLogs()//打印日志
                .tasksProcessingOrder(QueueProcessingType.FIFO).build();
        ImageLoader.getInstance().init(config);
        initView();

        //playLive();
        /*播放直播按钮*/
        findViewById(R.id.dre_live).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                playLive();
            }
        });
        /*播放视频按钮*/
        findViewById(R.id.dre_video).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                isplaylive = false;
                PanoPlayerUrl panoplayerurl = new PanoPlayerUrl();
                panoplayerurl
                        .SetVideoUrlImage(
                                "http://v3.cztv.com/cztv/vod/2016/03/15/f71522061dc84e10bc012c5243585e0f/h264_1500k_mp4.mp4",
                                "");
                panoplayer_renderer.Play(panoplayerurl);

            }
        });

        findViewById(R.id.Gyro).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (panoplayer_renderer != null) {
                    isGyroEnable = !isGyroEnable;
                    panoplayer_renderer.setGyroEnable(isGyroEnable);
                }
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("", "click btn_play");
                switch (playerStatus) {
                    case VIDEO_STATUS_PAUSE:
                        videoplugin.start();
                        Log.e("", "click btn_play to start");
                        break;
                    case VIDEO_STATUS_STOP:
                        videoplugin.start();
                        Log.e("", "click btn_play to start");
                        break;
                    case VIDEO_STATUS_PLAYING:
                        videoplugin.pause();
                        Log.e("", "click btn_play to pause");
                        break;
                    default:
                        break;
                }
            }
        });

        sb_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar sb) {
                isSeekBarDragging = false;
                videoplugin.seekTo(sb.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                isSeekBarDragging = true;
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress,
                                          boolean fromUser) {
            }
        });
        /*全屏播放*/
        findViewById(R.id.btn_full).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) glview
                        .getLayoutParams();
                lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                glview.setLayoutParams(lp);
            }
        });

    }
    @Override
    protected void initView() {
        btn_play = (Button) findViewById(R.id.btn_play);
        sb_progress = (SeekBar) findViewById(R.id.sb_progress);
        glview = (PanoPlayerSurfaceView) findViewById(R.id.glview);
        maxtimelable = (TextView) findViewById(R.id.lable2);
        curtimelable = (TextView) findViewById(R.id.lable1);
        panoplayer_renderer = new PanoPlayer(glview, this);
        panoplayer_renderer.setListener(this);
        panoplayer_renderer.setVideoPluginListener(this);
        glview.setRenderer(panoplayer_renderer);
        findViewById(R.id.videolay).setVisibility(View.GONE);
    }

    /**
     * 播放直播
     */
    public void playLive(){
        isplaylive=true;
        PanoPlayerUrl panoplayerurl = new PanoPlayerUrl();

        //播放方式一:  setXmlContent(String content);  content 必须是如下格式的XML 文本才可以播放


        //播放方式二:  setXmlUrl(String url); url 地址 必须返回的是 如上格式 的XML 文本才可以播放

        //panoplayerurl.setXmlUrl("http://www.detu.com/live/xinlan/live-test.xml");

        String PanoPlayer_Template = "<DetuVr> "
                + "<settings init=\"pano1\" initmode=\"flat\" enablevr=\"false\"  title=\"\"/>"
                + 	"<scenes>"
                + 		"<scene name=\"pano1\"  title=\"\"    thumburl=\"\"   >"
                + 			"<image type=\"video\" url=\"%s\" rx=\"0\" ry=\"0\" rz=\"0\"/>"
                +            "<view hlookat=\"0\" vlookat=\"0\" fov=\"100\" vrfov=\"95\" vrz=\"0.5\" righteye=\"0.1\" fovmax=\"130\" defovmax=\"95\" gyroEnable=\"false\"/>"
                + 		"</scene>"
                + 	"</scenes>"
                + "</DetuVr>";
        String xmlstring = String.format(PanoPlayer_Template,"http://hls5.l.cztv.com/channels/lantian/wchannel102/720p.m3u8");

        panoplayerurl.setXmlContent(xmlstring);

        panoplayer_renderer.Play(panoplayerurl);
    }

    @Override
    public void PluginVideOnPlayerError(PanoPlayer.PanoPlayerErrorStatus s, String errorstr) {
        Log.d("PanoPlay", "PluginVideOnPlayerError" + errorstr);
    }

    @Override
    public void PluginVideoOnProgressChanged(final int curTime, int bufTime,
                                             final int maxTime) {
        if (!isSeekBarDragging) {
            sb_progress.setMax(maxTime);
            sb_progress.setSecondaryProgress(bufTime);
            sb_progress.setProgress(curTime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    maxtimelable.setText(formatDuring(maxTime));
                    curtimelable.setText(formatDuring(curTime));
                }
            });
        }
    }

    public String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60) + days
                * 24;
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;

        String HH = (hours > 0) ? String.valueOf(hours) : "00";
        String mm = (minutes > 0) ? String.valueOf(minutes) : "00";
        String ss = (seconds > 0) ? String.valueOf(seconds) : "00";

        HH = (HH.length() == 1) ? ("0" + HH) : (HH);
        mm = (mm.length() == 1) ? ("0" + mm) : (mm);
        ss = (ss.length() == 1) ? ("0" + ss) : (ss);
        return HH + " : " + mm + " : " + ss;
    }

    @Override
    public void PluginVideoOnSeekFinished() {
        Log.d("PanoPlay", "PluginVideoOnSeekFinished");
    }

    @Override
    public void PluginVideoOnStatusChanged(PanoPlayer.PanoVideoPluginStatus s) {
        playerStatus = s;
        switch (s) {
            case VIDEO_STATUS_PAUSE:
                btn_play.post(new Runnable() {
                    public void run() {
                        btn_play.setText("暂停");
                    }
                });
                Log.d("PanoPlay", "PluginVideoOnStatusChanged to pause");
                break;
            case VIDEO_STATUS_STOP:
                btn_play.post(new Runnable() {
                    public void run() {
                        btn_play.setText("停止");
                    }
                });
                Log.d("PanoPlay", "PluginVideoOnStatusChanged to stop");
                sb_progress.setProgress(0);
                break;
            case VIDEO_STATUS_PLAYING:
                btn_play.post(new Runnable() {
                    public void run() {
                        btn_play.setText("播放");
                    }
                });
                Log.d("PanoPlay", "PluginVideoOnStatusChanged to play");
                break;
            case VIDEO_STATUS_FINISH:
                Log.d("PanoPlay", "PluginVideoOnStatusChanged to FINISH");
                break;
            case VIDEO_STATUS_BUFFER_EMPTY:
                Log.d("PanoPlay", "PluginVideoOnStatusChanged to BUFFER_EMPTY");
                break;
            default:
                Log.d("PanoPlay", "PluginVideoOnStatusChanged to UNPREPARED;");
                break;
        }
    }

    @Override
    public void PanoPlayOnEnter(PanoramaData arg0) {
        Log.d("PanoPlay", "PanoPlayOnEnter");
    }

    @Override
    public void PanoPlayOnError(PanoPlayer.PanoPlayerErrorCode e) {
        Log.d("PanoPlay", "PanoPlayOnError" + e);
    }

    @Override
    public void PanoPlayOnLeave(PanoramaData arg0) {
        Log.d("PanoPlay", "PanoPlayOnLeave");
    }

    @Override
    public void PanoPlayOnLoaded() {
        Log.d("PanoPlay", "PanoPlayOnLoaded");
        findViewById(R.id.videolay).setVisibility(View.GONE);
        Plugin plugin = panoplayer_renderer.getCurPlugin();
        if (plugin instanceof VideoPlugin && !isplaylive) {
            videoplugin = (VideoPlugin) plugin;
            findViewById(R.id.videolay).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void PanoPlayOnLoading() {

    }

    @Override
    public void PluginVideoOnInit() {
        Plugin plugin = panoplayer_renderer.getCurPlugin();
        if (plugin != null && plugin instanceof VideoPlugin) {
            videoplugin = (VideoPlugin) plugin;
            videoplugin.setLogLevel(IjkMediaPlayer.IJK_LOG_DEFAULT);
        }
    }
}
