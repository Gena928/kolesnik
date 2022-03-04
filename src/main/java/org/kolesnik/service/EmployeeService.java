package org.kolesnik.service;

import lombok.RequiredArgsConstructor;
import org.kolesnik.dao.EmployeeDao;
import org.kolesnik.domain.AppRoles;
import org.kolesnik.domain.Employee;
import org.kolesnik.error.EntityNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeDao employeeDao;
    private final InMemoryUserDetailsManager userDetailsManager;

    public List<Employee> getAll() {
        return updatePasswordForList(employeeDao.getAll());
    }

    public Employee getByUserName(String userName) {
        return employeeDao.getByLogin(userName).orElseThrow(() -> new EntityNotFoundException("Can't find employee by login " + userName));
    }

    public List<Employee> getByDeptId(int departmentId) {
        return updatePasswordForList(employeeDao.getByDeptId(departmentId));
    }

    public List<Employee> getAllOrDepartment(String userName) {
        if (isEmployeeAdmin(userName)) {
            return getAll();
        }
        Employee currentEmployee = getByUserName(userName);
        return getByDeptId(currentEmployee.getDepartment().getId());
    }

    public boolean isEmployeeManager(String userName) {
        UserDetails details = userDetailsManager.loadUserByUsername(userName);
        if (details != null && details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(AppRoles.MANAGER.getAppRole()))) {
            return true;
        }
        return false;
    }

    public boolean isEmployeeAdmin(String userName) {
        UserDetails details = userDetailsManager.loadUserByUsername(userName);
        if (details != null && details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(AppRoles.ADMIN.getAppRole()))) {
            return true;
        }
        return false;
    }

    public void create(Employee employee) {
        employeeDao.create(employee);
    }

    public void delete(Employee employee) {
        employeeDao.delete(employee);
    }

    private List<Employee> updatePasswordForList(List<Employee> employees) {
        for (Employee singleEmployee : employees) {
            updatePasswordForEmployee(singleEmployee);
        }
        return employees;
    }

    private Employee updatePasswordForEmployee(Employee employee) {
        try {
            UserDetails details = userDetailsManager.loadUserByUsername(employee.getName());
            if (details != null) {
                employee.setPassword(details.getPassword());
                employee.setRolesList(new ArrayList<>());
                for (GrantedAuthority authority : details.getAuthorities()) {
                    employee.getRolesList().add(authority.toString());
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return employee;
    }
}
