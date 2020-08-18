package model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class is a Post
 *
 * @author Денис Висков
 * @version 1.0
 * @since 18.08.2020
 */
public class Post {
    /**
     * ID
     */
    private final int id;

    /**
     * Name
     */
    private final String name;

    /**
     * Description
     */
    private final String description;

    /**
     * Created
     */
    private final LocalDateTime created;

    public Post(int id, String name, String description, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id &&
                Objects.equals(name, post.name) &&
                Objects.equals(description, post.description) &&
                Objects.equals(created, post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, created);
    }
}
