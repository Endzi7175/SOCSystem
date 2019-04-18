package com.team20.siemcenter.service.mapper;

import com.team20.siemcenter.domain.*;
import com.team20.siemcenter.service.dto.SiemAgentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SiemAgent and its DTO SiemAgentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SiemAgentMapper extends EntityMapper<SiemAgentDTO, SiemAgent> {



    default SiemAgent fromId(Long id) {
        if (id == null) {
            return null;
        }
        SiemAgent siemAgent = new SiemAgent();
        siemAgent.setId(id);
        return siemAgent;
    }
}
