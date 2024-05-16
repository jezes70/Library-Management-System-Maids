package com.library.management.services.impl;

import com.library.management.mappers.Mapper;
import com.library.management.model.dto.PatronDto;
import com.library.management.model.entities.Patron;
import com.library.management.repositories.PatronRepository;
import com.library.management.services.PatronService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PatronServiceImpl implements PatronService {

    private PatronRepository patronRepository;
    private Mapper<Patron, PatronDto> patronMapper;

    public PatronServiceImpl(PatronRepository patronRepository, Mapper<Patron, PatronDto> patronMapper) {
        this.patronRepository = patronRepository;
        this.patronMapper = patronMapper;
    }

    @Override
    @CacheEvict(value = "patronList", allEntries = true)
    public PatronDto save(PatronDto patronDto) {
        Patron patron = patronMapper.mapEntityFromDto(patronDto);
        return patronMapper.mapEntityToDto(patronRepository.save(patron));
    }

    @Override
    @Cacheable(value = "patronList", key = "#root.methodName")
    public List<PatronDto> findAll() {
        List<Patron> patrons =  StreamSupport.stream(patronRepository.findAll().spliterator(), false)
                .toList();

        return patrons.stream()
                .map(patronMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "findPatronById", key = "#id")
    public Optional<PatronDto> findOne(Long id) {
        Optional<Patron> foundBook = patronRepository.findById(id);
        return foundBook.map(bookEntity -> patronMapper.mapEntityToDto(bookEntity));
    }

    @Override
    public boolean isExists(Long id) {
        return patronRepository.existsById(id);
    }

    @Override
    @CacheEvict(value = {"patronList", "findPatronById"}, allEntries = true)
    public PatronDto updatePatron(Long id, PatronDto patronDto) {
        if (!patronRepository.existsById(id)) {
            throw new RuntimeException("Patron does not exist");
        }

        Patron patron = patronMapper.mapEntityFromDto(patronDto);
        patron.setId(id);
        return patronMapper.mapEntityToDto(patronRepository.save(patron));
    }

    @Override
    @CacheEvict(value = {"patronList", "findPatronById"}, allEntries = true)
    public void delete(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new RuntimeException("Patron does not exist");
        }

        patronRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = {"patronList", "findPatronById"}, allEntries = true)
    public void deleteAll() {
        patronRepository.deleteAll();
    }
}
