package ydt.sunlightcongress.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.adapter.LegislatorListAdapter;
import ydt.sunlightcongress.data.model.Legislator;

/**
 * Created by Caodongyao on 2016/11/24.
 */

public class LegislatorDetailActivity extends AppCompatActivity{
    private Legislator legislator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_legislator);

        legislator = JSON.parseObject(getIntent().getStringExtra("data"), Legislator.class);
        if(legislator == null)
            finish();

        initToolbar();

        initView();

    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Legislator Info");
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView(){
        ImageView picImageView = (ImageView)findViewById(R.id.detail_legislator_image);
        Picasso.with(this)
                .load(LegislatorListAdapter.URL_PIC_PREFIX+legislator.bioguide_id+".jpg")
                .resize(800, 1000)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(picImageView);

        ((TextView)findViewById(R.id.detail_legislator_text_party)).setText(legislator.party);

        ((TextView)findViewById(R.id.detail_legislator_item_name).findViewById(R.id.detail_item_key)).setText("Name : ");
        ((TextView)findViewById(R.id.detail_legislator_item_name).findViewById(R.id.detail_item_value)).setText(legislator.first_name + " " + legislator.last_name);

        ((TextView)findViewById(R.id.detail_legislator_item_email).findViewById(R.id.detail_item_key)).setText("Email : ");
        ((TextView)findViewById(R.id.detail_legislator_item_email).findViewById(R.id.detail_item_value)).setText(legislator.oc_email);

        ((TextView)findViewById(R.id.detail_legislator_item_chamber).findViewById(R.id.detail_item_key)).setText("Chamber : ");
        ((TextView)findViewById(R.id.detail_legislator_item_chamber).findViewById(R.id.detail_item_value)).setText(legislator.chamber);

        //TODO
    }
}