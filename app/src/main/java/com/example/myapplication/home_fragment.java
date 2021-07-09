package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;


public class home_fragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView rv_date;
    RecyclerView rv_notify;

    // Array list for recycler view data source
    Vector source;
    Vector imgsource;
    Vector vDate;
    Vector vName;

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    // adapter class object
    FunctionAdapter adapter;
    DateAdapter DateAdapter;
    notifyAdapter notifyAdapter;

    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;
    private Context context;

    private CenterLayoutManager centerLayoutManager;

    private int lastLabelIndex;

    private List<FilterBean> list = new ArrayList<>();
    private float itemWidth;


    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // initialisation with id's
        recyclerView
                = (RecyclerView)root.findViewById(
                R.id.rv_function);
        RecyclerViewLayoutManager
                = new LinearLayoutManager(
                context);

        // Set LayoutManager on Recycler View
        recyclerView.setLayoutManager(
                RecyclerViewLayoutManager);

        // Adding items to RecyclerView.
        AddItemsToRecyclerView();
        AddImgToRecyclerView();


        // calling constructor of adapter
        // with source list as a parameter
        adapter = new FunctionAdapter(imgsource,source);

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout
                = new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);

        // Set adapter on recycler view
        recyclerView.setAdapter(adapter);


        rv_date
                = (RecyclerView)root.findViewById(
                R.id.rv_date);
        /*
        RecyclerViewLayoutManager
                = new LinearLayoutManager(
                context);
        rv_date.setLayoutManager(
                RecyclerViewLayoutManager);
        AddItemsToDateRecyclerView();

        DateAdapter = new DateAdapter(vDate);

        HorizontalLayout
                = new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false);
        rv_date.setLayoutManager(HorizontalLayout);

        // Set adapter on recycler view
        rv_date.setAdapter(DateAdapter);
        */
        AddItemsToDateRecyclerView();
        DateAdapter = new DateAdapter(list,vDate);
        centerLayoutManager = new CenterLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        rv_date.setLayoutManager(centerLayoutManager);
        rv_date.setAdapter(DateAdapter);
        //itemWidth = rv_date.getMeasuredWidth();
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv_date);

        //This is used to center first and last item on screen
        rv_date.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildViewHolder(view).getAdapterPosition();

                if (position == 0 || position == state.getItemCount() - 1) {

                    int elementWidth = (int)getResources().getDimension(R.dimen.element_width);
                    int elementMargin = (int)getResources().getDimension(R.dimen.element_margin);
                    int itemPosition = parent.getChildAdapterPosition(view);

                    // It is crucial to refer to layoutParams.width (view.width is 0 at this time)!
                    //int itemWidth = view.getWidth();
                    //int offset = (parent.getWidth() - itemWidth) / 2;
                    //int offset = (int) (parent.getWidth() * 0.5f - view.getWidth() * 0.5f);

                    int padding = Resources.getSystem().getDisplayMetrics().widthPixels / 2 - elementWidth - elementMargin/2;

                    if (position == 0) {
                        outRect.left = padding;

                    } else {
                        outRect.right = padding;
                    }
                }
            }
        });
        centerLayoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), lastLabelIndex);
        DateAdapter.notifyItemChanged(lastLabelIndex, DateAdapter.UPDATE_STATE);
        DateAdapter.setOnLabelClickListener(new DateAdapter.OnLabelClickListener() {

            @Override
            public void onClick(FilterBean bean,int position) {
                Log.i("test",position+" "+lastLabelIndex);
                if (position != lastLabelIndex) {
                    Log.i("onclick", String.valueOf(position)+" "+lastLabelIndex);
                    FilterBean lastBean = list.get(lastLabelIndex);
                    lastBean.setSelected(false);
                    DateAdapter.notifyItemChanged(lastLabelIndex, DateAdapter.UPDATE_STATE);

                    centerLayoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), position);
                    bean.setSelected(true);
                    DateAdapter.notifyItemChanged(position, DateAdapter.UPDATE_STATE);
                }
                lastLabelIndex = position;
            }
        });
        rv_date.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
                        View centerView = snapHelper.findSnapView(centerLayoutManager);
                        int position = centerLayoutManager.getPosition(centerView);
                        Log.i("onScrollStateChanged", String.valueOf(position));

                        //int position = centerLayoutManager.findLastVisibleItemPosition() -2;
                        if(position!=-1&&position!=lastLabelIndex) {
                            FilterBean bean = list.get(position);
                            bean.setSelected(true);
                            //centerLayoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), position);
                            DateAdapter.notifyItemChanged(position, DateAdapter.UPDATE_STATE);
                            FilterBean lastBean = list.get(lastLabelIndex);
                            lastBean.setSelected(false);
                            DateAdapter.notifyItemChanged(lastLabelIndex, DateAdapter.UPDATE_STATE);
                            lastLabelIndex=position;
                        }
                        break;
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });
        /*
        DateAdapter.setOnItemClickListener(new DateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("asdasdsdsclick","click");
                moveToMiddle(view);
                DateAdapter.notifyDataSetChanged();
            }
        });

        rv_date.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                moveToMiddle(recyclerView);
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                moveToMiddle(recyclerView);
            }
        });
        centerLayoutManager.smoothScrollToPosition(rv_date,new RecyclerView.State(),10);
        //centerLayoutManager.scrollToPositionWithOffset(10,6);
        //rv_date.scrollToPosition(10);

         */


        rv_notify
                = (RecyclerView)root.findViewById(
                R.id.rv_notify);
        RecyclerViewLayoutManager
                = new LinearLayoutManager(
                context);
        rv_notify.setLayoutManager(
                RecyclerViewLayoutManager);
        vName = new Vector();
        vName.add("L107666");

        vName.add("L107666");

        vName.add("L107666");

        notifyAdapter = new notifyAdapter(vName);

        HorizontalLayout
                = new LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false);
        rv_notify.setLayoutManager(HorizontalLayout);

        // Set adapter on recycler view
        rv_notify.setAdapter(notifyAdapter);
        return root;
    }
    public void moveToMiddle(View clkView){
        int itemWidth = clkView.getWidth();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int scrollWidth = clkView.getLeft() - (screenWidth / 2 - itemWidth / 2);
        rv_date.scrollBy(scrollWidth, 0);
    }



    public void AddItemsToRecyclerView()
    {
        // Adding items to ArrayList
        source = new Vector();
        source.add("排班");
        source.add("加班");
        source.add("打卡");
        source.add("請假");
        source.add("文件");
        source.add("訂餐");
    }

    public void AddImgToRecyclerView()
    {
        // Adding items to ArrayList
        imgsource = new Vector();
        Resources res = getResources();
        imgsource.add(res.getIdentifier("@drawable/add_overtime_schedule", null, "com.example.myapplication"));
        imgsource.add(res.getIdentifier("@drawable/add_overtime", null, "com.example.myapplication"));
        imgsource.add(res.getIdentifier("@drawable/clock_in", null, "com.example.myapplication"));
        imgsource.add(res.getIdentifier("@drawable/vacation", null, "com.example.myapplication"));
        imgsource.add(res.getIdentifier("@drawable/document", null, "com.example.myapplication"));
        imgsource.add(res.getIdentifier("@drawable/order", null, "com.example.myapplication"));
    }

    public void AddItemsToDateRecyclerView()
    {
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, 2021);

        cal.set(Calendar.MONTH, 06);

        cal.set(Calendar.DAY_OF_MONTH, 1);

        vDate = new Vector();
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i=1;i<=31;i++) {
            FilterBean bean = new FilterBean();
            bean.setName(String.valueOf(i));
            if(i==10) {
                bean.setSelected(true);
            }
            lastLabelIndex = 9;

            list.add(bean);

            //vDate.add(String.valueOf(i));
        }
    }
}
