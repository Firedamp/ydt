package ydt.sunlightcongress.fragment;

import ydt.sunlightcongress.view.IndicatorView;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class CommitteesFragment extends BaseTabListFragment {
    @Override
    public String[] getTitles() {
        return new String[]{"House", "Senate", "Joint"};
    }

    @Override
    public void onIndexSelected(int position) {

    }
}
