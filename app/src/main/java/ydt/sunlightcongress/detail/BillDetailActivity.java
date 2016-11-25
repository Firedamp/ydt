package ydt.sunlightcongress.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.data.model.Bill;
import ydt.sunlightcongress.data.model.Committee;

/**
 * Created by Caodongyao on 2016/11/25.
 */

public class BillDetailActivity extends AppCompatActivity{
    private Bill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);

        bill = JSON.parseObject(getIntent().getStringExtra("data"), Bill.class);
        if(bill == null)
            finish();

        initToolbar();

        initView();

    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Bill Info");
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
        ((TextView)findViewById(R.id.detail_bill_item_id).findViewById(R.id.detail_item_key)).setText("Bill ID: ");
        ((TextView)findViewById(R.id.detail_bill_item_id).findViewById(R.id.detail_item_value)).setText(bill.bill_id);

        ((TextView)findViewById(R.id.detail_bill_item_title).findViewById(R.id.detail_item_key)).setText("Title: ");
        ((TextView)findViewById(R.id.detail_bill_item_title).findViewById(R.id.detail_item_value)).setText(bill.short_title);

        ((TextView)findViewById(R.id.detail_bill_item_type).findViewById(R.id.detail_item_key)).setText("Bill Type: ");
        ((TextView)findViewById(R.id.detail_bill_item_type).findViewById(R.id.detail_item_value)).setText(bill.bill_type);

        ((TextView)findViewById(R.id.detail_bill_item_sponsor).findViewById(R.id.detail_item_key)).setText("Sponsor: ");
        ((TextView)findViewById(R.id.detail_bill_item_sponsor).findViewById(R.id.detail_item_value)).setText(bill.sponsor.title+", "+bill.sponsor.last_name+", "+bill.sponsor.first_name);

        ((TextView)findViewById(R.id.detail_bill_item_chamber).findViewById(R.id.detail_item_key)).setText("Chamber: ");
        ((TextView)findViewById(R.id.detail_bill_item_chamber).findViewById(R.id.detail_item_value)).setText(bill.chamber);

        ((TextView)findViewById(R.id.detail_bill_item_status).findViewById(R.id.detail_item_key)).setText("Status: ");
        ((TextView)findViewById(R.id.detail_bill_item_status).findViewById(R.id.detail_item_value)).setText(bill.history.active);

        ((TextView)findViewById(R.id.detail_bill_item_date).findViewById(R.id.detail_item_key)).setText("Introduced On: ");
        ((TextView)findViewById(R.id.detail_bill_item_date).findViewById(R.id.detail_item_value)).setText(bill.introduced_on);

        ((TextView)findViewById(R.id.detail_bill_item_url).findViewById(R.id.detail_item_key)).setText("Congress URL: ");
        ((TextView)findViewById(R.id.detail_bill_item_url).findViewById(R.id.detail_item_value)).setText(bill.last_version.urls.get("html"));

        ((TextView)findViewById(R.id.detail_bill_item_version).findViewById(R.id.detail_item_key)).setText("Version Status: ");
        ((TextView)findViewById(R.id.detail_bill_item_version).findViewById(R.id.detail_item_value)).setText(bill.last_version.version_name);

        ((TextView)findViewById(R.id.detail_bill_item_bill).findViewById(R.id.detail_item_key)).setText("Bill URL: ");
        ((TextView)findViewById(R.id.detail_bill_item_bill).findViewById(R.id.detail_item_value)).setText(bill.last_version.urls.get("pdf"));



    }

}
