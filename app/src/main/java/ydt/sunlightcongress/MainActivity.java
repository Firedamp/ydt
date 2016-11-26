package ydt.sunlightcongress;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.fragment.FragmentController;
import ydt.sunlightcongress.fragment.content.BillFragment;
import ydt.sunlightcongress.fragment.content.CommitteeFragment;
import ydt.sunlightcongress.fragment.content.FavoriteFragment;
import ydt.sunlightcongress.fragment.content.LegislatorFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentController.FragmentAdapter, DataSource.OnPendingDataListener{

    private DrawerLayout mDrawerLayout;
    private ProgressDialog mWaitingDialog;

    private FragmentController mFragmentController;

    public enum Page {
        LEGISLATORS,
        FAVORITE,
        COMMITTEES,
        BILLS
    }

    @Override
    public Fragment createFragment(String tag) {
        if (Page.LEGISLATORS.name().equals(tag)){
            return new LegislatorFragment();
        }
        else if (Page.FAVORITE.name().equals(tag))
            return new FavoriteFragment();
        else if (Page.COMMITTEES.name().equals(tag))
            return new CommitteeFragment();
        else if (Page.BILLS.name().equals(tag))
            return new BillFragment();
        return null;
    }

    @Override
    public void onFetchingData() {
        if(mWaitingDialog == null)
            mWaitingDialog = ProgressDialog.show(this, "Waiting", "Fetching data from the Internet ...");
        mWaitingDialog.show();
    }

    @Override
    public void onDataFetched(boolean succeed) {
        if(mWaitingDialog != null)
            mWaitingDialog.dismiss();
        if(!succeed)
            Toast.makeText(this, "Failed to fetch data from the Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_legislators);

        DataSource.getInstance().setOnPendingDataListener(this);

        mFragmentController = new FragmentController(getFragmentManager(), R.id.main_container, this);
        mFragmentController.go(Page.LEGISLATORS.name());
        setTitle("legislators");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
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
            mFragmentController.go(Page.LEGISLATORS.name());
            setTitle("legislators");
        } else if (id == R.id.nav_bills) {
            mFragmentController.go(Page.BILLS.name());
            setTitle("bills");
        } else if (id == R.id.nav_committees) {
            mFragmentController.go(Page.COMMITTEES.name());
            setTitle("committees");
        } else if (id == R.id.nav_favorites) {
            mFragmentController.go(Page.FAVORITE.name());
            setTitle("favorites");
        } else if (id == R.id.nav_aboutme) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
