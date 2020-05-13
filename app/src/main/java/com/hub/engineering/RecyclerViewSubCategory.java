package com.hub.engineering;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewSubCategory extends RecyclerView.Adapter<RecyclerViewSubCategory.ViewHolder>{
    Animation animation;
    int weight;
    MainActivity mainActivity = new MainActivity();
    private Context context;
    public ArrayList<SuitCaseCategory> categories = new ArrayList<>();
    private RecyclerViewSubCategory.OnItemClickListener mListener;
    public RecyclerViewSubCategory(Context context, ArrayList<SuitCaseCategory> categories, int weight){
        this.context = context;
        this.categories =categories;
        this.weight = weight;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewSubCategory.ViewHolder(LayoutInflater.from(context).inflate(R.layout.sub_category_layout, parent, false));

    }


    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(RecyclerViewSubCategory.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.imageView.setImageURI(categories.get(position).categoryImage);
        Glide.with(holder.imageView.getContext())
                .load(categories.get(position).url)
                .into(holder.imageView);
        holder.textView.setText(categories.get(position).categoryName);
        holder.cardView.setMinimumWidth(weight);
        /*animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.zoom_in);
        animation.setDuration(1000);
        holder.itemView.setAnimation(animation);*/
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CardView cardView;
        int positions;
        Animation animation;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.categoryName);
            imageView = itemView.findViewById(R.id.categoryImage);
            cardView = itemView.findViewById(R.id.cardView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);
                    imageView.setAnimation(buttonClick);
                    if (mListener != null) {
                        positions = getAdapterPosition();
                        if (positions != RecyclerView.NO_POSITION) mListener.onItemClick(positions,itemView);
                    }
                }
            });
        }
    }
}
