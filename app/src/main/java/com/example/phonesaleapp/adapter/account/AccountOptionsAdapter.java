package com.example.phonesaleapp.adapter.account;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.model.AccountOption;

import java.util.List;

public class AccountOptionsAdapter extends RecyclerView.Adapter<AccountOptionsAdapter.ViewHolder> {

    private List<AccountOption> mOptions;
    private static OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AccountOptionsAdapter(List<AccountOption> options) {
        mOptions = options;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccountOption option = mOptions.get(position);
        holder.mTextView.setText(option.getTitle());
        holder.mImageView.setImageResource(option.getIcon());
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.ivIcon);
            mTextView = itemView.findViewById(R.id.tvOption);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

    }
}
