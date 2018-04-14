package jlgc.tvheviewer;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private TextView[] mDots;

    /*Construtor*/
    public SliderAdapter(Context context) {
        this.context = context;
    }

    /*Headers, Images and Descriptions*/
    public int[] slideImages = {
            R.mipmap.tvhe_icon,
            R.drawable.tv,
            R.drawable.foto2,
            R.drawable.foto3
    };
    public String[] slideHeaders = {
            "Welcome to TVHE Viewer",
            "Real DVB-T Experience",
            "Full Secured",
            "No Ads"
    };
    public String[] slideDescriptions = {
            "Let's introduce you some interesting features about this application.",
            "Forget about poor quality IPTV streams. All channels are directly " +
                    "provided by a real DVB-T tuner.",
            "Full Secured",
            "No Ads"
    };

    /*Methods*/
    @Override
    public int getCount() {
        return slideHeaders.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.intro_slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeader = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDesc = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slideImages[position]);
        slideHeader.setText(slideHeaders[position]);
        slideDesc.setText(slideDescriptions[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

    public void addDotsIndicator(Context context, LinearLayout layout, int position, boolean cambio) {
        if(cambio) {
            for(int i=0;i <mDots.length;i++) {
                if(i==position) {
                    mDots[i].setTextColor(Color.WHITE);
                }
                else {
                    mDots[i].setTextColor(Color.GRAY);
                }
            }
        }
        else{
            mDots = new TextView[slideHeaders.length];
            for (int i = 0; i < mDots.length; i++) {
                mDots[i] = new TextView(context);
                mDots[i].setText(Html.fromHtml("&#8226"));
                mDots[i].setTextSize(45);
                if(i==position) {
                    mDots[i].setTextColor(Color.WHITE);
                }
                else {
                    mDots[i].setTextColor(Color.GRAY);
                }
                layout.addView(mDots[i]);
            }
        }
    }
}
