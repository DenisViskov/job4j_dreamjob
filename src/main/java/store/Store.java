package store;

import model.Candidate;
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

    /**
     * Candidates
     */
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", null, LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", null, LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", null, LocalDateTime.now()));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    /**
     * Return singleton
     *
     * @return INST
     */
    public static Store instOf() {
        return INST;
    }

    /**
     * Returns all posts from store
     *
     * @return posts
     */
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    /**
     * Return all candidates from store
     *
     * @return candidates
     */
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}
