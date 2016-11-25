package pl.linean.stepbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by Maciej Sady (linean) on 25.11.2016.
 */

@SuppressWarnings("unused")
public class Step {

    /**
     * Variables with sample initialization
     */
    private String stepMarker = "1";
    private String stepText = "";
    private int inactiveColor = Color.parseColor("#bbb7c9");
    private int activeColor = Color.parseColor("#1d99c5");
    private int completeColor = Color.parseColor("#f4e4ec");
    private int markerColor = 0;
    private int textColor = 0;
    private boolean isFirst = false;
    private boolean isLast = false;

    private TextView stepMarkerField;
    private TextView stepTextField;
    private View leftLine;
    private View rightLine;
    private LinearLayout stepActiveBackground;
    private GradientDrawable completeBackground;
    private GradientDrawable incompleteBackground;
    private GradientDrawable activeBackground;
    private GradientDrawable inactiveBackground;

    private View stepView;

    public Step withMarker(@NonNull String marker){
        this.stepMarker = marker;
        return this;
    }

    public Step withText(@NonNull String text){
        this.stepText = text;
        return this;
    }

    public Step withActiveColor(@ColorInt int color){
        this.activeColor = color;
        return this;
    }

    public Step withInactiveColor(@ColorInt int color){
        this.inactiveColor = color;
        return this;
    }

    public Step withCompleteColor(@ColorInt int color){
        this.completeColor = color;
        return this;
    }

    public Step withMarkerColor(@ColorInt int color){
        this.markerColor = color;
        return this;
    }

    public Step withTextColor(@ColorInt int color){
        this.textColor = color;
        return this;
    }

    View getStepView(){
        return stepView;
    }

    void setFirst(){
        isFirst = true;
    }

    void setLast(){
        isLast = true;
    }

    View buildView(Context context){
        inflateView(context);
        initLayout(stepView);
        initBackground();
        setLayout();

        return stepView;
    }

    @SuppressLint("InflateParams")
    private void inflateView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        stepView = inflater.inflate(R.layout.step, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1);
        stepView.setLayoutParams(params);
    }

    private void initLayout(View root){
        stepMarkerField = (TextView) root.findViewById(R.id.stepMarker);
        stepTextField = (TextView) root.findViewById(R.id.stepText);
        RelativeLayout stepHolder = (RelativeLayout) root.findViewById(R.id.stepHolder);
        stepActiveBackground = (LinearLayout) root.findViewById(R.id.stepActiveBackground);
        leftLine = root.findViewById(R.id.leftLine);
        rightLine = root.findViewById(R.id.rightLine);

        if(stepMarkerField == null || stepTextField == null || stepHolder == null || stepActiveBackground == null || rightLine == null || leftLine == null)
            throw new StepBar.StepBarException("Incomplete step view initialization !");
    }

    @SuppressWarnings("ConstantConditions")
    private void initBackground(){
        try {
            Drawable stepBackground = stepMarkerField.getBackground();
            completeBackground = (GradientDrawable) stepBackground.getConstantState().newDrawable();
            completeBackground.setColor(completeColor);

            incompleteBackground = (GradientDrawable) stepBackground.getConstantState().newDrawable();
            incompleteBackground.setColor(inactiveColor);

            Drawable background = stepActiveBackground.getBackground();
            activeBackground = (GradientDrawable) background.getConstantState().newDrawable();
            activeBackground.setColor(activeColor);

            inactiveBackground = (GradientDrawable) background.getConstantState().newDrawable();
            inactiveBackground.setColor(inactiveColor);
        } catch (Exception e){
            throw new StepBar.StepBarException("Incomplete step background initialization !");
        }
    }

    private void setLayout(){
        if(isFirst) leftLine.setVisibility(INVISIBLE);
        else if(isLast) rightLine.setVisibility(INVISIBLE);

        if(stepText.length() > 0) {
            stepTextField.setVisibility(VISIBLE);
            stepTextField.setText(stepText);

        } else
            stepTextField.setVisibility(INVISIBLE);

        if(textColor != 0)
            stepTextField.setTextColor(textColor);

        if(markerColor != 0)
            stepMarkerField.setTextColor(markerColor);

        stepMarkerField.setText(stepMarker);
        setInactive();
        setIncomplete();
    }

    void setIncomplete(){
        stepMarkerField.setBackground(incompleteBackground);
    }

    void setComplete(){
        stepMarkerField.setBackground(completeBackground);
    }

    void setActive(){
        stepActiveBackground.setBackground(activeBackground);
    }

    void setInactive(){
        stepActiveBackground.setBackground(inactiveBackground);
    }
}
