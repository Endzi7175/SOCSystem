package com.sbnz.SIEMAgent.FileWatcher;

import com.google.gson.annotations.Expose;
import com.sbnz.SIEMAgent.FileWatcher.filter.DataFilter;
import com.sbnz.SIEMAgent.Log.LogService;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Watcher extends Thread {
    @Expose
     String path ="";
    @Expose
     long batchTime=0;

    protected LogService logService;

    public void setLogService(LogService logService){
        this.logService=logService;
    }

    public Path getPath() {
        return Paths.get(path);
    }

    public abstract void addRegexFilter(String regexFilename, DataFilter filter);


}
