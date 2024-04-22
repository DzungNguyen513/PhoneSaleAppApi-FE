package com.example.phonesaleapp.view.bill;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.phonesaleapp.view.bill.ConfirmBillFragment;
import com.example.phonesaleapp.view.bill.DeliveryFragment;
import com.example.phonesaleapp.view.bill.DestroyBillFragment;
import com.example.phonesaleapp.view.bill.FinishBillFragment;
import com.example.phonesaleapp.view.bill.PickUpFragment;

public class BillPagerAdapter extends FragmentStateAdapter {

    public BillPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ConfirmBillFragment();
            case 1:
                return new PickUpFragment();
            case 2:
                return new DeliveryFragment();
            case 3:
                return new FinishBillFragment();
            case 4:
                return new DestroyBillFragment();
            default:
                return new ConfirmBillFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
