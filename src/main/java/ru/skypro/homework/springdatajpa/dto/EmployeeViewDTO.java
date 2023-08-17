package ru.skypro.homework.springdatajpa.dto;

import ru.skypro.homework.springdatajpa.model.EmployeeView;

import java.util.Objects;

public class EmployeeViewDTO {
    private String name;
    private Integer salary;
    private String role;

    public EmployeeViewDTO() {
    }
    public static EmployeeViewDTO fromEmployeeView(EmployeeView employeeView) {
        EmployeeViewDTO viewDto = new EmployeeViewDTO();
        viewDto.setName(employeeView.getName());
        viewDto.setSalary(employeeView.getSalary());
        viewDto.setRole(employeeView.getPosition().getRole());
        return viewDto;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "EmployeeViewDTO{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeViewDTO viewDTO = (EmployeeViewDTO) o;
        return name.equals(viewDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
