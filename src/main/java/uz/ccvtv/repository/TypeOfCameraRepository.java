package uz.ccvtv.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.ccvtv.domain.TypeOfCamera;

/**
 * Spring Data JPA repository for the TypeOfCamera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeOfCameraRepository extends JpaRepository<TypeOfCamera, Long> {}
