package ydt.sunlightcongress.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.common.BaseListAdapter;
import ydt.sunlightcongress.view.IndicatorView;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public abstract class BaseTabListFragment extends Fragment implements IndicatorView.OnIndexSelectListener{
    private IndicatorView mIndicator;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base, container, false);

        mListView = (ListView)view.findViewById(R.id.fragment_base_list);
        mIndicator = (IndicatorView)view.findViewById(R.id.fragment_base_indicator);

        mIndicator.init(getTitles());
        mIndicator.setOnIndexSelectListener(this);

        return view;
    }

    protected ListView getListView(){
        return mListView;
    }

    protected abstract String[] getTitles();
}
