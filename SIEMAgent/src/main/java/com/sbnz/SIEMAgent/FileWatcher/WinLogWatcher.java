package com.sbnz.SIEMAgent.FileWatcher;
import com.sbnz.SIEMAgent.FileWatcher.filter.DataFilter;
import com.sbnz.SIEMAgent.Log.Log;
import com.sbnz.SIEMAgent.Log.LogEntry;
import com.sbnz.SIEMAgent.Log.WinLog;
import com.sun.jna.platform.win32.Advapi32Util;

public class WinLogWatcher extends  Watcher {
    private Advapi32Util.EventLogIterator iter = new Advapi32Util.EventLogIterator("Security");
String name;
    @Override
    public void run(){
        WinLog log = logService.getWinLogByPath(name);

        try {
            while (true){
                while(iter.hasNext()) {
                    Advapi32Util.EventLogRecord record = iter.next();

                    logService.processNewEntries( log,  );

                }
                if(batchTime>0){

                        Thread.sleep(batchTime);

                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void addRegexFilter(String regexFilename, DataFilter filter) {
        
    }
}
