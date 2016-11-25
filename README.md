# StepBar
Stepbar is Android library which allows you to show steps in some process


# Usage

Add StepBarWidget to your layout: (you can set bar height, but steps size are constant)

    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/activity_main"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
        <!--YOUR LAYOUT-->

        <pl.linean.stepbar.widget.StepBarWidget
            android:layout_width="match_parent"
            android:layout_height="90dp"
            android:id="@+id/step_bar_widget"/>
            
        <!--YOUR LAYOUT-->


    </RelativeLayout>

Then add StepBar to your Activity (or another context holder):

    public class MainActivity extends AppCompatActivity implements StepBar.OnStepClick{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Create all steps you need
            Step step0 = new Step()
                    .withMarker("0")
                    // All below are optional
                    .withText("Step 0")
                    .withMarkerColor(Color.WHITE)
                    .withTextColor(Color.GRAY)
                    .withInactiveColor(Color.CYAN)
                    .withCompleteColor(Color.BLACK)
                    .withActiveColor(Color.BLUE);

            //step2, step3 .....
            Step step1 = new Step().withMarker("1").withText("Step 1");
            Step step2 = new Step().withMarker("1").withText("Step 2");

            // Create StepBar and add your steps (min. 2)
            StepBarWidget stepBarWidget = (StepBarWidget) findViewById(R.id.step_bar_widget);
            StepBar stepBar = new StepBar(this, stepBarWidget)
                    .withOnClickListener(this) // optional
                    .withCompleteMarker() // optional
                    .addStep(step0) // add steps in right order
                    .addStep(step1)
                    .addStep(step2)
                    .build();


            // What elese you can do
            stepBar.setActiveStepIndex(1);
            stepBar.getActiveStep();
        }

        // You can also add step change listener
        @Override
        public void onStepChange(int stepIndex) {
            Toast.makeText(this, "Clicked step " + stepIndex, Toast.LENGTH_SHORT).show();
        }
    }

Thats all :) 
