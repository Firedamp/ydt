package ydt.sunlightcongress.fragment;

import ydt.sunlightcongress.view.IndicatorView;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class LegislatorsFragment extends BaseTabListFragment {
    @Override
    public String[] getTitles() {
        return new String[]{"By States", "House", "Senate"};
    }

    @Override
    public void onIndexSelected(int position) {

    }
}
