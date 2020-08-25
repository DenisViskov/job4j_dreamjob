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
     * ID
     */
    private int id;

    /**
     * Name
     */
    private final String name;

    /**
     * Photo name
     */
    private String photo;

    public Candidate(int id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return id == candidate.id &&
                Objects.equals(name, candidate.name) &&
                Objects.equals(photo, candidate.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, photo);
    }
}
