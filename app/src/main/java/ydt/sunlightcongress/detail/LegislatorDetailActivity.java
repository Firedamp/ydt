package ydt.sunlightcongress.detail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.adapter.LegislatorListAdapter;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Legislator;

/**
 * Created by Caodongyao on 2016/11/24.
 */

public class LegislatorDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Legislator legislator;
    private MenuItem menuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail_menu, menu);
        menuItem = menu.findItem(R.id.detail_menu_like);
        updateMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(DataSource.getInstance().setFacoriteLegislator(legislator.bioguide_id)){
            updateMenu();
        }
        else
            Toast.makeText(this, "Failed !!!!! ", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.detail_legislator_icon_twitter){
            if(TextUtils.isEmpty(legislator.twitter_id)) {
                Toast.makeText(this, "no twwiter", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twwiter.com/"+legislator.twitter_id)));
        }
        else if(v.getId() == R.id.detail_legislator_icon_facebook){
            if(TextUtils.isEmpty(legislator.facebook_id)) {
                Toast.makeText(this, "no facebook", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+legislator.facebook_id)));
        }
        else if(v.getId() == R.id.detail_legislator_icon_earth){
            if(TextUtils.isEmpty(legislator.twitter_id)) {
                Toast.makeText(this, "no website", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(legislator.website)));
        }
    }

    private void updateMenu(){
        if(DataSource.getInstance().isFavoriteLegislator(legislator.bioguide_id))
            menuItem.setIcon(R.drawable.ic_star_full);
        else
            menuItem.setIcon(R.drawable.ic_star_empty);
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

        findViewById(R.id.detail_legislator_icon_twitter).setOnClickListener(this);
        findViewById(R.id.detail_legislator_icon_facebook).setOnClickListener(this);
        findViewById(R.id.detail_legislator_icon_earth).setOnClickListener(this);

        int res = R.drawable.ic_party_i;
        if("r".equalsIgnoreCase(legislator.party))
            res = R.drawable.ic_party_r;
        else if("d".equalsIgnoreCase(legislator.party))
            res = R.drawable.ic_party_d;
        ((ImageView)findViewById(R.id.detail_legislator_icon_party)).setImageResource(res);
        ((TextView)findViewById(R.id.detail_legislator_text_party)).setText(legislator.party);

        Date startTerm;
        Date stopTerm;
        Date birthday;
        try {
            startTerm = new SimpleDateFormat("YYYY-MM-DD").parse(legislator.term_start);
            stopTerm = new SimpleDateFormat("YYYY-MM-DD").parse(legislator.term_end);
            birthday = new SimpleDateFormat("YYYY-MM-DD").parse(legislator.birthday);
        }catch (Exception e){
            startTerm = null;
            stopTerm = null;
            birthday = null;
        }

        if(startTerm != null && stopTerm != null){
            long current = System.currentTimeMillis();
            int term = (int) (100 * (current-startTerm.getTime()) / (stopTerm.getTime()-startTerm.getTime()));
            ((ProgressBar)findViewById(R.id.detail_legislator_item_term_progress)).setProgress(term);
            ((TextView)findViewById(R.id.detail_legislator_item_term_text_progress)).setText(term+"%");
        }

        ((TextView)findViewById(R.id.detail_legislator_item_name).findViewById(R.id.detail_item_key)).setText("Name: ");
        ((TextView)findViewById(R.id.detail_legislator_item_name).findViewById(R.id.detail_item_value)).setText(legislator.first_name + " " + legislator.last_name);

        ((TextView)findViewById(R.id.detail_legislator_item_email).findViewById(R.id.detail_item_key)).setText("Email: ");
        ((TextView)findViewById(R.id.detail_legislator_item_email).findViewById(R.id.detail_item_value)).setText(legislator.oc_email);

        ((TextView)findViewById(R.id.detail_legislator_item_chamber).findViewById(R.id.detail_item_key)).setText("Chamber: ");
        ((TextView)findViewById(R.id.detail_legislator_item_chamber).findViewById(R.id.detail_item_value)).setText(legislator.chamber);

        ((TextView)findViewById(R.id.detail_legislator_item_contact).findViewById(R.id.detail_item_key)).setText("Contact: ");
        ((TextView)findViewById(R.id.detail_legislator_item_contact).findViewById(R.id.detail_item_value)).setText(legislator.phone);

        ((TextView)findViewById(R.id.detail_legislator_item_start_term).findViewById(R.id.detail_item_key)).setText("Start Term: ");
        ((TextView)findViewById(R.id.detail_legislator_item_start_term).findViewById(R.id.detail_item_value)).setText(startTerm == null ? "" : new SimpleDateFormat("MMM dd, YYYY", Locale.ENGLISH).format(startTerm));

        ((TextView)findViewById(R.id.detail_legislator_item_end_term).findViewById(R.id.detail_item_key)).setText("End Term: ");
        ((TextView)findViewById(R.id.detail_legislator_item_end_term).findViewById(R.id.detail_item_value)).setText(stopTerm == null ? "" : new SimpleDateFormat("MMM dd, YYYY", Locale.ENGLISH).format(stopTerm));

        ((TextView)findViewById(R.id.detail_legislator_item_office).findViewById(R.id.detail_item_key)).setText("Office: ");
        ((TextView)findViewById(R.id.detail_legislator_item_office).findViewById(R.id.detail_item_value)).setText(legislator.office);

        ((TextView)findViewById(R.id.detail_legislator_item_state).findViewById(R.id.detail_item_key)).setText("State: ");
        ((TextView)findViewById(R.id.detail_legislator_item_state).findViewById(R.id.detail_item_value)).setText(legislator.state_name);

        ((TextView)findViewById(R.id.detail_legislator_item_fax).findViewById(R.id.detail_item_key)).setText("Fax: ");
        ((TextView)findViewById(R.id.detail_legislator_item_fax).findViewById(R.id.detail_item_value)).setText(legislator.fax);

        ((TextView)findViewById(R.id.detail_legislator_item_birthday).findViewById(R.id.detail_item_key)).setText("Birthday: ");
        ((TextView)findViewById(R.id.detail_legislator_item_birthday).findViewById(R.id.detail_item_value)).setText(birthday == null ? "" : new SimpleDateFormat("MMM dd, YYYY", Locale.ENGLISH).format(birthday));
    }
}
