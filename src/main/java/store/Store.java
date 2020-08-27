package store;

import model.Candidate;
import model.Post;
import model.User;

import java.util.Collection;

/**
 * Interface of Store
 *
 * @author Денис Висков
 * @version 1.0
 * @since 21.08.2020
 */
public interface Store {
    /**
     * Method should return all posts from storage
     *
     * @return collection
     */
    Collection<Post> findAllPosts();

    /**
     * Method should return all candidates from storage
     *
     * @return collection
     */
    Collection<Candidate> findAllCandidates();

    /**
     * Method should save post into storage
     *
     * @param post
     */
    void save(Post post);

    /**
     * Method should return post by given ID
     *
     * @param id
     * @return Post
     */
    Post findById(int id);

    /**
     * Method should save candidate
     *
     * @param candidate
     */
    void saveCandidate(Candidate candidate);

    /**
     * Method should return candidate by given ID
     *
     * @param id
     * @return candidate
     */
    Candidate findByIdCandidate(int id);

    /**
     * Method should delete candidate from storage
     *
     * @param id
     */
    void deleteCandidate(int id);

    /**
     * Method should save user to store
     *
     * @param user
     */
    void saveUser(User user);

    /**
     * Method should return user from store by given email
     *
     * @param email
     * @return User
     */
    User findByEmail(String email);
}
