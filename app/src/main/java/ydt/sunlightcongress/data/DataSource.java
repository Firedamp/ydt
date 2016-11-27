package ydt.sunlightcongress.data;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import ydt.sunlightcongress.data.cache.Cache;
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
    public final static String ACTION_LEGISLATOR_HAS_UPDATED = "ydt.sunlightcongress.action.legislator_updated";

    // DO NOT approach the fields directly, use get methods instead
    private Cache mCache;

    private List<Bill> favoriteBills;
    private List<Committee> favoriteCommittees;
    private List<Legislator> favoriteLegistors;

    private OnPendingDataListener mListener;

    private Context mContext;
    private DataCache mFileCache;

    private static DataSource mInstance;

    public static void init(Context mContext){
        mInstance = new DataSource(mContext);
    }

    public static void destroy(){
        mInstance = null;
    }

    public static DataSource getInstance(){
        return mInstance;
    }




    private DataSource(Context context){
        this.mContext = context;
        this.mFileCache = new DataCache(context);
        this.mCache = new Cache();
    }

    public void setOnPendingDataListener(OnPendingDataListener listener){
        this.mListener = listener;
    }

    public void clear(){
        mCache.bills = null;
        mCache.committees = null;
        mCache.legislators = null;
        favoriteBills = null;
        favoriteCommittees = null;
        favoriteLegistors = null;
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
    // you should get care of the broadcast and then refresh data when received
    protected List<Legislator> getLegislators(){
        if(mCache.legislators != null)
            return mCache.legislators;

        mCache = mFileCache.get();

        favoriteLegistors = null;
        if(mCache.legislators != null)
            return mCache.legislators;

        callListenerFetching();
        NetworkTask.fetchLegislators(new NetworkTask.NetWorkBusinessListener() {
            @Override
            public void onSucceed(String result) {
                LegislatorResponseData data = JSON.parseObject(result, LegislatorResponseData.class);
                if(data != null && data.results != null){
                    callListenerFetched(true);
                    mCache.legislators = data.results;
                    mFileCache.put(mCache);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(ACTION_LEGISLATOR_HAS_UPDATED));
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
        if(mCache.bills != null)
            return mCache.bills;

        favoriteBills = null;
        mCache = mFileCache.get();

        if(mCache.bills != null)
            return mCache.bills;

        callListenerFetching();
        NetworkTask.fetchBills(new NetworkTask.NetWorkBusinessListener() {
            @Override
            public void onSucceed(String result) {
                BillResponseData data = JSON.parseObject(result, BillResponseData.class);
                if(data != null && data.results != null){
                    callListenerFetched(true);
                    mCache.bills = data.results;
                    mFileCache.put(mCache);
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
        if(mCache.committees != null)
            return mCache.committees;

        favoriteCommittees = null;
        mCache = mFileCache.get();

        if(mCache.committees != null)
            return mCache.committees;

        callListenerFetching();
        NetworkTask.fetchomittees(new NetworkTask.NetWorkBusinessListener() {
            @Override
            public void onSucceed(String result) {
                CommitteeResponseData data = JSON.parseObject(result, CommitteeResponseData.class);
                if(data != null && data.results != null){
                    callListenerFetched(true);
                    mCache.committees = data.results;
                    mFileCache.put(mCache);
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

    //TODO implement following method to sort data you want, it's better if there is a Cache
    //TODO if you has a data Cache, BE SURE to clear it when complete data is updated
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
        if(favoriteBills != null)
            return favoriteBills;

        favoriteBills = new ArrayList<Bill>();

        if(mCache.favoriteBillId == null || getBills() == null)
            return favoriteBills;

        for(String id : mCache.favoriteBillId){
            for(Bill bill : getBills()){
                if(id.equals(bill.bill_id)) {
                    favoriteBills.add(bill);
                    continue;
                }
            }
        }

        return favoriteBills;

    }

    public List<Committee> getFavoriteCommittee(){
        if(favoriteCommittees != null)
            return favoriteCommittees;

        favoriteCommittees = new ArrayList<Committee>();

        if(mCache.favoritecommitteeId == null || getCommittees() == null)
            return favoriteCommittees;

        for(String id : mCache.favoritecommitteeId){
            for(Committee committee : getCommittees()){
                if(id.equals(committee.committee_id)) {
                    favoriteCommittees.add(committee);
                    continue;
                }
            }
        }

        return favoriteCommittees;
    }

    public List<Legislator> getFavoriteLegislator(){
        if(favoriteLegistors != null)
            return favoriteLegistors;

        favoriteLegistors = new ArrayList<Legislator>();

        if(mCache.favoriteLegislatorId == null || getLegislators() == null)
            return favoriteLegistors;

        for(String id : mCache.favoriteLegislatorId){
            for(Legislator legislator : getLegislators()){
                if(id.equals(legislator.bioguide_id)) {
                    favoriteLegistors.add(legislator);
                    continue;
                }
            }
        }

        return favoriteLegistors;
    }

    public boolean isFavoriteBill(String bill_id){
        if(mCache.favoriteBillId == null || mCache.favoriteBillId.isEmpty())
            return false;
        if(TextUtils.isEmpty(bill_id))
            return false;
        return mCache.favoriteBillId.contains(bill_id);
    }

    public boolean isFavoriteCommittee(String committee_id){
        if(mCache.favoritecommitteeId == null || mCache.favoritecommitteeId.isEmpty())
            return false;
        if(TextUtils.isEmpty(committee_id))
            return false;
        return mCache.favoritecommitteeId.contains(committee_id);
    }

    public boolean isFavoriteLegislator(String bioguide_id){
        if(mCache.favoriteLegislatorId == null || mCache.favoriteLegislatorId.isEmpty())
            return false;
        if(TextUtils.isEmpty(bioguide_id))
            return false;
        return mCache.favoriteLegislatorId.contains(bioguide_id);
    }

    public boolean setFacoriteBill(String bill_id){
        if(TextUtils.isEmpty(bill_id))
            return false;
        if(mCache.favoriteBillId == null)
            mCache.favoriteBillId = new ArrayList<String>();

        if(mCache.favoriteBillId.contains(bill_id))
            mCache.favoriteBillId.remove(bill_id);
        else
            mCache.favoriteBillId.add(bill_id);

        mFileCache.put(mCache);
        favoriteBills = null;
        return true;
    }

    public boolean setFacoriteCommittee(String comittee_id){
        if(TextUtils.isEmpty(comittee_id))
            return false;
        if(mCache.favoritecommitteeId == null)
            mCache.favoritecommitteeId = new ArrayList<String>();

        if(mCache.favoritecommitteeId.contains(comittee_id))
            mCache.favoritecommitteeId.remove(comittee_id);
        else
            mCache.favoritecommitteeId.add(comittee_id);

        mFileCache.put(mCache);
        favoriteCommittees = null;
        return true;
    }

    public boolean setFacoriteLegislator(String bioguide_id){
        if(TextUtils.isEmpty(bioguide_id))
            return false;
        if(mCache.favoriteLegislatorId == null)
            mCache.favoriteLegislatorId = new ArrayList<String>();

        if(mCache.favoriteLegislatorId.contains(bioguide_id))
            mCache.favoriteLegislatorId.remove(bioguide_id);
        else
            mCache.favoriteLegislatorId.add(bioguide_id);

        mFileCache.put(mCache);
        favoriteLegistors = null;
        return true;
    }

    public interface OnPendingDataListener {
        void onFetchingData();
        void onDataFetched(boolean succeed);
    }
}
