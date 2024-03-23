package com.example.phonesaleapp.view.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.phonesaleapp.R;
import com.google.android.material.tabs.TabLayout;

public class CircleTabLayout extends TabLayout {

    public CircleTabLayout(@NonNull Context context) {
        super(context);
    }
    public CircleTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public CircleTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addTab(@NonNull Tab tab) {
        super.addTab(tab);
        ViewGroup tabView= (ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(tab.getPosition());
        View tabTextView= tabView.getChildAt(1);
        if (tabTextView != null) {
            // Đặt background của customView thành drawable hình tròn
            tabTextView.setBackgroundResource(R.drawable.tab_indicator_circle);
            ViewGroup.LayoutParams params = tabTextView.getLayoutParams();
            params.width = 188;/* width của tab */;
            params.height = 23;/* height của tab */;
            tabTextView.setLayoutParams(params);
            // Đảm bảo customView được vẽ lại để áp dụng các thay đổi
            tabTextView.requestLayout();
        }
    }


}
