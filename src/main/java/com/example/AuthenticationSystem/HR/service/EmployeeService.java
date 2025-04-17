package com.example.AuthenticationSystem.HR.service;
import com.example.AuthenticationSystem.HR.model.*;
import com.example.AuthenticationSystem.HR.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {

    @Autowired
    private  LeaveRepo leaveRepository;


    @Autowired
    private SupportRequestRepo support;

    @Autowired
    private AnnouncementRepo announcementRepo;
@Autowired
private FeedbacksAndComplaintsRepo feedbacksRepository;
    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired  // Inject JobRepo with @Autowired
    private JobRepo jobRepository;

    @Autowired  // Inject CompensationRepo with @Autowired
    private CompensationRepo compensationRepository;


    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private SupportRequestRepo supportRequestRepository;


    @Autowired
    private EmployeeAttendance employeeAttendance;

    @Transactional
    public Employee addEmployee(Employee employee) {
       return employeeRepository.save(employee);

    }

    @Transactional
    public JobDetails saveJobDetails(JobDetails jobDetails) {
        return jobRepository.save(jobDetails);
    }

    @Transactional
    public Compensation saveCompensationDetails(Compensation compensation) {
        return compensationRepository.save(compensation);
    }

    public Page<Employee> findEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);  // Create a Pageable object
        return employeeRepository.findAll(pageable);  // Return paged results
    }

    public Employee LoginUser(String email, String password) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee == null) {
            throw new RuntimeException("Employee not found!");
        }


        if (!employee.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password!");
        }



        return employee;
    }

    public Attendance markAttendance(Attendance attendance) {
        LocalDate today = LocalDate.now();
        String uniqueId = attendance.getUniqueId();

        // Check if the user has already checked in today
        Optional<Attendance> existingAttendance = employeeAttendance.findByUniqueIdAndDate(uniqueId, today);

        if (existingAttendance.isPresent()) {
            return existingAttendance.get();
        }


        attendance.setCheckInTime(attendance.getCheckInTime().truncatedTo(ChronoUnit.MINUTES));

        return employeeAttendance.save(attendance);
    }


    public  Leave LeaveRequest (Leave leave) {

        return leaveRepository.save(leave);
    }
    public List<Leave> showLeaveForms(){
        return leaveRepository.findAllByStatus("Pending");
    }
    public  List<Attendance>AttendanceRecords(){
        return employeeAttendance.findAll();
    }

    public boolean isLoggedIn(String uniqueId) {
        LocalDate today = LocalDate.now();

        Optional<Attendance> attendanceOpt=employeeAttendance.findByUniqueIdAndDate(uniqueId,today);
        if(attendanceOpt.isPresent()){
            Attendance attendance = attendanceOpt.get();
         attendance.setCheckOutTime(LocalTime.now());
            employeeAttendance.save(attendance);
            return true;
        }
else{
    return false;
        }
    }
    @Transactional
    public FandC SaveFeedbacksAndComplaints(FandC fandC) {
        return feedbacksRepository.save(fandC);
    }

    public Page<Leave> getLeavePages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return leaveRepository.findAll( pageable);
    }
    public Page<Attendance> getAttendances(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeAttendance.findAll(pageable);
    }
    public Page<Compensation> getPayroll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return compensationRepository.findAll(pageable);
    }
        public Page<Employee> getEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return employeeRepository.findAll(pageable);
    }
    public Long getEmployeeCount() {
        return employeeRepository.count();  // This should return Long (or use Optional<Long>)
    }

