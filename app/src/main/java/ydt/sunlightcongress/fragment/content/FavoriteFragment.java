package ydt.sunlightcongress.fragment.content;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ydt.sunlightcongress.adapter.BaseListAdapter;
import ydt.sunlightcongress.adapter.BillListAdapter;
import ydt.sunlightcongress.adapter.CommitteeListAdapter;
import ydt.sunlightcongress.adapter.LegistorListAdapter;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Legislator;
import ydt.sunlightcongress.fragment.BaseTabListFragment;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class FavoriteFragment extends BaseTabListFragment {
    private BillListAdapter mBillAdapter;
    private CommitteeListAdapter mCommitteeAdapter;
    private LegistorListAdapter mLegistorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBillAdapter = new BillListAdapter();
        mCommitteeAdapter = new CommitteeListAdapter();
        mLegistorAdapter = new LegistorListAdapter();
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
        return new String[]{DataSource.ACTION_LEGISTOR_HAS_UPDATED, DataSource.ACTION_COMMITTEE_HAS_UPDATED, DataSource.ACTION_BILL_HAS_UPDATED};
    }

    @Override
    public void updateData() {
        getListView().smoothScrollToPosition(0);
        switch (getCurrentPostion()){
            default:
            case 0:
                mLegistorAdapter.update(getDataSource().getFavoriteLegistor());
                getListView().setAdapter(mLegistorAdapter);
                break;
            case 1:
                mBillAdapter.update(getDataSource().getFavoriteBills());
                getListView().setAdapter(mBillAdapter);
                break;
            case 2:
                mCommitteeAdapter.update(getDataSource().getFavoriteCommittee());
                getListView().setAdapter(mCommitteeAdapter);
                break;
        }
    }
}
