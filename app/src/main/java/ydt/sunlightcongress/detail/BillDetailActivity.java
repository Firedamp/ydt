package ydt.sunlightcongress.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
    }

}
