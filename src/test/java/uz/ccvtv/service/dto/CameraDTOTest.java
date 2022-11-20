package uz.ccvtv.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.ccvtv.web.rest.TestUtil;

class CameraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CameraDTO.class);
        CameraDTO cameraDTO1 = new CameraDTO();
        cameraDTO1.setId(1L);
        CameraDTO cameraDTO2 = new CameraDTO();
        assertThat(cameraDTO1).isNotEqualTo(cameraDTO2);
        cameraDTO2.setId(cameraDTO1.getId());
        assertThat(cameraDTO1).isEqualTo(cameraDTO2);
        cameraDTO2.setId(2L);
        assertThat(cameraDTO1).isNotEqualTo(cameraDTO2);
        cameraDTO1.setId(null);
        assertThat(cameraDTO1).isNotEqualTo(cameraDTO2);
    }
}
