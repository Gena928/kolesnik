package org.kolesnik.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StaffController {

    @GetMapping({"/staff/list"})
    public String startPage(Model model) {

        return "staff/list";
    }
}
