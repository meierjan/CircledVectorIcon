package wtf.meier.circledvectoricon;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by meier on 20/03/2017.
 */

public class CircledVectorIcon extends FrameLayout {

    private static final int UNDEFINED = -1;

    private AppCompatImageView circleView;
    private AppCompatImageView imageView;

    public CircledVectorIcon(@NonNull Context context) {
        this(context, null);
        inflateViewAndBind(context);
    }

    public CircledVectorIcon(
            @NonNull Context context, @Nullable AttributeSet attrs
    ) {
        super(context, attrs);

        inflateViewAndBind(context);

        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CircledVectorIcon);

            final @DrawableRes int innerDrawableResId =
                    attributeArray.getResourceId(R.styleable.CircledVectorIcon_drawable, UNDEFINED);

            final @ColorRes int drawableColorRes =
                    attributeArray.getResourceId(R.styleable.CircledVectorIcon_drawableColor, UNDEFINED);

            final @ColorRes int circleColorRes =
                    attributeArray.getResourceId(R.styleable.CircledVectorIcon_circleColor, UNDEFINED);

            if (circleColorRes != UNDEFINED) {
                setCircleColor(circleColorRes);
            }

            if (innerDrawableResId != UNDEFINED) {
                setVectorDrawable(innerDrawableResId);
                if (drawableColorRes != UNDEFINED) {
                    setDrawableColor(drawableColorRes);
                }
            }

            attributeArray.recycle();
        }
    }

    public CircledVectorIcon(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateViewAndBind(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircledVectorIcon(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateViewAndBind(context);
    }

    void inflateViewAndBind(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_white_circle_image, this);
        imageView = (AppCompatImageView) findViewById(R.id.circle_imageview);
        circleView = (AppCompatImageView) findViewById(R.id.circle_circleview);
    }

    public CircledVectorIcon setVectorDrawable(@DrawableRes int drawable) {
        VectorDrawableCompat vectorDrawableCompat =
                VectorDrawableCompat.create(getResources(), drawable, getContext().getTheme());
        imageView.setImageDrawable(vectorDrawableCompat);
        return this;
    }

    public CircledVectorIcon setDrawableColor(@ColorRes int colorRes) {
        changeColorOfVectorDrawable(imageView.getDrawable(), colorRes);
        imageView.invalidate();
        return this;
    }

    public CircledVectorIcon setCircleColor(@ColorRes int colorRes) {
        changeColorOfVectorDrawable(circleView.getDrawable(), colorRes);
        circleView.invalidate();
        return this;
    }

    private void changeColorOfVectorDrawable(Drawable drawableToChange, @ColorRes int colorRes) {
        Drawable drawable = DrawableCompat.wrap(drawableToChange);
        DrawableCompat.setTint(drawable,
                ContextCompat.getColor(getContext(), colorRes)
        );
    }
}
