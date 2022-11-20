package uz.ccvtv.service.mapper;

import org.mapstruct.*;
import uz.ccvtv.domain.Location;
import uz.ccvtv.domain.Street;
import uz.ccvtv.service.dto.LocationDTO;
import uz.ccvtv.service.dto.StreetDTO;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {
    @Mapping(target = "street", source = "street", qualifiedByName = "streetId")
    LocationDTO toDto(Location s);

    @Named("streetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StreetDTO toDtoStreetId(Street street);
}
