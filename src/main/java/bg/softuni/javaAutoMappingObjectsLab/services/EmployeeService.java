package bg.softuni.javaAutoMappingObjectsLab.services;

import bg.softuni.javaAutoMappingObjectsLab.models.Employee;
import bg.softuni.javaAutoMappingObjectsLab.dtos.EmployeeDto;
import bg.softuni.javaAutoMappingObjectsLab.dtos.EmployeeNamesSalaryAndManagerLastNameDto;
import bg.softuni.javaAutoMappingObjectsLab.dtos.ManagerDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto getEmployee(long id);

    List<ManagerDto> getAllManagers();

    void addAll(List<Employee> employees);

    List<EmployeeNamesSalaryAndManagerLastNameDto> getAllEmployeesWithNamesAndManagerLastNameBornBeforeSortedBySalaryReversed(int year);

}
