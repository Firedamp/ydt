package ydt.sunlightcongress.fragment.content;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;

import java.util.List;

import ydt.sunlightcongress.adapter.BaseListAdapter;
import ydt.sunlightcongress.adapter.CommitteeListAdapter;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Committee;
import ydt.sunlightcongress.detail.CommitteeDetailActivity;
import ydt.sunlightcongress.fragment.BaseTabListFragment;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class CommitteeFragment extends BaseTabListFragment<Committee> {
    @Override
    public BaseListAdapter<Committee> createAdapter() {
        return new CommitteeListAdapter();
    }

    @Override
    public String[] getTitles() {
        return new String[]{"House", "Senate", "Joint"};
    }

    @Override
    public String[] getIntentFilterActions() {
        return new String[]{DataSource.ACTION_COMMITTEE_HAS_UPDATED};
    }

    @Override
    protected List<Committee> getData() {
        switch (getCurrentPostion()){
            default:
            case 0:
                return getDataSource().getHouseCommittees();
            case 1:
                return getDataSource().getSenateCommittees();
            case 2:
                return getDataSource().getJointCommittees();
        }
    }

    @Override
    protected String getItemIndex(Committee committee) {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), CommitteeDetailActivity.class);
        intent.putExtra("data", JSON.toJSONString(getListAdapter().getItem(position)));
        startActivity(intent);
    }
}
