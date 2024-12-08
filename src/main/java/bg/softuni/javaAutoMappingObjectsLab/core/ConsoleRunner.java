package bg.softuni.javaAutoMappingObjectsLab.core;

import bg.softuni.javaAutoMappingObjectsLab.models.Employee;
import bg.softuni.javaAutoMappingObjectsLab.dtos.EmployeeDto;
import bg.softuni.javaAutoMappingObjectsLab.dtos.ManagerDto;
import bg.softuni.javaAutoMappingObjectsLab.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private EmployeeService employeeService;
    private static final String EMPLOYEE_DATA_FOLDER_PATH = "src/main/resources/employeeData/";
    private static final String FIRST_NAMES_FILE_PATH = "first-names.txt";
    private static final String LAST_NAMES_FILE_PATH = "last-names.txt";
    private static final String ADDRESSES_FILE_PATH = "addresses.txt";

    @Autowired
    public ConsoleRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedData();
//        simpleMapping();
//        advancedMapping();
        projection();
    }

    private void simpleMapping() {
        EmployeeDto employeeDto = employeeService.getEmployee(1L);

        System.out.println(employeeDto);
    }

    private void advancedMapping() {
        List<ManagerDto> managers = employeeService.getAllManagers();

        managers.forEach(System.out::println);
    }

    private void projection() {
        employeeService
                .getAllEmployeesWithNamesAndManagerLastNameBornBeforeSortedBySalaryReversed(1990)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        List<Employee> employees = createEmployees();

        employeeService.addAll(employees);
    }

    private List<Employee> createEmployees() throws IOException {
        List<String> firstNames = Files.readAllLines(Path.of(EMPLOYEE_DATA_FOLDER_PATH + FIRST_NAMES_FILE_PATH));
        List<String> lastNames = Files.readAllLines(Path.of(EMPLOYEE_DATA_FOLDER_PATH + LAST_NAMES_FILE_PATH));
        List<String> addresses = Files.readAllLines(Path.of(EMPLOYEE_DATA_FOLDER_PATH + ADDRESSES_FILE_PATH));

        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setFirstName(getRandomFirstName(firstNames));
        employee.setLastName(getRandomLastName(lastNames));
        employee.setSalary(new BigDecimal("20000"));
        employee.setBirthday(getRandomDayOfBirth());
        employee.setAddress(getRandomAddress(addresses));
        employee.setOnHoliday(getRandomBoolean());
        employee.setManager(null);
        employees.add(employee);

        for (int i = 0; i < 100; i++) {
            employees.add(getEmployee(firstNames, lastNames, addresses, getRandomBoolean(), employees));
        }

        return employees;
    }

    private boolean getRandomBoolean() {
        int number = ThreadLocalRandom.current().nextInt(100) + 1;
        return isPrime(number);
    }

    private boolean isPrime(int number) {
        return number > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(number))
                       .noneMatch(n -> (number % n == 0));
    }

    private Employee getEmployee(List<String> firstNames, List<String> lastNames, List<String> addresses, boolean randomBoolean, List<Employee> employees) {
        Employee employee = new Employee();
        employee.setFirstName(getRandomFirstName(firstNames));
        employee.setLastName(getRandomLastName(lastNames));
        employee.setSalary(getRandomSalary());
        employee.setBirthday(getRandomDayOfBirth());
        employee.setAddress(getRandomAddress(addresses));
        employee.setOnHoliday(getRandomBoolean());
        employee.setManager(getRandomManager(employees));
        return employee;
    }

    private Employee getRandomManager(List<Employee> employeeDtos) {
        return employeeDtos.get(ThreadLocalRandom.current().nextInt(employeeDtos.size()));
    }

    private String getRandomAddress(List<String> addresses) {
        int index = ThreadLocalRandom.current().nextInt(addresses.size());
        return addresses.get(index);
    }

    private LocalDate getRandomDayOfBirth() {
        int year = ThreadLocalRandom.current().nextInt(40) + 1960;
        int month = ThreadLocalRandom.current().nextInt(12) + 1;
        int day = ThreadLocalRandom.current().nextInt(27) + 1;
        return LocalDate.parse(String.format("%s-%02d-%02d", year, month, day), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    }

    private BigDecimal getRandomSalary() {
        double salary = ThreadLocalRandom.current().nextDouble(10) * 1000;

        return new BigDecimal(String.format("%.2f", salary));
    }

    private String getRandomLastName(List<String> lastNames) {
        int index = ThreadLocalRandom.current().nextInt(lastNames.size());

        return lastNames.get(index);
    }

    private String getRandomFirstName(List<String> firstNames) {
        int index = ThreadLocalRandom.current().nextInt(firstNames.size());

        return firstNames.get(index);
    }

}
