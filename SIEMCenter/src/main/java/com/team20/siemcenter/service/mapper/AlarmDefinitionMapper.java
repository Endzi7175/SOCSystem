package com.team20.siemcenter.service.mapper;

import com.team20.siemcenter.domain.*;
import com.team20.siemcenter.service.dto.AlarmDefinitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AlarmDefinition and its DTO AlarmDefinitionDTO.
 */
@Mapper(componentModel = "spring", uses = {SiemLogTypeMapper.class})
public interface AlarmDefinitionMapper extends EntityMapper<AlarmDefinitionDTO, AlarmDefinition> {

    @Mapping(source = "siemLogType.id", target = "siemLogTypeId")
    AlarmDefinitionDTO toDto(AlarmDefinition alarmDefinition);

    @Mapping(source = "siemLogTypeId", target = "siemLogType")
    AlarmDefinition toEntity(AlarmDefinitionDTO alarmDefinitionDTO);

    default AlarmDefinition fromId(Long id) {
        if (id == null) {
            return null;
        }
        AlarmDefinition alarmDefinition = new AlarmDefinition();
        alarmDefinition.setId(id);
        return alarmDefinition;
    }
}
