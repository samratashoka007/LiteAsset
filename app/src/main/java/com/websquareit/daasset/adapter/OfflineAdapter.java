package com.websquareit.daasset.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import android.widget.TextView;

import com.websquareit.daasset.DataModel;
import com.websquareit.daasset.R;

import java.util.ArrayList;
import java.util.Iterator;

public class OfflineAdapter extends ArrayAdapter<DataModel> {
    public ArrayList<DataModel> MainList;
    public ArrayList<DataModel> StudentListTemp = new ArrayList<>();
    Filter myFilter = new Filter() {
        public CharSequence convertResultToString(Object resultValue) {
            return ((DataModel) resultValue).getSiteName();
        }

        /* access modifiers changed from: protected */
        public FilterResults performFiltering(CharSequence constraint) {
            if (constraint == null) {
                return new FilterResults();
            }
            OfflineAdapter.this.MainList.clear();
            Iterator it = OfflineAdapter.this.StudentListTemp.iterator();
            while (it.hasNext()) {
                DataModel cust = (DataModel) it.next();
                if (cust.getSiteName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                    OfflineAdapter.this.MainList.add(cust);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = OfflineAdapter.this.MainList;
            filterResults.count = OfflineAdapter.this.MainList.size();
            return filterResults;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<DataModel> c = (ArrayList) results.values;
            if (results == null || results.count <= 0) {
                OfflineAdapter.this.clear();
                OfflineAdapter.this.notifyDataSetChanged();
                return;
            }
            OfflineAdapter.this.clear();
            Iterator it = c.iterator();
            while (it.hasNext()) {
                OfflineAdapter.this.add((DataModel) it.next());
                OfflineAdapter.this.notifyDataSetChanged();
            }
        }
    };

    public class ViewHolder {
        TextView Date;
        TextView Name;
        TextView Number;
        TextView Twin;
        TextView blade;
        TextView box;
        TextView comment;
        TextView er2cross14;
        TextView er2cross18;
        TextView er2cross28;
        TextView er2ross36;
        TextView jumbo;
        TextView location;
        TextView property;
        TextView quick;
        TextView swing;
        TextView weather,baten,emergancy,exitsign;

        public ViewHolder() {
        }
    }

    public OfflineAdapter(Context context, int id, ArrayList<DataModel> studentArrayList) {
        super(context, id, studentArrayList);
        this.StudentListTemp.addAll(studentArrayList);
        this.MainList = new ArrayList<>(studentArrayList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.offlinelayout, parent, false);
           // convertView = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.offlinelayout, null);
            holder = new ViewHolder();

            holder.Name = (TextView) convertView.findViewById(R.id.oflid);
            holder.Number = (TextView) convertView.findViewById(R.id.oflsite);
            holder.Date = (TextView) convertView.findViewById(R.id.oflDate);
            holder.Twin = (TextView) convertView.findViewById(R.id.ofltwinasset);
            holder.er2cross14 = (TextView) convertView.findViewById(R.id.ofledit2cross14);
            holder.er2cross18 = (TextView) convertView.findViewById(R.id.ofledit2cross18);
            holder.er2cross28 = (TextView) convertView.findViewById(R.id.ofledit2cross28);
            holder.er2ross36 = (TextView) convertView.findViewById(R.id.ofledit2cross36);
            holder.quick = (TextView) convertView.findViewById(R.id.ofleditQuick);
            holder.box = (TextView) convertView.findViewById(R.id.ofleditBox);
            holder.blade = (TextView) convertView.findViewById(R.id.ofleditBlad);
            holder.swing = (TextView) convertView.findViewById(R.id.ofleditSwing);
            holder.weather = (TextView) convertView.findViewById(R.id.ofleditWeather);
            holder.jumbo = (TextView) convertView.findViewById(R.id.ofleditJumbo);
            holder.location = (TextView) convertView.findViewById(R.id.ofleditLocation);
            holder.property = (TextView) convertView.findViewById(R.id.ofleditProperty);
            holder.comment = (TextView) convertView.findViewById(R.id.ofleditCmnt);
            holder.baten=convertView.findViewById(R.id.battentext);
            holder.emergancy=convertView.findViewById(R.id.emrtxt);
            holder.exitsign=convertView.findViewById(R.id.exitsign);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DataModel student = (DataModel) getItem(position);
        holder.Name.setText(student.getId());
        holder.Number.setText(student.getSiteName());
        holder.Date.setText(student.getDate());
        holder.Twin.setText(student.getTwin());
        holder.er2cross14.setText(student.getEronefour());
        holder.er2cross18.setText(student.getEroneeight());
        holder.er2cross28.setText(student.getErtwoeight());
        holder.er2ross36.setText(student.getErthreesix());
        holder.quick.setText(student.getQuickfit());
        holder.box.setText(student.getBoxtype());
        holder.blade.setText(student.getBladetype());
        holder.swing.setText(student.getSwingtype());
        holder.weather.setText(student.getWeatherlevel());
        holder.jumbo.setText(student.getJumbo());
        holder.location.setText(student.getLocation());
        holder.property.setText(student.getProperty());
        holder.comment.setText(student.getCmnt());
        holder.baten.setText(student.getBatten());
        holder.emergancy.setText(student.getEmergancy());
        holder.exitsign.setText(student.getExitSign());
        return convertView;
    }

    public Filter getFilter() {
        return this.myFilter;
    }
}
