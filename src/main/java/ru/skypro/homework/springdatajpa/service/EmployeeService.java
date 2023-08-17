package ru.skypro.homework.springdatajpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.skypro.homework.springdatajpa.dto.ENewDto;
import ru.skypro.homework.springdatajpa.dto.EmployeeDTO;
import ru.skypro.homework.springdatajpa.dto.EmployeeDtoNew;
import ru.skypro.homework.springdatajpa.dto.EmployeeViewDTO;
import ru.skypro.homework.springdatajpa.exceptions.ApplicationErrors;
import ru.skypro.homework.springdatajpa.exceptions.IncorrectEmployeeIdException;
import ru.skypro.homework.springdatajpa.model.Employee;
import ru.skypro.homework.springdatajpa.model.EmployeeView;
import ru.skypro.homework.springdatajpa.model.Position;
import ru.skypro.homework.springdatajpa.repository.EmployeeRepository;
import ru.skypro.homework.springdatajpa.repository.PositionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final DtoService dtoService;
    private final PositionService positionService;
    public static final Logger logger = LoggerFactory.getLogger(EmployeeService.class.getName());

    public EmployeeService(EmployeeRepository employeeRepository,
                           DtoService dtoService,
                           PositionRepository positionRepository,
                           PositionService positionService) {
        this.employeeRepository = employeeRepository;
        this.dtoService = dtoService;
        this.positionRepository = positionRepository;
        this.positionService = positionService;
    }

    public EmployeeDTO createEmployee(EmployeeDtoNew employeeDtoNew) {
        logger.info("New Employee is being created");
        Boolean employeeDtoNewValidation = dtoService.validateEmployeeDtoNewFields(employeeDtoNew);
        if (employeeDtoNewValidation == false) {
            throw new ApplicationErrors("Новый Employee передан с ошибкой", HttpStatus.BAD_REQUEST);
        } else {
            Integer posId = employeeDtoNew.getPositionIndex();
            Position position = positionService.getPositionById(posId);
            Employee employee = dtoService.fromEmployeeDtoNewToEmployee(employeeDtoNew, position);
            Employee newSavedEmployee = employeeRepository.save(employee);
            logger.info("New Employee successfully created: " + newSavedEmployee);
            EmployeeDTO employeeDTO = dtoService.fromEmployeeToEmployeeDto(newSavedEmployee);
            logger.info("EmployeeDTO передан в слой контроллеров " + employeeDTO);
            return employeeDTO;
        }
    }

    public EmployeeDTO getEmployeeById(Integer id) {
        logger.info("Getting employee by id: " + id);
        Employee employee = findEmployeeById(id);
        EmployeeDTO employeeDTO = dtoService.fromEmployeeToEmployeeDto(employee);
        return employeeDTO;
    }

    public Employee findEmployeeById(Integer id) {
        //Используется в методах: getEmployeeById(Integer id), updateEmployeeById(Integer employeeId, EmployeeDTO employeeDto)
        //deleteEmployeeById(Integer id)
        logger.info("Finding employee by id: " + id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = new Employee();
        if(optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
            logger.info("По id: " + id + " найден " + employee);
        } else {
            throw new IncorrectEmployeeIdException("Employee c id: " + id + " не найден ", HttpStatus.NOT_FOUND);
        }
        return employee;
    }

    public EmployeeDTO updateEmployeeById(Integer employeeId, EmployeeDtoNew employeeDtoNew) {
        logger.info("Employee is being updated");
        Boolean employeeDtoNewValidation = dtoService.validateEmployeeDtoNewFields(employeeDtoNew);
        if (employeeDtoNewValidation == false) {
            throw new ApplicationErrors("Employee для изменения передан с ошибкой", HttpStatus.BAD_REQUEST);
        } else {
            Integer posId = employeeDtoNew.getPositionIndex();
            Position position = positionService.getPositionById(posId);
            Employee employee = findEmployeeById(employeeId);
            //employee = dtoService.fromEmployeeDtoNewToEmployee(employeeDtoNew, position);
            Employee dataToUpdateEmployee =
                    dtoService.fromEmployeeDtoNewToUpdatedEmployee(employeeDtoNew, employee, position);
            Employee updatedEmployee = employeeRepository.save(dataToUpdateEmployee);
            logger.info("Employee successfully updated: " + updatedEmployee);
            EmployeeDTO employeeDTO = dtoService.fromEmployeeToEmployeeDto(updatedEmployee);
            logger.info("EmployeeDTO передан в слой контроллеров " + employeeDTO);
            return employeeDTO;
        }
    }

    public Employee deleteEmployeeById(Integer id) {
        logger.info("Deleting employee by id: " + id);
        Employee employee = findEmployeeById(id);
        employeeRepository.deleteById(id);
        //EmployeeDTO employeeDTO = dtoService.fromEmployeeToEmployeeDto(employee);
        logger.info("Удален Employee: " + employee);
        return employee;
    }

    public List<EmployeeDTO> getAllWithMaxSalary() {
        logger.info("Getting all employees by the greatest salary");
        return employeeRepository.findAllWithMaxSalary()
                .stream()
                .map(EmployeeDTO::fromEmployeeAllFields)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> findAllWithPosition(String position) {
        //System.out.println("position = " + position);
        logger.info("Getting all employees by specific position");
        if(!position.isEmpty()) {
            return employeeRepository.findAllWithPosition(position)
                    .stream()
                    .map(EmployeeDTO::fromEmployeeAllFields)
                    .collect(Collectors.toList());
        } else {
            return employeeRepository.findAll()
                    .stream()
                    .map(EmployeeDTO::fromEmployeeAllFields)
                    .collect(Collectors.toList());
        }
    }

    public EmployeeView findFullInfoById(Integer id) {
        logger.info("DTO is not used: Getting full info by id directly from employeeView");
        logger.info("id = " + id);
        Optional<EmployeeView> employeeView = employeeRepository.findByIdEmployeeView(id);

        return employeeView.orElseThrow(() -> new IncorrectEmployeeIdException("id: " + id));
    }
    public EmployeeViewDTO findViewInfo(Integer id) {
        logger.info("DTO is used: employee full info by id");
        return EmployeeViewDTO.fromEmployeeView(employeeRepository.findByIdEmployeeView(id)
                .orElseThrow(() -> new IncorrectEmployeeIdException("id: " + id)));
    }

    public List<Employee> getAllByPageNumber(int pageIndex, int unitPerPage, String sortBy) {
        logger.info("Getting all employees by specific page: " + pageIndex);
        Pageable paging = PageRequest.of(pageIndex, unitPerPage, Sort.by(sortBy));

        Page<Employee> pagedResult = employeeRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Employee>();
        }
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(EmployeeDTO::fromEmployeeAllFields)
                .collect(Collectors.toList());
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    public int getMinEmployeeSalary() {
        return employeeRepository.getMinSalary();
    }

    /* ########## Методы для отладки

        public Employee createNewEmployee (ENewDto eNewDto) {
        System.out.println("############" + eNewDto);
        Integer posId = eNewDto.getPos();
        Position position = positionService.getPositionById(posId);
        Employee e = new Employee();
        e.setName(eNewDto.getName());
        e.setSalary(eNewDto.getSalary());
        e.setPosition(position);
        System.out.println("EEEEEEEE  " + e);
        Employee newEmployee = employeeRepository.save(e);
        //System.out.println(eNewDto);
        //System.out.println(position);
        System.out.println(newEmployee);
        return newEmployee;
        //return employee;
    }


     ######### */
}
