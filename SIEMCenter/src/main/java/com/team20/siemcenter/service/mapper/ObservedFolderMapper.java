package com.team20.siemcenter.service.mapper;

import com.team20.siemcenter.domain.*;
import com.team20.siemcenter.service.dto.ObservedFolderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ObservedFolder and its DTO ObservedFolderDTO.
 */
@Mapper(componentModel = "spring", uses = {SiemAgentConfigMapper.class, SiemLogTypeMapper.class})
public interface ObservedFolderMapper extends EntityMapper<ObservedFolderDTO, ObservedFolder> {

    @Mapping(source = "siemAgentConfig.id", target = "siemAgentConfigId")
    ObservedFolderDTO toDto(ObservedFolder observedFolder);

    @Mapping(source = "siemAgentConfigId", target = "siemAgentConfig")
    ObservedFolder toEntity(ObservedFolderDTO observedFolderDTO);

    default ObservedFolder fromId(Long id) {
        if (id == null) {
            return null;
        }
        ObservedFolder observedFolder = new ObservedFolder();
        observedFolder.setId(id);
        return observedFolder;
    }
}
