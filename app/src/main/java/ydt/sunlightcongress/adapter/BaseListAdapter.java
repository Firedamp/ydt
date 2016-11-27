package ydt.sunlightcongress.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import ydt.sunlightcongress.data.model.Model;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public abstract class BaseListAdapter<T extends Model> extends BaseAdapter{
    private List<T> data;

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public T getItem(int position) {
        if(position >= 0 && data != null && position < data.size())
            return data.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(List<T> list){
        this.data = list;
        notifyDataSetChanged();
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
