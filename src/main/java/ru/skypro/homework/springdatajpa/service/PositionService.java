package ru.skypro.homework.springdatajpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.skypro.homework.springdatajpa.dto.EmployeeDTO;
import ru.skypro.homework.springdatajpa.exceptions.ApplicationErrors;
import ru.skypro.homework.springdatajpa.model.Employee;
import ru.skypro.homework.springdatajpa.model.Position;
import ru.skypro.homework.springdatajpa.repository.PositionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService {
    public static final Logger logger = LoggerFactory.getLogger(PositionService.class.getName());
    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }
    public List<Position> getPositions() {
        return positionRepository.findAll();
    }

    public Position getPositionById(Integer id) {
        logger.info("Getting position by id: " + id);
        Optional<Position> optionalPosition = positionRepository.findById(id);
        Position position = new Position();
        if(optionalPosition.isPresent()) {
            position = optionalPosition.get();
            logger.info("По id: " + id + " найдена " + position);
        } else {
            throw new ApplicationErrors("Position c id: " + id + " не найдена ", HttpStatus.NOT_FOUND);
        }
        return position;
    }
}
