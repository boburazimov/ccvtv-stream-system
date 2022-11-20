package uz.ccvtv.service.mapper;

import org.mapstruct.*;
import uz.ccvtv.domain.TypeOfCamera;
import uz.ccvtv.service.dto.TypeOfCameraDTO;

/**
 * Mapper for the entity {@link TypeOfCamera} and its DTO {@link TypeOfCameraDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeOfCameraMapper extends EntityMapper<TypeOfCameraDTO, TypeOfCamera> {}
