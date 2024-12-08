package bg.softuni.javaAutoMappingObjectsLab.repositories;

import bg.softuni.javaAutoMappingObjectsLab.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllBySubordinatesIsNotEmpty();

    List<Employee> findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate birthDay);
}
