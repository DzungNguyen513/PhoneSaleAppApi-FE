package com.example.phonesaleapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.phonesaleapp.view.bill.ConfirmBillFragment;

public class BillPagerAdapter extends FragmentStateAdapter {

    public BillPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                //return new ConfirmFragment();
            case 1:
                //return new PickUpFragment();
            case 2:
                //return new DeliveryFragment();
            default:
                return new ConfirmBillFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
