package store;

import model.Candidate;
import model.Post;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class MemStoreTest {

    private final MemStore store = MemStore.instOf();

    @Test
    public void saveTest() {
        Post post = new Post(0, "Post", null, null);
        store.save(post);
        boolean result = store.findAllPosts().contains(post);
        assertThat(result, is(true));
    }

    @Test
    public void saveCandidateTest() {
        Candidate candidate = new Candidate(0, "Ivan");
        store.saveCandidate(candidate);
        boolean result = store.findAllCandidates().contains(candidate);
        assertThat(result, is(true));
    }

    @Test
    public void findByIdTest() {
        Post post = new Post(5, "Post", null, null);
        store.save(post);
        Post expected = store.findById(5);
        assertThat(expected.getName(), is("Post"));
    }

    @Test
    public void findByIdCandidateTest() {
        Candidate candidate = new Candidate(5, "Candidate");
        store.saveCandidate(candidate);
        Candidate expected = store.findByIdCandidate(5);
        assertThat(expected.getName(), is("Candidate"));
    }
}