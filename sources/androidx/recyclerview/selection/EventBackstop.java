package androidx.recyclerview.selection;

import android.view.MotionEvent;
import androidx.recyclerview.widget.RecyclerView;

class EventBackstop implements RecyclerView.OnItemTouchListener, Resettable {
    private boolean mLongPressFired;

    EventBackstop() {
    }

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (!MotionEvents.isActionUp(e) || !this.mLongPressFired) {
            if (MotionEvents.isActionDown(e) && isResetRequired()) {
                reset();
            }
            return false;
        }
        this.mLongPressFired = false;
        return true;
    }

    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        throw new UnsupportedOperationException("Wrap me in an InterceptFilter.");
    }

    public boolean isResetRequired() {
        return this.mLongPressFired;
    }

    public void reset() {
        this.mLongPressFired = false;
    }

    /* access modifiers changed from: package-private */
    public void onLongPress() {
        this.mLongPressFired = true;
    }
}
