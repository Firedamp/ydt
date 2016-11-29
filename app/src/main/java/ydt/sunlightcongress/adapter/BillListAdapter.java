package ydt.sunlightcongress.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.data.model.Bill;

/**
 * Created by Caodongyao on 2016/11/24.
 */

public class BillListAdapter extends BaseListAdapter<Bill>{

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_bill, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Bill item = getItem(position);
        viewHolder.idTextView.setText(item.bill_id);
        viewHolder.titleTextView.setText(TextUtils.isEmpty(item.short_title) ?
                                            item.official_title : item.short_title);
        viewHolder.dateTextView.setText(item.introduced_on);

        return convertView;
    }

    private class ViewHolder{
        private TextView idTextView;
        private TextView titleTextView;
        private TextView dateTextView;

        public ViewHolder(View view){
            idTextView = (TextView)view.findViewById(R.id.list_item_bill_id);
            titleTextView = (TextView)view.findViewById(R.id.list_item_bill_title);
            dateTextView = (TextView)view.findViewById(R.id.list_item_bill_date);
        }
    }
}
