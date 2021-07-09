package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder>{
    private Vector vDATA_DATE;
    private Vector vDATA_WEEK;
    int row_index;
    private DateAdapter.OnItemClickListener onItemClickListener;
    private List<FilterBean> list;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_week;
        private final TextView tv_date;
        private final CardView cardView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tv_week = (TextView) view.findViewById(R.id.weekId);
            tv_date = (TextView) view.findViewById(R.id.dateId);
            cardView = (CardView)view.findViewById(R.id.cardview_date);
        }

        public TextView getTextView_Week() {
            return tv_week;
        }
        public TextView getTextView_Date() {
            return tv_date;
        }
        public CardView getCardView() {
            return cardView;
        }
    }


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param vDATA_DATE Vector containing the data to populate views to be used
     * by RecyclerView.
     */
    public DateAdapter(List<FilterBean> list ,Vector vDATA_DATE) {
        this.list = list;
        this.vDATA_DATE = vDATA_DATE;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DateAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.date_item, viewGroup, false);

        return new DateAdapter.ViewHolder(view);
    }

    public void setOnItemClickListener(DateAdapter.OnItemClickListener listener){
        this.onItemClickListener = listener;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DateAdapter.ViewHolder viewHolder, final int position1) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        final int position = viewHolder.getAdapterPosition();
        //viewHolder.getTextView_Date().setText((String) vDATA_DATE.get(position));
        if (list != null && null != list.get(position)) {
            FilterBean bean = list.get(position);
            boolean bCHECK = bean.isSelected();

            String name = bean.getName();
            viewHolder.getTextView_Date().setText(name);
            if(bCHECK){

                viewHolder.getCardView().setCardBackgroundColor(Color.parseColor("#9561FF"));
                viewHolder.getTextView_Date().setTextColor(Color.parseColor("#FFFFFFFF"));
                viewHolder.getTextView_Week().setTextColor(Color.parseColor("#FFFFFFFF"));
                viewHolder.getCardView().setRadius(20);
            }else{

                viewHolder.getCardView().setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));
                viewHolder.getTextView_Date().setTextColor(Color.parseColor("#A1A9C9"));
                viewHolder.getTextView_Week().setTextColor(Color.parseColor("#A1A9C9"));
            }

            viewHolder.getCardView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLabelClickListener != null) {
                        onLabelClickListener.onClick(bean, position);
                    }
                }
            });
        }


        /*
        viewHolder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                int position1 = viewHolder.getPosition();
                onItemClickListener.onItemClick(viewHolder.itemView, position1);
                notifyDataSetChanged();
            }
        });

         */
        /*
        if(row_index==position){

            viewHolder.getCardView().setCardBackgroundColor(Color.parseColor("#9561FF"));
            viewHolder.getTextView_Date().setTextColor(Color.parseColor("#FFFFFFFF"));
            viewHolder.getTextView_Week().setTextColor(Color.parseColor("#FFFFFFFF"));
            viewHolder.getCardView().setRadius(20);
        }
        else
        {
            viewHolder.getCardView().setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));
            viewHolder.getTextView_Date().setTextColor(Color.parseColor("#A1A9C9"));
            viewHolder.getTextView_Week().setTextColor(Color.parseColor("#A1A9C9"));
        }
        */



    }

    public interface OnLabelClickListener {
        /**
         * 点击label
         *
         * @param bean     点击label的对象
         * @param position 点击位置
         */
        void onClick(FilterBean bean,int position);
    }

    private OnLabelClickListener onLabelClickListener;

    public void setOnLabelClickListener(OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
    }

    public static final int UPDATE_STATE = 101;
    public static final int UPDATE_NAME = 102;

    @Override
    public void onBindViewHolder(@NonNull @NotNull DateAdapter.ViewHolder holder, int position, @NonNull @NotNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        //list为空时，必须调用两个参数的onBindViewHolder(@NonNull LabelHolder holder, int position)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else if (payloads.get(0) instanceof Integer) {
            int payLoad = (int) payloads.get(0);
            switch (payLoad) {
                case UPDATE_STATE:
                    FilterBean bean = list.get(position);
                    boolean bCHECK = bean.isSelected();
                    Log.i("bind",bCHECK+" "+position);
                    if(bCHECK){

                        holder.getCardView().setCardBackgroundColor(Color.parseColor("#9561FF"));
                        holder.getTextView_Date().setTextColor(Color.parseColor("#FFFFFFFF"));
                        holder.getTextView_Week().setTextColor(Color.parseColor("#FFFFFFFF"));
                        holder.getCardView().setRadius(20);
                    }else{

                        holder.getCardView().setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));
                        holder.getTextView_Date().setTextColor(Color.parseColor("#A1A9C9"));
                        holder.getTextView_Week().setTextColor(Color.parseColor("#A1A9C9"));
                    }
                    break;
                case UPDATE_NAME:
                    holder.getTextView_Date().setText(list.get(position).getName());
                    break;
                default:
                    break;
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
//      void onItemLongClick(View view, int position); //长按
    }


}
