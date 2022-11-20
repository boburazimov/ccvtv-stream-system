package uz.ccvtv.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ccvtv.domain.Street;
import uz.ccvtv.repository.StreetRepository;
import uz.ccvtv.service.StreetService;
import uz.ccvtv.service.dto.StreetDTO;
import uz.ccvtv.service.mapper.StreetMapper;

/**
 * Service Implementation for managing {@link Street}.
 */
@Service
@Transactional
public class StreetServiceImpl implements StreetService {

    private final Logger log = LoggerFactory.getLogger(StreetServiceImpl.class);

    private final StreetRepository streetRepository;

    private final StreetMapper streetMapper;

    public StreetServiceImpl(StreetRepository streetRepository, StreetMapper streetMapper) {
        this.streetRepository = streetRepository;
        this.streetMapper = streetMapper;
    }

    @Override
    public StreetDTO save(StreetDTO streetDTO) {
        log.debug("Request to save Street : {}", streetDTO);
        Street street = streetMapper.toEntity(streetDTO);
        street = streetRepository.save(street);
        return streetMapper.toDto(street);
    }

    @Override
    public StreetDTO update(StreetDTO streetDTO) {
        log.debug("Request to update Street : {}", streetDTO);
        Street street = streetMapper.toEntity(streetDTO);
        street = streetRepository.save(street);
        return streetMapper.toDto(street);
    }

    @Override
    public Optional<StreetDTO> partialUpdate(StreetDTO streetDTO) {
        log.debug("Request to partially update Street : {}", streetDTO);

        return streetRepository
            .findById(streetDTO.getId())
            .map(existingStreet -> {
                streetMapper.partialUpdate(existingStreet, streetDTO);

                return existingStreet;
            })
            .map(streetRepository::save)
            .map(streetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StreetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Streets");
        return streetRepository.findAll(pageable).map(streetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StreetDTO> findOne(Long id) {
        log.debug("Request to get Street : {}", id);
        return streetRepository.findById(id).map(streetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Street : {}", id);
        streetRepository.deleteById(id);
    }
}
