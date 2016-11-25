package ydt.sunlightcongress.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.data.model.Committee;
import ydt.sunlightcongress.data.model.Legislator;

/**
 * Created by Caodongyao on 2016/11/25.
 */

public class CommitteeDetailActivity extends AppCompatActivity{
    private Committee committee;

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
        //TODO
        ((TextView)findViewById(R.id.detail_committee_item_id).findViewById(R.id.detail_item_key)).setText("Committee ID: ");
        ((TextView)findViewById(R.id.detail_committee_item_id).findViewById(R.id.detail_item_value)).setText(committee.committee_id);

        ((TextView)findViewById(R.id.detail_committee_item_name).findViewById(R.id.detail_item_key)).setText("Name: ");
        ((TextView)findViewById(R.id.detail_committee_item_name).findViewById(R.id.detail_item_value)).setText(committee.name);

        ((TextView)findViewById(R.id.detail_committee_item_chamber).findViewById(R.id.detail_item_key)).setText("Chamber: ");
        ((TextView)findViewById(R.id.detail_committee_item_chamber).findViewById(R.id.detail_item_value)).setText(committee.chamber);

        ((TextView)findViewById(R.id.detail_committee_item_parent).findViewById(R.id.detail_item_key)).setText("Parent Committee: ");
        ((TextView)findViewById(R.id.detail_committee_item_parent).findViewById(R.id.detail_item_value)).setText(committee.parent_committee_id);

//        ((TextView)findViewById(R.id.detail_committee_item_contract).findViewById(R.id.detail_item_key)).setText("Contact: ");
//        ((TextView)findViewById(R.id.detail_committee_item_contract).findViewById(R.id.detail_item_value)).setText(committee.contact);

//        ((TextView)findViewById(R.id.detail_committee_item_office).findViewById(R.id.detail_item_key)).setText("Office: ");
//        ((TextView)findViewById(R.id.detail_committee_item_office).findViewById(R.id.detail_item_value)).setText(committee.office);
    }

}
