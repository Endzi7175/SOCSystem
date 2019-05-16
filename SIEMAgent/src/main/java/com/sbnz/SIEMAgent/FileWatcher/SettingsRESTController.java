package com.sbnz.SIEMAgent.FileWatcher;

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

    public ResponseEntity<?> addNewDirectoryWatch(@RequestBody() DirectoryDTO directoryDTO, @RequestBody(required = false) FilterDTO filterDTO)
    {
        try {
            Path path = Paths.get(directoryDTO.path);
            if(directoryDTO.path== null)
                throw new NullPointerException();
            if(filterDTO==null){
                filterDTO = new FilterDTO();
            }

            watchAgent.registerNewPath(path,directoryDTO.batch, filterDTO.filter, filterDTO.regex);
            return  new ResponseEntity<>(HttpStatus.OK);
        } catch (WatchAgent.WatchAgentException e) {
            e.printStackTrace();
        } catch (NullPointerException e){

        }
        return  new ResponseEntity<>(HttpStatus.OK);
    }

}