public List<Employee> getAssignableEmployees() {
        List<Employee> employees=employeeRepository.findAll();
        return  employees;
}
public Tasks saveTask(Tasks task){
      return taskRepo.save(task);
}
public Page<Tasks> findTasks(String uniqueId, int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        return taskRepo.findAllByUniqueId(uniqueId,pageable);
}
    public List<Attendance> findAttendances(String uniqueId){
        return employeeAttendance.findAllByUniqueId(uniqueId);
    }

    public Employee getEmployeeProfile(String firstName){
       Employee  employee= employeeRepository.findByUniqueId(firstName);
       return employee;
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return employeeAttendance.findByDate(date);
    }

    public int getPeoplePresent(LocalDate date) {
        return (int) employeeAttendance.findByDate(date)
                .stream()
                .filter(a -> "Present".equalsIgnoreCase(a.getStatus()))
                .count();
    }
    public boolean rejectLeave(Long id) {
        Optional<Leave> leaveOptional = leaveRepository.findById(id);

        if (leaveOptional.isPresent()) {
            Leave leave = leaveOptional.get();
            System.out.println("Leave found for ID: " + id);

            if (!"Denied".equalsIgnoreCase(leave.getStatus())) {
                leave.setStatus("Denied");
                leaveRepository.save(leave);
                return true;
            } else {
                System.out.println("Leave already denied.");
            }
        } else {
            System.out.println("Leave not found for ID: " + id);
        }

        return false;
    }
    public boolean approveLeave(Long id) {
        Optional<Leave> leaveOptional = leaveRepository.findById(id);

        if (leaveOptional.isPresent()) {
            Leave leave = leaveOptional.get();
            System.out.println("Leave found for ID: " + id);

            if (!"Approved".equalsIgnoreCase(leave.getStatus())) {
                leave.setStatus("Approved");
                leaveRepository.save(leave);
                return true;
            } else {
                System.out.println("Leave already approved.");
            }
        } else {
            System.out.println("Leave not found for ID: " + id);
        }

        return false;
    }
public Employee foundEmployee(String uniqueId){
    return employeeRepository.findByUniqueId(uniqueId);
}
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public int getTotalCapacity() {
        return (int) employeeAttendance.count(); // Total registered employees
    }

    public Page<Announcement> getAnnouncements(int page,int size){
        Pageable pageable=PageRequest.of(page,size);
        return  announcementRepo.findAll(pageable);
    }
    public void saveRequest(SupportRequest Request){

        supportRequestRepository.save(Request);
    }
    public List<Leave>findEmployeeLeaveRequest(String uniqueid){
        return leaveRepository.findByUniqueId(uniqueid);
    }
    public  Page<Leave> filteredRecords(String filter,int page,int size){
        Pageable pageable= PageRequest.of(page, size);
        return leaveRepository.findfilteredRecords(filter,pageable);

    }
    public  Page<Leave> filteredEmployeeRecords(String filter,String uniqueId,int page,int size){
        Pageable pageable= PageRequest.of(page, size);
        return leaveRepository.findEmployeeFilteredRecords(filter,uniqueId,pageable);

    }
    public  Page<Leave> EmployeeLeaveRecords(String uniqueId,int page,int size){
        Pageable pageable= PageRequest.of(page, size);
        return leaveRepository.findBySpecific(uniqueId,pageable);

    }


    public  Page<Attendance> filteredAttendanceRecords(String filter,int page,int size){
        Pageable pageable= PageRequest.of(page, size);
        return employeeAttendance.findfilteredRecords(filter,pageable);

    }

public SupportRequest submitRequest(SupportRequest supportRequest){
        return support.save(supportRequest);
}
    public void deleteEmployee(String uniqueId){
        employeeRepository.deleteByUniqueId(uniqueId);
    }

    public Page<SupportRequest> helpRequests(int page,int size){
        Pageable pageable=PageRequest.of(page,size);
        return  supportRequestRepository.findAll(pageable);
    }
    public Page<SupportRequest> filteredHelpRequests(String filter,int page,int size){
        Pageable pageable=PageRequest.of( page,size);
        return supportRequestRepository.filterPriorityRequests(filter,pageable);
    }
    public Optional<SupportRequest> findRequest(Long Id){
        return  supportRequestRepository.findById(Id);
    }

}
