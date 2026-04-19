package in.harshintech.moneymanger.repository;

import in.harshintech.moneymanger.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    //select * from tbl_categories where profile_id = ?1
    List<CategoryEntity> findByProfileId(Long profileId);

    //select * from tbl_categories where id = ?1 and profile_id = ?2
    Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);

    //select * from tbl_categories where type = ?1 and profile_id = ?2
    List<CategoryEntity> findByTypeAndProfileId(String type,Long profileId);

    //select count(*) > 0 from tbl_categories where name = ? and profile_id = ?
    //---> Sometimes Spring/Hibernate use this optimize query depending on database
    //this is fast. Stops after finding first match.
    //select 1 from tbl_categories where name = ? and profile_id = ? limit 1
    Boolean existsByNameAndProfileId(String name,Long profileId);
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