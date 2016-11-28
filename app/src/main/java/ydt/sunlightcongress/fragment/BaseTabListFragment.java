package ydt.sunlightcongress.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.adapter.BaseListAdapter;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Model;
import ydt.sunlightcongress.view.IndicatorView;
import ydt.sunlightcongress.view.SideBar;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public abstract class BaseTabListFragment<T extends Model> extends Fragment implements IndicatorView.OnIndexSelectListener, AdapterView.OnItemClickListener, SideBar.OnIndexChangeListener{
    private BaseListAdapter<T> mAdapter;

    private IndicatorView mIndicator;
    private ListView mListView;
    private SideBar mSidebar;
    private TextView mIndexText;
    private Map<String, Integer> mIndexMap;

    private int mCurrentPosition = -1;
    private BroadcastReceiver mReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base, container, false);
        mIndexMap = new LinkedHashMap<String, Integer>();

        mListView = (ListView)view.findViewById(R.id.fragment_base_list);
        mListView.setOnItemClickListener(this);
        mIndicator = (IndicatorView)view.findViewById(R.id.fragment_base_indicator);
        mIndexText = (TextView)view.findViewById(R.id.fragment_base_index);
        mSidebar = (SideBar)view.findViewById(R.id.fragment_base_sidebar);
        mSidebar.setOnIndexChangeListener(this);
        mSidebar.setVisibility(View.GONE);
        mIndexText.setVisibility(View.GONE);

        mAdapter = createAdapter();
        if(mAdapter != null)
            mListView.setAdapter(mAdapter);

        mIndicator.setOnIndexSelectListener(this);
        mIndicator.init(getTitles());

        mReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                updateList();
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
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public void onIndicatorIndexSelected(int position) {
        mCurrentPosition = position;
        updateList();
    }

    @Override
    public void onSidebarIndexChange(String index) {
        mIndexText.setText(index);
        if(index == null)
            mIndexText.setVisibility(View.GONE);
        else {
            mIndexText.setVisibility(View.VISIBLE);
            mListView.setSelection(mIndexMap.get(index));
//            mListView.smoothScrollToPosition(mIndexMap.get(index));
        }

    }

    public void setSideBarIndex(String[] index){
        mSidebar.setIndex(index);
        if(index == null || index.length == 0)
            mSidebar.setVisibility(View.GONE);
        else
            mSidebar.setVisibility(View.VISIBLE);
    }

    public int getCurrentPostion(){
        return mCurrentPosition;
    }

    public DataSource getDataSource(){
        return DataSource.getInstance();
    }

    public BaseListAdapter<T> getListAdapter(){
        return mAdapter;
    }

    public ListView getListView(){
        return mListView;
    }

    public Map<String, Integer> getIndexMap(){
        return mIndexMap;
    }

    public void updateList(){
        if(mAdapter != null)
            mAdapter.update(getData());
        mListView.setSelection(0);

        mIndexMap.clear();
        int i = 0;
        if(getData() != null) {
            for (T t : getData()) {
                String index = getItemIndex(t);
                if (!mIndexMap.containsKey(index) && !TextUtils.isEmpty(index))
                    mIndexMap.put(index, i);
                i++;
            }
        }

        setSideBarIndex(mIndexMap.keySet().toArray(new String[mIndexMap.size()]));
    }

    protected abstract BaseListAdapter<T> createAdapter();
    protected abstract String[] getTitles();
    protected abstract String[] getIntentFilterActions();
    protected abstract List<T> getData();
    protected abstract String getItemIndex(T t);
}
