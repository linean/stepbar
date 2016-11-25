package pl.linean.stepbar;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import pl.linean.stepbar.widget.StepBarWidget;

/**
 * Created by Maciej Sady on 23.08.2016.
 */
@SuppressWarnings("unused")
public class StepBar extends ContextWrapper{
    private LinearLayout root;
    private OnStepClick onStepClick;
    private int activeStepIndex;
    private boolean completeMarker = false;


    private final ArrayList<Step> stepList = new ArrayList<>();

    public interface OnStepClick {
        void onStepChange(int stepIndex);
    }

    public StepBar(Context base, StepBarWidget widget) {
        super(base);
        try {
            root = (LinearLayout) widget.getRoot();
        } catch (Exception e){
            throw new StepBarException("Widget initialization fail! Error: " + e);
        }
    }

    public StepBar withOnClickListener(final OnStepClick onStepClick){
        this.onStepClick = onStepClick;
        return this;
    }

    public StepBar addStep(Step step){
        if(stepList.contains(step))
            throw new StepBarException("This step is already added !");

        stepList.add(step);
        return this;
    }

    public StepBar withCompleteMarker(){
        completeMarker = true;
        return this;
    }

    public StepBar build(){
        checkIsComplete();
        setParentLayout();
        buildAndAddStepsViews();
        setClickLister();

        return this;
    }

    public int getActiveStep(){
        return  activeStepIndex;
    }

    public void setActiveStepIndex(int stepIndex){
        if(stepIndex > stepList.size()-1)
            throw new StepBarException("Step index out of range !");

        if(completeMarker)
            setComplete(stepIndex);

        setActive(stepIndex);
    }

    private void checkIsComplete(){
        if(stepList.size() < 2)
            throw new StepBarException("You must add minimum two steps !");
    }

    private void setParentLayout(){
        root.setWeightSum(stepList.size());
    }

    private void buildAndAddStepsViews(){
        for(int i = 0; i < stepList.size(); i++){
            Step step = stepList.get(i);

            if(i == 0) step.setFirst();
            else if(i == stepList.size()-1) step.setLast();

            final View stepView = step.buildView(getBaseContext());
            root.addView(stepView);
        }
    }

    private void setClickLister(){
        for(int i = 0; i < stepList.size(); i++){
            final int finalI = i;
            stepList.get(i).getStepView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onStepClick != null)
                        onStepClick.onStepChange(finalI);

                    setActiveStepIndex(finalI);
                }
            });
        }
    }

    private void setComplete(int stepIndex){
        for(int i = 0; i < stepList.size(); i++){
            Step step = stepList.get(i);

            if(i <= stepIndex) step.setComplete();
            else step.setIncomplete();
        }
    }

    private void setActive(int stepIndex){
        activeStepIndex = stepIndex;

        for(int i = 0; i < stepList.size(); i++){
            Step step = stepList.get(i);

            if(i == stepIndex) step.setActive();
            else step.setInactive();
        }
    }

    public static class StepBarException extends RuntimeException{
        StepBarException(String message) {
            super(message);
        }
    }
}
