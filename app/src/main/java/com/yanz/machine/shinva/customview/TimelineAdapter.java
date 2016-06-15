package com.yanz.machine.shinva.customview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yanz.machine.shinva.R;

import java.util.List;
import java.util.Map;

public class TimelineAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String,Object>> list;
	private LayoutInflater inflater;

	public TimelineAdapter(Context context,List<Map<String,Object>> list){
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null){
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.item_line, null);
			viewHolder = new ViewHolder();
			viewHolder.report = (TextView) convertView.findViewById(R.id.tv_report);
			viewHolder.cwpCode = (TextView) convertView.findViewById(R.id.tv_cwpCode);
			viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String report = list.get(position).get("report").toString();
		String cwpCode = list.get(position).get("cwpCode").toString();
		String content = list.get(position).get("content").toString();
		if (cwpCode!=""){
			viewHolder.cwpCode.setBackgroundColor(Color.WHITE);
		}
		if (report==null){
			report = "#";
		}
		if (content==null){
			content="#";
		}
		viewHolder.cwpCode.setText(cwpCode);
		viewHolder.cwpCode.setBackgroundResource(R.drawable.timeline_cwpcode);
		viewHolder.report.setText(report);
		viewHolder.content.setText(content);

		return convertView;

	}
	static class ViewHolder{
		public TextView cwpCode;
		public TextView report;
		public TextView content;
	}

}
