package androidx.recyclerview.selection;

import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;

final class EventRouter implements RecyclerView.OnItemTouchListener, Resettable {
    private final ToolHandlerRegistry<RecyclerView.OnItemTouchListener> mDelegates = new ToolHandlerRegistry<>(new DummyOnItemTouchListener());
    private boolean mDisallowIntercept;

    EventRouter() {
    }

    /* access modifiers changed from: package-private */
    public void set(int toolType, RecyclerView.OnItemTouchListener delegate) {
        Preconditions.checkArgument(delegate != null);
        this.mDelegates.set(toolType, delegate);
    }

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (this.mDisallowIntercept && MotionEvents.isActionDown(e)) {
            this.mDisallowIntercept = false;
        }
        if (this.mDisallowIntercept || !this.mDelegates.get(e).onInterceptTouchEvent(rv, e)) {
            return false;
        }
        return true;
    }

    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        if (!this.mDisallowIntercept) {
            this.mDelegates.get(e).onTouchEvent(rv, e);
        }
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            this.mDisallowIntercept = disallowIntercept;
        }
    }

    public boolean isResetRequired() {
        return this.mDisallowIntercept;
    }

    public void reset() {
        this.mDisallowIntercept = false;
    }
}
