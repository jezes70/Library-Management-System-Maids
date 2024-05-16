package com.library.management.mappers.impl;

import com.library.management.model.dto.PatronDto;
import com.library.management.model.entities.Patron;
import com.library.management.mappers.Mapper;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
public class PatronMapperImpl implements Mapper<Patron, PatronDto> {

    private ModelMapper modelMapper;

    public PatronMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PatronDto mapEntityToDto(Patron patron) {
        return modelMapper.map(patron, PatronDto.class);
    }

    @Override
    public Patron mapEntityFromDto(PatronDto patronDto) {
        return modelMapper.map(patronDto, Patron.class);
    }


}

