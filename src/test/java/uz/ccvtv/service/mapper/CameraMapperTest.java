package uz.ccvtv.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CameraMapperTest {

    private CameraMapper cameraMapper;

    @BeforeEach
    public void setUp() {
        cameraMapper = new CameraMapperImpl();
    }
}
