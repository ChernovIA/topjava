package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private static final BeanPropertyRowMapper<Role> ROW_MAPPER_ROLE = BeanPropertyRowMapper.newInstance(Role.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final SimpleJdbcInsert insertRole;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.insertRole = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_roles");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);

            SqlParameterSource[] paramMap = new SqlParameterSource[user.getRoles().size()];

            int count = 0;
            for(Role role: user.getRoles()){

                paramMap[count++] = new MapSqlParameterSource()
                        .addValue("user_id", newKey.intValue())
                        .addValue("role", role.toString());
            }
            insertRole.executeBatch(paramMap);

            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        //  final User lastRead = null;
        final User[] userArr = new User[1];
        List<User> users = jdbcTemplate.query("SELECT * FROM users as u LEFT JOIN user_roles as r on u.id = r.user_id WHERE id=?",
                (resultSet, i) -> {

                    User user = null;
                    int idRs = resultSet.getInt(1);
                    if (userArr[0] != null && userArr[0].getId() == idRs) {
                        user = userArr[0];
                    } else {
                        user = (new BeanPropertyRowMapper<>(User.class)).mapRow(resultSet, i);
                    }

                    String roleString = resultSet.getString(9);

                    if (roleString != null) {

                        Set<Role> roles = user.getRoles();
                        if (roles == null) {
                            roles = new HashSet<>();
                        }

                        roles.add(Role.valueOf(resultSet.getString(9)));
                        user.setRoles(roles);
                    }

                    userArr[0] = user;
                    return user;
                }, id);

        return users.stream().findFirst().orElse(null);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        final User[] userArr = new User[1];
        List<User> users = jdbcTemplate.query("SELECT * FROM users as u LEFT JOIN user_roles as r on u.id = r.user_id WHERE email=?", (resultSet, i) -> {

            User user = null;
            int idRs = resultSet.getInt(1);
            if (userArr[0] != null && userArr[0].getId() == idRs) {
                user = userArr[0];
            } else {
                user = (new BeanPropertyRowMapper<>(User.class)).mapRow(resultSet, i);
            }

            String roleString = resultSet.getString(9);

            if (roleString != null) {

                Set<Role> roles = user.getRoles();
                if (roles == null) {
                    roles = new HashSet<>();
                }

                roles.add(Role.valueOf(resultSet.getString(9)));
                user.setRoles(roles);
            }

            userArr[0] = user;
            return user;
        }, email);
        return users.stream().findFirst().orElse(null);
    }

    @Override
    public List<User> getAll() {
        final User[] userArr = new User[1];
        List<User> users = jdbcTemplate.query("SELECT * FROM users as u LEFT JOIN user_roles as r on u.id = r.user_id ORDER BY name, email",
                (resultSet, i) -> {

                    User user = null;
                    int idRs = resultSet.getInt(1);
                    if (userArr[0] != null && userArr[0].getId() == idRs) {
                        user = userArr[0];
                    } else {
                        user = (new BeanPropertyRowMapper<>(User.class)).mapRow(resultSet, i);
                    }

                    String roleString = resultSet.getString(9);

                    if (roleString != null) {

                        Set<Role> roles = user.getRoles();
                        if (roles == null) {
                            roles = new HashSet<>();
                        }

                        roles.add(Role.valueOf(resultSet.getString(9)));
                        user.setRoles(roles);
                    }

                    userArr[0] = user;
                    return user;
                });

        return users.stream().distinct().collect(Collectors.toList());
    }

}
