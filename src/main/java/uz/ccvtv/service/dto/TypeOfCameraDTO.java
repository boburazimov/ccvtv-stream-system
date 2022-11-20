package uz.ccvtv.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uz.ccvtv.domain.TypeOfCamera} entity.
 */
@Schema(description = "Тип камеры (распознавания и.д).")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfCameraDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 64)
    private String name;

    private String info;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfCameraDTO)) {
            return false;
        }

        TypeOfCameraDTO typeOfCameraDTO = (TypeOfCameraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeOfCameraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfCameraDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
