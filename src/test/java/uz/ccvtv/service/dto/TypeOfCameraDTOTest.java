package uz.ccvtv.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.ccvtv.web.rest.TestUtil;

class TypeOfCameraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfCameraDTO.class);
        TypeOfCameraDTO typeOfCameraDTO1 = new TypeOfCameraDTO();
        typeOfCameraDTO1.setId(1L);
        TypeOfCameraDTO typeOfCameraDTO2 = new TypeOfCameraDTO();
        assertThat(typeOfCameraDTO1).isNotEqualTo(typeOfCameraDTO2);
        typeOfCameraDTO2.setId(typeOfCameraDTO1.getId());
        assertThat(typeOfCameraDTO1).isEqualTo(typeOfCameraDTO2);
        typeOfCameraDTO2.setId(2L);
        assertThat(typeOfCameraDTO1).isNotEqualTo(typeOfCameraDTO2);
        typeOfCameraDTO1.setId(null);
        assertThat(typeOfCameraDTO1).isNotEqualTo(typeOfCameraDTO2);
    }
}
