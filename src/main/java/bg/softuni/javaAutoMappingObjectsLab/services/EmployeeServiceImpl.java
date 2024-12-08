package bg.softuni.javaAutoMappingObjectsLab.services;

import bg.softuni.javaAutoMappingObjectsLab.models.Employee;
import bg.softuni.javaAutoMappingObjectsLab.dtos.EmployeeDto;
import bg.softuni.javaAutoMappingObjectsLab.dtos.EmployeeNamesSalaryAndManagerLastNameDto;
import bg.softuni.javaAutoMappingObjectsLab.dtos.ManagerDto;
import bg.softuni.javaAutoMappingObjectsLab.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public EmployeeDto getEmployee(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public List<ManagerDto> getAllManagers() {
        return employeeRepository.findAllBySubordinatesIsNotEmpty()
                .stream()
                .map(m -> modelMapper.map(m, ManagerDto.class))
                .toList();
    }

    @Override
    public void addAll(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    @Override
    public List<EmployeeNamesSalaryAndManagerLastNameDto> getAllEmployeesWithNamesAndManagerLastNameBornBeforeSortedBySalaryReversed(int year) {
        LocalDate date = LocalDate.parse(year + "-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return employeeRepository.findAllByBirthdayBeforeOrderBySalaryDesc(date)
                .stream()
                .map(e -> modelMapper.map(e, EmployeeNamesSalaryAndManagerLastNameDto.class))
                .toList();
    }
}
