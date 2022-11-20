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
import uz.ccvtv.domain.Camera;
import uz.ccvtv.domain.enumeration.CameraStatusEnum;
import uz.ccvtv.repository.CameraRepository;
import uz.ccvtv.service.dto.CameraDTO;
import uz.ccvtv.service.mapper.CameraMapper;

/**
 * Integration tests for the {@link CameraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CameraResourceIT {

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PORT = "AAAA";
    private static final String UPDATED_PORT = "BBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_PATH = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_PATH = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_HLS_URL = "AAAAAAAAAA";
    private static final String UPDATED_HLS_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_TEMP = false;
    private static final Boolean UPDATED_IS_TEMP = true;

    private static final CameraStatusEnum DEFAULT_STATUS = CameraStatusEnum.ONLINE;
    private static final CameraStatusEnum UPDATED_STATUS = CameraStatusEnum.OFFLINE;

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cameras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private CameraMapper cameraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCameraMockMvc;

    private Camera camera;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Camera createEntity(EntityManager em) {
        Camera camera = new Camera()
            .ipAddress(DEFAULT_IP_ADDRESS)
            .port(DEFAULT_PORT)
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .mainPath(DEFAULT_MAIN_PATH)
            .secondaryPath(DEFAULT_SECONDARY_PATH)
            .url(DEFAULT_URL)
            .model(DEFAULT_MODEL)
            .name(DEFAULT_NAME)
            .vendorName(DEFAULT_VENDOR_NAME)
            .activated(DEFAULT_ACTIVATED)
            .hlsUrl(DEFAULT_HLS_URL)
            .isTemp(DEFAULT_IS_TEMP)
            .status(DEFAULT_STATUS)
            .info(DEFAULT_INFO);
        return camera;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Camera createUpdatedEntity(EntityManager em) {
        Camera camera = new Camera()
            .ipAddress(UPDATED_IP_ADDRESS)
            .port(UPDATED_PORT)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .mainPath(UPDATED_MAIN_PATH)
            .secondaryPath(UPDATED_SECONDARY_PATH)
            .url(UPDATED_URL)
            .model(UPDATED_MODEL)
            .name(UPDATED_NAME)
            .vendorName(UPDATED_VENDOR_NAME)
            .activated(UPDATED_ACTIVATED)
            .hlsUrl(UPDATED_HLS_URL)
            .isTemp(UPDATED_IS_TEMP)
            .status(UPDATED_STATUS)
            .info(UPDATED_INFO);
        return camera;
    }

    @BeforeEach
    public void initTest() {
        camera = createEntity(em);
    }

    @Test
    @Transactional
    void createCamera() throws Exception {
        int databaseSizeBeforeCreate = cameraRepository.findAll().size();
        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);
        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isCreated());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeCreate + 1);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testCamera.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testCamera.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testCamera.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testCamera.getMainPath()).isEqualTo(DEFAULT_MAIN_PATH);
        assertThat(testCamera.getSecondaryPath()).isEqualTo(DEFAULT_SECONDARY_PATH);
        assertThat(testCamera.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testCamera.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testCamera.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCamera.getVendorName()).isEqualTo(DEFAULT_VENDOR_NAME);
        assertThat(testCamera.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testCamera.getHlsUrl()).isEqualTo(DEFAULT_HLS_URL);
        assertThat(testCamera.getIsTemp()).isEqualTo(DEFAULT_IS_TEMP);
        assertThat(testCamera.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCamera.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createCameraWithExistingId() throws Exception {
        // Create the Camera with an existing ID
        camera.setId(1L);
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        int databaseSizeBeforeCreate = cameraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraRepository.findAll().size();
        // set the field null
        camera.setIpAddress(null);

        // Create the Camera, which fails.
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isBadRequest());

        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraRepository.findAll().size();
        // set the field null
        camera.setPort(null);

        // Create the Camera, which fails.
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isBadRequest());

        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraRepository.findAll().size();
        // set the field null
        camera.setLogin(null);

        // Create the Camera, which fails.
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isBadRequest());

        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraRepository.findAll().size();
        // set the field null
        camera.setPassword(null);

        // Create the Camera, which fails.
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isBadRequest());

        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMainPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraRepository.findAll().size();
        // set the field null
        camera.setMainPath(null);

        // Create the Camera, which fails.
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isBadRequest());

        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSecondaryPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraRepository.findAll().size();
        // set the field null
        camera.setSecondaryPath(null);

        // Create the Camera, which fails.
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isBadRequest());

        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCameras() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        // Get all the cameraList
        restCameraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(camera.getId().intValue())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].mainPath").value(hasItem(DEFAULT_MAIN_PATH)))
            .andExpect(jsonPath("$.[*].secondaryPath").value(hasItem(DEFAULT_SECONDARY_PATH)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].hlsUrl").value(hasItem(DEFAULT_HLS_URL)))
            .andExpect(jsonPath("$.[*].isTemp").value(hasItem(DEFAULT_IS_TEMP.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getCamera() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        // Get the camera
        restCameraMockMvc
            .perform(get(ENTITY_API_URL_ID, camera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(camera.getId().intValue()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.mainPath").value(DEFAULT_MAIN_PATH))
            .andExpect(jsonPath("$.secondaryPath").value(DEFAULT_SECONDARY_PATH))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.vendorName").value(DEFAULT_VENDOR_NAME))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.hlsUrl").value(DEFAULT_HLS_URL))
            .andExpect(jsonPath("$.isTemp").value(DEFAULT_IS_TEMP.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingCamera() throws Exception {
        // Get the camera
        restCameraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCamera() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();

        // Update the camera
        Camera updatedCamera = cameraRepository.findById(camera.getId()).get();
        // Disconnect from session so that the updates on updatedCamera are not directly saved in db
        em.detach(updatedCamera);
        updatedCamera
            .ipAddress(UPDATED_IP_ADDRESS)
            .port(UPDATED_PORT)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .mainPath(UPDATED_MAIN_PATH)
            .secondaryPath(UPDATED_SECONDARY_PATH)
            .url(UPDATED_URL)
            .model(UPDATED_MODEL)
            .name(UPDATED_NAME)
            .vendorName(UPDATED_VENDOR_NAME)
            .activated(UPDATED_ACTIVATED)
            .hlsUrl(UPDATED_HLS_URL)
            .isTemp(UPDATED_IS_TEMP)
            .status(UPDATED_STATUS)
            .info(UPDATED_INFO);
        CameraDTO cameraDTO = cameraMapper.toDto(updatedCamera);

        restCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cameraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isOk());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testCamera.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testCamera.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testCamera.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testCamera.getMainPath()).isEqualTo(UPDATED_MAIN_PATH);
        assertThat(testCamera.getSecondaryPath()).isEqualTo(UPDATED_SECONDARY_PATH);
        assertThat(testCamera.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testCamera.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCamera.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCamera.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testCamera.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testCamera.getHlsUrl()).isEqualTo(UPDATED_HLS_URL);
        assertThat(testCamera.getIsTemp()).isEqualTo(UPDATED_IS_TEMP);
        assertThat(testCamera.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCamera.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cameraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCameraWithPatch() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();

        // Update the camera using partial update
        Camera partialUpdatedCamera = new Camera();
        partialUpdatedCamera.setId(camera.getId());

        partialUpdatedCamera
            .ipAddress(UPDATED_IP_ADDRESS)
            .port(UPDATED_PORT)
            .secondaryPath(UPDATED_SECONDARY_PATH)
            .model(UPDATED_MODEL)
            .name(UPDATED_NAME)
            .vendorName(UPDATED_VENDOR_NAME)
            .hlsUrl(UPDATED_HLS_URL)
            .info(UPDATED_INFO);

        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCamera.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCamera))
            )
            .andExpect(status().isOk());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testCamera.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testCamera.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testCamera.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testCamera.getMainPath()).isEqualTo(DEFAULT_MAIN_PATH);
        assertThat(testCamera.getSecondaryPath()).isEqualTo(UPDATED_SECONDARY_PATH);
        assertThat(testCamera.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testCamera.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCamera.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCamera.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testCamera.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testCamera.getHlsUrl()).isEqualTo(UPDATED_HLS_URL);
        assertThat(testCamera.getIsTemp()).isEqualTo(DEFAULT_IS_TEMP);
        assertThat(testCamera.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCamera.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void fullUpdateCameraWithPatch() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();

        // Update the camera using partial update
        Camera partialUpdatedCamera = new Camera();
        partialUpdatedCamera.setId(camera.getId());

        partialUpdatedCamera
            .ipAddress(UPDATED_IP_ADDRESS)
            .port(UPDATED_PORT)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .mainPath(UPDATED_MAIN_PATH)
            .secondaryPath(UPDATED_SECONDARY_PATH)
            .url(UPDATED_URL)
            .model(UPDATED_MODEL)
            .name(UPDATED_NAME)
            .vendorName(UPDATED_VENDOR_NAME)
            .activated(UPDATED_ACTIVATED)
            .hlsUrl(UPDATED_HLS_URL)
            .isTemp(UPDATED_IS_TEMP)
            .status(UPDATED_STATUS)
            .info(UPDATED_INFO);

        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCamera.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCamera))
            )
            .andExpect(status().isOk());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testCamera.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testCamera.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testCamera.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testCamera.getMainPath()).isEqualTo(UPDATED_MAIN_PATH);
        assertThat(testCamera.getSecondaryPath()).isEqualTo(UPDATED_SECONDARY_PATH);
        assertThat(testCamera.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testCamera.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCamera.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCamera.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testCamera.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testCamera.getHlsUrl()).isEqualTo(UPDATED_HLS_URL);
        assertThat(testCamera.getIsTemp()).isEqualTo(UPDATED_IS_TEMP);
        assertThat(testCamera.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCamera.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cameraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCamera() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeDelete = cameraRepository.findAll().size();

        // Delete the camera
        restCameraMockMvc
            .perform(delete(ENTITY_API_URL_ID, camera.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
