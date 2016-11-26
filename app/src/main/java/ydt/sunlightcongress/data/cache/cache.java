package ydt.sunlightcongress.data.cache;

import java.util.List;

import ydt.sunlightcongress.data.model.Bill;
import ydt.sunlightcongress.data.model.Committee;
import ydt.sunlightcongress.data.model.Legislator;

/**
 * Created by Caodongyao on 2016/11/26.
 */

public class Cache {
    public List<Legislator> legislators;
    public List<Committee> committees;
    public List<Bill> bills;

    public List<String> favoriteLegislatorId;
    public List<String> favoritecommitteeId;
    public List<String> favoriteBillId;
}
