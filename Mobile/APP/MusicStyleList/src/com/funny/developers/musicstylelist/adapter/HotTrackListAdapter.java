package com.funny.developers.musicstylelist.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.funny.developers.musicstylelist.R;
import com.funny.developers.musicstylelist.baseadapter.BaseListAdapter;
import com.funny.developers.musicstylelist.definition.Define;
import com.funny.developers.musicstylelist.dialog.UserFoldersPopUp;
import com.funny.developers.musicstylelist.model.SearchTrackListModel;
import com.funny.developers.musicstylelist.util.DigitUtils;
import com.funny.developers.musicstylelist.util.ImageUtils;

public class HotTrackListAdapter extends BaseListAdapter implements OnClickListener{

	public HotTrackListAdapter(Context context){
		super(context);
	}
	
	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_for_hot_track_list_data, null);
			holder = new ViewHolder();
			
			holder.mThumbnail = (ImageView)convertView.findViewById(R.id.list_thumb_image_view);
			holder.mTitle = (TextView)convertView.findViewById(R.id.list_title_text_view);
			holder.mDuration = (TextView)convertView.findViewById(R.id.list_duration_text_view);
			holder.mUploader = (TextView)convertView.findViewById(R.id.list_uploader_text_view);
			holder.mAddButton = (Button)convertView.findViewById(R.id.list_add_playlist);
			holder.mAddButton.setFocusable(false);
			holder.mAddButton.setOnClickListener(this);
			
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		SearchTrackListModel item = (SearchTrackListModel)mList.get(position);
		String thumbnail = null;
		if(item.trackType == Define.YOUTUBE_TRACK){
			thumbnail = DigitUtils.changeImageUrl(item.thumbnail, Define.YOUTUBE_THUMNAIL_DEFAULT, Define.YOUTUBE_THUMNAIL_HQDEFAULT);
		} else {
			thumbnail = DigitUtils.changeImageUrl(item.thumbnail, Define.SOUNDCLOUD_THUMNAIL_LARGE, Define.SOUNDCLOUD_THUMNAIL_T300X300);
		}
		
		ImageUtils.displayUrlImage(holder.mThumbnail, thumbnail, R.drawable.no_image);
		
		holder.mTitle.setText(item.title);
		holder.mDuration.setText(DigitUtils.stringForTime(item.duration, item.trackType));
		holder.mUploader.setText(item.uploader);
		holder.mAddButton.setBackgroundResource(R.drawable.selector_track_add_to_playlist_button);
		holder.mAddButton.setTag(position);
		
		return convertView;
	}
	
	protected class ViewHolder{
		ImageView mThumbnail = null;
		TextView mTitle  = null;
		TextView mDuration = null;
		TextView mUploader = null;
		Button mAddButton = null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.list_add_playlist:
			int position = (Integer) v.getTag();
			SearchTrackListModel item = (SearchTrackListModel)mList.get(position);
			
			UserFoldersPopUp dialog = new UserFoldersPopUp(mContext);
			dialog.setModel(item);
			dialog.show();
			break;
		}
		
	}
}
