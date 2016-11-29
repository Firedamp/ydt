package ydt.sunlightcongress.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ydt.sunlightcongress.R;
import ydt.sunlightcongress.data.model.Legislator;

/**
 * Created by Caodongyao on 2016/11/24.
 */

public class LegislatorListAdapter extends BaseListAdapter<Legislator> {
    public static final String URL_PIC_PREFIX = "https://theunitedstates.io/images/congress/original/";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_legislator, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Legislator item = getItem(position);
        String name = "";
        if (item != null) {
            if (!TextUtils.isEmpty(item.last_name))
                name += item.last_name;
            if (!TextUtils.isEmpty(item.first_name))
                name += (", " + item.first_name);
        }
        String info = "";
        if(item != null){
            if(!TextUtils.isEmpty(item.party))
                info += ("(" + item.party + ")");
            if(!TextUtils.isEmpty(item.state))
                info += item.state_name;
            info += " - ";
            if(item.district != -1)
                info += ("District " + item.district);
        }

        viewHolder.nameTextView.setText(name);
        viewHolder.infoTextView.setText(info);
        Picasso.with(convertView.getContext())
                .load(URL_PIC_PREFIX+item.bioguide_id+".jpg")
                .resize(500, 500)
                .centerCrop()
                .placeholder(R.drawable.ic_android)
                .error(R.drawable.ic_android)
                .into(viewHolder.picImageView);

        return convertView;
    }

    private class ViewHolder {
        private ImageView picImageView;
        private TextView nameTextView;
        private TextView infoTextView;

        public ViewHolder(View view) {
            picImageView = (ImageView) view.findViewById(R.id.list_item_legislator_picture);
            nameTextView = (TextView) view.findViewById(R.id.list_item_legislator_name);
            infoTextView = (TextView) view.findViewById(R.id.list_item_legislator_info);
        }
    }
}
