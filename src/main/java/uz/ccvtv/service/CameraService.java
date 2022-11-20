package uz.ccvtv.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ccvtv.service.dto.CameraDTO;

/**
 * Service Interface for managing {@link uz.ccvtv.domain.Camera}.
 */
public interface CameraService {
    /**
     * Save a camera.
     *
     * @param cameraDTO the entity to save.
     * @return the persisted entity.
     */
    CameraDTO save(CameraDTO cameraDTO);

    /**
     * Updates a camera.
     *
     * @param cameraDTO the entity to update.
     * @return the persisted entity.
     */
    CameraDTO update(CameraDTO cameraDTO);

    /**
     * Partially updates a camera.
     *
     * @param cameraDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CameraDTO> partialUpdate(CameraDTO cameraDTO);

    /**
     * Get all the cameras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CameraDTO> findAll(Pageable pageable);

    /**
     * Get the "id" camera.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CameraDTO> findOne(Long id);

    /**
     * Delete the "id" camera.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
