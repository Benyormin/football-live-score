package androidx.recyclerview.selection;

import android.view.GestureDetector;
import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;

final class GestureDetectorWrapper implements RecyclerView.OnItemTouchListener, Resettable {
    private final GestureDetector mDetector;
    private boolean mDisallowIntercept;

    GestureDetectorWrapper(GestureDetector detector) {
        Preconditions.checkArgument(detector != null);
        this.mDetector = detector;
    }

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (this.mDisallowIntercept && MotionEvents.isActionDown(e)) {
            this.mDisallowIntercept = false;
        }
        if (this.mDisallowIntercept || !this.mDetector.onTouchEvent(e)) {
            return false;
        }
        return true;
    }

    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            this.mDisallowIntercept = disallowIntercept;
            sendCancelEvent();
        }
    }

    public boolean isResetRequired() {
        return true;
    }

    public void reset() {
        this.mDisallowIntercept = false;
        sendCancelEvent();
    }

    private void sendCancelEvent() {
        this.mDetector.onTouchEvent(MotionEvents.createCancelEvent());
    }
}
