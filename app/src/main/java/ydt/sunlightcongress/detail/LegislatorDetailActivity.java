package ydt.sunlightcongress.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.adapter.LegislatorListAdapter;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Legislator;

/**
 * Created by Caodongyao on 2016/11/24.
 */

public class LegislatorDetailActivity extends AppCompatActivity{
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

        ((TextView)findViewById(R.id.detail_legislator_text_party)).setText(legislator.party);

        ((TextView)findViewById(R.id.detail_legislator_item_name).findViewById(R.id.detail_item_key)).setText("Name: ");
        ((TextView)findViewById(R.id.detail_legislator_item_name).findViewById(R.id.detail_item_value)).setText(legislator.title + ". " +legislator.last_name + ", " + legislator.first_name);

        ((TextView)findViewById(R.id.detail_legislator_item_email).findViewById(R.id.detail_item_key)).setText("Email: ");
        ((TextView)findViewById(R.id.detail_legislator_item_email).findViewById(R.id.detail_item_value)).setText(legislator.oc_email);

        ((TextView)findViewById(R.id.detail_legislator_item_chamber).findViewById(R.id.detail_item_key)).setText("Chamber: ");
        ((TextView)findViewById(R.id.detail_legislator_item_chamber).findViewById(R.id.detail_item_value)).setText(legislator.chamber);

        ((TextView)findViewById(R.id.detail_legislator_item_chamber).findViewById(R.id.detail_item_key)).setText("Contact : ");
        ((TextView)findViewById(R.id.detail_legislator_item_chamber).findViewById(R.id.detail_item_value)).setText(legislator.phone);

        ((TextView)findViewById(R.id.detail_legislator_item_start_term).findViewById(R.id.detail_item_key)).setText("Start Term: ");
        ((TextView)findViewById(R.id.detail_legislator_item_start_term).findViewById(R.id.detail_item_value)).setText(legislator.term_start);

        ((TextView)findViewById(R.id.detail_legislator_item_end_term).findViewById(R.id.detail_item_key)).setText("End Term: ");
        ((TextView)findViewById(R.id.detail_legislator_item_end_term).findViewById(R.id.detail_item_value)).setText(legislator.term_end);

        ((TextView)findViewById(R.id.detail_legislator_item_term).findViewById(R.id.detail_item_key)).setText("Term: ");
        ((TextView)findViewById(R.id.detail_legislator_item_term).findViewById(R.id.detail_item_value)).setText("Need a progress bar! (now-start)/(end-start) * 100%");

        ((TextView)findViewById(R.id.detail_legislator_item_office).findViewById(R.id.detail_item_key)).setText("Office: ");
        ((TextView)findViewById(R.id.detail_legislator_item_office).findViewById(R.id.detail_item_value)).setText(legislator.office);

        ((TextView)findViewById(R.id.detail_legislator_item_state).findViewById(R.id.detail_item_key)).setText("State: ");
        ((TextView)findViewById(R.id.detail_legislator_item_state).findViewById(R.id.detail_item_value)).setText(legislator.state);

        ((TextView)findViewById(R.id.detail_legislator_item_fax).findViewById(R.id.detail_item_key)).setText("Fax: ");
        ((TextView)findViewById(R.id.detail_legislator_item_fax).findViewById(R.id.detail_item_value)).setText(legislator.fax);

        ((TextView)findViewById(R.id.detail_legislator_item_birthday).findViewById(R.id.detail_item_key)).setText("Birthday: ");
        ((TextView)findViewById(R.id.detail_legislator_item_birthday).findViewById(R.id.detail_item_value)).setText(legislator.birthday);
    }


}
