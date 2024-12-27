package androidx.recyclerview.selection;

import android.util.Log;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

public class EventBridge {
    private static final String TAG = "EventsRelays";

    public static <K> void install(RecyclerView.Adapter<?> adapter, SelectionTracker<K> selectionTracker, ItemKeyProvider<K> keyProvider, Consumer<Runnable> runner) {
        new TrackerToAdapterBridge(selectionTracker, keyProvider, adapter, runner);
        adapter.registerAdapterDataObserver(selectionTracker.getAdapterDataObserver());
    }

    private static final class TrackerToAdapterBridge<K> extends SelectionTracker.SelectionObserver<K> {
        final RecyclerView.Adapter<?> mAdapter;
        private final ItemKeyProvider<K> mKeyProvider;
        private final Consumer<Runnable> mRunner;

        TrackerToAdapterBridge(SelectionTracker<K> selectionTracker, ItemKeyProvider<K> keyProvider, RecyclerView.Adapter<?> adapter, Consumer<Runnable> runner) {
            selectionTracker.addObserver(this);
            boolean z = true;
            Preconditions.checkArgument(keyProvider != null);
            Preconditions.checkArgument(adapter != null);
            Preconditions.checkArgument(runner == null ? false : z);
            this.mKeyProvider = keyProvider;
            this.mAdapter = adapter;
            this.mRunner = runner;
        }

        public void onItemStateChanged(K key, boolean selected) {
            final int position = this.mKeyProvider.getPosition(key);
            if (position < 0) {
                Log.w(EventBridge.TAG, "Item change notification received for unknown item: " + key);
                return;
            }
            this.mRunner.accept(new Runnable() {
                public void run() {
                    TrackerToAdapterBridge.this.mAdapter.notifyItemChanged(position, SelectionTracker.SELECTION_CHANGED_MARKER);
                }
            });
        }
    }

    private EventBridge() {
    }
}
