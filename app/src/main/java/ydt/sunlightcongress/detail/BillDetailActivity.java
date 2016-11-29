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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Bill;
import ydt.sunlightcongress.data.model.Committee;

/**
 * Created by Caodongyao on 2016/11/25.
 */

public class BillDetailActivity extends AppCompatActivity{
    private Bill bill;

    private MenuItem menuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail_menu, menu);
        menuItem = menu.findItem(R.id.detail_menu_like);
        updateMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(DataSource.getInstance().setFacoriteBill(bill.bill_id)){
            updateMenu();
        }
        else
            Toast.makeText(this, "Failed !!!!! ", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void updateMenu(){
        if(DataSource.getInstance().isFavoriteBill(bill.bill_id))
            menuItem.setIcon(R.drawable.ic_star_full);
        else
            menuItem.setIcon(R.drawable.ic_star_empty);
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
        Date introduceDate;
        try {
            introduceDate = new SimpleDateFormat("yyyy-MM-dd").parse(bill.introduced_on);
        }catch (Exception e){
            introduceDate = null;
        }

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
        ((TextView)findViewById(R.id.detail_bill_item_status).findViewById(R.id.detail_item_value)).setText(bill.history.active ? "active" : "inactive");

        ((TextView)findViewById(R.id.detail_bill_item_date).findViewById(R.id.detail_item_key)).setText("Introduced On: ");
        ((TextView)findViewById(R.id.detail_bill_item_date).findViewById(R.id.detail_item_value)).setText(introduceDate == null ? "" : new SimpleDateFormat("MMM dd, YYYY", Locale.ENGLISH).format(introduceDate));

        ((TextView)findViewById(R.id.detail_bill_item_url).findViewById(R.id.detail_item_key)).setText("Congress URL: ");
        ((TextView)findViewById(R.id.detail_bill_item_url).findViewById(R.id.detail_item_value)).setText(bill.urls == null ? "" : bill.urls.get("congress"));

        ((TextView)findViewById(R.id.detail_bill_item_version).findViewById(R.id.detail_item_key)).setText("Version Status: ");
        ((TextView)findViewById(R.id.detail_bill_item_version).findViewById(R.id.detail_item_value)).setText(bill.last_version == null ? "" : bill.last_version.version_name);

        ((TextView)findViewById(R.id.detail_bill_item_bill).findViewById(R.id.detail_item_key)).setText("Bill URL: ");
        ((TextView)findViewById(R.id.detail_bill_item_bill).findViewById(R.id.detail_item_value)).setText(bill.urls == null ? "" : bill.urls.get("html"));



    }

}
