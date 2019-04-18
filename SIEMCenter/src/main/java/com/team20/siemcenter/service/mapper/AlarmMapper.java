package com.team20.siemcenter.service.mapper;

import com.team20.siemcenter.domain.*;
import com.team20.siemcenter.service.dto.AlarmDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Alarm and its DTO AlarmDTO.
 */
@Mapper(componentModel = "spring", uses = {SiemAgentMapper.class, AlarmDefinitionMapper.class})
public interface AlarmMapper extends EntityMapper<AlarmDTO, Alarm> {

    @Mapping(source = "siemAgent.id", target = "siemAgentId")
    @Mapping(source = "alarmDefinition.id", target = "alarmDefinitionId")
    AlarmDTO toDto(Alarm alarm);

    @Mapping(source = "siemAgentId", target = "siemAgent")
    @Mapping(source = "alarmDefinitionId", target = "alarmDefinition")
    Alarm toEntity(AlarmDTO alarmDTO);

    default Alarm fromId(Long id) {
        if (id == null) {
            return null;
        }
        Alarm alarm = new Alarm();
        alarm.setId(id);
        return alarm;
    }
}
