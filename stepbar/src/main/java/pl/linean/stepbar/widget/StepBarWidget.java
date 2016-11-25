package pl.linean.stepbar.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import pl.linean.stepbar.R;

/**
 * Created by Maciej Sady (linean) on 23.08.2016.
 */
public class StepBarWidget extends LinearLayout {

    private View root;
    public StepBarWidget(Context context) {
        super(context);
        initialize(context);
    }

    public StepBarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context){
        if(isInEditMode())
            inflate(context, R.layout.step_widget_edit_mode, this);
        else
            root = inflate(context, R.layout.step_widget, this);
    }

    public View getRoot(){
        return root.findViewById(R.id.step_bar);
    }
}
