package ydt.sunlightcongress.fragment.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;

import java.util.List;

import ydt.sunlightcongress.adapter.BaseListAdapter;
import ydt.sunlightcongress.adapter.BillListAdapter;
import ydt.sunlightcongress.adapter.CommitteeListAdapter;
import ydt.sunlightcongress.adapter.LegislatorListAdapter;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Legislator;
import ydt.sunlightcongress.data.model.Model;
import ydt.sunlightcongress.detail.BillDetailActivity;
import ydt.sunlightcongress.detail.CommitteeDetailActivity;
import ydt.sunlightcongress.detail.LegislatorDetailActivity;
import ydt.sunlightcongress.fragment.BaseTabListFragment;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class FavoriteFragment extends BaseTabListFragment {
    private BillListAdapter mBillAdapter;
    private CommitteeListAdapter mCommitteeAdapter;
    private LegislatorListAdapter mLegislatorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBillAdapter = new BillListAdapter();
        mCommitteeAdapter = new CommitteeListAdapter();
        mLegislatorAdapter = new LegislatorListAdapter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public BaseListAdapter createAdapter() {
        return null;
    }

    @Override
    public String[] getTitles() {
        return new String[]{"Legislators", "Bills", "Committees"};
    }

    @Override
    public String[] getIntentFilterActions() {
        return new String[]{DataSource.ACTION_LEGISLATOR_HAS_UPDATED, DataSource.ACTION_COMMITTEE_HAS_UPDATED, DataSource.ACTION_BILL_HAS_UPDATED};
    }

    @Override
    protected List getData() {
        switch (getCurrentPostion()){
            default:
            case 0:
                mLegislatorAdapter.update(getDataSource().getFavoriteLegislator());
                getListView().setAdapter(mLegislatorAdapter);
                return getDataSource().getFavoriteLegislator();
            case 1:
                mBillAdapter.update(getDataSource().getFavoriteBills());
                getListView().setAdapter(mBillAdapter);
                return getDataSource().getFavoriteBills();
            case 2:
                mCommitteeAdapter.update(getDataSource().getFavoriteCommittee());
                getListView().setAdapter(mCommitteeAdapter);
                return getDataSource().getFavoriteCommittee();
        }
    }

    @Override
    protected String getItemIndex(Model model) {
        switch (getCurrentPostion()){
            default:
                return null;
            case 0:
                Legislator legislator = (Legislator)model;
                return TextUtils.isEmpty(legislator.last_name) ? null :
                        legislator.last_name.charAt(0)+"";
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class<? extends Activity> clazz;
        String data;
        switch (getCurrentPostion()){
            default:
            case 0:
                clazz = LegislatorDetailActivity.class;
                data = JSON.toJSONString(mLegislatorAdapter.getItem(position));
                break;
            case 1:
                clazz = BillDetailActivity.class;
                data = JSON.toJSONString(mBillAdapter.getItem(position));
                break;
            case 2:
                clazz = CommitteeDetailActivity.class;
                data = JSON.toJSONString(mCommitteeAdapter.getItem(position));
                break;
        }
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra("data", data);
        startActivity(intent);
    }
}
