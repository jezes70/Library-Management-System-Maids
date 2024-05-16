package com.library.management.services;

import com.library.management.TestDataUtil;
import com.library.management.mappers.Mapper;
import com.library.management.model.dto.PatronDto;
import com.library.management.model.entities.Patron;
import com.library.management.repositories.PatronRepository;
import com.library.management.services.impl.PatronServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PatronServiceImplTests {

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private Mapper<Patron, PatronDto> patronMapper;

    @InjectMocks
    private PatronServiceImpl patronService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSavePatron() {

        PatronDto inputPatronDto = TestDataUtil.createTestPatronDtoA();
        Patron savedPatron = TestDataUtil.createTestPatronEntityA();
        Mockito.when(patronMapper.mapEntityFromDto(inputPatronDto)).thenReturn(savedPatron);
        Mockito.when(patronRepository.save(savedPatron)).thenReturn(savedPatron);
        Mockito.when(patronMapper.mapEntityToDto(savedPatron)).thenReturn(inputPatronDto);


        PatronDto result = patronService.save(inputPatronDto);


        Assertions.assertEquals(inputPatronDto, result);
        Mockito.verify(patronRepository, Mockito.times(1)).save(savedPatron);
    }

    @Test
    public void testFindAllPatrons() {

        List<Patron> patronEntities = Arrays.asList(
                TestDataUtil.createTestPatronEntityA(),
                TestDataUtil.createTestPatronEntityB()
        );
        List<PatronDto> expectedPatronDtos = Arrays.asList(
                TestDataUtil.createTestPatronDtoA(),
                TestDataUtil.createTestPatronDtoB()
        );
        Mockito.when(patronRepository.findAll()).thenReturn(patronEntities);
        Mockito.when(patronMapper.mapEntityToDto(Mockito.any(Patron.class)))
                .thenReturn(expectedPatronDtos.get(0), expectedPatronDtos.get(1));


        List<PatronDto> result = patronService.findAll();


        Assertions.assertEquals(expectedPatronDtos, result);
        Mockito.verify(patronRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testFindOnePatronById() {

        Long patronId = 1L;
        Patron patron = TestDataUtil.createTestPatronEntityA();
        PatronDto expectedPatronDto = TestDataUtil.createTestPatronDtoA();
        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        Mockito.when(patronMapper.mapEntityToDto(patron)).thenReturn(expectedPatronDto);

        Optional<PatronDto> result = patronService.findOne(patronId);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedPatronDto, result.get());
        Mockito.verify(patronRepository, Mockito.times(1)).findById(patronId);
    }

    @Test
    public void testFindOnePatronByIdNotFound() {

        Long patronId = 1L;
        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.empty());


        Optional<PatronDto> result = patronService.findOne(patronId);


        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(patronRepository, Mockito.times(1)).findById(patronId);
    }

    @Test
    public void testIsPatronExists() {

        Long patronId = 1L;
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(true);


        boolean result = patronService.isExists(patronId);


        Assertions.assertTrue(result);
        Mockito.verify(patronRepository, Mockito.times(1)).existsById(patronId);
    }

    @Test
    public void testIsPatronNotExists() {

        Long patronId = 1L;
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(false);


        boolean result = patronService.isExists(patronId);


        Assertions.assertFalse(result);
        Mockito.verify(patronRepository, Mockito.times(1)).existsById(patronId);
    }

    @Test
    public void testUpdatePatron() {
        Patron existingPatron = TestDataUtil.createTestPatronEntityA();
        existingPatron.setId(1l);
        Patron updatedPatron = TestDataUtil.createTestPatronEntityB();
        PatronDto updatedPatronDto = TestDataUtil.createTestPatronDtoB();

        Mockito.when(patronRepository.existsById(existingPatron.getId())).thenReturn(true);
        Mockito.when(patronMapper.mapEntityFromDto(updatedPatronDto)).thenReturn(updatedPatron);
        Mockito.when(patronRepository.save(updatedPatron)).thenReturn(updatedPatron);
        Mockito.when(patronMapper.mapEntityToDto(updatedPatron)).thenReturn(updatedPatronDto);


        PatronDto result = patronService.updatePatron(existingPatron.getId(), updatedPatronDto);


        Assertions.assertEquals(updatedPatronDto, result);
        Mockito.verify(patronRepository, Mockito.times(1)).existsById(existingPatron.getId());
        Mockito.verify(patronRepository, Mockito.times(1)).save(updatedPatron);
    }

    @Test
    public void testUpdatePatronNotFound() {

        Long patronId = 1L;
        PatronDto updatedPatronDto = TestDataUtil.createTestPatronDtoB();

        Mockito.when(patronRepository.existsById(patronId)).thenReturn(false);


        Assertions.assertThrows(RuntimeException.class, () -> {
            patronService.updatePatron(patronId, updatedPatronDto);
        });

        Mockito.verify(patronRepository, Mockito.times(1)).existsById(patronId);
        Mockito.verify(patronRepository, Mockito.never()).save(Mockito.any(Patron.class));
    }

    @Test
    public void testDeletePatron() {

        Long patronId = 1L;
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(true);


        patronService.delete(patronId);


        Mockito.verify(patronRepository, Mockito.times(1)).deleteById(patronId);
    }

    @Test
    public void testDeletePatronNotFound() {

        Long patronId = 1L;
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(false);


        Assertions.assertThrows(RuntimeException.class, () -> {
            patronService.delete(patronId);
        });

        Mockito.verify(patronRepository, Mockito.times(1)).existsById(patronId);
        Mockito.verify(patronRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }

    @Test
    public void testDeleteAllPatrons() {

        patronService.deleteAll();


        Mockito.verify(patronRepository, Mockito.times(1)).deleteAll();
    }
}
