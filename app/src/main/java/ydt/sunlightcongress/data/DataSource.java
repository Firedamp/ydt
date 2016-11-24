package ydt.sunlightcongress.data;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.alibaba.fastjson.JSON;

import java.util.List;

import ydt.sunlightcongress.data.NetworkTask;
import ydt.sunlightcongress.data.model.Bill;
import ydt.sunlightcongress.data.model.Committee;
import ydt.sunlightcongress.data.model.Legislator;
import ydt.sunlightcongress.data.network.BillResponseData;
import ydt.sunlightcongress.data.network.CommitteeResponseData;
import ydt.sunlightcongress.data.network.LegislatorResponseData;

/**
 * Created by Caodongyao on 2016/11/24.
 */

public class DataSource {
    public final static String ACTION_BILL_HAS_UPDATED = "ydt.sunlightcongress.action.bill_updated";
    public final static String ACTION_COMMITTEE_HAS_UPDATED = "ydt.sunlightcongress.action.committee_updated";
    public final static String ACTION_LEGISTOR_HAS_UPDATED = "ydt.sunlightcongress.action.legistor_updated";

    // DO NOT approach the fields directly, use get methods instead
    private List<Bill> bills;
    private List<Committee> committees;
    private List<Legislator> legislators;

    private OnPendingDataListener mListener;

    private Context mContext;

    public DataSource(Context context){
        this.mContext = context;
    }

    public void setOnPendingDataListener(OnPendingDataListener listener){
        this.mListener = listener;
    }

    public void clear(){
        bills = null;
        committees = null;
        legislators = null;
    }

    protected void callListenerFetched(boolean succeed){
        if(mListener != null)
            mListener.onDataFetched(succeed);
    }

    protected void callListenerFetching(){
        if(mListener != null)
            mListener.onFetchingData();
    }

    // if the data is null, data source will to fetch data from the Internet
    // and when the data is fetched successfully, a broadcast will be sent
    protected List<Legislator> getLegislators(){
        if(legislators != null)
            return legislators;

        callListenerFetching();
        NetworkTask.fetchLegislators(new NetworkTask.NetWorkBusinessListener() {
            @Override
            public void onSucceed(String result) {
                LegislatorResponseData data = JSON.parseObject(result, LegislatorResponseData.class);
                if(data != null && data.results != null){
                    callListenerFetched(true);
                    legislators = data.results;
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(ACTION_LEGISTOR_HAS_UPDATED));
                }
                else
                    callListenerFetched(false);
            }

            @Override
            public void onFailed(int errCode, String result) {
                    callListenerFetched(false);
            }
        });

        return null;
    }

    protected List<Bill> getBills(){
        if(bills != null)
            return bills;

        callListenerFetching();
        NetworkTask.fetchBills(new NetworkTask.NetWorkBusinessListener() {
            @Override
            public void onSucceed(String result) {
                BillResponseData data = JSON.parseObject(result, BillResponseData.class);
                if(data != null && data.results != null){
                    callListenerFetched(true);
                    bills = data.results;
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(ACTION_BILL_HAS_UPDATED));
                }
                else
                    callListenerFetched(false);
            }

            @Override
            public void onFailed(int errCode, String result) {
                callListenerFetched(false);
            }
        });

        return null;
    }

    protected List<Committee> getCommittees(){
        if(committees != null)
            return committees;

        callListenerFetching();
        NetworkTask.fetchLegislators(new NetworkTask.NetWorkBusinessListener() {
            @Override
            public void onSucceed(String result) {
                CommitteeResponseData data = JSON.parseObject(result, CommitteeResponseData.class);
                if(data != null && data.results != null){
                    callListenerFetched(true);
                    committees = data.results;
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(ACTION_COMMITTEE_HAS_UPDATED));
                }
                else
                    callListenerFetched(false);
            }

            @Override
            public void onFailed(int errCode, String result) {
                callListenerFetched(false);
            }
        });

        return null;
    }

    //TODO implement following method to sort data you want, it's better if there is a cache
    //TODO if you has a data cache, BE SURE to clear it when complete data is updated
    public List<Legislator> getLegislatorsByState(){
        return getLegislators();
    }

    public List<Legislator> getLegislatorsByHouse(){
        return getLegislators();
    }

    public List<Legislator> getLegislatorsBySenate(){
        return getLegislators();
    }

    public List<Bill> getActiveBills(){
        return getBills();
    }

    public List<Bill> getNewBills(){
        return getBills();
    }

    public List<Committee> getCommitteesByHouse(){
        return getCommittees();
    }

    public List<Committee> getCommitteesBySenate(){
        return getCommittees();
    }

    public List<Committee> getCommitteesByJoint(){
        return getCommittees();
    }

    public List<Bill> getFavoriteBills(){
        return getBills();
    }

    public List<Committee> getFavoriteCommittee(){
        return getCommittees();
    }

    public List<Legislator> getFavoriteLegistor(){
        return getLegislators();
    }

    public interface OnPendingDataListener {
        void onFetchingData();
        void onDataFetched(boolean succeed);
    }
}
