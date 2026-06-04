package com.intellihire;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.util.List;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import com.intellihire.entity.Job;
import com.intellihire.service.JobService;
import com.intellihire.entity.Application;
import com.intellihire.service.ApplicationService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.intellihire.entity.User;
import com.intellihire.service.UserService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class HomeController {

    @Autowired
    private UserService service;
    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicationService applicationService;
    private Integer jobId;

    @GetMapping("/")
    public String home() {
        return "index";
    }



    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        if(name.isBlank() ||
                email.isBlank() ||
                password.isBlank()){

            model.addAttribute("error",
                    "All fields are required");

            return "index";
        }
        try {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole("USER");
            service.saveUser(user);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam int id, Model model) {
        User user = service.getUserById(id);
        model.addAttribute("user", user);
        return "edit";
    }
    @GetMapping("/applications")
    public String applications(
            Model model,
            HttpSession session) {

        User user =
                (User) session.getAttribute("loggedUser");

        if(user == null ||
                !user.getRole().equals("ADMIN")) {

            return "redirect:/dashboard";
        }

        model.addAttribute(
                "applications",
                applicationService.getAllApplications()
        );

        return "applications";
    }

    @PostMapping("/update")
    public String updateUser(
            @ModelAttribute User user,
            @RequestParam(required = false) String newPassword) {

        service.updateUser(user, newPassword);

        return "redirect:/users";
    }

    @GetMapping("/search")
    public String searchUsers(
            @RequestParam String name,
            Model model) {

        List<User> users = service.searchUsers(name);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        return "users";
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/uploadResume")
    public String uploadResume(
            @RequestParam("file") MultipartFile file,
            HttpSession session,
            RedirectAttributes redirectAttributes) throws IOException{

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        String fileName =
                System.currentTimeMillis() + "_" +
                        file.getOriginalFilename()
                                .replace(" ", "_")
                                .replace("(", "")
                                .replace(")", "");

        Path uploadPath = Paths.get(System.getProperty("user.dir"), uploadDir);
        System.out.println("user.dir = " + System.getProperty("user.dir"));
        System.out.println("uploadDir = " + uploadDir);
        System.out.println("uploadPath = " + uploadPath.toAbsolutePath());

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        System.out.println(filePath.toAbsolutePath());

        System.out.println(">>> Upload directory: " + uploadPath.toAbsolutePath());
        System.out.println(">>> File path: " + filePath.toAbsolutePath());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Exists: " + Files.exists(filePath));
        System.out.println("Size: " + Files.size(filePath));

        user.setResume(fileName);
        user.setResume(fileName);
        service.updateUser(user, null);
        session.setAttribute("loggedUser", user);
        redirectAttributes.addFlashAttribute(
                "success",
                "Resume uploaded successfully!"
        );

        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/loginUser")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {

        User user = service.loginUser(email, password);

        if (user != null) {
            session.setAttribute("loggedUser", user);
            return "redirect:/dashboard";
        }

        return "loginfail";
    }
    @GetMapping("/jobDetails")
    public String jobDetails(
            @RequestParam int id,
            Model model){

        Job job = jobService.getJobById(id);

        model.addAttribute("job", job);

        return "jobdetails";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }

        model.addAttribute("totalUsers", service.totalUsers());
        model.addAttribute("totalJobs", jobService.totalJobs());
        model.addAttribute("totalApplications", applicationService.totalApplications());

        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !user.getRole().equals("ADMIN")) {
            return "redirect:/dashboard";
        }

        return "admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String users(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        var usersPage = service.getUsersByPage(page);
        model.addAttribute("users", usersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        return "users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam int id) {
        service.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/addJob")
    public String addJobPage(HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !user.getRole().equals("ADMIN")) {
            return "redirect:/dashboard";
        }

        return "addjob";
    }

    @PostMapping("/saveJob")
    public String saveJob(
            @RequestParam String title,
            @RequestParam String company,
            @RequestParam String location,
            @RequestParam String salary,
            @RequestParam String description) {

        Job job = new Job();
        job.setTitle(title);
        job.setCompany(company);
        job.setLocation(location);
        job.setSalary(salary);
        job.setDescription(description);
        jobService.saveJob(job);
        return "redirect:/jobs";
    }

    @GetMapping("/jobs")
    public String jobs(Model model,
                       HttpSession session) {

        if(session.getAttribute("loggedUser") == null){
            return "redirect:/login";
        }
        User user =
                (User) session.getAttribute("loggedUser");

        model.addAttribute(
                "jobs",
                jobService.getAllJobs()
        );

        if(user != null) {

            model.addAttribute(
                    "applications",
                    applicationService.getApplicationsByUser(
                            user.getEmail()
                    )
            );
        }

        return "jobs";
    }

    @GetMapping("/deleteJob")
    public String deleteJob(@RequestParam int id, HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !user.getRole().equals("ADMIN")) {
            return "redirect:/jobs";
        }

        jobService.deleteJob(id);
        return "redirect:/jobs";
    }

    @GetMapping("/applyJob")
    public String applyJob(
            @RequestParam int id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("loggedUser");

        if(user == null){
            return "redirect:/login";
        }

        Job job = jobService.getJobById(id);
        if(applicationService.alreadyApplied(
                user.getEmail(),
                job.getTitle())){
            return "alreadyapplied";
        }

        if(job == null){
            return "redirect:/jobs";
        }


        if(user.getResume() == null || user.getResume().isEmpty()){
            return "redirect:/profile";
        }

        Application app = new Application();

        app.setUserName(user.getName());
        app.setUserEmail(user.getEmail());
        app.setJobTitle(job.getTitle());
        app.setCompany(job.getCompany());
        app.setResume(user.getResume());
        app.setStatus("PENDING");

        applicationService.save(app);

        redirectAttributes.addFlashAttribute(
                "success",
                "Application submitted successfully!"
        );

        return "redirect:/myApplications";
    }
    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotpassword";
    }
    @GetMapping("/updateStatus")
    public String updateStatus(
            @RequestParam int id,
            @RequestParam String status,
            HttpSession session){

        User user=(User)session.getAttribute("loggedUser");

        if(user==null || !user.getRole().equals("ADMIN")){
            return "redirect:/dashboard";
        }

        Application app=applicationService.getById(id);

        if(app!=null){
            app.setStatus(status);
            applicationService.save(app);
        }

        return "redirect:/applications";
    }
    @GetMapping("/myApplications")
    public String myApplications(
            Model model,
            HttpSession session){

        User user =
                (User) session.getAttribute("loggedUser");

        if(user == null){
            return "redirect:/login";
        }

        model.addAttribute(
                "applications",
                applicationService.getApplicationsByUser(
                        user.getEmail()
                )
        );

        return "myapplications";
    }
    @GetMapping("/searchJob")
    public String searchJob(
            @RequestParam String title,
            Model model,
            HttpSession session){

        User user =
                (User) session.getAttribute("loggedUser");

        model.addAttribute(
                "jobs",
                jobService.searchJobs(title)
        );

        if(user != null){
            model.addAttribute(
                    "applications",
                    applicationService.getApplicationsByUser(
                            user.getEmail()
                    )
            );
        }

        return "jobs";
    }


}