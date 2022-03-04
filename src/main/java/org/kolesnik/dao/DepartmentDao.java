package org.kolesnik.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.kolesnik.domain.Department;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DepartmentDao {

    private final SessionFactory sessionFactory;

    public List<Department> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Department> query = session.createNamedQuery("SelectAllDepartments");
        return query.list();
    }

    public Optional<Department> getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Department.class, id));
    }

    public void create(Department department) {
        Session session = sessionFactory.getCurrentSession();
        session.save(department);
    }

    public void delete(Department department) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(department);
    }

    public void update(Department department) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(department);
    }
}
