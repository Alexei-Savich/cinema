package com.example.validation.controllers;

import com.example.validation.entities.StaffWorker;
import com.example.validation.services.StaffWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    private final StaffWorkerService staffWorkerService;

    @Autowired
    public AdminController(StaffWorkerService userService) {
        this.staffWorkerService = userService;
    }

    @GetMapping("/admin/users/add")
    public String showAddUserForm() {
        return "user/add-user";
    }

    @PostMapping("/admin/users/add")
    public String addUser(@ModelAttribute StaffWorker worker, RedirectAttributes redirectAttributes) {
        worker.setPassword(new BCryptPasswordEncoder().encode(worker.getPassword()));
        staffWorkerService.saveWorker(worker);
        redirectAttributes.addFlashAttribute("message", "User added successfully.");
        return "redirect:/admin/users/add";
    }

    @GetMapping("/admin/users")
    public String searchUsers(@RequestParam(value = "query", defaultValue = "") String query,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              Model model) {
        int pageSize = 100;
        Page<StaffWorker> workers = staffWorkerService.searchWorkers(query, page, pageSize);
        model.addAttribute("query", query);
        model.addAttribute("users", workers);
        return "user/users";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        StaffWorker user = staffWorkerService.findWorkerById(id);
        model.addAttribute("user", user);
        return "user/edit-user";
    }

    @PostMapping("/admin/users/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute("user") StaffWorker updatedUser,
                             @RequestParam("password") String password,
                             @RequestParam("confirmPassword") String confirmPassword,
                             RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match.");
            return "redirect:/admin/users/edit/" + id;
        }
        StaffWorker user = staffWorkerService.findWorkerById(id);
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        user.setRole(updatedUser.getRole());
        if (!password.isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(password));
        }
        staffWorkerService.updateWorker(user);
        redirectAttributes.addFlashAttribute("successMessage", "User updated successfully.");
        return "redirect:/admin/users/edit/" + id;
    }


}
