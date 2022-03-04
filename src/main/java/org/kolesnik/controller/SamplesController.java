package org.kolesnik.controller;

import lombok.RequiredArgsConstructor;
import org.kolesnik.domain.AppRoles;
import org.kolesnik.domain.Department;
import org.kolesnik.domain.Employee;
import org.kolesnik.service.DepartmentService;
import org.kolesnik.service.EmployeeService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class SamplesController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final InMemoryUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping({"/samples/create"})
    public ModelAndView samplesCreate() {
        deleteAllEmployees();
        deleteAllDepartments();
        create3Departments();
        createEmployeesForDepartments();
        return new ModelAndView("redirect:/employees/list");
    }

    private void deleteAllEmployees() {
        // админа в базе нет, поэтому не удалится
        for (Employee employee : employeeService.getAll()) {
            userDetailsManager.deleteUser(employee.getName());
            employeeService.delete(employee);
        }
    }

    private void deleteAllDepartments() {
        for (Department department : departmentService.getAll()) {
            departmentService.deleteById(department.getId());
        }
    }

    private void create3Departments() {
        Department logistics = new Department();
        logistics.setName("Logistics");

        Department accounting = new Department();
        accounting.setName("Accounting");

        Department hr = new Department();
        hr.setName("Hr");

        departmentService.create(logistics);
        departmentService.create(accounting);
        departmentService.create(hr);
    }

    private void createEmployeesForDepartments() {
        int counter = 0;
        String login = "";

        for (Department department : departmentService.getAll()) {
            counter++;

            // Создаем менеджера
            Employee manager = new Employee();
            manager.setDepartment(department);
            login = String.format("login%s", counter);
            manager.setName(login);
            manager.setEmpLogin(login);
            manager.setIsDepartmentManager(true);
            userDetailsManager.createUser(createUserDetails(login, AppRoles.MANAGER));
            employeeService.create(manager);

            // Создаем 9 сотрудников. Иногда делаем админа
            for (int i = 0; i < 9; i++) {
                counter++;
                Employee employee = new Employee();
                employee.setDepartment(department);
                login = String.format("login%s", counter);
                employee.setName(login);
                employee.setEmpLogin(login);
                employee.setIsDepartmentManager(false);
                if (i %5 == 0){
                    userDetailsManager.createUser(createUserDetails(login, AppRoles.ADMIN));
                } else {
                    userDetailsManager.createUser(createUserDetails(login, AppRoles.USER));
                }
                employeeService.create(employee);
            }
        }
    }

    private UserDetails createUserDetails(String login, AppRoles userRole){
        return User
                .withUsername(login)
                .authorities(userRole.getAppRole())
                .password(passwordEncoder.encode(login))
                .build();
    }
}
