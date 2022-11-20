package uz.ccvtv.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ccvtv.domain.TypeOfCamera;
import uz.ccvtv.repository.TypeOfCameraRepository;
import uz.ccvtv.service.TypeOfCameraService;
import uz.ccvtv.service.dto.TypeOfCameraDTO;
import uz.ccvtv.service.mapper.TypeOfCameraMapper;

/**
 * Service Implementation for managing {@link TypeOfCamera}.
 */
@Service
@Transactional
public class TypeOfCameraServiceImpl implements TypeOfCameraService {

    private final Logger log = LoggerFactory.getLogger(TypeOfCameraServiceImpl.class);

    private final TypeOfCameraRepository typeOfCameraRepository;

    private final TypeOfCameraMapper typeOfCameraMapper;

    public TypeOfCameraServiceImpl(TypeOfCameraRepository typeOfCameraRepository, TypeOfCameraMapper typeOfCameraMapper) {
        this.typeOfCameraRepository = typeOfCameraRepository;
        this.typeOfCameraMapper = typeOfCameraMapper;
    }

    @Override
    public TypeOfCameraDTO save(TypeOfCameraDTO typeOfCameraDTO) {
        log.debug("Request to save TypeOfCamera : {}", typeOfCameraDTO);
        TypeOfCamera typeOfCamera = typeOfCameraMapper.toEntity(typeOfCameraDTO);
        typeOfCamera = typeOfCameraRepository.save(typeOfCamera);
        return typeOfCameraMapper.toDto(typeOfCamera);
    }

    @Override
    public TypeOfCameraDTO update(TypeOfCameraDTO typeOfCameraDTO) {
        log.debug("Request to update TypeOfCamera : {}", typeOfCameraDTO);
        TypeOfCamera typeOfCamera = typeOfCameraMapper.toEntity(typeOfCameraDTO);
        typeOfCamera = typeOfCameraRepository.save(typeOfCamera);
        return typeOfCameraMapper.toDto(typeOfCamera);
    }

    @Override
    public Optional<TypeOfCameraDTO> partialUpdate(TypeOfCameraDTO typeOfCameraDTO) {
        log.debug("Request to partially update TypeOfCamera : {}", typeOfCameraDTO);

        return typeOfCameraRepository
            .findById(typeOfCameraDTO.getId())
            .map(existingTypeOfCamera -> {
                typeOfCameraMapper.partialUpdate(existingTypeOfCamera, typeOfCameraDTO);

                return existingTypeOfCamera;
            })
            .map(typeOfCameraRepository::save)
            .map(typeOfCameraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfCameraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeOfCameras");
        return typeOfCameraRepository.findAll(pageable).map(typeOfCameraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeOfCameraDTO> findOne(Long id) {
        log.debug("Request to get TypeOfCamera : {}", id);
        return typeOfCameraRepository.findById(id).map(typeOfCameraMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeOfCamera : {}", id);
        typeOfCameraRepository.deleteById(id);
    }
}
