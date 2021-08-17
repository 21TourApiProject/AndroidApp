package com.starrynight.tourapiproject.postItemPage;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postPage.ImageSliderAdapter;

import java.util.ArrayList;

public class Post_item_adapter extends RecyclerView.Adapter<Post_item_adapter.ViewHolder>{
    ArrayList<post_item> items = new ArrayList<post_item>();
    OnPostItemClickListener listener;

    public void addItem(post_item item){
        items.add(item);
    }
    public void setItems(ArrayList<post_item>items){
        this.items = items;
    }
    public post_item getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, post_item item){
        items.set(position,item);
    }
    @NonNull
    @Override
    public Post_item_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_main, parent, false);
        return new ViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Post_item_adapter.ViewHolder viewHolder, int position) {
        post_item item = items.get(position);
        viewHolder.setItem(item);
        Glide.with(viewHolder.itemView.getContext())
                .load(item.getImage2())
                .into(viewHolder.profileimage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void  setOnItemClicklistener(OnPostItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Button;
        TextView Button2;
        TextView title;
        TextView nickname;
        ImageView profileimage;
        ViewPager2 mainslider;
        LinearLayout indicator;

        public ViewHolder(View itemView,final OnPostItemClickListener listener){
            super(itemView);

            Button =itemView.findViewById(R.id.hash__button);
            Button2 = itemView.findViewById(R.id.hash__button2);
            title = itemView.findViewById(R.id.mainpost_title);
            nickname = itemView.findViewById(R.id.nickname);
            profileimage = itemView.findViewById(R.id.mainprofileimage);
            mainslider = itemView.findViewById(R.id.mainslider);
            indicator = itemView.findViewById(R.id.mainindicator);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null){
                        listener.onItemClick(Post_item_adapter.ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(post_item item){
            Button.setText(item.getHash());
            Button2.setText(item.getHash2());
            title.setText(item.getTitle());
            nickname.setText(item.getNickname());
            mainslider.setOffscreenPageLimit(3);
            mainslider.setAdapter(new ImageSliderAdapter(mainslider.getContext(), item.getImages()));
            mainslider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    setCurrentIndicator(position);
                }
            });
            setupIndicators(item.getImages().length);
        }
        private void setupIndicators(int count) {
            ImageView[] indicators = new ImageView[count];
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            params.setMargins(16, 8, 16, 8);

            for (int i = 0; i < indicators.length; i++) {
                indicators[i] = new ImageView(indicator.getContext());
                indicators[i].setImageDrawable(ContextCompat.getDrawable(indicator.getContext(),
                        R.drawable.post__indicator_inactive));
                indicators[i].setLayoutParams(params);
                indicator.addView(indicators[i]);
            }
            setCurrentIndicator(0);
        }
        private void setCurrentIndicator(int position) {
            int childCount = indicator.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ImageView imageView = (ImageView) indicator.getChildAt(i);
                if (i == position) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(
                            indicator.getContext(),
                            R.drawable.post__indicator_active
                    ));
                } else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(
                            indicator.getContext(),
                            R.drawable.post__indicator_inactive
                    ));
                }
            }
        }
    }

}
