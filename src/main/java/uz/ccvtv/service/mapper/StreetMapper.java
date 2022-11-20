package uz.ccvtv.service.mapper;

import org.mapstruct.*;
import uz.ccvtv.domain.District;
import uz.ccvtv.domain.Street;
import uz.ccvtv.service.dto.DistrictDTO;
import uz.ccvtv.service.dto.StreetDTO;

/**
 * Mapper for the entity {@link Street} and its DTO {@link StreetDTO}.
 */
@Mapper(componentModel = "spring")
public interface StreetMapper extends EntityMapper<StreetDTO, Street> {
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    StreetDTO toDto(Street s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);
}
