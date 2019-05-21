package com.sbnz.SIEMAgent.Log;

import com.sbnz.SIEMAgent.FileWatcher.filter.DataFilter;
import com.sbnz.SIEMAgent.PostService;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class LogService {

    @Autowired
    LogRepository repository;

    @Autowired
    private PostService postService;

    public  LogService (){
    }

    public Log getLogByPath(String logPath){
        Log log = repository.findByPath(logPath);

        if(log==null){
            log = new Log(logPath);
            repository.save(log);
        } else{
            System.err.println("Found log with path :" +logPath);
            System.err.println(log.lastLine);
        }
        return log;

    }

    public void processNewEntries(Log log, Set<DataFilter> filters){
        System.err.println("Started reading of log: " + log.path);

        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(new File(log.path))) {
            List<LogEntry> newEntries = new ArrayList<>();
            String line;
            String firstLine = null;
            while ((line = reader.readLine()) != null ){
                if(firstLine==null){
                    firstLine = line;
                }
                if(line.equals(log.lastLine))
                    break;
                LogEntry entry = new LogEntry(line);
                if (logEntryApplyFilters(filters, entry)) {
                    newEntries.add(entry);
                }
            }
            if(newEntries.size()>0){
                postService.sendEntriesToSIEM(newEntries);
                log.lastLine = firstLine;
                repository.save(log);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean logEntryApplyFilters(Set<DataFilter> filters, LogEntry entry) {
        for(DataFilter filter : filters){
            if(filter.applyFilter(entry)){
                return  true;
            }
        }

        return filters.size() == 0;
    }

    private boolean checkRegex(String line, Set<String> dataRegex)
    {
        if(dataRegex.isEmpty())
            return true;
        for(String regex : dataRegex){
            if(line.matches(regex)){
                return true;
            }
        }
        return  false;
    }

}
