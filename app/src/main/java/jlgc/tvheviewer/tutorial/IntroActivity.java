package jlgc.tvheviewer.tutorial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import jlgc.tvheviewer.LoginActivity;
import jlgc.tvheviewer.R;

public class IntroActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private int currentPage;
    private ImageButton backButton;
    private ImageButton nextButton;
    SharedPreferences mPreferences;
    private boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
        firstTime = mPreferences.getBoolean("firstTime", true);
        if(!firstTime) {
            Intent login = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(login);
        }
        else {
            setContentView(R.layout.intro_activity);

            mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
            mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
            backButton = (ImageButton) findViewById(R.id.slideBack);
            backButton.setEnabled(false);
            backButton.setVisibility(View.INVISIBLE);
            nextButton = (ImageButton) findViewById(R.id.slideNext);

            sliderAdapter = new SliderAdapter(this);
            mSlideViewPager.setAdapter(sliderAdapter);
            sliderAdapter.addDotsIndicator(this, mDotLayout, 0);
            mSlideViewPager.addOnPageChangeListener(viewListener);

            //Listener buttons
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentPage==sliderAdapter.getCount()-1)
                    {
                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.putBoolean("firstTime", false);
                        editor.commit();
                        Intent login = new Intent(IntroActivity.this, LoginActivity.class);
                        startActivity(login);

                    }
                    else {
                        mSlideViewPager.setCurrentItem(currentPage +1);
                    }
                }
            });
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSlideViewPager.setCurrentItem(currentPage -1);
                }
            });
        }
    }

    /*Change dots color when moving to another page*/
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //
        }

        @Override
        public void onPageSelected(int position) {
            sliderAdapter.addDotsIndicator(getApplicationContext(), mDotLayout, position);
            currentPage = position;
            if(currentPage ==0) {
                backButton.setEnabled(false);
                backButton.setVisibility(View.INVISIBLE);
            }
            else if (currentPage == sliderAdapter.getCount()-1) {
                nextButton.setImageResource(R.drawable.intro_ic_check_circle);
            }
            else {
                backButton.setEnabled(true);
                backButton.setVisibility(View.VISIBLE);
                nextButton.setImageResource(R.drawable.intro_ic_keyboard_arrow_right);
                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //
        }
    };
}
