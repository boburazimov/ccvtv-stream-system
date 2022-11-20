package uz.ccvtv.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.ccvtv.web.rest.TestUtil;

class StreetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StreetDTO.class);
        StreetDTO streetDTO1 = new StreetDTO();
        streetDTO1.setId(1L);
        StreetDTO streetDTO2 = new StreetDTO();
        assertThat(streetDTO1).isNotEqualTo(streetDTO2);
        streetDTO2.setId(streetDTO1.getId());
        assertThat(streetDTO1).isEqualTo(streetDTO2);
        streetDTO2.setId(2L);
        assertThat(streetDTO1).isNotEqualTo(streetDTO2);
        streetDTO1.setId(null);
        assertThat(streetDTO1).isNotEqualTo(streetDTO2);
    }
}
