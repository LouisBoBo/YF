package com.yssj.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.FaverateItemView;
import com.yssj.custom.view.FootItemView;
import com.yssj.data.YDBHelper;
import com.yssj.entity.Like;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.fragment.MyFavorProductListFragment;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MyFavorStaggeredAdapter extends BaseAdapter {
    private LinkedList<Like> mInfos;
    //	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private Context context;
    private YDBHelper dbHelper;

    private MyFavorProductListFragment mFragment;

//	private int imageWidth;

    private boolean flag = false;
    private boolean isVip; //是否是会员
    private VipDikouData vipDikouData;
    private static List<Like> checkList;

    public MyFavorStaggeredAdapter(Context context, MyFavorProductListFragment mFragment) {
        this.context = context;
        mInfos = new LinkedList<Like>();

//		int width = context.getResources().getDisplayMetrics().widthPixels;
//		imageWidth = width / 2;
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.cacheInMemory(true)
//				.cacheOnDisk(true).considerExifParams(true)
//				.displayer(new FadeInBitmapDisplayer(35)).build();

        dbHelper = new YDBHelper(context);
        checkList = new ArrayList<Like>();

        this.mFragment = mFragment;
    }


    public static List<Like> getCheckList() {

        return checkList;
    }

    public void clearData(String isDel) {
        if ("1".equals(isDel)) {
            mIsSxInfos.clear();
        } else {
            mInfos.clear();
        }
    }

    private boolean isSx = false;

    public void setEdit(boolean bl) {
        this.flag = bl;
        if (bl == false) {
            checkList.clear();
        }
        notifyDataSetChanged();
    }

    /*
     * public void setTop(boolean flag) { this.flag = flag; }
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

//		final Like like = mInfos.get(position);

        if (convertView == null) {

            LayoutInflater layoutInflator = LayoutInflater.from(parent
                    .getContext());
            convertView = layoutInflator.inflate(R.layout.favourite_item_view,
                    null);
            holder = new ViewHolder();

            holder.left = (FaverateItemView) convertView.findViewById(R.id.left);
            holder.right = (FaverateItemView) convertView.findViewById(R.id.right);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        position = position * 2;

        if (isSx) {
//            holder.left.initView(flag, mIsSxInfos.get(position));

            if (isVip) {
                holder.left.initViewVip(mIsSxInfos.get(position), vipDikouData);

            } else {
                holder.left.initView(flag,mIsSxInfos.get(position));
            }


            if (mIsSxInfos.size() > position + 1) {
                holder.right.setVisibility(View.VISIBLE);
//                holder.right.initView(flag, mIsSxInfos.get(position + 1));

                if (isVip) {
                    holder.right.initViewVip(mIsSxInfos.get(position+1), vipDikouData);
                } else {
                    holder.right.initView(flag, mIsSxInfos.get(position + 1));
                }
            } else {
                holder.right.setVisibility(View.INVISIBLE);
            }
        } else {


//		holder.left.initView(flag,mInfos.get(position));
            if (isVip) {
                holder.left.initViewVip(mInfos.get(position), vipDikouData);

            } else {
				holder.left.initView(flag,mInfos.get(position));
            }


            if (mInfos.size() > position + 1) {
                holder.right.setVisibility(View.VISIBLE);


				if (isVip) {
					holder.right.initViewVip(mInfos.get(position+1), vipDikouData);
				} else {
					holder.right.initView(flag, mInfos.get(position + 1));
				}



			} else {
                holder.right.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }





//	class MySimpleImageLoadingListener extends SimpleImageLoadingListener {
//		private ImageView igView;
//		String url;
//
//		public MySimpleImageLoadingListener(ImageView igView, String imgUrl) {
//			// TODO Auto-generated constructor stub
//			this.igView = igView;
//			this.url = imgUrl;
//		}
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view,
//				Bitmap loadedImage) {
//			// TODO Auto-generated method stub
//			super.onLoadingComplete(imageUri, view, loadedImage);
//			if (url.equals(igView.getTag())) {
//				igView.setImageBitmap(loadedImage);
//			}
//		}
//	}

    /*
     * 把浏览过的数据添加进数据库
     */
    private void addScanDataTo(final String shop_code) {
        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context,
                                                Void... params) throws Exception {
                return ComModel.addMySteps(context, shop_code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }

    private void isSetTop(final String shop_code, final String is_show) {
        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context,
                R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context,
                                                Void... params) throws Exception {
                return ComModel.getMyFavorIsSetTop(context, shop_code, is_show);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null && "1".equals(result.getStatus())) {
                        ToastUtil.showShortText(context, result.getMessage());
                    } else {
                        ToastUtil.showShortText(context, "糟糕，出错了。。。");
                    }
                }
            }

        }.execute();
    }

