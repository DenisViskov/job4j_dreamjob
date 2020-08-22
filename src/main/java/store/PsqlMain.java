package store;

import model.Candidate;
import model.Post;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 22.08.2020
 */
public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, ("Java Job"), null, null));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        store.saveCandidate(new Candidate(0, "Ivan java junior"));
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        Post post = store.findById(1);
        Candidate candidate = store.findByIdCandidate(1);
        System.out.println(post.getName() + System.lineSeparator() + candidate.getName());
    }
}
