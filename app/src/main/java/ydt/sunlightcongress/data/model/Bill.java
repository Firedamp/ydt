package ydt.sunlightcongress.data.model;

import java.util.Map;

/**
 * Created by Caodongyao on 2016/11/24.
 */

public class Bill {
    public String bill_id;
    public String bill_type;
    public int number;
    public int congress;
    public String chamber;
    public String short_title;
    public String official_title;
    public String popular_title;
    public Sponsor sponsor;
    public String sponsor_id;
    public int cosponsors_count;
    public int withdrawn_cosponsors_count;
    public String introduced_on;
    public History history;
    public String enacted_as;
    public String last_action_at;
    public String last_vote_at;
    public String[] committee_ids;
    public String[] related_bill_ids;
    public Map<String, String> urls;
    public String last_version_on;
    public LastVersion last_version;



    public static class Sponsor{
        public String first_name;
        public String nickname;
        public String last_name;
        public String middle_name;
        public String name_suffix;
        public String title;
    }

    public static class History{
        public String active;
        public String active_at;
        public boolean awaiting_signature;
        public boolean enacted;
        public String senate_passage_result;
        public String senate_passage_result_at;
        public boolean vetoed;
    }

    public static class LastVersion{
        public String version_code;
        public String issued_on;
        public String version_name;
        public String bill_version_id;
        public Map<String, String> urls;
        public int pages;
    }
}
