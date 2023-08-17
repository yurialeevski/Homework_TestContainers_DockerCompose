package ru.skypro.homework.springdatajpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.springdatajpa.dto.EmployeeDTO;
import ru.skypro.homework.springdatajpa.dto.EmployeeDtoNew;
import ru.skypro.homework.springdatajpa.model.Employee;
import ru.skypro.homework.springdatajpa.model.Position;

@Service
public class DtoService {
    private static final Logger logger = LoggerFactory.getLogger(DtoService.class);

    public EmployeeDTO fromEmployeeToEmployeeDto(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getEmployeeId());
        dto.setName(employee.getName());
        dto.setSalary(employee.getSalary());
        dto.setPosition(employee.getPosition());
        return dto;
    }

    public Employee fromEmployeeDtoNewToUpdatedEmployee(EmployeeDtoNew employeeDtoNew, Employee employee, Position position) {
        employee.setName(employeeDtoNew.getName());
        employee.setSalary(employeeDtoNew.getSalary());
        employee.setPosition(position);
        return employee;
    }

    public Employee fromEmployeeDtoNewToEmployee(EmployeeDtoNew dtoNew, Position position){
        Employee employee = new Employee();
        employee.setName(dtoNew.getName());
        employee.setSalary(dtoNew.getSalary());
        employee.setPosition(position);
        return employee;
    }

    public boolean validateEmployeeDtoNewFields (EmployeeDtoNew dtoNew) {
        logger.info("Run Validation of EmployeeDtoNew fields");
        if(dtoNew.getName() == null || dtoNew.getName().isEmpty()) {
            logger.info("The field 'name' is not correct");
            return false;
        } else if (dtoNew.getSalary() == null) {
            logger.info("The field 'salary' is not correct");
            return false;
        } else if (dtoNew.getPositionIndex() == null) {
            logger.info("The field 'position' is not correct");
            return false;
        }
        logger.info("Fields of EmployeeDtoNew are correct" + dtoNew);
        return true;
    }

    public boolean validateEmployeeDtoFields (EmployeeDTO employeeDTO) {
        logger.info("Run Validation of EmployeeDto fields");
        if(employeeDTO.getName() == null || employeeDTO.getName().isEmpty()) {
            logger.info("The field 'name' is not correct");
            return false;
        } else if (employeeDTO.getSalary() == 0) {
            logger.info("The field 'salary' is not correct");
            return false;
        } else if (employeeDTO.getPosition() == null) {
            logger.info("The field 'position' is not correct");
            return false;
        }
        logger.info("Fields of EmployeeDtoNew are correct" + employeeDTO);
        return true;
    }
}
