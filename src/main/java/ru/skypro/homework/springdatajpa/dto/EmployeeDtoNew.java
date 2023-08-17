package ru.skypro.homework.springdatajpa.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import ru.skypro.homework.springdatajpa.model.Position;

public class EmployeeDtoNew {
    private String name;
    private Integer salary;
    private Integer positionIndex;

    public EmployeeDtoNew() {
    }

    public EmployeeDtoNew(String name, Integer salary, Integer positionIndex) {
        this.name = name;
        this.salary = salary;
        this.positionIndex = positionIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(Integer positionIndex) {
        this.positionIndex = positionIndex;
    }

    @Override
    public String toString() {
        return "EmployeeDtoNew{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", positionIndex=" + positionIndex +
                '}';
    }
}
