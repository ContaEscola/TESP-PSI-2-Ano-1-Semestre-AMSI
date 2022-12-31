package amsi.dei.estg.ipleiria.aerocontrol.utils;

import android.content.Context;
import android.util.AttributeSet;

import amsi.dei.estg.ipleiria.aerocontrol.R;

public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {

    private static final int[] STATE_ERROR = {R.attr.state_on_error};
    private boolean haveError = false;

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (haveError) {
            mergeDrawableStates(drawableState, STATE_ERROR);
        }
        return drawableState;
    }

    public void setHaveError(boolean haveError) {
        this.haveError = haveError;
        this.refreshDrawableState();
    }

    public void enableError(String errorMessage){
        if (this.haveError) return;
        this.setHaveError(true);
        this.setError(errorMessage);
    }

    public void disableError(){
        if (!this.haveError) return;
        this.setHaveError(false);
        this.setError(null);
    }
}
