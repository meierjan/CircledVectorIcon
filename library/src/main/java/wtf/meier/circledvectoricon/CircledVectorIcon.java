package wtf.meier.circledvectoricon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by meier on 20/03/2017.
 * <p>
 * This {@link CircledVectorIcon} provides a basic implementation for cases where you want to display
 * a VectorDrawable inside of a colored circle.
 */
public class CircledVectorIcon extends FrameLayout {

    /**
     * the icon-side-padding that is applied if nothing else is specified
     */
    @FloatRange(from = 0, to = .5)
    public static final float DEFAULT_PADDING_IN_PERCENT = 0.2f;

    /**
     * constant for resource values that are not defined
     */
    private static final int UNDEFINED = -1;

    private AppCompatImageView circleImageView;
    private AppCompatImageView iconImageView;


    private Guideline guidelineLeft;
    private Guideline guidelineRight;
    private Guideline guidelineTop;
    private Guideline guidelineBottom;


    public CircledVectorIcon(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CircledVectorIcon(
            @NonNull Context context, @Nullable AttributeSet attrs
    ) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircledVectorIcon(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircledVectorIcon(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        inflateViewAndBind(context);

        // resource set here (rather then in the xml) because we want to wrap it with
        // DrawableCompat.wrap() so we can tint it across api-levels
        initializeCircleImageView();

        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CircledVectorIcon);

            final @DrawableRes int innerDrawableResId =
                    attributeArray.getResourceId(R.styleable.CircledVectorIcon_drawable, UNDEFINED);

            final @ColorRes int drawableColorRes =
                    attributeArray.getResourceId(R.styleable.CircledVectorIcon_drawableColorRes, UNDEFINED);

            final @ColorInt int drawableColor =
                    attributeArray.getColor(R.styleable.CircledVectorIcon_drawableColorValue, UNDEFINED);

            final @ColorRes int circleColorRes =
                    attributeArray.getResourceId(R.styleable.CircledVectorIcon_circleColorRes, UNDEFINED);

            final @ColorInt int circleColor =
                    attributeArray.getColor(R.styleable.CircledVectorIcon_circleColorValue, UNDEFINED);

            final float imagePaddingPercentage = attributeArray.getFloat(
                    R.styleable.CircledVectorIcon_imagePaddingPercentage,
                    DEFAULT_PADDING_IN_PERCENT
            );

            setImageSidePaddingInPercent(imagePaddingPercentage);

            if (circleColorRes != UNDEFINED) {
                setCircleColor(circleColorRes);
            } else if (circleColor != UNDEFINED) {
                setCircleColorInt(circleColor);
            }

            if (innerDrawableResId != UNDEFINED) {
                setVectorDrawable(innerDrawableResId);
                if (drawableColorRes != UNDEFINED) {
                    setDrawableColor(drawableColorRes);
                } else if (drawableColor != UNDEFINED) {
                    setDrawableColorInt(drawableColor);
                }
            }

            attributeArray.recycle();
        }
    }

    private void initializeCircleImageView() {
        Drawable circle = ResourcesCompat.getDrawable(getResources(), R.drawable.circle_white, null);
        assert circle != null : "the background-circle could not be loaded from resources";
        Drawable wrappedCircle = DrawableCompat.wrap(circle.mutate());
        circleImageView.setImageDrawable(wrappedCircle);
    }

    private void inflateViewAndBind(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_white_circle_image, this);
        iconImageView = findViewById(R.id.circle_imageview);
        circleImageView = findViewById(R.id.circle_circleview);
        guidelineLeft = findViewById(R.id.guideline_outer_left);
        guidelineRight = findViewById(R.id.guideline_outer_right);
        guidelineTop = findViewById(R.id.guideline_outer_top);
        guidelineBottom = findViewById(R.id.guideline_outer_bottom);
    }

    /**
     * Changes the icon to the specified drawable. Please note that the previous set colors will not
     * be applied automatically.
     *
     * @param drawable the drawable to show
     * @throws IllegalAccessException if the provided drawable is not a vector
     */
    public CircledVectorIcon setVectorDrawable(@DrawableRes int drawable) {
        VectorDrawableCompat vectorDrawableCompat =
                VectorDrawableCompat.create(getResources(), drawable, getContext().getTheme());
        if (vectorDrawableCompat == null) {
            throw new IllegalArgumentException("drawable resources have to be an vector-drawable");
        }
        iconImageView.setImageDrawable(vectorDrawableCompat);
        return this;
    }


    /**
     * changes the color of the icon in the foreground
     *
     * @param colorRes the color to change the background to
     */
    public CircledVectorIcon setDrawableColor(@ColorRes int colorRes) {
        changeColorOfVectorDrawableWithColorRes(iconImageView.getDrawable(), colorRes);
        iconImageView.invalidate();
        return this;
    }

    /**
     * changes the color of the icon in the foreground
     *
     * @param colorInt the color to change the background to
     */
    public CircledVectorIcon setDrawableColorInt(@ColorInt int colorInt) {
        changeColorOfVectorDrawableWithColorInt(iconImageView.getDrawable(), colorInt);
        iconImageView.invalidate();
        return this;
    }

    /**
     * changes the color of the circle in the background
     *
     * @param colorRes the color to change the background to
     */
    public CircledVectorIcon setCircleColor(@ColorRes int colorRes) {
        changeColorOfVectorDrawableWithColorRes(circleImageView.getDrawable(), colorRes);
        circleImageView.invalidate();
        return this;
    }

    /**
     * changes the color of the circle in the background
     *
     * @param colorInt the color to change the background to
     */
    public CircledVectorIcon setCircleColorInt(@ColorInt int colorInt) {
        changeColorOfVectorDrawableWithColorInt(circleImageView.getDrawable(), colorInt);
        circleImageView.invalidate();
        return this;
    }

    /**
     * sets the padding around the {@link Drawable} set by {{@link #setVectorDrawable(int)}} in percent
     *
     * @param percentage should be in range 0.0f - 0.5f where 0.0f is 0% and 0.5f is 50% - remember
     *                   it's padding so its applied to each side
     * @throws IllegalArgumentException if percentage outside range 0.0f - 0.5f
     */
    @SuppressLint("DefaultLocale")
    public CircledVectorIcon setImageSidePaddingInPercent(@FloatRange(from = 0F, to = .5F) float percentage) {
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
    private void changePercentageForGuidelines(@FloatRange(from = 0, to = 1) float newPercentage, Guideline... guidelines) {
        if (newPercentage < 0 || newPercentage > 1) {
            throw new IllegalArgumentException("guideline-percentage must be within 0 and 0.5");
        }
        for (Guideline guideline : guidelines) {
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
            lp.guidePercent = newPercentage;
            guideline.setLayoutParams(lp);
        }
    }

    private void changeColorOfVectorDrawableWithColorRes(Drawable drawableToChange, @ColorRes int colorRes) {
        changeColorOfVectorDrawableWithColorInt(drawableToChange, ContextCompat.getColor(getContext(), colorRes));
    }

    private void changeColorOfVectorDrawableWithColorInt(Drawable drawableToChange, @ColorInt int colorInt) {
        DrawableCompat.setTint(
                drawableToChange,
                colorInt
        );
    }
}
