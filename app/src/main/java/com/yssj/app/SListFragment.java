package com.yssj.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yssj.activity.R;


public class SListFragment extends Fragment {

	private View content;
	private ImageView emptyImg;
	private TextView emptyText;
	private View listContent;
	private ProgressBar progress;
	private ListView list;
	private boolean listShown;
	private View progressContent;
	private ListAdapter mAdapter;
	private ImageView loadingImage;
	
	private final AdapterView.OnItemClickListener onItemClickListener
    		= new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			SListFragment.this.onItemClick(parent, v, position, id);
		}
	};
	private TextView loadingText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, container, false);
	}
	
	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureList();
    }
	
	@Override
    public void onDestroyView() {
        list = null;
        listShown = false;
        progressContent = listContent = null;
        super.onDestroyView();
    }

	public void ensureList() {
		if (list != null) {
			return;
		}
		View root = getView();
		if (root == null) {
			throw new IllegalStateException("Content view not yet created");
		}
		content = root.findViewById(android.R.id.content);
		progressContent = root.findViewById(R.id.progress_container);
		listContent = root.findViewById(R.id.list_content);
		emptyImg = (ImageView) root.findViewById(R.id.empty_img);
		emptyText = (TextView) root.findViewById(R.id.empty_text);
		progress = (ProgressBar) root.findViewById(android.R.id.progress);
		list = (ListView) root.findViewById(android.R.id.list);
		loadingImage = (ImageView) root.findViewById(R.id.loading_image);
		loadingText = (TextView) root.findViewById(R.id.loading_text);
		
		listShown = true;
		list.setOnItemClickListener(onItemClickListener);
        if (mAdapter != null) {
            ListAdapter adapter = mAdapter;
            mAdapter = null;
            setListAdapter(adapter);
        } else {
            // We are starting without an adapter, so assume we won't
            // have our data right away and start with the progress indicator.
            if (progressContent != null) {
                setListShown(false, false);
            }
        }
	}
	
	public void setLoadingText(String text) {
		ensureList();
		loadingText.setText(text);
	}
	
	public void setLoadingImageResource(int resId) {
		ensureList();
		loadingImage.setImageResource(resId);
		progress.setVisibility(View.GONE);
	}

	public void setBackgroundResource(int resId) {
		ensureList();
		content.setBackgroundResource(resId);
	}

	//没有数据的时候显示的图片
	public void setEmptyImageResource(int resId) {
		ensureList();
		emptyImg.setImageResource(resId);
	}
	//没有数据的时候显示的文字
	public void setEmptyText(String text) {
		ensureList();
		emptyText.setText(text);
	}

	public void setListShown(boolean shown) {
		setListShown(shown, true);
	}

	public void setListShownNoAnimation(boolean shown) {
		setListShown(shown, false);
	}

	public void setListShown(boolean shown, boolean animate) {
		ensureList();
		if (progress == null) {
			throw new IllegalStateException(
					"Can't be used with a custom content view");
		}
		
		if (listShown == shown) {
			if (list.getCount() <= 0) {
				list.setVisibility(View.GONE);
				emptyImg.setVisibility(View.VISIBLE);
				emptyText.setVisibility(View.VISIBLE);
			} else {
				list.setVisibility(View.VISIBLE);
				emptyImg.setVisibility(View.GONE);
				emptyText.setVisibility(View.GONE);
			}
			return;
		}
		listShown = shown;
		if (shown) {
			if (animate) {
				progressContent.startAnimation(AnimationUtils.loadAnimation(
						getActivity(), android.R.anim.fade_out));
				listContent.startAnimation(AnimationUtils.loadAnimation(
						getActivity(), android.R.anim.fade_in));
			} else {
				progressContent.clearAnimation();
				listContent.clearAnimation();
			}
			progressContent.setVisibility(View.GONE);
			listContent.setVisibility(View.VISIBLE);
			
			if (list.getCount() <= 0) {
				list.setVisibility(View.GONE);
				emptyImg.setVisibility(View.VISIBLE);
				emptyText.setVisibility(View.VISIBLE);
			} else {
				list.setVisibility(View.VISIBLE);
				emptyImg.setVisibility(View.GONE);
				emptyText.setVisibility(View.GONE);
			}
		} else {
			if (animate) {
				progressContent.startAnimation(AnimationUtils.loadAnimation(
						getActivity(), android.R.anim.fade_in));
				listContent.startAnimation(AnimationUtils.loadAnimation(
						getActivity(), android.R.anim.fade_out));
			} else {
				progressContent.clearAnimation();
				listContent.clearAnimation();
			}
			progressContent.setVisibility(View.VISIBLE);
			listContent.setVisibility(View.GONE);
		}
	}
	
	public void setListAdapter(ListAdapter adapter) {
        boolean hadAdapter = mAdapter != null;
        mAdapter = adapter;
        if (list != null) {
        	list.setAdapter(adapter);
            if (!listShown && !hadAdapter) {
                // The list was hidden, and previously didn't have an
                // adapter.  It is now time to show it.
                setListShown(true, getView().getWindowToken() != null);
            }
        }
    }
	
	public void setSelection(int position) {
        ensureList();
        list.setSelection(position);
    }
	
	public int getSelectedItemPosition() {
        ensureList();
        return list.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        ensureList();
        return list.getSelectedItemId();
    }

    public ListView getListView() {
        ensureList();
        return list;
    }
    
    public ListAdapter getListAdapter() {
        return mAdapter;
    }
	
	protected void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		
	}
}
