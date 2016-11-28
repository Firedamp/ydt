package ydt.sunlightcongress.fragment.content;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;

import java.util.List;

import ydt.sunlightcongress.adapter.BaseListAdapter;
import ydt.sunlightcongress.adapter.LegislatorListAdapter;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Legislator;
import ydt.sunlightcongress.detail.LegislatorDetailActivity;
import ydt.sunlightcongress.fragment.BaseTabListFragment;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class LegislatorFragment extends BaseTabListFragment<Legislator> {
    @Override
    protected BaseListAdapter<Legislator> createAdapter() {
        return new LegislatorListAdapter();
    }

    @Override
    protected String[] getTitles() {
        return new String[]{"By States", "House", "Senate"};
    }

    @Override
    protected String[] getIntentFilterActions() {
        return new String[]{DataSource.ACTION_LEGISLATOR_HAS_UPDATED};
    }

    @Override
    protected List<Legislator> getData() {
        switch (getCurrentPostion()){
            default:
            case 0:
                return getDataSource().getLegislatorsByState();
            case 1:
                return getDataSource().getHouseLegislators();
            case 2:
                return getDataSource().getSenateLegislators();
        }
    }

    @Override
    protected String getItemIndex(Legislator legislator) {
        switch (getCurrentPostion()){
            default:
                return TextUtils.isEmpty(legislator.last_name) ? null : legislator.last_name.charAt(0)+"";
            case 0:
                return TextUtils.isEmpty(legislator.state_name) ? null : legislator.state_name.charAt(0)+"";
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), LegislatorDetailActivity.class);
        intent.putExtra("data", JSON.toJSONString(getListAdapter().getItem(position)));
        startActivity(intent);
    }
}
