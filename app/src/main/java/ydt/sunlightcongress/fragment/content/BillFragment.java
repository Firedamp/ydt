package ydt.sunlightcongress.fragment.content;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;

import java.util.List;

import ydt.sunlightcongress.adapter.BaseListAdapter;
import ydt.sunlightcongress.adapter.BillListAdapter;
import ydt.sunlightcongress.data.DataSource;
import ydt.sunlightcongress.data.model.Bill;
import ydt.sunlightcongress.detail.BillDetailActivity;
import ydt.sunlightcongress.fragment.BaseTabListFragment;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class BillFragment extends BaseTabListFragment<Bill> {

    @Override
    public BaseListAdapter<Bill> createAdapter() {
        return new BillListAdapter();
    }

    @Override
    public String[] getTitles() {
        return new String[]{"Active Bills", "New Bills"};
    }

    @Override
    public String[] getIntentFilterActions() {
        return new String[]{DataSource.ACTION_BILL_HAS_UPDATED};
    }

    @Override
    protected List<Bill> getData() {
        switch (getCurrentPostion()){
            default:
            case 0:
                return getDataSource().getActiveBills();
            case 1:
                return getDataSource().getNewBills();
        }
    }

    @Override
    protected String getItemIndex(Bill bill) {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), BillDetailActivity.class);
        intent.putExtra("data", JSON.toJSONString(getListAdapter().getItem(position)));
        startActivity(intent);
    }
}
