package com.iAnalyze.AuthMicroService.dao;

import com.iAnalyze.AuthMicroService.utils.SqlCommands;
import com.iAnalyze.AuthMicroService.exceptions.CustomRuntimeException;
import com.iAnalyze.AuthMicroService.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDataAccessService implements UserDao {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer registerUser(UUID id, User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());

        try {
            return jdbcTemplate.update(
                    SqlCommands.SQL_CREATE,
                    id,
                    user.getUsername(),
                    user.getEmail(),
                    hashedPassword
            );
        } catch(Exception ex){
            throw new CustomRuntimeException("Invalid details. Failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws CustomRuntimeException {
       try {

           User user = jdbcTemplate.queryForObject(SqlCommands.SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
           if(!BCrypt.checkpw(password, user.getPassword()))
               throw new CustomRuntimeException("Invalid email/password");
           return user;
       } catch (EmptyResultDataAccessException ex) {
           throw new CustomRuntimeException("Invalid email/password");
       }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SqlCommands.SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject(SqlCommands.SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
    }

//    @Override
//    public User findById(String userId) {
//        return null;
//    }

//    @Override
//    public User findById(String userId) {
//        return jdbcTemplate.queryForObject(SqlCommands.SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
//    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
//        String stringId = rs.getString("id");
        UUID id = UUID.fromString(rs.getString("id"));
        String username = rs.getString("username");
        String email = rs.getString("username");
        String password = rs.getString("password");
        return
                new User(
                        id,
                        username,
                        email,
                        password
                );
    });
}
