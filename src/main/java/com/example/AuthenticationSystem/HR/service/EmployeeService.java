package com.example.AuthenticationSystem.HR.service;
import com.example.AuthenticationSystem.HR.model.*;
import com.example.AuthenticationSystem.HR.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {

    @Autowired
    private  LeaveRepo leaveRepository;

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
    private EmployeeAttendance employeeAttendance;

    @Transactional
    public ResponseEntity<Object> addEmployee(Employee employee) {
       Employee savedEmployee= employeeRepository.save(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
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

        // Check if the password matches (no encoder)
        if (!employee.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password!");
        }



        return employee;
    }

    public Attendance markAttendance(Attendance attendance) {

      return employeeAttendance.save(attendance);
    }
    public  Leave LeaveRequest (Leave leave) {

        return leaveRepository.save(leave);
    }
    public List<Leave> showLeaveForms(){
        return leaveRepository.findAll();
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
        return leaveRepository.findAll(pageable);
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
public List<Tasks> findTasks(String uniqueId){
        return taskRepo.findAllByUniqueId(uniqueId);
}
    public List<Attendance> findAttendances(String uniqueId){
        return employeeAttendance.findAllByUniqueId(uniqueId);
    }
}
