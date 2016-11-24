package ydt.sunlightcongress.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.common.FragmentController.Page;

import ydt.sunlightcongress.common.FragmentController;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentController mFragmentController;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_legislators);

        mFragmentController = new FragmentController(getFragmentManager(), R.id.main_container);
        mFragmentController.go(Page.LEGISLATORS);
        setTitle("legislators");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_legislators) {
            mFragmentController.go(Page.LEGISLATORS);
            setTitle("legislators");
        }
        else if (id == R.id.nav_bills) {
            mFragmentController.go(Page.BILLS);
            setTitle("bills");
        }
        else if (id == R.id.nav_committees) {
            mFragmentController.go(Page.COMMITTEES);
            setTitle("committees");
        }
        else if (id == R.id.nav_favorites) {
            mFragmentController.go(Page.FAVORITE);
            setTitle("favorites");
        }
        else if (id == R.id.nav_aboutme) {
            //放到这里
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
