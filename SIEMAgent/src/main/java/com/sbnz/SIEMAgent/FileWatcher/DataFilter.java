package com.sbnz.SIEMAgent.FileWatcher;

import com.sbnz.SIEMAgent.Log.LogEntry;


public interface DataFilter {
     boolean applyFilter(LogEntry entry);
}



