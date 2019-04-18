package com.team20.siemcenter.service.mapper;

import com.team20.siemcenter.domain.*;
import com.team20.siemcenter.service.dto.SiemAgentConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SiemAgentConfig and its DTO SiemAgentConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {SiemAgentMapper.class})
public interface SiemAgentConfigMapper extends EntityMapper<SiemAgentConfigDTO, SiemAgentConfig> {

    @Mapping(source = "siemAgent.id", target = "siemAgentId")
    SiemAgentConfigDTO toDto(SiemAgentConfig siemAgentConfig);

    @Mapping(target = "observedfolders", ignore = true)
    @Mapping(source = "siemAgentId", target = "siemAgent")
    SiemAgentConfig toEntity(SiemAgentConfigDTO siemAgentConfigDTO);

    default SiemAgentConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        SiemAgentConfig siemAgentConfig = new SiemAgentConfig();
        siemAgentConfig.setId(id);
        return siemAgentConfig;
    }
}
