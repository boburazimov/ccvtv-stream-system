package uz.ccvtv.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ccvtv.service.dto.TypeOfCameraDTO;

/**
 * Service Interface for managing {@link uz.ccvtv.domain.TypeOfCamera}.
 */
public interface TypeOfCameraService {
    /**
     * Save a typeOfCamera.
     *
     * @param typeOfCameraDTO the entity to save.
     * @return the persisted entity.
     */
    TypeOfCameraDTO save(TypeOfCameraDTO typeOfCameraDTO);

    /**
     * Updates a typeOfCamera.
     *
     * @param typeOfCameraDTO the entity to update.
     * @return the persisted entity.
     */
    TypeOfCameraDTO update(TypeOfCameraDTO typeOfCameraDTO);

    /**
     * Partially updates a typeOfCamera.
     *
     * @param typeOfCameraDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeOfCameraDTO> partialUpdate(TypeOfCameraDTO typeOfCameraDTO);

    /**
     * Get all the typeOfCameras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeOfCameraDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeOfCamera.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeOfCameraDTO> findOne(Long id);

    /**
     * Delete the "id" typeOfCamera.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
