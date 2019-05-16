package com.sbnz.SIEMAgent.FileWatcher.filter;

import com.sbnz.SIEMAgent.Log.LogEntry;


public interface DataFilter {

     boolean applyFilter(LogEntry entry);
}



