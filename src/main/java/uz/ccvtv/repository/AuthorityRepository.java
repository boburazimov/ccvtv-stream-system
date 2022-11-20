package uz.ccvtv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ccvtv.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
