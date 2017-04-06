package wtf.meier.circledvectoricon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by meier on 20/03/2017.
 */

public class CircledVectorIcon extends FrameLayout {

    public static final float DEFAULT_PADDING_IN_PERCENT = 0.2f;

    private static final int UNDEFINED = -1;

    private AppCompatImageView circleView;
    private AppCompatImageView imageView;


    private Guideline guidelineLeft;
    private Guideline guidelineRight;
    private Guideline guidelineTop;
    private Guideline guidelineBottom;


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

            final float imagePaddingPercentage = attributeArray.getFloat(R.styleable.CircledVectorIcon_imagePaddingPercentage, DEFAULT_PADDING_IN_PERCENT);

            setImageSidePaddingInPercent(imagePaddingPercentage);

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

    // TODO: support this constructor
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
        guidelineLeft = (Guideline) findViewById(R.id.guideline_outer_left);
        guidelineRight = (Guideline) findViewById(R.id.guideline_outer_right);
        guidelineTop = (Guideline) findViewById(R.id.guideline_outer_top);
        guidelineBottom = (Guideline) findViewById(R.id.guideline_outer_bottom);
    }

    public CircledVectorIcon setVectorDrawable(@DrawableRes int drawable) {
        VectorDrawableCompat vectorDrawableCompat =
                VectorDrawableCompat.create(getResources(), drawable, getContext().getTheme());
        imageView.setImageDrawable(vectorDrawableCompat);
        return this;
    }

    public CircledVectorIcon setDrawableColor(@ColorRes int colorRes) {
        imageView.setColorFilter(ContextCompat.getColor(getContext(), colorRes), PorterDuff.Mode.SRC_IN);
        return this;
    }

    public CircledVectorIcon setCircleColor(@ColorRes int colorRes) {
        circleView.setColorFilter(ContextCompat.getColor(getContext(), colorRes), PorterDuff.Mode.SRC_IN);
        return this;
    }

    /**
     * @param percentage should be in range 0.0f - 0.5f where 0.0f is 0% and 0.5f is 50% - remember
     *                   it's padding so its applied to each side
     * @throws IllegalArgumentException if percentage outside range 0.0f - 0.5f
     */
    @SuppressLint("DefaultLocale")
    public CircledVectorIcon setImageSidePaddingInPercent(float percentage) {
        if (percentage < 0 || percentage > 0.5) {
            throw new IllegalArgumentException(String.format("Provided percentage '%f' is not within range 0.0f - 0.5f", percentage));
        }
        float percentageEnd = 1 - percentage;
        changePercentageForGuidelines(percentage, guidelineLeft, guidelineTop);
        changePercentageForGuidelines(percentageEnd, guidelineRight, guidelineBottom);
        return this;
    }

    /**
     * @throws IllegalArgumentException if percentage outside range 0.0f - 1.0f
     */
    private void changePercentageForGuidelines(float newPercentage, Guideline... guidelines) {
        if (newPercentage < 0 || newPercentage > 1) {
            throw new IllegalArgumentException("guideline-percentage must be within 0 and 0.5");
        }
        for (Guideline guideline : guidelines) {
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
            lp.guidePercent = newPercentage;
            guideline.setLayoutParams(lp);
        }
    }
}
