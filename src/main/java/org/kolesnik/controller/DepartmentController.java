package org.kolesnik.controller;

import lombok.RequiredArgsConstructor;
import org.kolesnik.service.DepartmentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping({"/departments/list"})
    public String startPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("departments", departmentService.getAllForUser(auth.getName()));
        return "departments/list";
    }
}
