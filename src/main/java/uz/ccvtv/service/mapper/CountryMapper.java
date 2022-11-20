package uz.ccvtv.service.mapper;

import org.mapstruct.*;
import uz.ccvtv.domain.Country;
import uz.ccvtv.service.dto.CountryDTO;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {}
