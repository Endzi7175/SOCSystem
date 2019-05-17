package com.sbnz.SIEMAgent.Log;

import com.sbnz.SIEMAgent.FileWatcher.filter.DataFilter;
import com.sbnz.SIEMAgent.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LogService {

    @Autowired
    private LogRepository repository;

    @Autowired
    private PostService postService;

    public  LogService (){
    }

    public FileLog getFileLogByPath(String logPath){
        FileLog log = repository.findByPathOrName(logPath, FileLog.class);

        if(log==null){
            log = new FileLog(logPath);
            repository.save(log);
        } else{
            System.err.println("Found log with path :" +logPath);
        }
        return log;

    }
    public WinLog getWinLogByPath(String name){
        WinLog log = repository.findByPathOrName(logPath, FileLog.class);

        if(log==null){
            log = new WinLog(name);
            repository.save(log);
        } else{
            System.err.println("Found log with path :" +logPath);
        }
        return log;

    }

    public void processNewEntries(FileLog log, Set<DataFilter> filters){
            List<LogEntry> newEntries = log.getNewEntries(filters);
            if(newEntries.size()>0){
                postService.sendEntriesToSIEM(newEntries);
                repository.save(log);
            }

    }

}
