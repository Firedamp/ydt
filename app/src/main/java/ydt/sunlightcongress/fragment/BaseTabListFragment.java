package ydt.sunlightcongress.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.adapter.BaseListAdapter;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.view.IndicatorView;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public abstract class BaseTabListFragment<T> extends Fragment implements IndicatorView.OnIndexSelectListener, AdapterView.OnItemClickListener{
    private BaseListAdapter<T> mAdapter;
    private DataSource mDataSource;

    private IndicatorView mIndicator;
    private ListView mListView;

    private int mCurrentPosition = -1;
    private BroadcastReceiver mReceiver;

    public Fragment setDataSource(DataSource dataSource){
        this.mDataSource = dataSource;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base, container, false);

        mListView = (ListView)view.findViewById(R.id.fragment_base_list);
        mListView.setOnItemClickListener(this);
        mIndicator = (IndicatorView)view.findViewById(R.id.fragment_base_indicator);

        mAdapter = createAdapter();
        if(mAdapter != null)
            mListView.setAdapter(mAdapter);

        mIndicator.setOnIndexSelectListener(this);
        mIndicator.init(getTitles());

        mReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                updateData();
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        for(String action : getIntentFilterActions())
            intentFilter.addAction(action);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, intentFilter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
    }

    @Override
    public void onIndexSelected(int position) {
        mCurrentPosition = position;
        updateData();
    }

    public int getCurrentPostion(){
        return mCurrentPosition;
    }

    public DataSource getDataSource(){
        return mDataSource;
    }

    public BaseListAdapter<T> getListAdapter(){
        return mAdapter;
    }

    public ListView getListView(){
        return mListView;
    }

    public abstract BaseListAdapter<T> createAdapter();
    public abstract String[] getTitles();
    public abstract String[] getIntentFilterActions();
    public abstract void updateData();
}
