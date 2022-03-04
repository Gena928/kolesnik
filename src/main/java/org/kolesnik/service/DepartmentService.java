package org.kolesnik.service;

import lombok.RequiredArgsConstructor;
import org.kolesnik.dao.DepartmentDao;
import org.kolesnik.dao.EmployeeDao;
import org.kolesnik.domain.Department;
import org.kolesnik.domain.Employee;
import org.kolesnik.error.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentDao departmentDao;
    private final EmployeeService employeeService;
    private final InMemoryUserDetailsManager userDetailsManager;

    public List<Department> getAll() {
        return departmentDao.getAll();
    }

    public List<Department> getAllForUser(String userName){
        UserDetails details = userDetailsManager.loadUserByUsername(userName);
        if (employeeService.isEmployeeAdmin(details.getUsername())){
            return departmentDao.getAll();
        }
        Employee employee = employeeService.getByUserName(details.getUsername());
        return departmentDao
                .getAll()
                .stream()
                .filter(d->d.getId() == employee.getDepartment().getId())
                .collect(Collectors.toList());
    }

    public Department getById(int id) {
        return departmentDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Can't find department by id " + id));
    }

    public void create(Department department) {
        departmentDao.create(department);
    }

    public void deleteById(int id) {
        Department department = departmentDao.getById(id).get();
        departmentDao.delete(department);
    }

    public void update(Department department) {
        departmentDao.update(department);
    }
}
