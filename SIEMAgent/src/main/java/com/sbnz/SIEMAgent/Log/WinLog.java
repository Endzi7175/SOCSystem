package com.sbnz.SIEMAgent.Log;

import com.sbnz.SIEMAgent.FileWatcher.filter.DataFilter;

import java.util.List;
import java.util.Set;

public class WinLog extends Log {

    public String name;

    public WinLog(String name){
        this.name = name;
    }

    @Override
    public List<LogEntry> getNewEntries(Set<DataFilter> filters) {
        return null;
    }
}
