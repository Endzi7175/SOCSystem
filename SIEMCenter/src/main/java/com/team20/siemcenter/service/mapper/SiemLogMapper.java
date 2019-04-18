package com.team20.siemcenter.service.mapper;

import com.team20.siemcenter.domain.*;
import com.team20.siemcenter.service.dto.SiemLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SiemLog and its DTO SiemLogDTO.
 */
@Mapper(componentModel = "spring", uses = {SiemLogTypeMapper.class, SiemAgentMapper.class})
public interface SiemLogMapper extends EntityMapper<SiemLogDTO, SiemLog> {

    @Mapping(source = "siemLogType.id", target = "siemLogTypeId")
    @Mapping(source = "siemAgent.id", target = "siemAgentId")
    SiemLogDTO toDto(SiemLog siemLog);

    @Mapping(source = "siemLogTypeId", target = "siemLogType")
    @Mapping(source = "siemAgentId", target = "siemAgent")
    SiemLog toEntity(SiemLogDTO siemLogDTO);

    default SiemLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        SiemLog siemLog = new SiemLog();
        siemLog.setId(id);
        return siemLog;
    }
}
