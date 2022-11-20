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
import uz.ccvtv.repository.StreetRepository;
import uz.ccvtv.service.StreetService;
import uz.ccvtv.service.dto.StreetDTO;
import uz.ccvtv.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.ccvtv.domain.Street}.
 */
@RestController
@RequestMapping("/api")
public class StreetResource {

    private final Logger log = LoggerFactory.getLogger(StreetResource.class);

    private static final String ENTITY_NAME = "street";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StreetService streetService;

    private final StreetRepository streetRepository;

    public StreetResource(StreetService streetService, StreetRepository streetRepository) {
        this.streetService = streetService;
        this.streetRepository = streetRepository;
    }

    /**
     * {@code POST  /streets} : Create a new street.
     *
     * @param streetDTO the streetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new streetDTO, or with status {@code 400 (Bad Request)} if the street has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/streets")
    public ResponseEntity<StreetDTO> createStreet(@Valid @RequestBody StreetDTO streetDTO) throws URISyntaxException {
        log.debug("REST request to save Street : {}", streetDTO);
        if (streetDTO.getId() != null) {
            throw new BadRequestAlertException("A new street cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StreetDTO result = streetService.save(streetDTO);
        return ResponseEntity
            .created(new URI("/api/streets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /streets/:id} : Updates an existing street.
     *
     * @param id the id of the streetDTO to save.
     * @param streetDTO the streetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated streetDTO,
     * or with status {@code 400 (Bad Request)} if the streetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the streetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/streets/{id}")
    public ResponseEntity<StreetDTO> updateStreet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StreetDTO streetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Street : {}, {}", id, streetDTO);
        if (streetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, streetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!streetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StreetDTO result = streetService.update(streetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, streetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /streets/:id} : Partial updates given fields of an existing street, field will ignore if it is null
     *
     * @param id the id of the streetDTO to save.
     * @param streetDTO the streetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated streetDTO,
     * or with status {@code 400 (Bad Request)} if the streetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the streetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the streetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/streets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StreetDTO> partialUpdateStreet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StreetDTO streetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Street partially : {}, {}", id, streetDTO);
        if (streetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, streetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!streetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StreetDTO> result = streetService.partialUpdate(streetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, streetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /streets} : get all the streets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of streets in body.
     */
    @GetMapping("/streets")
    public ResponseEntity<List<StreetDTO>> getAllStreets(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Streets");
        Page<StreetDTO> page = streetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /streets/:id} : get the "id" street.
     *
     * @param id the id of the streetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the streetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/streets/{id}")
    public ResponseEntity<StreetDTO> getStreet(@PathVariable Long id) {
        log.debug("REST request to get Street : {}", id);
        Optional<StreetDTO> streetDTO = streetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(streetDTO);
    }

    /**
     * {@code DELETE  /streets/:id} : delete the "id" street.
     *
     * @param id the id of the streetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/streets/{id}")
    public ResponseEntity<Void> deleteStreet(@PathVariable Long id) {
        log.debug("REST request to delete Street : {}", id);
        streetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
