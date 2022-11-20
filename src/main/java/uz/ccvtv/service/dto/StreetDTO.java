package uz.ccvtv.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uz.ccvtv.domain.Street} entity.
 */
@Schema(description = "Улица.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StreetDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String name;

    @Size(max = 1024)
    private String info;

    private DistrictDTO district;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StreetDTO)) {
            return false;
        }

        StreetDTO streetDTO = (StreetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, streetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StreetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", info='" + getInfo() + "'" +
            ", district=" + getDistrict() +
            "}";
    }
}
