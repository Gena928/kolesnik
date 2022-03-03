package org.kolesnik.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffController {

    @GetMapping({"/staff/list"})
    public String startPage() {
        return "staff/list";
    }
}
