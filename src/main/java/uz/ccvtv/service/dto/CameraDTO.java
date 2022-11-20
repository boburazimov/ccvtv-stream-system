package uz.ccvtv.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import uz.ccvtv.domain.enumeration.CameraStatusEnum;

/**
 * A DTO for the {@link uz.ccvtv.domain.Camera} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CameraDTO implements Serializable {

    private Long id;

    /**
     * IP адрес.
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "IP адрес.", required = true)
    private String ipAddress;

    /**
     * Порт.
     */
    @NotNull
    @Size(max = 4)
    @Schema(description = "Порт.", required = true)
    private String port;

    /**
     * Логин от камеры.
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "Логин от камеры.", required = true)
    private String login;

    /**
     * Пароль от камеры.
     */
    @NotNull
    @Size(max = 128)
    @Schema(description = "Пароль от камеры.", required = true)
    private String password;

    /**
     * Основной адрес камеры до IP.
     */
    @NotNull
    @Size(max = 128)
    @Schema(description = "Основной адрес камеры до IP.", required = true)
    private String mainPath;

    /**
     * Доп. адрес камеры до IP.
     */
    @NotNull
    @Size(max = 128)
    @Schema(description = "Доп. адрес камеры до IP.", required = true)
    private String secondaryPath;

    /**
     * Польный пут до камеры.
     */
    @Size(max = 256)
    @Schema(description = "Польный пут до камеры.")
    private String url;

    /**
     * Модель камеры.
     */
    @Size(max = 64)
    @Schema(description = "Модель камеры.")
    private String model;

    /**
     * Прикрепленный имя - псевдоним.
     */
    @Schema(description = "Прикрепленный имя - псевдоним.")
    private String name;

    /**
     * Бренд камеры.
     */
    @Size(max = 64)
    @Schema(description = "Бренд камеры.")
    private String vendorName;

    /**
     * Включение/Отключения.
     */
    @Schema(description = "Включение/Отключения.")
    private Boolean activated;

    /**
     * Путь до видео файла.
     */
    @Schema(description = "Путь до видео файла.")
    private String hlsUrl;

    /**
     * Обозначения временных камер.
     */
    @Schema(description = "Обозначения временных камер.")
    private Boolean isTemp;

    /**
     * Статус камеры.
     */
    @Schema(description = "Статус камеры.")
    private CameraStatusEnum status;

    /**
     * Доп инфо.
     */
    @Schema(description = "Доп инфо.")
    private String info;

    private LocationDTO location;

    private TypeOfCameraDTO typeOfCamera;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMainPath() {
        return mainPath;
    }

    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }

    public String getSecondaryPath() {
        return secondaryPath;
    }

    public void setSecondaryPath(String secondaryPath) {
        this.secondaryPath = secondaryPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public Boolean getIsTemp() {
        return isTemp;
    }

    public void setIsTemp(Boolean isTemp) {
        this.isTemp = isTemp;
    }

    public CameraStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CameraStatusEnum status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public TypeOfCameraDTO getTypeOfCamera() {
        return typeOfCamera;
    }

    public void setTypeOfCamera(TypeOfCameraDTO typeOfCamera) {
        this.typeOfCamera = typeOfCamera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CameraDTO)) {
            return false;
        }

        CameraDTO cameraDTO = (CameraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cameraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CameraDTO{" +
            "id=" + getId() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", port='" + getPort() + "'" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", mainPath='" + getMainPath() + "'" +
            ", secondaryPath='" + getSecondaryPath() + "'" +
            ", url='" + getUrl() + "'" +
            ", model='" + getModel() + "'" +
            ", name='" + getName() + "'" +
            ", vendorName='" + getVendorName() + "'" +
            ", activated='" + getActivated() + "'" +
            ", hlsUrl='" + getHlsUrl() + "'" +
            ", isTemp='" + getIsTemp() + "'" +
            ", status='" + getStatus() + "'" +
            ", info='" + getInfo() + "'" +
            ", location=" + getLocation() +
            ", typeOfCamera=" + getTypeOfCamera() +
            "}";
    }
}
