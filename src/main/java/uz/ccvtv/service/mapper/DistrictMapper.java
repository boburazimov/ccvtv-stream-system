package uz.ccvtv.service.mapper;

import org.mapstruct.*;
import uz.ccvtv.domain.City;
import uz.ccvtv.domain.District;
import uz.ccvtv.service.dto.CityDTO;
import uz.ccvtv.service.dto.DistrictDTO;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    DistrictDTO toDto(District s);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);
}
