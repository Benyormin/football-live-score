package androidx.recyclerview.selection;

import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;

final class TouchInputHandler<K> extends MotionInputHandler<K> {
    private static final String TAG = "TouchInputHandler";
    private final ItemDetailsLookup<K> mDetailsLookup;
    private final Runnable mGestureStarter;
    private final Runnable mHapticPerformer;
    private final Runnable mLongPressCallback;
    private final OnDragInitiatedListener mOnDragInitiatedListener;
    private final OnItemActivatedListener<K> mOnItemActivatedListener;
    private final SelectionTracker.SelectionPredicate<K> mSelectionPredicate;

    TouchInputHandler(SelectionTracker<K> selectionTracker, ItemKeyProvider<K> keyProvider, ItemDetailsLookup<K> detailsLookup, SelectionTracker.SelectionPredicate<K> selectionPredicate, Runnable gestureStarter, OnDragInitiatedListener onDragInitiatedListener, OnItemActivatedListener<K> onItemActivatedListener, FocusDelegate<K> focusDelegate, Runnable hapticPerformer, Runnable longPressCallback) {
        super(selectionTracker, keyProvider, focusDelegate);
        boolean z = true;
        Preconditions.checkArgument(detailsLookup != null);
        Preconditions.checkArgument(selectionPredicate != null);
        Preconditions.checkArgument(gestureStarter != null);
        Preconditions.checkArgument(onItemActivatedListener != null);
        Preconditions.checkArgument(onDragInitiatedListener != null);
        Preconditions.checkArgument(hapticPerformer == null ? false : z);
        this.mDetailsLookup = detailsLookup;
        this.mSelectionPredicate = selectionPredicate;
        this.mGestureStarter = gestureStarter;
        this.mOnItemActivatedListener = onItemActivatedListener;
        this.mOnDragInitiatedListener = onDragInitiatedListener;
        this.mHapticPerformer = hapticPerformer;
        this.mLongPressCallback = longPressCallback;
    }

    public boolean onSingleTapUp(MotionEvent e) {
        ItemDetailsLookup.ItemDetails<K> item = this.mDetailsLookup.getItemDetails(e);
        if (item == null || !item.hasSelectionKey()) {
            return this.mSelectionTracker.clearSelection();
        }
        if (this.mSelectionTracker.hasSelection()) {
            if (shouldExtendRange(e)) {
                extendSelectionRange(item);
                return true;
            } else if (this.mSelectionTracker.isSelected(item.getSelectionKey())) {
                this.mSelectionTracker.deselect(item.getSelectionKey());
                return true;
            } else {
                selectItem(item);
                return true;
            }
        } else if (item.inSelectionHotspot(e)) {
            return selectItem(item);
        } else {
            return this.mOnItemActivatedListener.onItemActivated(item, e);
        }
    }

    public boolean onDoubleTapEvent(MotionEvent e) {
        return MotionEvents.isActionUp(e) && onSingleTapUp(e);
    }

    public void onLongPress(MotionEvent e) {
        ItemDetailsLookup.ItemDetails<K> item;
        if (this.mDetailsLookup.overItemWithSelectionKey(e) && (item = this.mDetailsLookup.getItemDetails(e)) != null) {
            this.mLongPressCallback.run();
            if (shouldExtendRange(e)) {
                extendSelectionRange(item);
                this.mHapticPerformer.run();
            } else if (this.mSelectionTracker.isSelected(item.getSelectionKey())) {
                if (this.mOnDragInitiatedListener.onDragInitiated(e)) {
                    this.mHapticPerformer.run();
                }
            } else if (this.mSelectionPredicate.canSetStateForKey(item.getSelectionKey(), true) && selectItem(item)) {
                if (this.mSelectionPredicate.canSelectMultiple() && this.mSelectionTracker.isRangeActive()) {
                    this.mGestureStarter.run();
                }
                this.mHapticPerformer.run();
            }
        }
    }
}
