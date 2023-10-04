package com.linerock.ccvalidate.bankdetail.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.nativead.NativeAdView;
import com.iten.trendzad.ad.AdShow;
import com.iten.trendzad.ad.Qureka.QuerkaNative;
import com.iten.trendzad.utils.AdConstant;
import com.iten.trendzad.utils.AdUtils;
import com.linerock.ccvalidate.bankdetail.activity.bankdetails.bankingstuff.bankholiday.BankSelectStateActivity;
import com.linerock.ccvalidate.bankdetail.databinding.AdviewQurekaBinding;
import com.linerock.ccvalidate.bankdetail.databinding.ItemBankDetailHomeBinding;
import com.linerock.ccvalidate.bankdetail.databinding.NativeBigBinding;
import com.linerock.ccvalidate.bankdetail.model.BankHolidayModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BankSelectStateAdapter extends RecyclerView.Adapter {
    Activity activity;
    ArrayList<String> stateList;
    JSONObject jsonObject;
    private static final int ITEM_VIEW_TYPE = 0;
    private static final int NATIVE_VIEW_TYPE = 1;
    private static final int QUREKA_VIEW_TYPE = 2;

    public BankSelectStateAdapter(Activity activity, ArrayList<String> stateList, JSONObject listpathObject) {
        this.activity = activity;
        this.stateList = stateList;
        this.jsonObject=listpathObject;
    }

    @Override
    public int getItemViewType(int position) {
        if(stateList.get(position) == null)
        {
            return NATIVE_VIEW_TYPE;
        }else if(stateList.get(position).equals("QUREKA"))
        {
            return  QUREKA_VIEW_TYPE;
        }else
        {
           return  ITEM_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case ITEM_VIEW_TYPE:
                return new MyViewHolder(ItemBankDetailHomeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
            case QUREKA_VIEW_TYPE:
                return  new QurekaViewHolder(AdviewQurekaBinding.inflate(LayoutInflater.from(activity),parent,false));
            case NATIVE_VIEW_TYPE:
            default:
                return new NativeViewHolder(NativeBigBinding.inflate(LayoutInflater.from(activity),parent,false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType)
        {
            case ITEM_VIEW_TYPE:
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.x.textviewTittle.setText(stateList.get(position));
                myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            ((BankSelectStateActivity)activity).onClickState(jsonObject.getJSONArray(stateList.get(position)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case QUREKA_VIEW_TYPE:
                QurekaViewHolder qurekaViewHolder = (QurekaViewHolder) holder;
                QuerkaNative.getInstance(activity).QuerkaAd(qurekaViewHolder.x.imageQureka,null,qurekaViewHolder.x.imageQureka);
                break;

            case NATIVE_VIEW_TYPE:
            default:
              NativeViewHolder nativeViewHolder = (NativeViewHolder) holder;
                NativeAdView nativeAdView = AdConstant.nativeAdViewHashMapBig.get(position);
                if (nativeAdView == null) {
                    AdShow.getInstance(activity).ShowRecyclerViewNativeAd(nativeViewHolder.x.nativeAdLayout, AdUtils.NativeType.NATIVE_BIG, position);
                } else {
                    if (nativeAdView.getParent() != null) {
                        ((ViewGroup) nativeAdView.getParent()).removeView(nativeAdView);

                    }
                    nativeViewHolder.x.nativeAdLayout.removeAllViews();
                    nativeViewHolder.x.nativeAdLayout.addView(nativeAdView);

                }
                break;


        }


    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemBankDetailHomeBinding x;
        public MyViewHolder(@NonNull  ItemBankDetailHomeBinding itemView) {
            super(itemView.getRoot());
            x=itemView;
        }
    }
    public class NativeViewHolder extends RecyclerView.ViewHolder {
        NativeBigBinding x;
        public NativeViewHolder(@NonNull  NativeBigBinding itemView) {
            super(itemView.getRoot());
            x=itemView;
        }
    }

    public class QurekaViewHolder extends RecyclerView.ViewHolder {
        AdviewQurekaBinding x;
        public QurekaViewHolder(@NonNull  AdviewQurekaBinding itemView) {
            super(itemView.getRoot());
            x=itemView;
        }
    }

}
