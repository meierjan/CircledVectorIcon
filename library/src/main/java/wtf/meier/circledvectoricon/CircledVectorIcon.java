package wtf.meier.circledvectoricon;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by meier on 20/03/2017.
 */

public class CircledVectorIcon extends FrameLayout {


    private static final String TAG = CircledVectorIcon.class.getName();

    private static int UNDEFINED = -1;

    private AppCompatImageView circleView;
    private AppCompatImageView imageView;

    public CircledVectorIcon(@NonNull Context context) {
        this(context, null);
    }

    public CircledVectorIcon(
            @NonNull Context context, @Nullable AttributeSet attrs
    ) {
        super(context, attrs);

        inflateView(context);

        TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CircledVectorIcon);

        final @DrawableRes int innerDrawableResId =
                attributeArray.getResourceId(R.styleable.CircledVectorIcon_drawable, UNDEFINED);

        final @ColorRes int drawableColorRes =
                attributeArray.getResourceId(R.styleable.CircledVectorIcon_drawableColor, UNDEFINED);

        final @ColorRes int circleColorRes =
                attributeArray.getResourceId(R.styleable.CircledVectorIcon_circleColor, UNDEFINED);

        if (circleColorRes != UNDEFINED) {
            Drawable drawable = DrawableCompat.wrap(circleView.getDrawable());
            DrawableCompat.setTint(drawable,
                    ContextCompat.getColor(getContext(), circleColorRes)
            );
        }

        if (innerDrawableResId != UNDEFINED) {
            VectorDrawableCompat vectorDrawableCompat =
                    VectorDrawableCompat.create(getResources(), innerDrawableResId, context.getTheme());

            if (vectorDrawableCompat != null) {
                if (drawableColorRes != UNDEFINED) {
                    DrawableCompat.setTint(vectorDrawableCompat,
                            ContextCompat.getColor(getContext(), drawableColorRes)
                    );
                }
                imageView.setImageDrawable(vectorDrawableCompat);
            } else {
                Log.d(TAG, "drawable is null", new NullPointerException("drawable is null"));
            }
        }

        attributeArray.recycle();
    }

    void inflateView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_white_circle_image, this);
        imageView = (AppCompatImageView) findViewById(R.id.circle_imageview);
        circleView = (AppCompatImageView) findViewById(R.id.circle_circleview);
    }
}
