package org.kolesnik.controller;

import lombok.RequiredArgsConstructor;
import org.kolesnik.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class EmployeesController {

    private final EmployeeService employeeService;

    @GetMapping({"/employees/list"})
    public String startPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("employees", employeeService.getAllOrDepartment(auth.getName()));

        model.addAttribute("canEdit", "false");
        if (employeeService.isEmployeeManager(auth.getName())){
            model.addAttribute("canEdit", "true");
        }
        if (employeeService.isEmployeeAdmin(auth.getName())){
            model.addAttribute("canEdit", "true");
        }
        return "employees/list";
    }
}
