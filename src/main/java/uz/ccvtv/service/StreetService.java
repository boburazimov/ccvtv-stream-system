package uz.ccvtv.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ccvtv.service.dto.StreetDTO;

/**
 * Service Interface for managing {@link uz.ccvtv.domain.Street}.
 */
public interface StreetService {
    /**
     * Save a street.
     *
     * @param streetDTO the entity to save.
     * @return the persisted entity.
     */
    StreetDTO save(StreetDTO streetDTO);

    /**
     * Updates a street.
     *
     * @param streetDTO the entity to update.
     * @return the persisted entity.
     */
    StreetDTO update(StreetDTO streetDTO);

    /**
     * Partially updates a street.
     *
     * @param streetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StreetDTO> partialUpdate(StreetDTO streetDTO);

    /**
     * Get all the streets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StreetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" street.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StreetDTO> findOne(Long id);

    /**
     * Delete the "id" street.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
