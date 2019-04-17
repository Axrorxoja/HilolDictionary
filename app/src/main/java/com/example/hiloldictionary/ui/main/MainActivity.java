package com.example.hiloldictionary.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hiloldictionary.R;
import com.example.hiloldictionary.repository.storage.db.Definition;
import com.example.hiloldictionary.repository.storage.db.DefinitionDao;
import com.example.hiloldictionary.repository.storage.db.DefinitionService;
import com.example.hiloldictionary.ui.item.ItemActivity;
import com.example.hiloldictionary.ui.main.adapter.EndlessRecyclerOnScrollListener;
import com.example.hiloldictionary.ui.main.adapter.IAction;
import com.example.hiloldictionary.ui.main.adapter.ItemClickListener;
import com.example.hiloldictionary.ui.main.adapter.WordAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ItemClickListener,
        IAction {
    private RecyclerView rv;
    private CompositeDisposable cd;
    private WordAdapter adapter;
    private int offset = 0;
    private SearchView sv;
    private DefinitionDao definitionDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        search1();
    }

    private void initView() {
        cd = new CompositeDisposable();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rv = findViewById(R.id.rv);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(false);
        adapter = new WordAdapter(this, new ArrayList<>(), this);
        rv.setAdapter(adapter);
        EndlessRecyclerOnScrollListener listener = new EndlessRecyclerOnScrollListener(lm, this);
        rv.addOnScrollListener(listener);
        loadData();
    }



    private void loadData() {
        DefinitionDao dao = DefinitionService.loadDAO(this);
        Disposable d = dao.loadDefinitionsByPage(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateAdapter);
        cd.add(d);
        offset += 20;
    }

    private void updateAdapter(List<Definition> it) {
          adapter.updateData(it);
    }
    private void updateAdapter1(List<Definition> it) {
        adapter.onSearchUpdate(it);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cd != null) {
            cd.clear();
        }
    }

    @Override
    public void onItemClick(Definition definition) {
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("item", definition);
        startActivity(intent);
    }

    @Override
    public void onAction() {
        loadData();
    }

    public void search1() {
        sv = findViewById(R.id.sv);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                Timber.d("" + newText);
                return true;
            }

        });


    }


    public void callSearch(String query) {
        definitionDao=DefinitionService.loadDAO(this);
        Disposable a = definitionDao.search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateAdapter1);
        cd.add(a);
    }
}
