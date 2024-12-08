package bg.softuni.javaAutoMappingObjectsLab.config;

import bg.softuni.javaAutoMappingObjectsLab.models.Employee;
import bg.softuni.javaAutoMappingObjectsLab.dtos.EmployeeDto;
import bg.softuni.javaAutoMappingObjectsLab.dtos.EmployeeNamesSalaryAndManagerLastNameDto;
import bg.softuni.javaAutoMappingObjectsLab.dtos.ManagerDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
public class ApplicationBeanConfiguration {
    private static ModelMapper modelMapper;

    @Bean
    public ModelMapper getInstance() {
        if (modelMapper != null) {
            return modelMapper;
        }

        modelMapper = new ModelMapper();

        addMappings();

        return modelMapper;
    }

    private void addMappings() {
        employeeToManagerDtoMappings();
        employeeToEmployeeNamesSalaryAndManagerLastNameDtoMappings();
    }

    private void employeeToManagerDtoMappings() {
        Converter<Collection<EmployeeDto>, Integer> employeeCountConverter = c -> c.getSource() == null ?
                null :
                c.getSource().size();

        modelMapper
                .createTypeMap(Employee.class, ManagerDto.class)
                .addMappings(mapper -> mapper
                .using(employeeCountConverter)
                .map(Employee::getSubordinates, ManagerDto::setCountOfSubordinates));
    }

    private void employeeToEmployeeNamesSalaryAndManagerLastNameDtoMappings() {
        Converter<Employee, String> managerConverter = c -> c.getSource() == null ? "[no manager]" : c.getSource().getLastName();
        modelMapper.createTypeMap(Employee.class, EmployeeNamesSalaryAndManagerLastNameDto.class)
                .addMappings(mapper -> mapper
                        .using(managerConverter)
                        .map(Employee::getManager, EmployeeNamesSalaryAndManagerLastNameDto::setManagerLastName));
    }

}
