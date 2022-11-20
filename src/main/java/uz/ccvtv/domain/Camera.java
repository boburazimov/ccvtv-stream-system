package uz.ccvtv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import uz.ccvtv.domain.enumeration.CameraStatusEnum;

/**
 * A Camera.
 */
@Entity
@Table(name = "camera")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Camera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * IP адрес.
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "ip_address", length = 64, nullable = false)
    private String ipAddress;

    /**
     * Порт.
     */
    @NotNull
    @Size(max = 4)
    @Column(name = "port", length = 4, nullable = false)
    private String port;

    /**
     * Логин от камеры.
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "login", length = 64, nullable = false)
    private String login;

    /**
     * Пароль от камеры.
     */
    @NotNull
    @Size(max = 128)
    @Column(name = "password", length = 128, nullable = false)
    private String password;

    /**
     * Основной адрес камеры до IP.
     */
    @NotNull
    @Size(max = 128)
    @Column(name = "main_path", length = 128, nullable = false)
    private String mainPath;

    /**
     * Доп. адрес камеры до IP.
     */
    @NotNull
    @Size(max = 128)
    @Column(name = "secondary_path", length = 128, nullable = false)
    private String secondaryPath;

    /**
     * Польный пут до камеры.
     */
    @Size(max = 256)
    @Column(name = "url", length = 256, unique = true)
    private String url;

    /**
     * Модель камеры.
     */
    @Size(max = 64)
    @Column(name = "model", length = 64)
    private String model;

    /**
     * Прикрепленный имя - псевдоним.
     */
    @Column(name = "name")
    private String name;

    /**
     * Бренд камеры.
     */
    @Size(max = 64)
    @Column(name = "vendor_name", length = 64)
    private String vendorName;

    /**
     * Включение/Отключения.
     */
    @Column(name = "activated")
    private Boolean activated;

    /**
     * Путь до видео файла.
     */
    @Column(name = "hls_url")
    private String hlsUrl;

    /**
     * Обозначения временных камер.
     */
    @Column(name = "is_temp")
    private Boolean isTemp;

    /**
     * Статус камеры.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CameraStatusEnum status;

    /**
     * Доп инфо.
     */
    @Column(name = "info")
    private String info;

    @ManyToOne
    @JsonIgnoreProperties(value = { "street" }, allowSetters = true)
    private Location location;

    @ManyToOne
    private TypeOfCamera typeOfCamera;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Camera id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public Camera ipAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return this.port;
    }

    public Camera port(String port) {
        this.setPort(port);
        return this;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLogin() {
        return this.login;
    }

    public Camera login(String login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public Camera password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMainPath() {
        return this.mainPath;
    }

    public Camera mainPath(String mainPath) {
        this.setMainPath(mainPath);
        return this;
    }

    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }

    public String getSecondaryPath() {
        return this.secondaryPath;
    }

    public Camera secondaryPath(String secondaryPath) {
        this.setSecondaryPath(secondaryPath);
        return this;
    }

    public void setSecondaryPath(String secondaryPath) {
        this.secondaryPath = secondaryPath;
    }

    public String getUrl() {
        return this.url;
    }

    public Camera url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModel() {
        return this.model;
    }

    public Camera model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return this.name;
    }

    public Camera name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public Camera vendorName(String vendorName) {
        this.setVendorName(vendorName);
        return this;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public Camera activated(Boolean activated) {
        this.setActivated(activated);
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getHlsUrl() {
        return this.hlsUrl;
    }

    public Camera hlsUrl(String hlsUrl) {
        this.setHlsUrl(hlsUrl);
        return this;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public Boolean getIsTemp() {
        return this.isTemp;
    }

    public Camera isTemp(Boolean isTemp) {
        this.setIsTemp(isTemp);
        return this;
    }

    public void setIsTemp(Boolean isTemp) {
        this.isTemp = isTemp;
    }

    public CameraStatusEnum getStatus() {
        return this.status;
    }

    public Camera status(CameraStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CameraStatusEnum status) {
        this.status = status;
    }

    public String getInfo() {
        return this.info;
    }

    public Camera info(String info) {
        this.setInfo(info);
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Camera location(Location location) {
        this.setLocation(location);
        return this;
    }

    public TypeOfCamera getTypeOfCamera() {
        return this.typeOfCamera;
    }

    public void setTypeOfCamera(TypeOfCamera typeOfCamera) {
        this.typeOfCamera = typeOfCamera;
    }

    public Camera typeOfCamera(TypeOfCamera typeOfCamera) {
        this.setTypeOfCamera(typeOfCamera);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Camera)) {
            return false;
        }
        return id != null && id.equals(((Camera) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Camera{" +
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
            "}";
    }
}
