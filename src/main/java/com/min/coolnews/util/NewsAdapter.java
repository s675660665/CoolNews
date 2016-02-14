package com.min.coolnews.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.min.coolnews.R;
import com.min.coolnews.model.News;

import java.util.List;

/**
 * Created by Administrator on 2016/1/14.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public List<News> mData;

    public OnItemClickListener onItemClickListener;

    public Context context;

    public NewsAdapter(List<News> data, Context context){
        mData=data;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView newsText;
        public ImageView newsImage;

        public ViewHolder(View itemView) {
            super(itemView);
            newsText= (TextView) itemView.findViewById(R.id.news_content);
            newsImage= (ImageView) itemView.findViewById(R.id.news_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener!=null){
                onItemClickListener.onItemClick(v,getPosition());
            }
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.item_in_bottom_anim));
        ((ViewHolder)holder).newsText.setText(mData.get(position).getDesc());
        if((mData.get(position).getImageUrls()).size()==0){
            ((ViewHolder) holder).newsImage.setImageResource(R.mipmap.ic_launcher);
        }else {
            String imageurl = mData.get(position).getImageUrls().get(0);
            ImageView imageView=((ViewHolder)holder).newsImage;
            Utility.loadImageFromUrl(imageurl,imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();//回收动画防止卡顿
    }
}
