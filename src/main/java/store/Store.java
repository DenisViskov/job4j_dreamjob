package store;

import model.Candidate;
import model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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

    /**
     * Post ID
     */
    private static AtomicInteger POST_ID = new AtomicInteger(4);

    /**
     * Candidate ID
     */
    private static AtomicInteger CANDIDATE_ID = new AtomicInteger(4);

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

    /**
     * Method of save post
     *
     * @param post
     */
    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    /**
     * Method of save candidate
     *
     * @param candidate
     */
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    /**
     * Method returns post by given ID
     *
     * @param id
     * @return Post
     */
    public Post findById(int id) {
        return posts.get(id);
    }

    /**
     * Method returns candidate by given ID
     *
     * @param id
     * @return Candidate
     */
    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }
}
