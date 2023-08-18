package ru.skypro.homework.springdatajpa.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test; //
import org.springframework.beans.factory.annotation.Autowired; //
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; //
import org.springframework.boot.test.context.SpringBootTest; //
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc; //
import ru.skypro.homework.springdatajpa.dto.ENewDto;
import ru.skypro.homework.springdatajpa.dto.EmployeeDtoNew;
import ru.skypro.homework.springdatajpa.exceptions.ApplicationErrors;
import ru.skypro.homework.springdatajpa.exceptions.IncorrectEmployeeIdException;
import ru.skypro.homework.springdatajpa.repository.EmployeeRepository;
import ru.skypro.homework.springdatajpa.repository.PositionRepository;
import ru.skypro.homework.springdatajpa.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EmployeeControllerMockMvcIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    MockMvc mockMvc;

    /**@AfterEach
    public void resetDb() {
        employeeRepository.deleteAll();
        positionRepository.deleteAll();
    }*/
 /*   @Test
    public void getAllByPage() throws Exception {
        Employee employee = createTestEmployee("PageName");
        //List<Employee> employeeList = new ArrayList<>();
        //employeeList.add(employee);
        mockMvc.perform(get("/employee/pages")
                        .param("pageIndex", "0")
                        .param("unitPerPage", "1")
                        .param("sortBy", "employeeId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(1));
    }
    @Test
    void addNewEmployeeToEmptyDataBase_thenCheckDataBaseIsNotEmpty() throws Exception {
        Position position = new Position();
        position = createTestPosition("middle");
        EmployeeDtoNew employeeDtoNew = new EmployeeDtoNew("TestToCreate", 300,1);
        //JSONObject employeeObject =
        //new JSONObject("{\"name\":\"TestToCreate\",\"salary\":300,\"positionIndex\":1}");

        mockMvc.perform(post("/employee/add-one-employee")
                        .content(objectMapper.writeValueAsString(employeeDtoNew))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.salary").isNumber())
                .andExpect(jsonPath("$.name").value("TestToCreate"));

        List<Employee> employeeList = employeeRepository.findAll();
        Integer integer = employeeList.get(0).getEmployeeId();
        mockMvc.perform(get("/employee/{id}/employee-by-id", integer))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(integer))
                .andExpect(jsonPath("$.name").value("TestToCreate"));
    }

    @Test
    public void gettingByIdExistingEmployee_thenStatus200andEmployeeDtoReturned() throws Exception {
        Employee employee = createTestEmployee("TestGettingEmployee");
        Integer integer = employee.getEmployeeId();
        mockMvc.perform(get("/employee/{id}/employee-by-id", integer))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(integer))
                .andExpect(jsonPath("$.name").value("TestGettingEmployee"));
    }
    @Test
    public void gettingByIdEmployee_whenIdEmployeeNotExists_thenThrowStatus404Exception() throws Exception {
        mockMvc.perform(get("/employee/1/employee-by-id"))
                .andExpect(status().isNotFound());
    }*/
/*
    @Test
    public void inputNewEmployeeDataWhenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        Employee employee = createTestEmployee("CreatedName");
        Integer integer = employee.getEmployeeId();
        EmployeeDtoNew employeeDtoNew = new EmployeeDtoNew("TestToUpdate", 400,integer);
        mockMvc.perform(put("/employee/{id}", integer)
                                .content(objectMapper.writeValueAsString(employeeDtoNew))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(integer))
                .andExpect(jsonPath("$.name").value("TestToUpdate"));
    }
*/
 /*   @Test
    public void inputNewEmployeeDataWhenDelete_thenStatus200andDeletedReturns() throws Exception {
        Employee employee = createTestEmployee("CreatedName");
        mockMvc.perform(get("/employee/allEmployees"))
                .andExpect(status().isOk())
                .andExpectAll(jsonPath("$").isArray(), jsonPath("$").isNotEmpty());

        Integer integer = employee.getEmployeeId();
        mockMvc.perform(delete("/employee/{id}", integer))
                        //.content(objectMapper.writeValueAsString(employeeDtoNew))
                        //.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employee)));

        mockMvc.perform(get("/employee/allEmployees"))
                .andExpect(status().isOk())
                .andExpectAll(jsonPath("$").isArray(), jsonPath("$").isEmpty());
    }*/


    private Position createTestPosition(String name) {
        Position position = new Position();
        position.setRole(name);
        return positionRepository.save(position);
    }
    private Employee createTestEmployee(String name){
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSalary(300);
        Position position = new Position();
        position = createTestPosition("director");
        employee.setPosition(position);
        return employeeRepository.save(employee);
    }

/*    @Test
    void givenNoUsersInDatabase_whenGetUsers_thenEmptyJsonArray() throws Exception {
        mockMvc.perform(get("/employee/allEmployees"))
                .andExpect(status().isOk())
                .andExpectAll(jsonPath("$").isArray(), jsonPath("$").isEmpty());
    }*/

    /* ########### Методы для отладки тестов

        @Test
        public void givenEmployee_whenAdd_thenStatus200andEmployeeReturned() throws Exception {
        Position position = new Position();
        position = createTestPosition("junior");

        ENewDto eNewDto = new ENewDto("TestName", 300, 1);

        Employee employee = new Employee(null, "TestName", 300, null);

        mockMvc.perform(post("/employee/add-employee")
                                .content(objectMapper.writeValueAsString(eNewDto))
                                //.content(objectMapper.writeValueAsString(employee))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").isNumber())
                .andExpect(jsonPath("$.salary").isNumber())
                .andExpect(jsonPath("$.name").value("TestName"));
    }

     №№№№№№№№№№№№№№№№№№№№*/




}