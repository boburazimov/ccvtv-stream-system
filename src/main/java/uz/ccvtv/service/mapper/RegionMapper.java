package uz.ccvtv.service.mapper;

import org.mapstruct.*;
import uz.ccvtv.domain.Country;
import uz.ccvtv.domain.Region;
import uz.ccvtv.service.dto.CountryDTO;
import uz.ccvtv.service.dto.RegionDTO;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    RegionDTO toDto(Region s);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);
}
