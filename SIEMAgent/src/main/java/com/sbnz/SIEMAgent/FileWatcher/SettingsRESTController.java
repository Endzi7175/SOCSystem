package com.sbnz.SIEMAgent.FileWatcher;

import com.sbnz.SIEMAgent.FileWatcher.filter.DataFilterFactory;
import com.sbnz.SIEMAgent.Log.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController("agent")
public class SettingsRESTController {

    @Autowired
    WatchAgent watchAgent;

    @Autowired
    DataFilterFactory filterFactory;

    public ResponseEntity<?> updateOrCreateDirectorySettings(@RequestBody(required =  false) DirectoryDTO directoryDTO, @RequestBody(required = false) FilterDTO filterDTO)
    {
        try {
            Path path;
            if(directoryDTO!=null){
                path = Paths.get(directoryDTO.path);
                watchAgent.registerNewPath(path, directoryDTO.batch);
            }
            if(filterDTO!=null){
                if(filterDTO.path==null || filterDTO.regex==null ){
                    path = Paths.get(filterDTO.path);
                    watchAgent.addRegexFilter(path, filterDTO.regex, filterFactory.generateDataFilter(filterDTO.seed));
                }
            }
        } catch (WatchAgent.WatchAgentException e) {
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException e){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<>(HttpStatus.OK);
    }

}
