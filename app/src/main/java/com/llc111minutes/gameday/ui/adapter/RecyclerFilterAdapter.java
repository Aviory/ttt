package com.llc111minutes.gameday.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.interfaces.FilterMenuOpetation;
import com.llc111minutes.gameday.ui.widget.owner.CustomFontTextView;
import com.llc111minutes.gameday.util.FilterUtil;
import com.llc111minutes.gameday.util.LogUtil;

import java.lang.ref.SoftReference;
import java.util.concurrent.atomic.AtomicReference;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class RecyclerFilterAdapter extends RecyclerView.Adapter<RecyclerFilterAdapter.FontViewHolder>
     implements View.OnClickListener {
    private Bitmap sourceBitmap;
    private AtomicReference<SparseArrayCompat<SoftReference<Bitmap>>> mBitmaps;
    //Initial size bitmap
    private int bitmapSize = 200;
    private int minPhotoSize = 200;
    private int selected;
    private FilterMenuOpetation filterMenuOpetation;

    private CompositeSubscription compositeSubscription;
    //private GPUImage gpuImage;
    //private GPUImageFilter gpuImageFilter;

    public RecyclerFilterAdapter(Bitmap sourceBitmap) {
        this.sourceBitmap = sourceBitmap;
        SparseArrayCompat<SoftReference<Bitmap>> softReferenceSparseArrayCompat = new SparseArrayCompat<>();
        mBitmaps = new AtomicReference<>(softReferenceSparseArrayCompat);
    }

    public void onAttach(Context context) {
        LogUtil.info(this, "onAttach");
        //gpuImage = new GPUImage(context);
        mPublish = PublishSubject.create();
        mSunscriber = new Subscriber<MyObjects>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(MyObjects objects) {
                //LogUtil.info(this, "mSubscriber: " + objects.getPosition());
                objects.getImageView().setImageBitmap(objects.getBitmap());
            }
        };
        compositeSubscription = new CompositeSubscription();
        createSubscription();
        minPhotoSize = context.getResources().getDimensionPixelSize(R.dimen.min_size_photo);
    }

    public void setSourceBitmap(Bitmap sourceBitmap) {
        this.sourceBitmap = sourceBitmap;
    }

    public void setFilterMenuOpetation(FilterMenuOpetation filterMenuOpetation) {
        this.filterMenuOpetation = filterMenuOpetation;
    }

    @Override
    public FontViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_filter, parent, false);
        FontViewHolder fontViewHolder = new FontViewHolder(v);
        fontViewHolder.llContainer.setOnClickListener(this);
        fontViewHolder.imageView.setTag("" + -1);
        return fontViewHolder;
    }

    private Subscriber<MyObjects> mSunscriber;
    private PublishSubject<FontViewHolder> mPublish;// = PublishSubject.create();
    private Subscription mSubscription;

    private void createSubscription() {
        mSubscription = mPublish
                .map(new Func1<FontViewHolder, MyObjects>() {
                    @Override
                    public MyObjects call(FontViewHolder fontViewHolder) {
                        fontViewHolder.imageView.setImageBitmap(sourceBitmap);
                        int position = Integer
                                .parseInt(fontViewHolder.llContainer.getTag().toString());
                        LogUtil.info(this, "call position: " + position);
                        fontViewHolder.imageView.setTag(position);
                        return new MyObjects(fontViewHolder.imageView, null, position);
                    }
                })
                .observeOn(Schedulers.computation())
                .map(new Func1<MyObjects, MyObjects>() {
                    @Override
                    public MyObjects call(MyObjects objects) {
                        GPUImage gpuImage = new GPUImage(objects.getImageView().getContext());
                        GPUImageFilter gpuImageFilter =
                                FilterUtil.getFilter(FilterUtil.FILTER.values()[objects.getPosition()]);
                        Bitmap bitmapTmp;
                        if (gpuImageFilter == null) {
                            bitmapTmp = Bitmap.createScaledBitmap(
                                    sourceBitmap,
                                    bitmapSize,
                                    bitmapSize,
                                    false);
                        } else {
                            gpuImage.setFilter(gpuImageFilter);
                            bitmapTmp = Bitmap.createScaledBitmap(
                                    gpuImage.getBitmapWithFilterApplied(sourceBitmap),
                                    bitmapSize,
                                    bitmapSize,
                                    false);
                        }

                        //if (mBitmaps.get().get(objects.getPosition()) == null) {
                            mBitmaps.get().put(objects.getPosition(), new SoftReference<>(bitmapTmp));
                        //}
                        gpuImage = null;
                        gpuImageFilter = null;
                        LogUtil.info(this, "Position: " + objects.getPosition());
                        return new MyObjects(objects.imageView, bitmapTmp, objects.position);
                    }
                })
                .onBackpressureDrop()
                .retry()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<MyObjects, Boolean>() {
                    @Override
                    public Boolean call(MyObjects myObjects) {
                        int position = Integer
                                .parseInt(myObjects.getImageView().getTag().toString());
                        boolean isChange = position == myObjects.getPosition();
                        //LogUtil.info(this, "New position: " + position +" old position: " + myObjects.getPosition() + " " + isChange);
                        return isChange;
                    }
                })
                .subscribe(mSunscriber);
        compositeSubscription.add(mSubscription);
    }

    class MyObjects {
        ImageView imageView;
        Bitmap bitmap;
        int position;

        public MyObjects() {
        }

        public MyObjects(ImageView imageView, Bitmap bitmap, int position) {
            this.imageView = imageView;
            this.bitmap = bitmap;
            this.position = position;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public int getPosition() {
            return position;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    @Override
    public void onBindViewHolder(FontViewHolder holder, int position) {
        holder.llContainer.setTag("" + position);
        LogUtil.info(this, "Bitmap: " + sourceBitmap.getWidth() + " width: " + holder.imageView.getWidth());

        FilterUtil.FILTER filter = FilterUtil.FILTER.values()[position];
        //LogUtil.info(this, "Filter: " + filter + " position: " + position);

        if (holder.imageView.getWidth() > minPhotoSize) {
            //LogUtil.info(this, "Width: " + holder.imageView.getWidth()+" measured: "+holder.imageView.getMeasuredWidth());
            //int size
            bitmapSize = holder.imageView.getWidth() / 2;
            LogUtil.info(this, "bitmap size: " + bitmapSize);
        }

        holder.tvName.setText(filter.getFilterName());

        if (selected == position) {
            holder.imageView.setBackgroundResource(R.drawable.shape_red_border);
        } else {
            holder.imageView.setBackgroundResource(0);
        }

        if (mBitmaps.get().get(position) != null && mBitmaps.get().get(position).get()!=null) {
            LogUtil.info(this, "Mbitmaps: "+mBitmaps.get().get(position).get());
            holder.imageView.setImageBitmap(mBitmaps.get().get(position).get());
        } else {
            LogUtil.info(this, "publish.onNext");
            mPublish.onNext(holder);
        }

    }



    @Override
    public int getItemCount() {
        return FilterUtil.FILTER.values().length;
    }

    @Override
    public void onClick(View view) {
        selected = Integer.parseInt(view.getTag().toString());
        LogUtil.info(this, "Selected: " + selected + " filtermenuOperation: " + filterMenuOpetation);
        if (filterMenuOpetation != null) {
            filterMenuOpetation.appFilter(FilterUtil.FILTER.values()[selected]);
        }
        notifyDataSetChanged();
    }

    static class FontViewHolder extends RecyclerView.ViewHolder {
        CustomFontTextView tvName;
        ImageView imageView;
        View llContainer;

        FontViewHolder(View v) {
            super(v);
            tvName = (CustomFontTextView) v.findViewById(R.id.tvName);
            imageView = (ImageView) v.findViewById(R.id.imgView);
            llContainer = v.findViewById(R.id.llContainer);
        }
    }

    public void onDetach(){
        if (mSubscription != null) {
            mBitmaps.get().clear();
            compositeSubscription.unsubscribe();
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }


}