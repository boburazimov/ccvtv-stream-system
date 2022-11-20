package uz.ccvtv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.ccvtv.web.rest.TestUtil;

class TypeOfCameraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfCamera.class);
        TypeOfCamera typeOfCamera1 = new TypeOfCamera();
        typeOfCamera1.setId(1L);
        TypeOfCamera typeOfCamera2 = new TypeOfCamera();
        typeOfCamera2.setId(typeOfCamera1.getId());
        assertThat(typeOfCamera1).isEqualTo(typeOfCamera2);
        typeOfCamera2.setId(2L);
        assertThat(typeOfCamera1).isNotEqualTo(typeOfCamera2);
        typeOfCamera1.setId(null);
        assertThat(typeOfCamera1).isNotEqualTo(typeOfCamera2);
    }
}
