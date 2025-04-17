package com.example.AuthenticationSystem.HR.controller;

import com.example.AuthenticationSystem.HR.model.*;
import com.example.AuthenticationSystem.HR.repository.EmployeeAttendance;
import com.example.AuthenticationSystem.HR.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
public class addEmployee {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeAttendance employeeAttendance;
    // Temporary storage for employee and job details
    private Employee temporaryEmployeeDetails = null;
    private JobDetails temporaryJobDetails = null;
    private final LocalTime allowedCheckTime = LocalTime.of(9, 0);
    private String UniqueId;

    //Mapping for webpages
    @GetMapping("/security")
    public String signupPage() {
        return "HR/security";  // Spring Boot for templates/security.html
    }

    @GetMapping("/tasks")
    public String tasksPage() {
        return "HR/Tasks";
    }

    @GetMapping("/newprofile")
    public  String newProfile(){
        return  "HR/newprofile";
    }
@GetMapping("/leaveHistory")
public String leaveHistoryPage(){
        return  "Employee/leaveHistory";
}
    @GetMapping("/Attendances")
    public String attendancePage(Model model) {
        List<Attendance> attendance = employeeService.AttendanceRecords();
        model.addAttribute("attendances", attendance);
        return "HR/Attendances";
    }

    @GetMapping("/HRannouncements")
    public String HRAnnouncments(){
        return "HR/Announcements";
    }
@GetMapping("/help&support")
public String HelpSupportPage(){
        return "Employee/Help&Support";
}
    @GetMapping("/support")
    public String SupportPage(){
        return "HR/Support";
    }

    @GetMapping("/profile")
    public String Profile() {
        return "Employee/profile-management";
    }

