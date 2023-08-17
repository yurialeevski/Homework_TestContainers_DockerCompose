package ru.skypro.homework.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skypro.homework.springdatajpa.model.Employee;
import ru.skypro.homework.springdatajpa.model.EmployeeView;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "select e from Employee e where e.salary = (SELECT MAX(e.salary) FROM Employee e)")
    List<Employee> findAllWithMaxSalary();
    @Query(value= "select e from Employee e where e.position.role = :position")
    List<Employee> findAllWithPosition(@Param("position") String position);
    @Query(value = "select e from Employee e where e.employeeId = :id")
    Optional<EmployeeView> findByIdEmployeeView(@Param("id") Integer id);
    @Query(value = "select MIN(e.salary) from Employee e")
    int getMinSalary();
}
