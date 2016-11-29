package ydt.sunlightcongress.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.data.model.Committee;

/**
 * Created by Caodongyao on 2016/11/24.
 */

public class CommitteeListAdapter extends BaseListAdapter<Committee>{
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_committee, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Committee item = getItem(position);
        viewHolder.idTextView.setText(item.committee_id);
        viewHolder.nameTextView.setText(item.name);
        viewHolder.chamberTextView.setText(item.chamber);

        return convertView;
    }

    private class ViewHolder{
        private TextView idTextView;
        private TextView nameTextView;
        private TextView chamberTextView;

        public ViewHolder(View view){
            idTextView = (TextView)view.findViewById(R.id.list_item_committee_id);
            nameTextView = (TextView)view.findViewById(R.id.list_item_committee_name);
            chamberTextView = (TextView)view.findViewById(R.id.list_item_committee_chamber);
        }
    }
}
