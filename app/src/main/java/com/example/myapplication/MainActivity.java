package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {
    // Recycler View object
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

         */
        CurvedBottomNavigationView curvedBottomNavigationView = findViewById(R.id.customBottomBar);
        //curvedBottomNavigationView.inflateMenu(R.menu.bottom_nav_menu);
        curvedBottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
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
        imgsource.add(res.getIdentifier("@drawable/add_overtime_schedule", null, getPackageName()));
        imgsource.add(res.getIdentifier("@drawable/add_overtime", null, getPackageName()));
        imgsource.add(res.getIdentifier("@drawable/clock_in", null, getPackageName()));
        imgsource.add(res.getIdentifier("@drawable/vacation", null, getPackageName()));
        imgsource.add(res.getIdentifier("@drawable/document", null, getPackageName()));
        imgsource.add(res.getIdentifier("@drawable/order", null, getPackageName()));
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
           vDate.add(String.valueOf(i));
        }
    }
}