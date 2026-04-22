package in.harshintech.moneymanger.repository;

import in.harshintech.moneymanger.entity.ExpenseEntity;
import in.harshintech.moneymanger.entity.IncomeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Long> {

    //select * from tbl_expenses where profile_id = ?1 order by date desc
    List<ExpenseEntity> findByProfileIdOrderByDateDesc(Long profileId);

    //select * from tbl_expenses where profile_id = ?1 order by date desc limit 5
    List<ExpenseEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);

    //all income by id
    List<ExpenseEntity> findByProfileId(Long profileId);

    @Query("SELECT SUM(e.amount) FROM ExpenseEntity e WHERE e.profile.id = :profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);
    /**
     Give me the total (sum) of all expenses for this user
     :profileId   ←→   @Param("profileId")
     @Param means: “Take this Java variable and put it into the query”
     @Param passes value from Java method into :variable inside query”
     */

   /**
    SELECT *
    FROM tbl_expenses
    WHERE profile_id = ?1
    AND date BETWEEN ?2 AND ?3
    AND LOWER(name) LIKE LOWER(CONCAT('%', ?4, '%')) --> LOWER(name) LIKE LOWER('%food%')
    ORDER BY amount DESC;
    */
    List<ExpenseEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            Long profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );
    /**
     expenseRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
        1L,
        LocalDate.of(2026,1,1),
        LocalDate.of(2026,12,31),
        "food",
        Sort.by("amount").descending()
     );

     Break it into simple parts
     findByProfileId --> Filter by user
     profile_id = 1

     AndDateBetween --> Filter by date range
     date BETWEEN startDate AND endDate

     AndNameContainingIgnoreCase --> Search keyword (case insensitive)
     name LIKE %keyword%  (ignore case)
     LOWER(name) LIKE LOWER('%food%')
     "food"
     "fast food"
     "food item"
     "junkfood"

     Sort sort -> Sorting (dynamic)
     Sort.by("amount").descending()

     */

    //select * from tbl_expenses where profile_id = ?1 and date between ?2 and ?3
    List<ExpenseEntity> findByProfileIdAndDateBetween(Long profileId,LocalDate startDate,LocalDate endDate);

    //select * from tbl_expenses where profile_id = ?1 and date ?2
    List<ExpenseEntity> findByProfileIdAndDate(Long profileId,LocalDate date);
}
