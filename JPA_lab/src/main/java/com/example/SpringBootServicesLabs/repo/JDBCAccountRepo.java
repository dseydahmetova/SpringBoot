package com.example.SpringBootServicesLabs.repo;

import com.example.SpringBootServicesLabs.entities.Account;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
@Profile("h2")
public class JDBCAccountRepo implements AccountRepository{

    private JdbcTemplate template;

    public  JDBCAccountRepo(JdbcTemplate template){
        this.template = template;
    }

        private RowMapper<Account> getAccountRowMapper(){
        return (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Double balance = resultSet.getDouble("balance");
            return new Account(id, name, balance);
        };
    }

@Override
    public List<Account> getAllAccounts() {
        String sql = "SELECT * FROM accounts";
        return template.query(sql, getAccountRowMapper());
    }

    @Override
    public Account findAccountById(int id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        return template.queryForObject(sql, getAccountRowMapper(), id);
    }

    @Override
    public void saveAccount(Account account) {
        String sql = "INSERT INTO accounts (name, balance) VALUES (?, ?)";
        template.update(sql, account.getName(), account.getBalance());

    }

    @Override
    public void batchInsert(List<Account> accounts) {
        String sql = "INSERT INTO accounts (name, balance) VALUES (?, ?)";
        template.batchUpdate(sql, accounts, 50,
                (PreparedStatement ps, Account account) -> {
            ps.setString(1, account.getName());
            ps.setDouble(2, account.getBalance());
                });
    }

}
