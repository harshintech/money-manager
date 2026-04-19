package in.harshintech.moneymanger.repository;

import in.harshintech.moneymanger.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 Your Interface
 ↓
 Spring Data JPA
 ↓
 JPA (rules)
 ↓
 Hibernate (implementation)
 ↓
 SQL
 ↓
 Database
 */
public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {

    //select * from tbl_profiles where email = ?
    Optional<ProfileEntity> findByEmail(String email);

    //select * from tbl_profiles where activation_token = ?
    Optional<ProfileEntity> findByActivationToken(String activationToken);
}


/**
 * Optional<T> → 0 or 1 result
 * List<T> → 0 to many results

  Optional use when one row or result get
  Optional<ProfileEntity> findByEmail(String email);
  If Email not found then we have this propertie in Optional--> Optional.empty()

  List use when many row or result coming from database
  And if data is not found then we get empty list ---> [](empty list)

 ------> Even if no data found:
 Optional → Optional.empty()
 List → []
 So both avoid null

 */
