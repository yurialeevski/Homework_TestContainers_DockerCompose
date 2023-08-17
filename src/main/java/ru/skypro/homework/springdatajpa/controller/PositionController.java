package ru.skypro.homework.springdatajpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.springdatajpa.model.Position;
import ru.skypro.homework.springdatajpa.service.PositionService;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }
    @GetMapping("/all-positions")
    public List<Position> getPositions() {
        return positionService.getPositions();
    }
}
