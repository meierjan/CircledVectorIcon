package wtf.meier.circledvectoriconexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import wtf.meier.circledvectoricon.CircledVectorIcon;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean isColorToggle = false;

    private CircledVectorIcon circleVectorIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.circleVectorIcon = (CircledVectorIcon) findViewById(R.id.circleVectorIcon);
        circleVectorIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        circleVectorIcon.setDrawableColor(isColorToggle ? R.color.colorPrimary : R.color.colorAccent);
        circleVectorIcon.setCircleColor(isColorToggle ? R.color.colorAccent : R.color.colorPrimary);
        isColorToggle = !isColorToggle;
    }
}
