package ydt.sunlightcongress.fragment.content;

import android.app.Fragment;

import ydt.sunlightcongress.adapter.BaseListAdapter;
import ydt.sunlightcongress.adapter.CommitteeListAdapter;
import ydt.sunlightcongress.adapter.LegistorListAdapter;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Committee;
import ydt.sunlightcongress.data.model.Legislator;
import ydt.sunlightcongress.fragment.BaseTabListFragment;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class LegislatorFragment extends BaseTabListFragment<Legislator> {
    @Override
    public BaseListAdapter<Legislator> createAdapter() {
        return new LegistorListAdapter();
    }

    @Override
    public String[] getTitles() {
        return new String[]{"By States", "House", "Senate"};
    }

    @Override
    public String[] getIntentFilterActions() {
        return new String[]{DataSource.ACTION_LEGISTOR_HAS_UPDATED};
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

}
