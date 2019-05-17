package com.sbnz.SIEMAgent.Log;

import com.sbnz.SIEMAgent.FileWatcher.filter.DataFilter;

import java.util.List;
import java.util.Set;

public abstract class Log {
    public abstract List<LogEntry> getNewEntries(Set<DataFilter> filters);
    protected boolean logEntryApplyFilters(Set<DataFilter> filters, LogEntry entry) {
        for(DataFilter filter : filters){
            if(filter.applyFilter(entry)){
                return  true;
            }
        }
        return  false;
    }
}
