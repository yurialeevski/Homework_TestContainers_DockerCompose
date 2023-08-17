package ru.skypro.homework.springdatajpa.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import ru.skypro.homework.springdatajpa.dto.EmployeeDTO;
import ru.skypro.homework.springdatajpa.dto.EmployeeViewDTO;
import ru.skypro.homework.springdatajpa.model.Employee;
import ru.skypro.homework.springdatajpa.model.EmployeeView;
import ru.skypro.homework.springdatajpa.model.Position;
import ru.skypro.homework.springdatajpa.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    public static final Logger logger = LoggerFactory.getLogger(EmployeeServiceTest.class.getName());
    @InjectMocks
    private EmployeeService out;
    @Mock
    private EmployeeRepository dao;

    /*@BeforeEach
    public void init() {
        out = new EmployeeService(repositoryMock);
    }*/
    @Test
    void getAllWithMaxSalary() {
        List<Employee> employeeList = new ArrayList<>();
        for(int i=0; i <= 4; i++) {
            employeeList.add(initEmployee(i+1));
        }
        Mockito.when(dao.findAllWithMaxSalary()).thenReturn(employeeList);
        List<EmployeeDTO> employeeDTOList = out.getAllWithMaxSalary();
        assertEquals(5, employeeDTOList.size());
        Mockito.verify(dao, times(1)).findAllWithMaxSalary();
    }

    @ParameterizedTest
    @ValueSource(strings = {"junior"})
    void shouldReturn_findAllWithPosition(String position) {
        List<Employee> employeeList = new ArrayList<>();
        for(int i=0; i <= 4; i++) {
            employeeList.add(initEmployee(i+1));
        }
        Mockito.when(dao.findAllWithPosition(position)).thenReturn(employeeList);
        List<EmployeeDTO> empDTOList = out.findAllWithPosition(position);
        assertEquals(5, empDTOList.size());
        Mockito.verify(dao, times(1)).findAllWithPosition(position);
        logger.info("должность " + position);
        logger.info("Возвращает " + empDTOList);
    }

    @Test
    void shouldReturnPageWithEmployeesWhen_getAllByPageNumber() {
        List<Employee> employeeList = new ArrayList<>();
        for(int i=0; i <= 4; i++) {
            employeeList.add(initEmployee(i+1));
        }
        int pageIndex = 0;
        int unitPerPage = 6;
        String sortBy = "name";
        Page page = new PageImpl(employeeList);
        Pageable paging = PageRequest.of(pageIndex, unitPerPage, Sort.by(sortBy));
        Mockito.when(dao.findAll(paging)).thenReturn(page);
        assertEquals(employeeList, out.getAllByPageNumber(pageIndex, unitPerPage, sortBy));
        Mockito.verify(dao, times(1)).findAll(paging);
    }

    @Test
    void shouldReturn100When_getMinEmployeeSalary() {
        Mockito.when(dao.getMinSalary()).thenReturn(100);
        assertEquals(100, out.getMinEmployeeSalary());
        Mockito.verify(dao, times(1)).getMinSalary();
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void shouldReturnProjectionFrom_findViewInfo(Integer argument) {
        EmployeeView employeeView = new EmployeeView() {
            @Override
            public String getName() {
                return "viewName";
            }

            @Override
            public Integer getSalary() {
                return 100;
            }

            @Override
            public Position getPosition() {
                Position position = new Position();
                position.setId(1);
                position.setRole("middle");
                return position;
            }
        };

        EmployeeViewDTO viewDto = new EmployeeViewDTO();
        viewDto.setName(employeeView.getName());
        viewDto.setSalary(employeeView.getSalary());
        viewDto.setRole(employeeView.getPosition().getRole());

        logger.info("Возвращает " + viewDto);
        Mockito.when(dao.findByIdEmployeeView(argument)).thenReturn(Optional.of(employeeView));
        assertEquals(viewDto, out.findViewInfo(argument));
        Mockito.verify(dao, times(1)).findByIdEmployeeView(argument);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void shouldReturnEmployeeWhenIdIsCorrectIn_getEmployeeById(Integer id) {
        Employee employee = initEmployee(id);
        logger.info("Возвращает " + employee);
        Mockito.when(dao.findById(id)).thenReturn(Optional.of(employee));
        assertEquals(employee, out.getEmployeeById(id));
        Mockito.verify(dao, times(1)).findById(id);
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 22, 33})
    public void shouldThrowRuntimeExceptionWhenIncorrectIdIn_getEmployeeById(Integer argument) {
        Mockito.when(dao.findById(argument))
                .thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class,
                () -> out.getEmployeeById(argument));
        //out.getEmployeeById(argument);
    }

    @Test
    public void testFindAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        for(int i=0; i <= 4; i++) {
            employeeList.add(initEmployee(i+1));
        }
        //System.out.println(employeeList);
        Mockito.when(dao.findAll()).thenReturn(employeeList);
        List<Employee> empList = out.findAll();
        assertEquals(5, empList.size());
        Mockito.verify(dao, times(1)).findAll();
    }
    public static Employee initEmployee(Integer id) {
        Position position = new Position();
        position.setId(1);
        position.setRole("junior");
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("employee_" + id);
        employee.setSalary(200*id);
        employee.setPosition(position);
        return employee;
    }

}