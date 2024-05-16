package com.library.management.mappers.impl;

import com.library.management.model.dto.BookDto;
import com.library.management.model.entities.Book;
import org.springframework.stereotype.Component;

import com.library.management.mappers.Mapper;
import org.modelmapper.ModelMapper;
@Component
public class BookMapperImpl implements Mapper<Book, BookDto> {

    private ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapEntityToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public Book mapEntityFromDto(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }

}
