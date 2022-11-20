package uz.ccvtv.service.mapper;

import org.mapstruct.*;
import uz.ccvtv.domain.Camera;
import uz.ccvtv.domain.Location;
import uz.ccvtv.domain.TypeOfCamera;
import uz.ccvtv.service.dto.CameraDTO;
import uz.ccvtv.service.dto.LocationDTO;
import uz.ccvtv.service.dto.TypeOfCameraDTO;

/**
 * Mapper for the entity {@link Camera} and its DTO {@link CameraDTO}.
 */
@Mapper(componentModel = "spring")
public interface CameraMapper extends EntityMapper<CameraDTO, Camera> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    @Mapping(target = "typeOfCamera", source = "typeOfCamera", qualifiedByName = "typeOfCameraId")
    CameraDTO toDto(Camera s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("typeOfCameraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TypeOfCameraDTO toDtoTypeOfCameraId(TypeOfCamera typeOfCamera);
}
