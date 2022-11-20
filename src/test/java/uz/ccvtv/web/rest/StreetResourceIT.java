package uz.ccvtv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uz.ccvtv.IntegrationTest;
import uz.ccvtv.domain.Street;
import uz.ccvtv.repository.StreetRepository;
import uz.ccvtv.service.dto.StreetDTO;
import uz.ccvtv.service.mapper.StreetMapper;

/**
 * Integration tests for the {@link StreetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StreetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/streets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StreetRepository streetRepository;

    @Autowired
    private StreetMapper streetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStreetMockMvc;

    private Street street;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Street createEntity(EntityManager em) {
        Street street = new Street().name(DEFAULT_NAME).info(DEFAULT_INFO);
        return street;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Street createUpdatedEntity(EntityManager em) {
        Street street = new Street().name(UPDATED_NAME).info(UPDATED_INFO);
        return street;
    }

    @BeforeEach
    public void initTest() {
        street = createEntity(em);
    }

    @Test
    @Transactional
    void createStreet() throws Exception {
        int databaseSizeBeforeCreate = streetRepository.findAll().size();
        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);
        restStreetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isCreated());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeCreate + 1);
        Street testStreet = streetList.get(streetList.size() - 1);
        assertThat(testStreet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStreet.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createStreetWithExistingId() throws Exception {
        // Create the Street with an existing ID
        street.setId(1L);
        StreetDTO streetDTO = streetMapper.toDto(street);

        int databaseSizeBeforeCreate = streetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStreetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = streetRepository.findAll().size();
        // set the field null
        street.setName(null);

        // Create the Street, which fails.
        StreetDTO streetDTO = streetMapper.toDto(street);

        restStreetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isBadRequest());

        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStreets() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList
        restStreetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(street.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getStreet() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get the street
        restStreetMockMvc
            .perform(get(ENTITY_API_URL_ID, street.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(street.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingStreet() throws Exception {
        // Get the street
        restStreetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStreet() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        int databaseSizeBeforeUpdate = streetRepository.findAll().size();

        // Update the street
        Street updatedStreet = streetRepository.findById(street.getId()).get();
        // Disconnect from session so that the updates on updatedStreet are not directly saved in db
        em.detach(updatedStreet);
        updatedStreet.name(UPDATED_NAME).info(UPDATED_INFO);
        StreetDTO streetDTO = streetMapper.toDto(updatedStreet);

        restStreetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, streetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isOk());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
        Street testStreet = streetList.get(streetList.size() - 1);
        assertThat(testStreet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStreet.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, streetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStreetWithPatch() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        int databaseSizeBeforeUpdate = streetRepository.findAll().size();

        // Update the street using partial update
        Street partialUpdatedStreet = new Street();
        partialUpdatedStreet.setId(street.getId());

        restStreetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStreet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStreet))
            )
            .andExpect(status().isOk());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
        Street testStreet = streetList.get(streetList.size() - 1);
        assertThat(testStreet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStreet.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateStreetWithPatch() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        int databaseSizeBeforeUpdate = streetRepository.findAll().size();

        // Update the street using partial update
        Street partialUpdatedStreet = new Street();
        partialUpdatedStreet.setId(street.getId());

        partialUpdatedStreet.name(UPDATED_NAME).info(UPDATED_INFO);

        restStreetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStreet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStreet))
            )
            .andExpect(status().isOk());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
        Street testStreet = streetList.get(streetList.size() - 1);
        assertThat(testStreet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStreet.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, streetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStreet() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        int databaseSizeBeforeDelete = streetRepository.findAll().size();

        // Delete the street
        restStreetMockMvc
            .perform(delete(ENTITY_API_URL_ID, street.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
