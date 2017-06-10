package com.yuqianhao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yuqianhao.myapplication.R;

/**
 * Created by 于千皓 on 2017/6/10.
 */

public class GradientView extends View {
    /*文本颜色，内容，大小*/
    private int mColor=0xFF333333;//默认灰色
    private String mText;
    private int mTextSize= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics());
    /*文本默认颜色*/
    private int mDefaultTextColor=0xFF333333;
    /*图片*/
    private Bitmap mImageBitmap;
    /*绘制文本的Paint*/
    private Paint mTextPaint;
    /*文本矩形*/
    private Rect mTextRect;
    /*图片矩形*/
    private Rect mImageRect;
    /*透明度*/
    private float mAlpha;
    /*缓存绘制器*/
    private Paint mPaint;
    /*缓存图片画布*/
    private Canvas mCanvas;
    /*遮盖图层*/
    private Bitmap mBitmap;

    private static final String INSTANCE_KEY="com.yuqianhao.view.GradientView::Instance";
    private static final String ALPHA_CACHE="com.yuqianhao.view.GradientView::mAlpha";

    public GradientView(Context context) {
        this(context,null);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initXMLValue(context,attrs);
        initTextPaint();
    }

    private void initTextPaint() {
        mTextPaint=new Paint();
        mTextRect=new Rect();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mDefaultTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.getTextBounds(mText,0,mText.length(),mTextRect);
    }

    private void initXMLValue(Context context, AttributeSet attrs) {
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.GradientView);
        int mXMLValueSize=typedArray.getIndexCount();
        for(int i=0;i<mXMLValueSize;i++){
            int attrsIndex=typedArray.getIndex(i);
            switch(attrsIndex){
                case R.styleable.GradientView_text:{
                    mText=typedArray.getString(attrsIndex);
                }break;
                case R.styleable.GradientView_src:{
                    mImageBitmap=((BitmapDrawable)typedArray.getDrawable(attrsIndex)).getBitmap();
                }break;
                case R.styleable.GradientView_achieveColor:{
                    mColor=typedArray.getColor(attrsIndex,0xFF333333);
                }break;
                case R.styleable.GradientView_textSize:{
                    mTextSize= (int) typedArray.getDimension(attrsIndex, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics()));
                }break;
                case R.styleable.GradientView_textColor:{
                    mDefaultTextColor=typedArray.getColor(attrsIndex,0xFF333333);
                }break;
            }
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int imageWidth=Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(),
                getMeasuredHeight()-mTextRect.height()-getPaddingBottom()-getPaddingTop());
        int left=getMeasuredWidth()/2-imageWidth/2;
        int top=(getMeasuredHeight()-mTextRect.height())/2-imageWidth/2;
        mImageRect=new Rect(left,top,left+imageWidth,top+imageWidth);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle b=new Bundle();
        b.putParcelable(INSTANCE_KEY,super.onSaveInstanceState());
        b.putFloat(ALPHA_CACHE,mAlpha);
        return b;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle b= (Bundle) state;
            super.onRestoreInstanceState(b.getParcelable(INSTANCE_KEY));
            mAlpha=b.getFloat(ALPHA_CACHE);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mImageBitmap,null,mImageRect,null);
        int alpha= (int) Math.ceil(255*mAlpha);
        drawCacheBitmap(alpha);
        drawSourceText(canvas,alpha);
        drawCacheText(canvas,alpha);
        canvas.drawBitmap(mBitmap,0,0,null);
    }

    private void drawCacheText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        canvas.drawText(mText,getMeasuredWidth()/2-mTextRect.width()/2,
                mImageRect.bottom+mTextRect.height(),mTextPaint);
    }

    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mDefaultTextColor);
        mTextPaint.setAlpha(255-alpha);
        canvas.drawText(mText,getMeasuredWidth()/2-mTextRect.width()/2,
                mImageRect.bottom+mTextRect.height(),mTextPaint);
    }

    private void drawCacheBitmap(int alpha) {
        mBitmap=Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(mBitmap);
        mPaint=new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(mImageRect,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mImageBitmap,null,mImageRect,mPaint);
    }

    public void setImageAlpha(float alpha){
        mAlpha=alpha;
        invalidateImage();
    }

    private void invalidateImage() {
        if(Looper.myLooper()==Looper.getMainLooper()){
            invalidate();
        }else{
            postInvalidate();
        }
    }
}
