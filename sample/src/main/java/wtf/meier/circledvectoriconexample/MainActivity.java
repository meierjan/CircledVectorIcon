package wtf.meier.circledvectoriconexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import wtf.meier.circledvectoricon.CircledVectorIcon;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    boolean isColorToggle = false;

    private TextView currentValueDisplayTextView;
    private CircledVectorIcon circleVectorIcon;
    private SeekBar paddingSeekBar;

    private ImageView globalStateWatchingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final float initialPercentageFloat = CircledVectorIcon.DEFAULT_PADDING_IN_PERCENT;
        final int initialPercentageInt = (int) (initialPercentageFloat * 100);

        this.circleVectorIcon = (CircledVectorIcon) findViewById(R.id.circleVectorIcon);
        circleVectorIcon.setOnClickListener(this);

        this.globalStateWatchingIcon = (ImageView) findViewById(R.id.global_state_bike_icon);

        this.currentValueDisplayTextView = (TextView) findViewById(R.id.currentValueDisplay);
        currentValueDisplayTextView.setText(getString(R.string.current_percentage_value_display, initialPercentageInt, initialPercentageFloat));

        this.paddingSeekBar = (SeekBar) findViewById(R.id.imagePaddingSeekBar);
        paddingSeekBar.setProgress(initialPercentageInt);
        paddingSeekBar.setOnSeekBarChangeListener(this);
        paddingSeekBar.setMax(50);

    }

    @Override
    public void onClick(View v) {
        circleVectorIcon.setDrawableColor(isColorToggle ? R.color.colorPrimary : R.color.colorAccent);
        circleVectorIcon.setCircleColor(isColorToggle ? R.color.colorAccent : R.color.colorPrimary);
        globalStateWatchingIcon.invalidate();
        isColorToggle = !isColorToggle;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float newProgress = progress / 100f;
        Log.d("MainActivity", String.format("new padding: %f", newProgress));
        circleVectorIcon.setImageSidePaddingInPercent(newProgress);
        currentValueDisplayTextView.setText(getString(R.string.current_percentage_value_display, progress, newProgress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
