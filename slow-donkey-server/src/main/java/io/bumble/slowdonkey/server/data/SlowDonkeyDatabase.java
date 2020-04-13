package io.bumble.slowdonkey.server.data;

import io.bumble.slowdonkey.common.model.network.client2server.WriteRequest;
import io.bumble.slowdonkey.common.util.SingletonUtil;
import io.bumble.slowdonkey.server.persistence.Snapshot;
import io.bumble.slowdonkey.server.persistence.TxnLog;
import io.bumble.slowdonkey.server.persistence.TxnLogEntry;

/**
 * Database storing the data.
 * Store data in {@code dataTree} as memory store.
 * Store data in {@code dataStore} as persistence store.
 *
 * @author shenxiangyu on 2020/04/09
 */
public class SlowDonkeyDatabase {

    private DataTree dataTree = DataTree.getInstance();

    private Snapshot snapshot = Snapshot.getInstance();

    private TxnLog txnLog = TxnLog.getInstance();

    private boolean available;

    public static SlowDonkeyDatabase getInstance() {
        return SingletonUtil.getInstance(SlowDonkeyDatabase.class);
    }

    /**
     * Load data from disk.
     */
    public boolean load() {

        boolean loadSnapshotToDataTreeSuccess = snapshot.loadDataTreeFromSnapshotFile(dataTree);
        if (!loadSnapshotToDataTreeSuccess) {
            return false;
        }

        // Replay the transactions in transaction log to the data tree
        return txnLog.replayTransactionsToDataTree(dataTree);
    }

    /**
     * <ol>
     *     <li>Use the incoming bytes array to reload the data tree.</li>
     *     <li>Save the data tree to snapshot file.</li>
     * </ol>
     *
     * @param bytes data bytes array
     * @return true on success
     */
    public boolean receiveSnapshot(byte[] bytes) {

        // Reload data tree from the incoming bytes data
        boolean recoverDataTreeSuccess = dataTree.fromBytes(bytes);
        if (!recoverDataTreeSuccess) {
            return false;
        }

        // Save the data tree to snapshot file
        return snapshot.saveDataTreeToSnapshotFile(dataTree);
    }

    /**
     * Append transaction log entry to transaction log
     *
     * @param txnLogEntry log entry
     * @return true on success
     */
    public boolean append(TxnLogEntry txnLogEntry) {
        return txnLog.append(txnLogEntry);
    }

    /**
     * Append proposal data to transaction log
     *
     * @param request write request
     * @return true on success
     */
    public boolean propose(WriteRequest request) {
        return this.append(TxnLogEntry.getProposalLog(request));
    }

    /**
     * Append commit data to transaction log
     *
     * @param request write request
     * @return true on success
     */
    public boolean commit(WriteRequest request) {

        TxnLogEntry commitLogEntry = TxnLogEntry.getCommitLog(request);

        boolean appendSuccess = this.append(commitLogEntry);
        if (!appendSuccess) {
            return false;
        }

        // Update data tree
        return dataTree.update(commitLogEntry);
    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public DataTree getDataTree() {
        return dataTree;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
