package ru.skypro.homework.springdatajpa.dto;

import ru.skypro.homework.springdatajpa.model.Position;

public class ENewDto {
    private String name;
    private Integer salary;
    private Integer pos;

    public ENewDto() {
    }

    public ENewDto(String name, Integer salary, Integer pos) {
        this.name = name;
        this.salary = salary;
        this.pos = pos;
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

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "ENewDto{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", pos=" + pos +
                '}';
    }
}
