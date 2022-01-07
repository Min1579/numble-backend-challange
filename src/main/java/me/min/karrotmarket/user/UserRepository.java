package me.min.karrotmarket.user;

import me.min.karrotmarket.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>  {
}
