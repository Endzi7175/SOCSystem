package com.team20.siemcenter.service.mapper;

import com.team20.siemcenter.domain.*;
import com.team20.siemcenter.service.dto.SiemLogTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SiemLogType and its DTO SiemLogTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SiemLogTypeMapper extends EntityMapper<SiemLogTypeDTO, SiemLogType> {



    default SiemLogType fromId(Long id) {
        if (id == null) {
            return null;
        }
        SiemLogType siemLogType = new SiemLogType();
        siemLogType.setId(id);
        return siemLogType;
    }
}
