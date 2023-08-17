package ru.skypro.homework.springdatajpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.springdatajpa.dto.ENewDto;
import ru.skypro.homework.springdatajpa.dto.EmployeeDTO;
import ru.skypro.homework.springdatajpa.dto.EmployeeDtoNew;
import ru.skypro.homework.springdatajpa.dto.EmployeeViewDTO;
import ru.skypro.homework.springdatajpa.model.Employee;
import ru.skypro.homework.springdatajpa.service.EmployeeService;
import ru.skypro.homework.springdatajpa.model.EmployeeView;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/add-one-employee")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDtoNew employeeDtoNew){
        EmployeeDTO employeeDTO = employeeService.createEmployee(employeeDtoNew);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/employee-by-id")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer id) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @GetMapping("/allEmployees")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@PathVariable Integer id, @RequestBody EmployeeDtoNew employeeDtoNew) {
        EmployeeDTO emplDTO = employeeService.updateEmployeeById(id, employeeDtoNew);
        return new ResponseEntity<>(emplDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @GetMapping("/pages")
    public List<Employee> getAllEmployeesByPageNumber(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "employeeId") String sortBy) {
        List<Employee> list = employeeService.getAllByPageNumber(pageNo, pageSize, sortBy);
        return list;
    }


    /*############################################



    @GetMapping("/salary/min")
    public int showMinEmployeeSalary() {
        return employeeService.getMinEmployeeSalary();
    }

    @GetMapping("/with-greatest-salary")
    public List<EmployeeDTO> getAllEmployeesWithMaxSalary() {
        return employeeService.getAllWithMaxSalary();
    }

    @GetMapping("/all-with-position")
    public List<EmployeeDTO> getAllEmployeesByPosition(@RequestParam("position") String position) {
        return employeeService.findAllWithPosition(position);
    }
    @GetMapping("/{id}/fullInfo")
    public EmployeeView getEmployeeFullInfoById(@PathVariable Integer id) {
        return employeeService.findFullInfoById(id);
    }
    @GetMapping({"/{id}/fullViewInfo"})
    public EmployeeViewDTO getViewInfoById(@PathVariable Integer id) {
        return employeeService.findViewInfo(id);
    }




    #############################################*/

    /*


    @GetMapping("/find-by-name")
    public List<Employee> findByName(String name) {
        return employeeService.findByName(name);
    }
    @GetMapping("/salary-greater-than")
    public List<Employee> findBySalaryGreaterThan(int salary) {
        return employeeService.findBySalaryGreaterThan(salary);
    }

    @GetMapping("/greatest-salary-employees") public List<Employee> findWithGreatestSalary() {
        return employeeService.findWithGreatestSalary();
    }


    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addListOfEmployees(@RequestBody List<Employee> employees){
        employeeService.addListOfEmployees(employees);
    }

    @GetMapping("/salaryHigherThan")
    public List<Employee> showSalaryHigherThan(@RequestParam("salary") Integer salary) {
        return employeeService.getHigherThanSalaryEmployees(salary);
    }

    @GetMapping("/salary/sum")
    public int showSumEmployeeSalary() {
        return employeeService.getSumEmployeeSalary();
    }

    @GetMapping("/salary/max")
    public int showMaxEmployeeSalary() { return employeeService.getMaxEmployeeSalary();}

    @GetMapping("/high-salary")
    public List<Employee> showHighSalaryEmployees() { return employeeService.getHighSalaryEmployees();}


        ############# Методы для отладки

    @PostMapping("/add-employee")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody ENewDto eNewDto){

        Employee employee1 = employeeService.createNewEmployee(eNewDto);
        return new ResponseEntity<>(employee1, HttpStatus.OK);
    }

     */
}

