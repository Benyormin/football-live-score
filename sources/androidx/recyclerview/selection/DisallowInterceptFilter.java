package androidx.recyclerview.selection;

import android.view.MotionEvent;
import androidx.recyclerview.widget.RecyclerView;

class DisallowInterceptFilter implements RecyclerView.OnItemTouchListener, Resettable {
    private final RecyclerView.OnItemTouchListener mDelegate;
    private boolean mDisallowIntercept;

    DisallowInterceptFilter(RecyclerView.OnItemTouchListener delegate) {
        this.mDelegate = delegate;
    }

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (this.mDisallowIntercept && MotionEvents.isActionDown(e)) {
            this.mDisallowIntercept = false;
        }
        if (this.mDisallowIntercept || !this.mDelegate.onInterceptTouchEvent(rv, e)) {
            return false;
        }
        return true;
    }

    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        this.mDelegate.onInterceptTouchEvent(rv, e);
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        this.mDisallowIntercept = true;
    }

    public boolean isResetRequired() {
        return this.mDisallowIntercept;
    }

    public void reset() {
        this.mDisallowIntercept = false;
    }
}
