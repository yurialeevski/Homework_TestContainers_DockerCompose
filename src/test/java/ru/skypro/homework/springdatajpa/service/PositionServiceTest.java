package ru.skypro.homework.springdatajpa.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skypro.homework.springdatajpa.model.Employee;
import ru.skypro.homework.springdatajpa.model.Position;
import ru.skypro.homework.springdatajpa.repository.EmployeeRepository;
import ru.skypro.homework.springdatajpa.repository.PositionRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)
class PositionServiceTest {
    public static final Logger logger = LoggerFactory.getLogger(PositionServiceTest.class.getName());
    @InjectMocks
    private PositionService out;
    @Mock
    private PositionRepository dao;

    @Test
    void shouldReturnAllPositions_getPositions() {
        List<Position> positionList = List.of(initPosition(1, "junior"),
                                                initPosition(2, "middle"),
                                                initPosition(3, "senior"));
        logger.info("positionsInput " + positionList);
        Mockito.when(dao.findAll()).thenReturn(positionList);
        List<Position> positions = out.getPositions();
        assertEquals(3, positions.size());
        Mockito.verify(dao, times(1)).findAll();
    }
    public static Position initPosition(Integer id, String role) {
        Position position = new Position();
        position.setId(id);
        position.setRole(role);
        return position;
    }
}