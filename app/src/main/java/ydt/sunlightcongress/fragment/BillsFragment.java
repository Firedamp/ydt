package ydt.sunlightcongress.fragment;

import ydt.sunlightcongress.view.IndicatorView;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class BillsFragment extends BaseTabListFragment {
    @Override
    public String[] getTitles() {
        return new String[]{"Active Bills", "New Bills"};
    }

    @Override
    public void onIndexSelected(int position) {

    }
}
