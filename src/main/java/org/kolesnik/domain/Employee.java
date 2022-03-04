package org.kolesnik.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
@NamedQueries({
        @NamedQuery(name = "SelectAllEmployees", query = "from Employee e order by e.department.name"),
        @NamedQuery(name = "SelectEmployeesByDept",
                query = "select emp from Employee emp inner join emp.department as dept where dept.id = :id order by dept.name")
})
public class Employee {

    @Id
    @Column(nullable = false)
    private String empLogin;
    private String name;
    @ManyToOne
    private Department department;
    private Boolean isDepartmentManager;

    // Эти поля берутся не из базы, а из Spring Security
    @Transient
    private String password;
    @Transient
    private List<String> rolesList;
}
