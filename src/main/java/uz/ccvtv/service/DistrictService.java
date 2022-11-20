package uz.ccvtv.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ccvtv.service.dto.DistrictDTO;

/**
 * Service Interface for managing {@link uz.ccvtv.domain.District}.
 */
public interface DistrictService {
    /**
     * Save a district.
     *
     * @param districtDTO the entity to save.
     * @return the persisted entity.
     */
    DistrictDTO save(DistrictDTO districtDTO);

    /**
     * Updates a district.
     *
     * @param districtDTO the entity to update.
     * @return the persisted entity.
     */
    DistrictDTO update(DistrictDTO districtDTO);

    /**
     * Partially updates a district.
     *
     * @param districtDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DistrictDTO> partialUpdate(DistrictDTO districtDTO);

    /**
     * Get all the districts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DistrictDTO> findAll(Pageable pageable);

    /**
     * Get the "id" district.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistrictDTO> findOne(Long id);

    /**
     * Delete the "id" district.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
