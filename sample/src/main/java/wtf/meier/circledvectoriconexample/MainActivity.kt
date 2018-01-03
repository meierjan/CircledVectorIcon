package wtf.meier.circledvectoriconexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import wtf.meier.circledvectoricon.CircledVectorIcon

class MainActivity : AppCompatActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private var isColorToggle = false

    private lateinit var currentValueDisplayTextView: TextView
    private lateinit var circleVectorIcon: CircledVectorIcon
    private lateinit var paddingSeekBar: SeekBar

    private lateinit var globalStateWatchingIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val initialPercentageFloat = CircledVectorIcon.DEFAULT_PADDING_IN_PERCENT
        val initialPercentageInt = (initialPercentageFloat * 100).toInt()

        this.circleVectorIcon = findViewById(R.id.circleVectorIcon)
        circleVectorIcon.setOnClickListener(this)

        this.globalStateWatchingIcon = findViewById(R.id.global_state_bike_icon)

        this.currentValueDisplayTextView = findViewById(R.id.currentValueDisplay)
        currentValueDisplayTextView.text = getString(R.string.current_percentage_value_display, initialPercentageInt, initialPercentageFloat)

        this.paddingSeekBar = findViewById(R.id.imagePaddingSeekBar)
        paddingSeekBar.apply {
            progress = initialPercentageInt
            max = 50
            setOnSeekBarChangeListener(this@MainActivity)
        }

    }

    override fun onClick(v: View) {
        circleVectorIcon.apply {
            setDrawableColor(if (isColorToggle) R.color.colorPrimary else R.color.colorAccent)
            setCircleColor(if (isColorToggle) R.color.colorAccent else R.color.colorPrimary)
        }
        globalStateWatchingIcon.invalidate()
        isColorToggle = !isColorToggle
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        val newProgress = progress / 100f
        Log.d("MainActivity", "new padding: $newProgress")
        circleVectorIcon.setImageSidePaddingInPercent(newProgress)
        currentValueDisplayTextView.text = getString(R.string.current_percentage_value_display, progress, newProgress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {

    }
}
