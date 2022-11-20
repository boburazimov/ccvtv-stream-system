package uz.ccvtv.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.ccvtv.repository.TypeOfCameraRepository;
import uz.ccvtv.service.TypeOfCameraService;
import uz.ccvtv.service.dto.TypeOfCameraDTO;
import uz.ccvtv.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.ccvtv.domain.TypeOfCamera}.
 */
@RestController
@RequestMapping("/api")
public class TypeOfCameraResource {

    private final Logger log = LoggerFactory.getLogger(TypeOfCameraResource.class);

    private static final String ENTITY_NAME = "typeOfCamera";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeOfCameraService typeOfCameraService;

    private final TypeOfCameraRepository typeOfCameraRepository;

    public TypeOfCameraResource(TypeOfCameraService typeOfCameraService, TypeOfCameraRepository typeOfCameraRepository) {
        this.typeOfCameraService = typeOfCameraService;
        this.typeOfCameraRepository = typeOfCameraRepository;
    }

    /**
     * {@code POST  /type-of-cameras} : Create a new typeOfCamera.
     *
     * @param typeOfCameraDTO the typeOfCameraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeOfCameraDTO, or with status {@code 400 (Bad Request)} if the typeOfCamera has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-of-cameras")
    public ResponseEntity<TypeOfCameraDTO> createTypeOfCamera(@Valid @RequestBody TypeOfCameraDTO typeOfCameraDTO)
        throws URISyntaxException {
        log.debug("REST request to save TypeOfCamera : {}", typeOfCameraDTO);
        if (typeOfCameraDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeOfCamera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeOfCameraDTO result = typeOfCameraService.save(typeOfCameraDTO);
        return ResponseEntity
            .created(new URI("/api/type-of-cameras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-of-cameras/:id} : Updates an existing typeOfCamera.
     *
     * @param id the id of the typeOfCameraDTO to save.
     * @param typeOfCameraDTO the typeOfCameraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfCameraDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfCameraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeOfCameraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-of-cameras/{id}")
    public ResponseEntity<TypeOfCameraDTO> updateTypeOfCamera(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TypeOfCameraDTO typeOfCameraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeOfCamera : {}, {}", id, typeOfCameraDTO);
        if (typeOfCameraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfCameraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfCameraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeOfCameraDTO result = typeOfCameraService.update(typeOfCameraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeOfCameraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-of-cameras/:id} : Partial updates given fields of an existing typeOfCamera, field will ignore if it is null
     *
     * @param id the id of the typeOfCameraDTO to save.
     * @param typeOfCameraDTO the typeOfCameraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfCameraDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfCameraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeOfCameraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeOfCameraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-of-cameras/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeOfCameraDTO> partialUpdateTypeOfCamera(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TypeOfCameraDTO typeOfCameraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeOfCamera partially : {}, {}", id, typeOfCameraDTO);
        if (typeOfCameraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfCameraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfCameraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeOfCameraDTO> result = typeOfCameraService.partialUpdate(typeOfCameraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeOfCameraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-of-cameras} : get all the typeOfCameras.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeOfCameras in body.
     */
    @GetMapping("/type-of-cameras")
    public ResponseEntity<List<TypeOfCameraDTO>> getAllTypeOfCameras(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TypeOfCameras");
        Page<TypeOfCameraDTO> page = typeOfCameraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-of-cameras/:id} : get the "id" typeOfCamera.
     *
     * @param id the id of the typeOfCameraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeOfCameraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-of-cameras/{id}")
    public ResponseEntity<TypeOfCameraDTO> getTypeOfCamera(@PathVariable Long id) {
        log.debug("REST request to get TypeOfCamera : {}", id);
        Optional<TypeOfCameraDTO> typeOfCameraDTO = typeOfCameraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeOfCameraDTO);
    }

    /**
     * {@code DELETE  /type-of-cameras/:id} : delete the "id" typeOfCamera.
     *
     * @param id the id of the typeOfCameraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-of-cameras/{id}")
    public ResponseEntity<Void> deleteTypeOfCamera(@PathVariable Long id) {
        log.debug("REST request to delete TypeOfCamera : {}", id);
        typeOfCameraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
