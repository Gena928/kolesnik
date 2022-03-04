package org.kolesnik.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.kolesnik.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeDao {

    private final SessionFactory sessionFactory;

    public List<Employee> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Employee> query = session.createNamedQuery("SelectAllEmployees");
        return query.list();
    }

    public Optional<Employee> getByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Employee.class, login));
    }

    public List<Employee> getByDeptId(int departmentId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Employee> query = session.createNamedQuery("SelectEmployeesByDept");
        query.setParameter("id", departmentId);
        return query.list();
    }

    public void create(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
        session.save(employee);
    }

    public void delete(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(employee);
    }
}
