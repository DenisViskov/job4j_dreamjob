package model;

import java.util.Objects;

/**
 * Class is an HR
 *
 * @author Денис Висков
 * @version 1.0
 * @since 14.08.2020
 */
public class HR {
    /**
     * Name
     */
    private final String name;

    /**
     * Last name
     */
    private final String lastName;

    /**
     * Department
     */
    private final String department;

    public HR(String name, String lastName, String department) {
        this.name = name;
        this.lastName = lastName;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HR hr = (HR) o;
        return Objects.equals(name, hr.name)
                && Objects.equals(lastName, hr.lastName)
                && Objects.equals(department, hr.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, department);
    }
}
