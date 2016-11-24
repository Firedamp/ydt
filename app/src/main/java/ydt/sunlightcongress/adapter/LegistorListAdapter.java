package ydt.sunlightcongress.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.data.model.Legislator;

/**
 * Created by Caodongyao on 2016/11/24.
 */

public class LegistorListAdapter extends BaseListAdapter<Legislator> {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_legistor, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Legislator item = getItem(position);
        String name = "";
        if (item != null) {
            if (!TextUtils.isEmpty(item.first_name))
                name += (item.first_name + " ");
            if (!TextUtils.isEmpty(item.last_name))
                name += item.last_name;
        }
        String info = "";
        if(item != null){
            if(!TextUtils.isEmpty(item.party))
                info += ("(" + item.party + ")");
            if(!TextUtils.isEmpty(item.state))
                info += item.state;
            info += " - ";
            if(item.district != -1)
                info += ("District " + item.district);
        }

        viewHolder.nameTextView.setText(name);
        viewHolder.infoTextView.setText(info);


        return convertView;
    }

    private class ViewHolder {
        private ImageView picImageView;
        private TextView nameTextView;
        private TextView infoTextView;

        public ViewHolder(View view) {
            picImageView = (ImageView) view.findViewById(R.id.list_item_legistor_picture);
            nameTextView = (TextView) view.findViewById(R.id.list_item_legistor_name);
            infoTextView = (TextView) view.findViewById(R.id.list_item_legistor_info);
        }
    }
}
