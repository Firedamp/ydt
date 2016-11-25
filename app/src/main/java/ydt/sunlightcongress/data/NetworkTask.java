package ydt.sunlightcongress.data;

import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caodongyao on 2016/11/24.
 */

public class NetworkTask extends AsyncTask<Void, Void, Void> {
    private NetWorkBusinessListener mListener;
    private String requestUrl;

    private int retCode;
    private String result;

    public NetworkTask(String hostUrl, Map<String, String> params, NetWorkBusinessListener listener) {
        if (params == null || params.size() == 0)
            ;
        else {
            hostUrl += "?";
            boolean flag = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                hostUrl += ((flag ? "" : "&") + entry.getKey() + "=" + entry.getValue());
                flag = false;
            }
        }
        this.requestUrl = hostUrl;
        this.mListener = listener;
    }

    public NetworkTask(String requestUrl, NetWorkBusinessListener listener) {
        this.requestUrl = requestUrl;
        this.mListener = listener;
    }

    private String convertInputStreamToString(InputStream is) {
        try {
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = is.read(b)) != -1; ) {
                out.append(new String(b, 0, n));
            }
            return out.toString();
        }catch (Exception e){
            return "";
        }
    }


    @Override
    protected Void doInBackground(Void... params) {
        try {
            retCode = 0;
            result = "";
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            retCode = conn.getResponseCode();
            InputStream is = conn.getInputStream();
            result = convertInputStreamToString(is);
            return null;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(Void v) {
        if(mListener != null){
            if(200 != retCode)
                mListener.onFailed(retCode, result);
            else
                mListener.onSucceed(result);
        }
    }


    public interface NetWorkBusinessListener {
        void onSucceed(String result);

        void onFailed(int errCode, String result);
    }








    private static final String HOST_URL = "http://sample-env.7jzgjvh45k.us-west-1.elasticbeanstalk.com";
    private static final String PARAM_ID = "id";
    private static final String PARAM_MEMBER = "member";

    public static void fetchLegislators(NetWorkBusinessListener listener){
        fetchData("1", listener);
    }

    public static void fetchBills(NetWorkBusinessListener listener){
        fetchData("2", listener);
    }

    public static void fetchomittees(NetWorkBusinessListener listener){
        fetchData("3", listener);
    }

    private static void fetchData(String id, NetWorkBusinessListener listener){
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_ID, id);
        params.put(PARAM_MEMBER, "0");

        new NetworkTask(HOST_URL, params, listener).execute();
    }

}
