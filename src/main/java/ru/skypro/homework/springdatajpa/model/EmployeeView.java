package ru.skypro.homework.springdatajpa.model;

import ru.skypro.homework.springdatajpa.model.Position;

public interface EmployeeView {
    String getName();
    Integer getSalary();
    Position getPosition();
}
