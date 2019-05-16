package com.sbnz.SIEMAgent.FileWatcher.filter;

import com.sbnz.SIEMAgent.Log.LogEntry;


public abstract class DataFilter {
    public final FilterSignature signature;
    public DataFilter(FilterSignature signature) {
        this.signature = signature;
    }
    abstract boolean applyFilter(LogEntry entry);
}



