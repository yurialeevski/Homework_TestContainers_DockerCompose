package ru.skypro.homework.springdatajpa.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private Integer id;
    private String role;
    //@OneToMany(mappedBy = "position", orphanRemoval = true)
    //private List<Employee> employees;

    public Position() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //public List<Employee> getEmployees() {
        //return employees;
    //}

    //public void setEmployees(List<Employee> employees) {
        //this.employees = employees;
    //}

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", role='" + role + '\'' +
                //", employees=" + employees +
                '}';
    }
}
