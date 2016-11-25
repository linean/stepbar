package pl.linean.stepapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import pl.linean.stepbar.StepBar;
import pl.linean.stepbar.Step;
import pl.linean.stepbar.widget.StepBarWidget;

public class MainActivity extends AppCompatActivity implements StepBar.OnStepClick{

    private Toast toast;
    private StepBar stepBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Step step0 = new Step().withMarker("0").withText("Step 0");
        Step step1 = new Step().withMarker("1").withText("Step 1");
        Step step2 = new Step().withMarker("2").withText("Step 2");

        // Create StepBar and add your steps (min. 2)
        StepBarWidget stepBarWidget = (StepBarWidget) findViewById(R.id.step_bar_widget);
        stepBar = new StepBar(this, stepBarWidget)
                .withOnClickListener(this) // optional
                .withCompleteMarker() // optional
                .addStep(step0) // add steps in right order
                .addStep(step1)
                .addStep(step2)
                .build();

        stepBar.setActiveStepIndex(0);
        initButtons();
    }

    // You can also add step change listener
    @SuppressLint("ShowToast")
    @Override
    public void onStepChange(int stepIndex) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(this, "Clicked step " + stepIndex, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void initButtons(){
        findViewById(R.id.secondButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepBar.setActiveStepIndex(1);
            }
        });

        findViewById(R.id.firstButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepBar.setActiveStepIndex(0);
            }
        });

        findViewById(R.id.thirdButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepBar.setActiveStepIndex(2);
            }
        });
    }
}
