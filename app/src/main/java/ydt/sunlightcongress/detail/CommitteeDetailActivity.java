package ydt.sunlightcongress.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Committee;
import ydt.sunlightcongress.data.model.Legislator;

/**
 * Created by Caodongyao on 2016/11/25.
 */

public class CommitteeDetailActivity extends AppCompatActivity{
    private Committee committee;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_committee);

        committee = JSON.parseObject(getIntent().getStringExtra("data"), Committee.class);
        if(committee == null)
            finish();

        initToolbar();

        initView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail_menu, menu);
        menuItem = menu.findItem(R.id.detail_menu_like);
        updateMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(DataSource.getInstance().setFacoriteCommittee(committee.committee_id)){
            updateMenu();
        }
        else
            Toast.makeText(this, "Failed !!!!! ", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void updateMenu(){
        if(DataSource.getInstance().isFavoriteCommittee(committee.committee_id))
            menuItem.setIcon(R.drawable.ic_star_full);
        else
            menuItem.setIcon(R.drawable.ic_star_empty);
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Committee Info");
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initView(){

        ((TextView)findViewById(R.id.detail_committee_item_id).findViewById(R.id.detail_item_key)).setText("Committee ID: ");
        ((TextView)findViewById(R.id.detail_committee_item_id).findViewById(R.id.detail_item_value)).setText(committee.committee_id);

        ((TextView)findViewById(R.id.detail_committee_item_name).findViewById(R.id.detail_item_key)).setText("Name: ");
        ((TextView)findViewById(R.id.detail_committee_item_name).findViewById(R.id.detail_item_value)).setText(committee.name);

        ((TextView)findViewById(R.id.detail_committee_item_chamber).findViewById(R.id.detail_item_key)).setText("Chamber: ");
        ((TextView)findViewById(R.id.detail_committee_item_chamber).findViewById(R.id.detail_item_value)).setText(committee.chamber);

        ((TextView)findViewById(R.id.detail_committee_item_parent).findViewById(R.id.detail_item_key)).setText("Parent Committee: ");
        ((TextView)findViewById(R.id.detail_committee_item_parent).findViewById(R.id.detail_item_value)).setText(committee.parent_committee_id);

        ((TextView)findViewById(R.id.detail_committee_item_contract).findViewById(R.id.detail_item_key)).setText("Contact: ");
        ((TextView)findViewById(R.id.detail_committee_item_contract).findViewById(R.id.detail_item_value)).setText(committee.phone);

        ((TextView)findViewById(R.id.detail_committee_item_office).findViewById(R.id.detail_item_key)).setText("Office: ");
        ((TextView)findViewById(R.id.detail_committee_item_office).findViewById(R.id.detail_item_value)).setText(committee.office);
    }

}
