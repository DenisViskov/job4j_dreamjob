package store;

import model.Candidate;
import model.Post;
import model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Class is a psql Store
 *
 * @author Денис Висков
 * @version 1.0
 * @since 21.08.2020
 */
public class PsqlStore implements Store {

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class);

    /**
     * Pool of connection
     */
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    /**
     * Singleton initialization
     */
    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    /**
     * Method returns store instance
     *
     * @return store
     */
    public static Store instOf() {
        return Lazy.INST;
    }

    /**
     * Method returns all posts from DB
     *
     * @return collection
     */
    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"),
                            it.getString("name"),
                            null,
                            null));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return posts;
    }

    /**
     * Method returns all candidates from DB
     *
     * @return collection
     */
    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getString("photo"),
                            it.getString("city")));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidates;
    }

    /**
     * Method save post into DB
     *
     * @param post
     */
    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    /**
     * Method create new post in DB
     *
     * @param post
     * @return post
     */
    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return post;
    }

    /**
     * Method update post in DB
     *
     * @param post
     */
    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE post SET name = (?) WHERE id = (?)")) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            LOG.error(throwables.getSQLState(), throwables);
            throwables.printStackTrace();
        }
    }

    /**
     * Method return post from DB by given ID
     *
     * @param id
     * @return post
     */
    @Override
    public Post findById(int id) {
        Post result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = (?)")) {
            ps.setInt(1, id);
            ResultSet post = ps.executeQuery();
            while (post.next()) {
                result = new Post(post.getInt("id")
                        , post.getString("name"),
                        null,
                        null);
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getSQLState(), throwables);
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * Method save candidate into DB
     *
     * @param candidate
     */
    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    /**
     * Method is an overloading post create and replacement on candidate
     *
     * @param candidate
     * @return candidate
     */
    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO candidates(name,photo,city) VALUES (?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPhoto());
            ps.setString(3, candidate.getCity());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidate;
    }

    /**
     * Method is an overloading post update and replacement on candidate
     *
     * @param candidate
     */
    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE candidates SET name = (?),SET photo = (?),SET city = (?), WHERE id = (?)")) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPhoto());
            ps.setString(3, candidate.getCity());
            ps.setInt(4, candidate.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            LOG.error(throwables.getSQLState(), throwables);
            throwables.printStackTrace();
        }
    }

    /**
     * Method return candidate from DB by given ID
     *
     * @param id
     * @return candidate
     */
    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates WHERE id = (?)")) {
            ps.setInt(1, id);
            ResultSet candidate = ps.executeQuery();
            while (candidate.next()) {
                result = new Candidate(candidate.getInt("id")
                        , candidate.getString("name"),
                        candidate.getString("photo"),
                        candidate.getString("city"));
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getSQLState(), throwables);
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * Method of delete candidate from DB by given ID
     *
     * @param id
     */
    @Override
    public void deleteCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM candidates WHERE id = (?)")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            LOG.error(throwables.getSQLState(), throwables);
            throwables.printStackTrace();
        }
    }

    /**
     * Method of save user into DB
     *
     * @param user
     */
    @Override
    public void saveUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO client(name,email,password) VALUES (?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (SQLException throwables) {
            LOG.error("That user already exists");
            LOG.error(throwables.getMessage(), throwables);
        }
    }

    /**
     * Method looking for user by given email
     *
     * @param email
     * @return user
     */
    @Override
    public User findByEmail(String email) {
        User result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM client WHERE email = (?)")) {
            ps.setString(1, email);
            ResultSet user = ps.executeQuery();
            while (user.next()) {
                result = new User(user.getInt("id"),
                        user.getString("name"),
                        user.getString("email"),
                        user.getString("password"));
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getSQLState(), throwables);
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * Method return all cities from DB
     *
     * @return collection
     */
    @Override
    public Collection<String> getCities() {
        List<String> result = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             Statement st = cn.createStatement()) {
            ResultSet cities = st.executeQuery("SELECT * FROM city");
            while (cities.next()) {
                result.add(cities.getString("name"));
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getSQLState(), throwables);
            throwables.printStackTrace();
        }
        return result;
    }
}
