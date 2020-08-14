package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class of Candidate
 *
 * @author Денис Висков
 * @version 1.0
 * @since 14.08.2020
 */
public class Candidate {
    /**
     * Name
     */
    private final String name;

    /**
     * Last Name
     */
    private final String lastName;

    /**
     * Position
     */
    private final String position;

    /**
     * Birth date
     */
    private final LocalDate birthDate;

    public Candidate(String name, String lastName, String position, LocalDate birthDate) {
        this.name = name;
        this.lastName = lastName;
        this.position = position;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return Objects.equals(name, candidate.name)
                && Objects.equals(lastName, candidate.lastName)
                && Objects.equals(position, candidate.position)
                && Objects.equals(birthDate, candidate.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, position, birthDate);
    }
}
