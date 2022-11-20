package uz.ccvtv.service.mapper;

import org.mapstruct.*;
import uz.ccvtv.domain.City;
import uz.ccvtv.domain.Region;
import uz.ccvtv.service.dto.CityDTO;
import uz.ccvtv.service.dto.RegionDTO;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {
    @Mapping(target = "regiob", source = "regiob", qualifiedByName = "regionId")
    CityDTO toDto(City s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);
}