//	private void initData() {
//		new SAsyncTask<Void, Void, List<Like>>((FragmentActivity) context) {
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<Like> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				// listResult.addAll(result);
//				if(null == e){
//				addItemTop(result);
//				}
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected List<Like> doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//
//				return ComModel2.getMyFavourList(context, 1);
//			}
//
//		}.execute();
//	}

    class ViewHolder {

        FaverateItemView left;
        FaverateItemView right;
    }


    @Override
    public int getCount() {
        return isSx ? (mIsSxInfos.size() % 2 == 0 ? mIsSxInfos.size() / 2 : mIsSxInfos.size() / 2 + 1) : (mInfos.size() % 2 == 0 ? mInfos.size() / 2 : mInfos.size() / 2 + 1);
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void addItemLast(List<Like> datas) {
        if (isSx) {
            mIsSxInfos.addAll(datas);
        } else {
            mInfos.addAll(datas);
        }
//		for (int i = 0; i < datas.size(); i++) {
//			if ("1".equals(datas.get(i).getIs_del())) {
//				mIsSxInfos.addLast(datas.get(i));
//			}
//		}
        if (!isSx && mInfos.size() == 0) {
            mFragment.showNoData();
        } else if (isSx && mIsSxInfos.isEmpty()) {
            mFragment.showNoData();
        } else {
            mFragment.showData();
        }
        this.notifyDataSetChanged();
    }

    public void addItemTop(List<Like> datas) {
        if (isSx) {
            mIsSxInfos.clear();
            mIsSxInfos.addAll(datas);
        } else {
            mInfos.clear();
            mInfos.addAll(datas);
        }
        if (!isSx && mInfos.size() == 0) {
            mFragment.showNoData();
        } else if (isSx && mIsSxInfos.isEmpty()) {
            mFragment.showNoData();
        } else {
            mFragment.showData();
        }
//		for (int i = 0; i < datas.size(); i++) {
//			if ("1".equals(datas.get(i).getIs_del())) {
//				mIsSxInfos.addLast(datas.get(i));
//			}
//		}

        this.notifyDataSetChanged();
    }

    private LinkedList<Like> mIsSxInfos = new LinkedList<Like>();

    //private LinkedList<Like> mIsAllInfos = new LinkedList<Like>();

    // 过滤是否是失效
    public void filterSX(String is_del) {
//		mInfos.clear();
//		mIsSxInfos.clear();
//		for (int i = 0; i < mIsAllInfos.size(); i++) {
//			if ("1".equals(mIsAllInfos.get(i).getIs_del())) {
//				mIsSxInfos.addLast(mIsAllInfos.get(i));
//			}
//		}
        if ("1".equals(is_del)) {//失效
            //mInfos.addAll(mIsSxInfos);
            isSx = true;
        } else {
            //mInfos.addAll(mIsAllInfos);
            isSx = false;
        }

//		if(mInfos.size() == 0){
//			mFragment.showNoData();
//		}else{
//			if(isSx&&mIsSxInfos.isEmpty()){
//				mFragment.showNoData();
//			}else{
//				mFragment.showData();
//			}
//		}

        this.notifyDataSetChanged();
    }

//	public void clearData() {
//		mInfos.clear();
//		mIsSxInfos.clear();
//		// this.notifyDataSetChanged();
//	}

//	private static class AnimateFirstDisplayListener extends
//			SimpleImageLoadingListener {
//
//		static final List<String> displayedImages = Collections
//				.synchronizedList(new LinkedList<String>());
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view,
//				Bitmap loadedImage) {
//			if (loadedImage != null) {
//				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}


    public void setDataVip(List<Like> result, boolean isVip, VipDikouData vipDikouData) {
        this.isVip = isVip;
        this.vipDikouData = vipDikouData;
        clearData();
        mInfos.addAll(result);
        this.notifyDataSetChanged();
    }

    public void clearData() {
        mInfos.clear();
    }


}
