package ydt.sunlightcongress.fragment.content;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;

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
    public BaseListAdapter<Legislator> createAdapter() {
        return new LegislatorListAdapter();
    }

    @Override
    public String[] getTitles() {
        return new String[]{"By States", "House", "Senate"};
    }

    @Override
    public String[] getIntentFilterActions() {
        return new String[]{DataSource.ACTION_LEGISLATOR_HAS_UPDATED};
    }

    @Override
    public void updateData(){
        getListView().smoothScrollToPosition(0);
        switch (getCurrentPostion()){
            default:
            case 0:
                getListAdapter().update(getDataSource().getLegislatorsByState());
                break;
            case 1:
                getListAdapter().update(getDataSource().getLegislatorsByHouse());
                break;
            case 2:
                getListAdapter().update(getDataSource().getLegislatorsBySenate());
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), LegislatorDetailActivity.class);
        intent.putExtra("data", JSON.toJSONString(getListAdapter().getItem(position)));
        startActivity(intent);
    }
}
