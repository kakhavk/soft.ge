package hi.soft.ge;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by kakha on 11/28/16.
 */

public class StatusPanel extends TextView {

    public StatusPanel(Context context) {
        super(context);
        setStatus(null);
    }

    public StatusPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStatus(null);
    }

    private void setStatus(String date){
        setText("New");
    }
}
