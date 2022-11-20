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
import uz.ccvtv.domain.TypeOfCamera;
import uz.ccvtv.repository.TypeOfCameraRepository;
import uz.ccvtv.service.dto.TypeOfCameraDTO;
import uz.ccvtv.service.mapper.TypeOfCameraMapper;

/**
 * Integration tests for the {@link TypeOfCameraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeOfCameraResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-of-cameras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeOfCameraRepository typeOfCameraRepository;

    @Autowired
    private TypeOfCameraMapper typeOfCameraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeOfCameraMockMvc;

    private TypeOfCamera typeOfCamera;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfCamera createEntity(EntityManager em) {
        TypeOfCamera typeOfCamera = new TypeOfCamera().name(DEFAULT_NAME).info(DEFAULT_INFO);
        return typeOfCamera;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfCamera createUpdatedEntity(EntityManager em) {
        TypeOfCamera typeOfCamera = new TypeOfCamera().name(UPDATED_NAME).info(UPDATED_INFO);
        return typeOfCamera;
    }

    @BeforeEach
    public void initTest() {
        typeOfCamera = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeOfCamera() throws Exception {
        int databaseSizeBeforeCreate = typeOfCameraRepository.findAll().size();
        // Create the TypeOfCamera
        TypeOfCameraDTO typeOfCameraDTO = typeOfCameraMapper.toDto(typeOfCamera);
        restTypeOfCameraMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeOfCameraDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TypeOfCamera in the database
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeCreate + 1);
        TypeOfCamera testTypeOfCamera = typeOfCameraList.get(typeOfCameraList.size() - 1);
        assertThat(testTypeOfCamera.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTypeOfCamera.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createTypeOfCameraWithExistingId() throws Exception {
        // Create the TypeOfCamera with an existing ID
        typeOfCamera.setId(1L);
        TypeOfCameraDTO typeOfCameraDTO = typeOfCameraMapper.toDto(typeOfCamera);

        int databaseSizeBeforeCreate = typeOfCameraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeOfCameraMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeOfCameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfCamera in the database
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeOfCameraRepository.findAll().size();
        // set the field null
        typeOfCamera.setName(null);

        // Create the TypeOfCamera, which fails.
        TypeOfCameraDTO typeOfCameraDTO = typeOfCameraMapper.toDto(typeOfCamera);

        restTypeOfCameraMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeOfCameraDTO))
            )
            .andExpect(status().isBadRequest());

        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeOfCameras() throws Exception {
        // Initialize the database
        typeOfCameraRepository.saveAndFlush(typeOfCamera);

        // Get all the typeOfCameraList
        restTypeOfCameraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeOfCamera.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getTypeOfCamera() throws Exception {
        // Initialize the database
        typeOfCameraRepository.saveAndFlush(typeOfCamera);

        // Get the typeOfCamera
        restTypeOfCameraMockMvc
            .perform(get(ENTITY_API_URL_ID, typeOfCamera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeOfCamera.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingTypeOfCamera() throws Exception {
        // Get the typeOfCamera
        restTypeOfCameraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeOfCamera() throws Exception {
        // Initialize the database
        typeOfCameraRepository.saveAndFlush(typeOfCamera);

        int databaseSizeBeforeUpdate = typeOfCameraRepository.findAll().size();

        // Update the typeOfCamera
        TypeOfCamera updatedTypeOfCamera = typeOfCameraRepository.findById(typeOfCamera.getId()).get();
        // Disconnect from session so that the updates on updatedTypeOfCamera are not directly saved in db
        em.detach(updatedTypeOfCamera);
        updatedTypeOfCamera.name(UPDATED_NAME).info(UPDATED_INFO);
        TypeOfCameraDTO typeOfCameraDTO = typeOfCameraMapper.toDto(updatedTypeOfCamera);

        restTypeOfCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfCameraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeOfCameraDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfCamera in the database
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeUpdate);
        TypeOfCamera testTypeOfCamera = typeOfCameraList.get(typeOfCameraList.size() - 1);
        assertThat(testTypeOfCamera.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTypeOfCamera.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingTypeOfCamera() throws Exception {
        int databaseSizeBeforeUpdate = typeOfCameraRepository.findAll().size();
        typeOfCamera.setId(count.incrementAndGet());

        // Create the TypeOfCamera
        TypeOfCameraDTO typeOfCameraDTO = typeOfCameraMapper.toDto(typeOfCamera);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfCameraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeOfCameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfCamera in the database
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeOfCamera() throws Exception {
        int databaseSizeBeforeUpdate = typeOfCameraRepository.findAll().size();
        typeOfCamera.setId(count.incrementAndGet());

        // Create the TypeOfCamera
        TypeOfCameraDTO typeOfCameraDTO = typeOfCameraMapper.toDto(typeOfCamera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeOfCameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfCamera in the database
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeOfCamera() throws Exception {
        int databaseSizeBeforeUpdate = typeOfCameraRepository.findAll().size();
        typeOfCamera.setId(count.incrementAndGet());

        // Create the TypeOfCamera
        TypeOfCameraDTO typeOfCameraDTO = typeOfCameraMapper.toDto(typeOfCamera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfCameraMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeOfCameraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfCamera in the database
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeOfCameraWithPatch() throws Exception {
        // Initialize the database
        typeOfCameraRepository.saveAndFlush(typeOfCamera);

        int databaseSizeBeforeUpdate = typeOfCameraRepository.findAll().size();

        // Update the typeOfCamera using partial update
        TypeOfCamera partialUpdatedTypeOfCamera = new TypeOfCamera();
        partialUpdatedTypeOfCamera.setId(typeOfCamera.getId());

        restTypeOfCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfCamera.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeOfCamera))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfCamera in the database
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeUpdate);
        TypeOfCamera testTypeOfCamera = typeOfCameraList.get(typeOfCameraList.size() - 1);
        assertThat(testTypeOfCamera.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTypeOfCamera.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateTypeOfCameraWithPatch() throws Exception {
        // Initialize the database
        typeOfCameraRepository.saveAndFlush(typeOfCamera);

        int databaseSizeBeforeUpdate = typeOfCameraRepository.findAll().size();

        // Update the typeOfCamera using partial update
        TypeOfCamera partialUpdatedTypeOfCamera = new TypeOfCamera();
        partialUpdatedTypeOfCamera.setId(typeOfCamera.getId());

        partialUpdatedTypeOfCamera.name(UPDATED_NAME).info(UPDATED_INFO);

        restTypeOfCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfCamera.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeOfCamera))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfCamera in the database
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeUpdate);
        TypeOfCamera testTypeOfCamera = typeOfCameraList.get(typeOfCameraList.size() - 1);
        assertThat(testTypeOfCamera.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTypeOfCamera.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingTypeOfCamera() throws Exception {
        int databaseSizeBeforeUpdate = typeOfCameraRepository.findAll().size();
        typeOfCamera.setId(count.incrementAndGet());

        // Create the TypeOfCamera
        TypeOfCameraDTO typeOfCameraDTO = typeOfCameraMapper.toDto(typeOfCamera);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeOfCameraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeOfCameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfCamera in the database
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeOfCamera() throws Exception {
        int databaseSizeBeforeUpdate = typeOfCameraRepository.findAll().size();
        typeOfCamera.setId(count.incrementAndGet());

        // Create the TypeOfCamera
        TypeOfCameraDTO typeOfCameraDTO = typeOfCameraMapper.toDto(typeOfCamera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeOfCameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfCamera in the database
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeOfCamera() throws Exception {
        int databaseSizeBeforeUpdate = typeOfCameraRepository.findAll().size();
        typeOfCamera.setId(count.incrementAndGet());

        // Create the TypeOfCamera
        TypeOfCameraDTO typeOfCameraDTO = typeOfCameraMapper.toDto(typeOfCamera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfCameraMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeOfCameraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfCamera in the database
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeOfCamera() throws Exception {
        // Initialize the database
        typeOfCameraRepository.saveAndFlush(typeOfCamera);

        int databaseSizeBeforeDelete = typeOfCameraRepository.findAll().size();

        // Delete the typeOfCamera
        restTypeOfCameraMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeOfCamera.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeOfCamera> typeOfCameraList = typeOfCameraRepository.findAll();
        assertThat(typeOfCameraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
