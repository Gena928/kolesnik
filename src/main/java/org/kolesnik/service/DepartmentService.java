package org.kolesnik.service;

import lombok.RequiredArgsConstructor;
import org.kolesnik.dao.DepartmentDao;
import org.kolesnik.domain.Department;
import org.kolesnik.error.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentDao departmentDao;
    private final InMemoryUserDetailsManager userDetailsManager;

    public List<Department> getAll() {
        return departmentDao.getAll();
    }

    public List<Department> getAllForUser(String userName){
        UserDetails details = userDetailsManager.loadUserByUsername(userName);
        return departmentDao.getAll();
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
