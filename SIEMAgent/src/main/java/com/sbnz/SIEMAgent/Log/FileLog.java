package com.sbnz.SIEMAgent.Log;

import com.sbnz.SIEMAgent.FileWatcher.filter.DataFilter;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileLog extends Log {

    String path;
    private String lastLine;
    public FileLog(){
    }
    public FileLog(String path){
        this.path=path;
    }

    @Override
    public List<LogEntry> getNewEntries(Set<DataFilter> filters) {
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(new File(path))) {
            List<LogEntry> newEntries = new ArrayList<>();
            String line;
            String firstLine = null;
            while ((line = reader.readLine()) != null ){
                if(firstLine==null){
                    firstLine = line;
                }
                if(line.equals(lastLine))
                    break;
                LogEntry entry = new LogEntry(line);
                if (logEntryApplyFilters(filters, entry)) {
                    newEntries.add(entry);
                }
            }
            lastLine = line;
            return newEntries;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ArrayList<>();
    }
}
