package com.example.hiloldictionary.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiloldictionary.R;
import com.example.hiloldictionary.repository.storage.db.Definition;
import com.example.hiloldictionary.repository.storage.db.DefinitionDao;
import com.example.hiloldictionary.repository.storage.pref.IPreference;
import com.example.hiloldictionary.ui.item.ItemActivity;
import com.example.hiloldictionary.ui.main.adapter.EndlessRecyclerOnScrollListener;
import com.example.hiloldictionary.ui.main.adapter.IAction;
import com.example.hiloldictionary.ui.main.adapter.ItemClickListener;
import com.example.hiloldictionary.ui.main.adapter.WordAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ItemClickListener,
        IAction {
    @Inject
    DefinitionDao dao;

    @Inject
    WordAdapter adapter;

    @Inject
    IPreference preference;

    private RecyclerView rv;
    private CompositeDisposable cd;
    private int offset = 0;
    private boolean isPaginationEnabled = true;
    private LinearLayoutManager lm;
    private int lastPos;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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

        setUpRv();
        loadData();
    }

    private void setUpRv() {
        rv = findViewById(R.id.rv);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(false);
//        adapter = new WordAdapter(this, new ArrayList<>(), this);
        rv.setAdapter(adapter);
        EndlessRecyclerOnScrollListener listener = new EndlessRecyclerOnScrollListener(lm, this);
        rv.addOnScrollListener(listener);
    }

    private void loadData() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.getItem(0);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onSearch(newText);
                return true;
            }
        });

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                isPaginationEnabled = false;
                Timber.d("onMenuItemActionExpand");
                lastPos = lm.findFirstCompletelyVisibleItemPosition();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Timber.d("onMenuItemActionCollapse");
                adapter.searchClosed();
                lm.scrollToPosition(lastPos);
                isPaginationEnabled = true;//todo scroll position not restored correctly
                return true;
            }
        });
        return true;
    }

    private void onSearch(String newText) {
        Timber.d("onSearch %s", newText);
        if (newText.isEmpty()) return;
        Disposable disposable = dao.search(newText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(adapter::onSearch)
                .subscribe();
        cd.add(disposable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

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
        if (isPaginationEnabled)
            loadData();
    }
}
