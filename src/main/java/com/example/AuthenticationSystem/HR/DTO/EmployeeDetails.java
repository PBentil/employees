package com.example.AuthenticationSystem.HR.DTO;

import com.example.AuthenticationSystem.HR.model.Compensation;
import com.example.AuthenticationSystem.HR.model.Employee;
import com.example.AuthenticationSystem.HR.model.JobDetails;

public class EmployeeDetails {
    private Employee employee;
    private JobDetails jobDetails;
    private Compensation compensation;

    // Getters and Setters
    public Compensation getCompensation() {
        return compensation;
    }

    public void setCompensation(Compensation compensation) {
        this.compensation = compensation;
    }

    public JobDetails getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(JobDetails jobDetails) {
        this.jobDetails = jobDetails;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public Employee toEntity() {
        Employee emp = new Employee();
        emp.setFirstName(this.employee.getFirstName());
        emp.setLastName(this.employee.getLastName());
        emp.setEmail(this.employee.getEmail());
        emp.setPhone(this.employee.getPhone());
        emp.setAddress(this.employee.getAddress());
        emp.setUniqueId(this.employee.getUniqueId());
        emp.setPassword(this.employee.getPassword());// Ensure uniqueId is set
        return emp;
    }
    public JobDetails toJobDTO() {
        JobDetails job = new JobDetails();
        job.setJobRole(this.jobDetails.getJobRole());
        job.setJobLevel(this.jobDetails.getJobLevel());
        job.setShiftStart(this.jobDetails.getShiftStart());
        job.setShiftEnd(this.jobDetails.getShiftEnd());
        return job;
    }
}