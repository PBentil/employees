package com.example.AuthenticationSystem.HR.controller;

import com.example.AuthenticationSystem.HR.model.*;
import com.example.AuthenticationSystem.HR.DTO.EmployeeDetails;
import com.example.AuthenticationSystem.HR.repository.EmployeeAttendance;
import com.example.AuthenticationSystem.HR.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class addEmployee {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeAttendance employeeAttendance;
    // Temporary storage for employee and job details
    private Employee temporaryEmployeeDetails = null;
    private JobDetails temporaryJobDetails = null;
    private final  LocalTime allowedCheckTime=LocalTime.of(9,0);
    private String UniqueId;

    //Mapping for webpages
    @GetMapping("/security")
    public String signupPage() {
        return "HR/security";  // Spring Boot for templates/security.html
    }

    @GetMapping("/tasks")
    public String tasksPage(){
        return "HR/Tasks";
    }
    @GetMapping("/Attendances")
    public  String attendancePage(Model model){
         List<Attendance> attendance=employeeService.AttendanceRecords();
         model.addAttribute("attendances",attendance);
        return "HR/Attendances";
    }


    @GetMapping("/profile")
    public String Profile(){
        return "Employee/profile-management";
    }
@GetMapping("/taskEmployees")
public ResponseEntity<List<Employee>> getAssignedEmployees(Model model){
        List<Employee> employees=employeeService.getAssignableEmployees();
 return ResponseEntity.ok(employees);
}

@GetMapping("/employees")
public String EmployeePage(){
        return "HR/employees";
}
@GetMapping("/contract")
public String contractPage() {
        return "HR/contract";
    }
@GetMapping("/dashboard")
public String dashboard() {
        return "HR/dashboard";
}
@GetMapping("/payroll")
public String payroll(){
        return  "HR/payroll";
}
@GetMapping("/document")
public String document(){
        return "HR/document";
}

    @GetMapping("/loginForm")
    public String login() {
        return "HR/EmployeeForm";}

    @GetMapping("/Leaves")
    public String leaves() {
        return "HR/leaves";
    }

    @GetMapping("attendance")
    public String attendance(){
        return "Employee/attendance";
    }
    @GetMapping("/feedbacks")
    public String FeedbackPage(){
        return "Employee/feedbacks";
    }
    @GetMapping("/leave")
    public  String Leave(){
        return "Employee/leave";
    }

    @GetMapping("announcements")
    public  String announcememts(){
        return "Employee/announcements";
    }
    @GetMapping("meetings")
    public String meetingsPage(){
        return "Employee/meeting";
    }
    @GetMapping("dash")
    public String eDashboard() {
        return "Employee/dash";
    }
    @GetMapping("/etask")
    public String taskPage(){

        return "Employee/tasks";
    }
    @GetMapping("epayroll")
    public String Epayroll(){
        return "Employee/payroll";
    }

    @GetMapping("/getEmployeeCount")
    public ResponseEntity<Long> getEmployeeCount(Model model) {
        Long count = employeeService.getEmployeeCount();
        model.addAttribute("count", count);
        return ResponseEntity.ok(count);
    }



    @PostMapping("/personal")
    public ResponseEntity<Map<String, Object>> submitAllData(@RequestBody Employee employeeDetails) {
        Map<String, Object> response = new HashMap<>();
        this.temporaryEmployeeDetails = employeeDetails;
        response.put("status", "success");
        response.put("message", "Personal data received. Please submit compensation details next.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/jobDetails")
    public ResponseEntity<Map<String, Object>> submitJobData(@RequestBody JobDetails jobDetails) {
        Map<String, Object> response = new HashMap<>();
        this.temporaryJobDetails = jobDetails;
        response.put("status", "success");
        response.put("message", "Job details received. Please submit compensation details next.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/compensation")
    public ResponseEntity<Map<String, Object>> submitCompensationData(@RequestBody Compensation compensation) {
        Map<String, Object> response = new HashMap<>();
        try {

            if (temporaryEmployeeDetails == null || temporaryJobDetails == null) {
                response.put("status", "error");
                response.put("message", "Employee or job details are missing. Please submit all the forms first.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }



            employeeService.addEmployee(temporaryEmployeeDetails);
            employeeService.saveJobDetails(temporaryJobDetails);
            employeeService.saveCompensationDetails(compensation);


            this.temporaryEmployeeDetails = null;
            this.temporaryJobDetails = null;

            response.put("status", "success");
            response.put("message", "All employee data, job details, and compensation details saved successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }




    @PostMapping("/loginForm")
    public String LogUser(@RequestParam String email, @RequestParam String password, Model model) {
        Employee employee = employeeService.LoginUser(email, password);
        if (employee != null) {
            model.addAttribute("employee", employee);
            Attendance attendance = new Attendance();
            attendance.setUniqueId(employee.getUniqueId());
            UniqueId = employee.getUniqueId();
            LocalDate today = LocalDate.now();
            Optional<Attendance> existingAttendance = employeeAttendance.findByUniqueIdAndDate(employee.getUniqueId(), today);

            if (existingAttendance.isPresent()) {
                model.addAttribute("error", "Already Logged in today");
                return "HR/EmployeeForm";
            }

            attendance.setDate(LocalDate.now());
            LocalTime checkInTime = LocalTime.now();
            attendance.setCheckInTime(checkInTime);
            if (checkInTime.isAfter(allowedCheckTime)) {
                attendance.setTimeliness("Late");
            } else {
                attendance.setTimeliness("Early");
            }
            attendance.setStatus("Present");
            employeeService.markAttendance(attendance);
            model.addAttribute("employee", employee);
            String role = employee.getRole();
            if ("Employee".equals(role)) {
                return "Employee/task";
            } else {
                return "HR/dashboard";
            }

        } else {
            model.addAttribute("error", "Invalid login credentials.");
            return "loginForm";
        }
    }

    @GetMapping("/logout")
    public String  getLogout(){
        return "redirect:/loginForm";
    }
    @PostMapping("/logout")
    public String LogOut() {


        boolean loggedIn = employeeService.isLoggedIn(UniqueId);

        if (loggedIn) {
      return "HR/EmployeeForm";} else {
                      return "Not logged in";
        }

    }



@PostMapping("/leave")
    public String LeaveRequest(@RequestParam String leaveType,@RequestParam String startDate,@RequestParam String endDate,@RequestParam String reason){
       Leave leave = new Leave();
       leave.setUniqueId(UniqueId);
       leave.setLeaveType(leaveType);
       leave.setStartDate(startDate);
       leave.setEndDate(endDate);
       leave.setReason(reason);
       leave.setStatus("Pending");
       employeeService.LeaveRequest(leave);
       return "Employee/leave";
    }

    @GetMapping("/leaveData")
    public ResponseEntity<Page<Leave>> leaveForms(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "4") int size) {
        Page<Leave> leavePage = employeeService.getLeavePages(page, size);
        return ResponseEntity.ok(leavePage);
    }
    @GetMapping("/AttendanceData")
    public ResponseEntity<Page<Attendance>>Attendances(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "4") int size) {
        Page<Attendance>AttendancePages = employeeService.getAttendances(page, size);
        return ResponseEntity.ok(AttendancePages);
    }
    @GetMapping("/PayrollData")
    public ResponseEntity<Page<Compensation>>Payroll(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "4") int size) {
        Page<Compensation>PayrollPages = employeeService.getPayroll(page, size);
        return ResponseEntity.ok(PayrollPages);
    }
    @GetMapping("/EmployeeData")
    public ResponseEntity<Page<Employee>>Employees(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "4") int size) {
        Page<Employee>Employees= employeeService.getEmployees(page, size);
        return ResponseEntity.ok(Employees);
    }
@PostMapping("/feedbacks")
    public String submitFeedback(@RequestParam String feedbackType, @RequestParam String feedbackDesc) {
        FandC fandC = new FandC();
        fandC.setUniqueId(UniqueId);
        fandC.setFeedbackType(feedbackType);
        fandC.setFeedbackDesc(feedbackDesc);
        fandC.setStatus("Pending");
        employeeService.SaveFeedbacksAndComplaints(fandC);
        return "Employee/feedbacks";
}
@PostMapping("/assignTask")
    public ResponseEntity<Tasks> assignTask(@RequestParam String uniqueId,@RequestParam String taskTitle,@RequestParam String priority,@RequestParam String dueDate,@RequestParam String taskDescription){
        Tasks newTask=new Tasks();
        newTask.setUniqueId(uniqueId);
        newTask.setTaskTitle(taskTitle);
        newTask.setPriority(priority);
        newTask.setDueDate(dueDate);
        newTask.setTaskDescription(taskDescription);
        LocalTime today=LocalTime.now();
        newTask.setAssignedAt(today);
        newTask.setStatus("Pending");
        employeeService.saveTask(newTask);
        return ResponseEntity.ok(newTask);
}
@GetMapping("/attendanceData")
public ResponseEntity<List<Attendance>> getAttendances( String uniqueId) {
    uniqueId=UniqueId;
    List<Attendance> attendances = employeeService.findAttendances(uniqueId);
    return ResponseEntity.ok(attendances);
}
    @GetMapping("/taskData")
    public ResponseEntity<List<Task>> getTasks( String uniqueId) {
        uniqueId=UniqueId;
        List<Task> tasks = employeeService.findTask(uniqueId);
        return ResponseEntity.ok(tasks);
    }
    @GetMapping ("/profileManagement")
    public ResponseEntity<Employee> getEmployeeProfile(String uniqueId){
        uniqueId=UniqueId;
       Employee employee=employeeService.getEmployeeProfile(uniqueId);
       return ResponseEntity.ok(employee);
    }
}