    @GetMapping("/taskEmployees")
    public ResponseEntity<List<Employee>> getAssignedEmployees(Model model) {
        List<Employee> employees = employeeService.getAssignableEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/reset-password")
    public String ResetPage(){
        return "Employee/reset-password";
    }

    @GetMapping("/employees")
    public String EmployeePage() {
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
    public String payroll() {
        return "HR/payroll";
    }

    @GetMapping("/document")
    public String document() {
        return "HR/document";
    }

    @GetMapping("/loginForm")
    public String login() {
        return "HR/EmployeeForm";
    }

    @GetMapping("/Leaves")
    public String leaves() {
        return "HR/leaves";
    }

    @GetMapping("attendance")
    public String attendance() {
        return "Employee/attendance";
    }

    @GetMapping("/feedbacks")
    public String FeedbackPage() {
        return "Employee/feedbacks";
    }

    @GetMapping("/leave")
    public String Leave() {
        return "Employee/leave";
    }

    @GetMapping("announcements")
    public String announcememts() {
        return "Employee/announcements";
    }

    @GetMapping("meetings")
    public String meetingsPage() {
        return "Employee/meeting";
    }

    @GetMapping("dash")
    public String eDashboard() {
        return "Employee/dash";
    }

    @GetMapping("/etask")
    public String taskPage() {

        return "Employee/tasks";
    }

    @GetMapping("epayroll")
    public String Epayroll() {
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
            UniqueId = employee.getUniqueId();  // Declare uniqueId
            LocalDate today = LocalDate.now();

            Optional<Attendance> existingAttendance = employeeAttendance.findByUniqueIdAndDate(UniqueId, today);
            List<Leave> leaveRequests = employeeService.findEmployeeLeaveRequest(UniqueId); // Get leave requests

//            if (existingAttendance.isPresent()) {
//                model.addAttribute("error", "Already Logged in today");
//                return "HR/EmployeeForm";
//            }

            for (Leave leave : leaveRequests) {
                LocalDate startDate = LocalDate.parse(leave.getStartDate());
                LocalDate endDate = LocalDate.parse(leave.getEndDate());



                    if ("Employee".equals(employee.getRole())) {
                        return "Employee/task";
                    } else {
                        return "HR/dashboard";
                    }

            }

            attendance.setDate(today);
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

            if ("Employee".equals(employee.getRole())) {
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
    public String getLogout() {
        return "redirect:/loginForm";
    }

    @PostMapping("/logout")
    public String LogOut() {


        boolean loggedIn = employeeService.isLoggedIn(UniqueId);

        if (loggedIn) {
            return "HR/EmployeeForm";
        } else {
            return "Not logged in";
        }

    }


    @PostMapping("/leave")
    public String LeaveRequest(@RequestParam String leaveType, @RequestParam String startDate, @RequestParam String endDate, @RequestParam String reason) {
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
    public ResponseEntity<Page<Attendance>> Attendances(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "8") int size) {
        Page<Attendance> AttendancePages = employeeService.getAttendances(page, size);
        return ResponseEntity.ok(AttendancePages);
    }

    @GetMapping("/PayrollData")
    public ResponseEntity<Page<Compensation>> Payroll(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "4") int size) {
        Page<Compensation> PayrollPages = employeeService.getPayroll(page, size);
        return ResponseEntity.ok(PayrollPages);
    }

    @GetMapping("/EmployeeData")
    public ResponseEntity<Page<Employee>> Employees(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "4") int size) {
        Page<Employee> Employees = employeeService.getEmployees(page, size);
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
    public ResponseEntity<Tasks> assignTask(@RequestParam String uniqueId, @RequestParam String taskTitle, @RequestParam String priority, @RequestParam String dueDate, @RequestParam String taskDescription) {
        Tasks newTask = new Tasks();
        newTask.setUniqueId(uniqueId);
        newTask.setTaskTitle(taskTitle);
        newTask.setPriority(priority);
        newTask.setDueDate(dueDate);
        newTask.setTaskDescription(taskDescription);
        LocalTime today = LocalTime.now();
        newTask.setAssignedAt(today);
        newTask.setStatus("Pending");
        employeeService.saveTask(newTask);
        return ResponseEntity.ok(newTask);
    }

    @GetMapping("/attendanceData")
    public ResponseEntity<List<Attendance>> getAttendances(String uniqueId) {
        uniqueId = UniqueId;
        List<Attendance> attendances = employeeService.findAttendances(uniqueId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/taskData")
    public ResponseEntity<Page<Tasks>> getTasks(String uniqueId,@RequestParam int page,@RequestParam int size) {
        uniqueId = UniqueId;
        Page<Tasks> tasks = employeeService.findTasks(uniqueId,page,size);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/profileManagement")
    public ResponseEntity<Employee> getEmployeeProfile(String uniqueId) {
        uniqueId = UniqueId;
        Employee employee = employeeService.getEmployeeProfile(uniqueId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/attendanceChart")
    public ResponseEntity<Map<String, Integer>> getAttendanceChartData() {
        LocalDate today = LocalDate.now(); // Get today's date
        List<Attendance> attendances = employeeAttendance.findByDate(today);

        int totalCapacity = 100; // Example: total expected employees
        int peoplePresent = (int) attendances.stream().filter(a -> "Present".equals(a.getStatus())).count();

        Map<String, Integer> response = new HashMap<>();
        response.put("totalCapacity", totalCapacity);
        response.put("peoplePresent", peoplePresent);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/today")
    public ResponseEntity<List<Attendance>> getTodayAttendance() {
        List<Attendance> attendances = employeeAttendance.findByDate(LocalDate.now());

        if (attendances == null) {
            return ResponseEntity.ok(Collections.emptyList()); // Return an empty JSON []
        }

        return ResponseEntity.ok(attendances);
    }

    @PostMapping("/approveLeave/{id}")
    public ResponseEntity<Map<String, String>> approveLeave(@PathVariable Long id) {
        boolean isApproved = employeeService.approveLeave(id);
        Map<String, String> response = new HashMap<>();

        if (isApproved) {
            response.put("message", "Leave approved successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Leave request not found or already approved.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/rejectLeave/{id}")
    public ResponseEntity<Map<String, String>> rejectLeave(@PathVariable Long id) {
        boolean isDenied = employeeService.rejectLeave(id);
        Map<String, String> response = new HashMap<>();

        if (isDenied) {
            response.put("message", "Leave approved successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Leave request not found or already approved.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/count/{dayOfWeek}")
    public ResponseEntity<Map<String, Object>> getAttendanceCountByDay(@PathVariable String dayOfWeek) {
        Map<String, Object> response = new HashMap<>();
        try {
            DayOfWeek day = DayOfWeek.valueOf(dayOfWeek.toUpperCase()); // Convert string to DayOfWeek enum
            long count = employeeAttendance.countByDayOfWeek(day);

            response.put("day", dayOfWeek);
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("error", "Invalid day of the week: " + dayOfWeek);
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody Map<String, String> passwordData){
        String currentPassword = passwordData.get("currentPassword");
        String newPassword = passwordData.get("newPassword");
        System.out.println("Received Data:");
        System.out.println("Current Password: " + currentPassword);
        System.out.println("New Password: " + newPassword);


        Map<String, String> response = new HashMap<>();

        // Check if UniqueId is received
        String unique =UniqueId;
        System.out.println("UniqueId: " + unique);

        if (unique == null || unique.isEmpty()) {
            response.put("error", "No employee is logged in or UniqueId is missing.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Employee employee = employeeService.foundEmployee(unique);
        if (employee == null) {
            response.put("error", "Employee not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (!employee.getPassword().equals(currentPassword)) {
            response.put("error", "Current password is incorrect.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }



        employee.setPassword(newPassword);
        employeeService.saveEmployee(employee);

        response.put("message", "Password changed successfully.");
        return ResponseEntity.ok(response);
    }
@GetMapping("eAnnouncements")
    public  ResponseEntity<Page<Announcement>> getAnnouncements(@RequestParam (defaultValue = "0")  int page,@RequestParam (defaultValue = "4") int size){

    Page<Announcement> announcements=employeeService.getAnnouncements(page, size);
    return ResponseEntity.ok(announcements);

}
    @DeleteMapping("deleteEmployee")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@RequestBody Map<String, String> requestBody) {
        String employeeId = requestBody.get("employeeId");

        if (employeeId == null || employeeId.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Employee ID is required"));
        }

        Employee employee = employeeService.foundEmployee(employeeId);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Employee not found"));
        }

        employeeService.deleteEmployee(employee.getUniqueId());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee Deleted Successfully");
        response.put("deletedEmployee", employee);

        return ResponseEntity.ok(response);
    }

@GetMapping("/getEmployeeInfo")
    public ResponseEntity<Employee>getEmployeeProfile(){
        Employee employee=employeeService.foundEmployee(UniqueId);
        return  ResponseEntity.ok(employee);
    }
    @GetMapping("/filteredLeaveRecords")
    public ResponseEntity<Page<Leave>>filteredLeaveData(@RequestParam String filter,@RequestParam int page,@RequestParam int size){
        if(filter.equalsIgnoreCase("All")){
            Page<Leave> leavePage = employeeService.getLeavePages(page, size);
            return ResponseEntity.ok(leavePage);
        }
        else {
            Page<Leave> filteredLeaveRecords = employeeService.filteredRecords(filter, page, size);
            return ResponseEntity.ok(filteredLeaveRecords);
        }
    }
    @GetMapping("/filteredEmployeeLeaveRecords")
    public ResponseEntity<Page<Leave>>filteredEmployeeLeaveData(@RequestParam String filter,@RequestParam int page,@RequestParam int size){
        if(filter.equalsIgnoreCase("All")){
            Page<Leave> leavePage = employeeService.EmployeeLeaveRecords(UniqueId,page, size);
            return ResponseEntity.ok(leavePage);
        }
        else {
            Page<Leave> filteredLeaveRecords = employeeService.filteredEmployeeRecords(filter,UniqueId, page, size);
            return ResponseEntity.ok(filteredLeaveRecords);
        }
    }
    @GetMapping("/filteredAttendanceRecords")
    public ResponseEntity<Page<Attendance>>filteredAttendanceData(@RequestParam String filter,@RequestParam int page,@RequestParam int size){
        if(filter.equalsIgnoreCase("All")){
            Page<Attendance> attendancePage = employeeService.getAttendances(page, size);
            return ResponseEntity.ok(attendancePage);
        }
        else {
            Page<Attendance> filteredAttendanceRecords = employeeService.filteredAttendanceRecords(filter, page, size);
            return ResponseEntity.ok(filteredAttendanceRecords);
        }
    }

    @PostMapping("/helpRequest")
    public ResponseEntity<Map<String, String>> helpRequest(@RequestBody SupportRequest supportRequest) {

        employeeService.submitRequest(supportRequest);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Help Request Submitted successfully");

        return ResponseEntity.ok(response);
    }
    @GetMapping("/helpRequests")
public ResponseEntity<Page<SupportRequest>> helpRequest(@RequestParam  int page, @RequestParam int size){
        Page<SupportRequest>requests=employeeService.helpRequests(page,size);
        return  ResponseEntity.ok(requests);
}
@PostMapping ("submitResponse")
    public ResponseEntity<String> submitResponse(@RequestParam Long id, @RequestParam String response) {
        Optional<SupportRequest> request = employeeService.findRequest(id);
        if (request.isPresent()) {
            SupportRequest supportRequest = request.get();
            supportRequest.setResponse(response);


            employeeService.submitRequest(supportRequest);

            return ResponseEntity.ok("Response submitted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found.");
        }
    }

}
