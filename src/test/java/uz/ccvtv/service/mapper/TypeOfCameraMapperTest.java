package uz.ccvtv.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeOfCameraMapperTest {

    private TypeOfCameraMapper typeOfCameraMapper;

    @BeforeEach
    public void setUp() {
        typeOfCameraMapper = new TypeOfCameraMapperImpl();
    }
}
