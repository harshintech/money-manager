package in.harshintech.moneymanger.repository;

import in.harshintech.moneymanger.entity.IncomeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeEntity,Long> {

    //select * from tbl_incomes where profile_id = ?1 order by date desc
    List<IncomeEntity> findByProfileIdOrderByDateDesc(Long profileId);

    //select * from tbl_incomes where profile_id = ?1 order by date desc limit 5
    List<IncomeEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);

    @Query("SELECT SUM(i.amount) FROM IncomeEntity i WHERE i.profile.id = :profileId")
    BigDecimal findTotalIncomeByProfileId(@Param("profileId") Long profileId);


    /**
     SELECT *
     FROM tbl_incomes
     WHERE profile_id = ?1
     AND date BETWEEN ?2 AND ?3
     AND LOWER(name) LIKE LOWER(CONCAT('%', ?4, '%'))
     ORDER BY amount DESC;
     */
    List<IncomeEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            Long profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );

    //select * from tbl_incomes where profile_id = ?1 and date between ?2 and ?3
    List<IncomeEntity> findByProfileIdAndDateBetween(Long profileId,LocalDate startDate,LocalDate endDate);


}

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

 * Question : so we can not use hibernate directly
 Good 🔥 now you’re asking **real architecture-level question**

 ---

 # 🧠 Short Answer

 👉 ❌ No, it’s NOT that you *cannot* use Hibernate
 👉 ✅ You **CAN use Hibernate directly**

 ---

 # 🔥 But in real projects

 👉 We usually use:

 > Spring Data JPA
 > instead of directly using
 > Hibernate

 ---

 # 🧠 Why?

 Because JPA + Spring makes life EASY 😄

 ---

 # ⚡ 1️⃣ Using Hibernate DIRECTLY (hard way)

 ```java id="0l3wfd"
 Session session = sessionFactory.openSession();
 Transaction tx = session.beginTransaction();

 session.save(entity);

 tx.commit();
 session.close();
 ```

 👉 You must:

 * manage session ❌
 * manage transaction ❌
 * write more code ❌

 ---

 # ⚡ 2️⃣ Using JPA (easy way)

 ```java id="4ph1lw"
 entityManager.persist(entity);
 ```

 👉 Less code ✅

 ---

 # ⚡ 3️⃣ Using Spring Data JPA (BEST 🔥)

 ```java id="qbhsvh"
 repository.save(entity);
 ```

 👉 No boilerplate at all ✅

 ---

 # 🧠 So what actually happens?

 ```text
 Your Code (Repository)
 ↓
 Spring Data JPA
 ↓
 JPA (rules)
 ↓
 Hibernate (implementation)
 ↓
 Database
 ```

 👉 Hibernate is still used internally 🔥

 ---

 # 🔍 When to use Hibernate directly?

 👉 Only when:

 * You need very complex queries
 * Performance tuning
 * Low-level control

 👉 Otherwise ❌ avoid

 ---

 # 🧠 Real Developer Rule

 👉 90% cases:

 * Use `JpaRepository` ✅

 👉 10% cases:

 * Use `EntityManager` or Hibernate directly ✅

 ---

 # 💥 One-Line Final

 👉 “You CAN use Hibernate directly, but Spring Data JPA is easier and recommended”

 ---

 # 🚀 Pro Insight

 👉 Even when you use JPA:

 👉 Hibernate is still running behind the scenes 😄

 ---

 If you want next:
 👉 I can show how to use `EntityManager` (mid-level control between JPA & Hibernate) 💯

 */