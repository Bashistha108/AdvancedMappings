package com.ende.FinalAdvancedMappings.controller;

import com.ende.FinalAdvancedMappings.entity.Professor;
import com.ende.FinalAdvancedMappings.service.ProfessorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ende.FinalAdvancedMappings.config.CustomUserDetails;

@Controller
public class LoginController {

    ProfessorService profService;

    @Autowired
    LoginController(ProfessorService profService){
        this.profService = profService;
    }

    @GetMapping("/")
    public String homePage(Model model, Principal principal){
        return handleHomeRequest(model, principal);
    }

    @GetMapping("/professor-home")
    public String professorHome(Model model, Principal principal){
        return handleHomeRequest(model, principal);
    }

    @GetMapping("/student-home")
    public String studentHome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        int studentId = userDetails.getId();
        model.addAttribute("studentId", studentId); 
        return "student/student-home";
    }

    @GetMapping("/login-page")
    public String loginPage(){
        return "login-page";
    }
    @GetMapping("/access-denied")
    public String accessDenied(){
        return "access-Denied";
    }



    public String handleHomeRequest(Model model, Principal principal){
        String username = principal.getName();
        Professor professor = profService.findProfessorByEmail(username);

        if(professor == null) {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("Professor not found for username: " + username);
            System.out.println();
            System.out.println();
            System.out.println();


        } else {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("Professor found: " + professor.getId());
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
        }

        model.addAttribute("professor", professor);
        return "professor/professor-home";
    }

}
