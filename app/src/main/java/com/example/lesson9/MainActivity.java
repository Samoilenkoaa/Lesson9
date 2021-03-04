package com.example.lesson9;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    boolean isLand;
    //    Toolbar toolbar;
    ArrayList<NoteDataClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);
        list = new ArrayList<>();
        list.add(new NoteDataClass("Заметки дня1", "Описание заметки дня 1", "17.01.2021"));
        list.add(new NoteDataClass("Заметки дня2", "Описание заметки дня 2", "16.01.2021"));
        list.add(new NoteDataClass("Заметки дня3", "Описание заметки дня 3", "15.01.2021"));
        initView();


        isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLand) {
            FragmentTransaction fragmentTr = getSupportFragmentManager().beginTransaction();
            NotesListFragment fragment1 = NotesListFragment.newInstance();
            DetaileFragment fragment2 = DetaileFragment.newInstance(null, null, null, 0);
            findViewById(R.id.Fr_container2).setVisibility(View.VISIBLE);
            fragmentTr.replace(R.id.Fr_container, fragment1);
            fragmentTr.replace(R.id.Fr_container2, fragment2);
            fragmentTr.commit();

        } else {
            FragmentTransaction fragmentTr = getSupportFragmentManager().beginTransaction();
            NotesListFragment fragment1 = NotesListFragment.newInstance();
            fragmentTr.replace(R.id.Fr_container, fragment1);
            fragmentTr.commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.Settings_Lable);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals(getResources().getString(R.string.Settings_Lable))) {
            if (isLand) {
                FragmentTransaction fragmentTr = getSupportFragmentManager().beginTransaction();
                SettingsFragment fragment1 = SettingsFragment.newInstance();
                fragmentTr.addToBackStack("");
                findViewById(R.id.Fr_container2).setVisibility(View.GONE);
                fragmentTr.replace(R.id.Fr_container, fragment1);
                fragmentTr.commit();
            } else {
                FragmentTransaction fragmentTr = getSupportFragmentManager().beginTransaction();
                SettingsFragment fragment1 = SettingsFragment.newInstance();
                fragmentTr.addToBackStack("");
                fragmentTr.replace(R.id.Fr_container, fragment1);
                fragmentTr.commit();
            }
        }
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();


        if (navigateFragment(id)) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
//        initButtonMain();
//        initButtonFavorite();
//        initButtonSettings();
//        initButtonBack();
    }

    // регистрация drawer
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (navigateFragment(id)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }


    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
//                addFragment(new SettingsFragment());
                Toast.makeText(this, "SettingsClick", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_main:
//                addFragment(new MainFragment());
                Toast.makeText(this, "MainClick", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite:
//                addFragment(new FavoriteFragment());
                Toast.makeText(this, "FavoriteClick", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }


}