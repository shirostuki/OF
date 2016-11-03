package com.dilidili.of.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dilidili.of.R;
import com.dilidili.of.base.J_BaseActivity;
import com.dilidili.of.fragment.BangumiFragment;
import com.dilidili.of.fragment.FocusFragment;
import com.dilidili.of.fragment.LiveFragment;
import com.dilidili.of.view.CircleImageView;
import com.dilidili.of.view.WrapContentHeightViewPager;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.toggle;

public class MainActivity extends J_BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /* 抽屉 */
    private DrawerLayout mDrawer;
    private ViewPager mViewPager;
    /* 碎片列表 */
    private List<Fragment> mFragmentList;
    /* 顶部导航 一 */
    private ImageView mToDrawer, mToGame, mToDownload, mToSearch;
    private CircleImageView mHeadMin;
    private TextView mUserName;
    /* 顶部导航 二 */
    private TabLayout mTabLayout;
    /* 导航标题 */
    private String[] mTabs = new String[]{"直播", "番剧", "关注"};
    public static String s="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        RequestParams params = new RequestParams("http://live.bilibili.com/h5/23382");    // 网址(请替换成实际的网址)
        //params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {

            }
            // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
            // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    // ...
                } else { // 其他错误
                    // ...
                }
            }

            // 不管成功或者失败最后都会回调该接口
            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String arg0) {
                Log.e("TCP", ""+arg0);
                s =arg0;

            }
        });
    }


    @Override
    protected void initView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mToDrawer = (ImageView) findViewById(R.id.img_to_drawer);
        mToDrawer.setOnClickListener(this);
        mToGame = (ImageView) findViewById(R.id.img_to_game);
        mToGame.setOnClickListener(this);
        mToDownload = (ImageView) findViewById(R.id.img_to_download);
        mToDownload.setOnClickListener(this);
        mToSearch = (ImageView) findViewById(R.id.img_to_search);
        mToSearch.setOnClickListener(this);
        mUserName = (TextView) findViewById(R.id.tv_user_name);

        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new LiveFragment());
        mFragmentList.add(new BangumiFragment());
        mFragmentList.add(new FocusFragment());

        mViewPager = (WrapContentHeightViewPager) findViewById(R.id.vp_main);
        mViewPager.setAdapter(new FragmentViewPagerAdapter(this.getSupportFragmentManager()));
        mViewPager.setCurrentItem(0, false);
        mViewPager.setOffscreenPageLimit(2);

        mTabLayout = (TabLayout) findViewById(R.id.tab_bar);
        mTabLayout.setupWithViewPager(mViewPager);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_to_drawer:
            case R.id.img_head_min:
                //打开抽屉
                mDrawer.openDrawer(GravityCompat.START);
                break;
            case R.id.img_to_game:
                break;
            case R.id.img_to_download:
                break;
            case R.id.img_to_search:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * viewpager的适配器
     */
    class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {

        public FragmentViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList != null ? mFragmentList.get(position) : null;
        }

        @Override
        public int getCount() {
            return mFragmentList != null ? mFragmentList.size() : 0;
        }

        /**
         * 设置导航的标题
         *
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs != null ? mTabs[position] : "";
        }
    }
}
