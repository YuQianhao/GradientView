package com.yuqianhao.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yuqianhao.view.GradientView;
import com.yuqianhao.view.ViewPagerBottomView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/10.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private ArrayList<GradientView> mArrayLists;
    private ArrayList<MyFragment> mFragmentArrays;
    private GradientView mViewButtonA;
    private GradientView mViewButtonB;
    private GradientView mViewButtonC;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mViewPager= (ViewPager) findViewById(R.id.act_view_viewpager);
        mArrayLists=new ArrayList<>();
        mViewButtonA= (GradientView) findViewById(R.id.button_0);
        mViewButtonA.setOnClickListener(this);
        mViewButtonB= (GradientView) findViewById(R.id.button_1);
        mViewButtonB.setOnClickListener(this);
        mViewButtonC= (GradientView) findViewById(R.id.button_2);
        mViewButtonC.setOnClickListener(this);
        mArrayLists.add(mViewButtonA);
        mArrayLists.add(mViewButtonB);
        mArrayLists.add(mViewButtonC);
        mFragmentArrays=new ArrayList<>();
        mFragmentArrays.add(new MyFragment("ViewPager_0"));
        mFragmentArrays.add(new MyFragment("ViewPager_1"));
        mFragmentArrays.add(new MyFragment("ViewPager_2"));
        mFragmentPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentArrays.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentArrays.size();
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);
        //-----------------
        mViewButtonA.setImageAlpha(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(positionOffset>0){
                    GradientView left=mArrayLists.get(position);
                    GradientView right=mArrayLists.get(position+1);
                    left.setImageAlpha(1-positionOffset);
                    right.setImageAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        resetOtherTabs();
        switch(v.getId()){
            case R.id.button_0:{
                mArrayLists.get(0).setImageAlpha(1.0f);
                mViewPager.setCurrentItem(0,false);
            }break;
            case R.id.button_1:{
                mArrayLists.get(1).setImageAlpha(1.0f);
                mViewPager.setCurrentItem(1,false);
            }break;
            case R.id.button_2:{
                mArrayLists.get(2).setImageAlpha(1.0f);
                mViewPager.setCurrentItem(2,false);
            }break;
        }
    }

    private void resetOtherTabs() {
        for(int i=0;i<mArrayLists.size();i++){
            mArrayLists.get(i).setImageAlpha(0);
        }
    }

    static class MyFragment extends Fragment {
        private TextView mTextView;
        private String mText;

        public MyFragment(String title){
            mText=title;
        }

        public MyFragment(){}

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView=inflater.inflate(R.layout.fragment,null,false);
            mTextView= (TextView) rootView.findViewById(R.id.textView);
            mTextView.setText(mText==null?"TextViewID="+mTextView.getId():mText);
            return rootView;
        }
    }
}
