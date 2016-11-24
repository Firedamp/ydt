package ydt.sunlightcongress.fragment.content;

import ydt.sunlightcongress.adapter.BaseListAdapter;
import ydt.sunlightcongress.adapter.CommitteeListAdapter;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Committee;
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
    public void updateData(){
        switch (getCurrentPostion()){
            default:
            case 0:
                getListAdapter().update(getDataSource().getCommitteesByHouse());
                break;
            case 1:
                getListAdapter().update(getDataSource().getCommitteesBySenate());
                break;
            case 2:
                getListAdapter().update(getDataSource().getCommitteesByJoint());
                break;
        }
    }
}
