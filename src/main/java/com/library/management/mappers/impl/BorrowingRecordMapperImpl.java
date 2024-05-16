package com.library.management.mappers.impl;
import com.library.management.model.dto.BorrowingRecordDto;
import com.library.management.model.entities.BorrowingRecord;
import com.library.management.mappers.Mapper;
import org.springframework.stereotype.Component;

import org.modelmapper.ModelMapper;

@Component
public class BorrowingRecordMapperImpl implements Mapper<BorrowingRecord, BorrowingRecordDto> {

    private ModelMapper modelMapper;

    public BorrowingRecordMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BorrowingRecordDto mapEntityToDto(BorrowingRecord borrowingRecord) {
        return modelMapper.map(borrowingRecord, BorrowingRecordDto.class);
    }

    @Override
    public BorrowingRecord mapEntityFromDto(BorrowingRecordDto borrowingRecordDto) {
        return modelMapper.map(borrowingRecordDto, BorrowingRecord.class);
    }


}

