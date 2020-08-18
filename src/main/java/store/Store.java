package store;

import model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class of Store
 *
 * @author Денис Висков
 * @version 1.0
 * @since 18.08.2020
 */
public class Store {
    /**
     * Store instance
     */
    private static final Store INST = new Store();

    /**
     * Posts
     */
    private Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", null, LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", null, LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", null, LocalDateTime.now()));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
