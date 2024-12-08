package bg.softuni.javaAutoMappingObjectsLab.dtos;

import java.util.List;
import java.util.stream.Collectors;

public class ManagerDto {
    private String firstName;
    private String lastName;
    private List<EmployeeDto> subordinates;
    private Integer countOfSubordinates;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<EmployeeDto> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<EmployeeDto> subordinates) {
        this.subordinates = subordinates;
    }

    public Integer getCountOfSubordinates() {
        return countOfSubordinates;
    }

    public void setCountOfSubordinates(Integer countOfSubordinates) {
        this.countOfSubordinates = countOfSubordinates;
    }

    @Override
    public String toString() {
        return String.format("""
                %s %s | Employees: %d
                %s
                """,
                firstName, lastName, countOfSubordinates,
                subordinates
                        .stream()
                        .map(e -> "    - " + e.toString())
                        .collect(Collectors.joining(System.lineSeparator())));
    }
}
