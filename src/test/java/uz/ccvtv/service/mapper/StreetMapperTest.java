package uz.ccvtv.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StreetMapperTest {

    private StreetMapper streetMapper;

    @BeforeEach
    public void setUp() {
        streetMapper = new StreetMapperImpl();
    }
}
