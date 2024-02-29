package srinageswari.programmedhousehold.backend.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.backend.model.RecipeEntity;

/**
 * @author smanickavasagam
 */
@Repository
public interface RecipeRepository
    extends JpaRepository<RecipeEntity, Long>, JpaSpecificationExecutor<RecipeEntity> {

  @Query(
      value = "SELECT * FROM recipe r where r.category_id = :categoryId ORDER BY scheduled_dt",
      nativeQuery = true)
  List<RecipeEntity> findRecipesByCategoryId(Long categoryId);

  @Procedure(procedureName = "RESET_ALL_RECIPE_SCHEDULES")
  void resetAllRecipeSchedules(LocalDate startDt);

  @Query(value = "SELECT * FROM recipe WHERE DATE(scheduled_dt) = CURDATE()", nativeQuery = true)
  List<RecipeEntity> queryTodaysRecipes();

  @Procedure(procedureName = "SYNC_RECIPE_CALENDAR")
  void synchronizeRecipeCalendar();

  @Query(
      value =
          "SELECT * FROM recipe r WHERE DATE(r.scheduled_dt) BETWEEN :startDate AND :endDate ORDER BY scheduled_dt",
      nativeQuery = true)
  List<RecipeEntity> findRecipesBetweenDates(LocalDate startDate, LocalDate endDate);

  @Query(
      value = "SELECT * FROM recipe r WHERE r.scheduled_dt IN :scheduleDates ORDER BY scheduled_dt",
      nativeQuery = true)
  List<RecipeEntity> getRecipesOnSelectedDates(List<LocalDate> scheduleDates);
}
