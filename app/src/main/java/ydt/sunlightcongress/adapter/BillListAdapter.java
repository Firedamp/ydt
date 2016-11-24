package ydt.sunlightcongress.adapter;

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

        viewHolder.nameTextView.setText(getItem(position).official_title);

        return convertView;
    }

    private class ViewHolder{
        private TextView nameTextView;

        public ViewHolder(View view){
            nameTextView = (TextView)view.findViewById(R.id.list_item_bill_name);
        }
    }
}
