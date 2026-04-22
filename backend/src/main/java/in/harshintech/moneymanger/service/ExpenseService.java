package in.harshintech.moneymanger.service;

import in.harshintech.moneymanger.dto.ExpenseDTO;
import in.harshintech.moneymanger.entity.CategoryEntity;
import in.harshintech.moneymanger.entity.ExpenseEntity;
import in.harshintech.moneymanger.entity.ProfileEntity;
import in.harshintech.moneymanger.repository.CategoryRepository;
import in.harshintech.moneymanger.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final ProfileService profileService;


    //Adds a new expense to the database
    public ExpenseDTO addExpenses(ExpenseDTO dto){
         ProfileEntity profile = profileService.getCurrentProfile();
         CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                 .orElseThrow(() -> new RuntimeException("Category not found"));
         ExpenseEntity newExpense = toEntity(dto,profile,category);
         newExpense = expenseRepository.save(newExpense);
         return toDTO(newExpense);
    }

    //get all expenses
    public List<ExpenseDTO> getAllExpenses() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenseEntity> list = expenseRepository.findByProfileId(profile.getId());
        return list.stream().map(this::toDTO).toList();
    }

    //Retrieves all expenses for the current month/based on the startdate and end date
    public List<ExpenseDTO> getCurrentMonthExpensesForCurrentUsre(){
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetween(profile.getId(), startDate,endDate);
        return list.stream().map(this::toDTO).toList();
    }

    //delete expense by id for current user
    public void deleteExpense(Long expenseId){
        ProfileEntity profile = profileService.getCurrentProfile();
        ExpenseEntity entity = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        if(!entity.getProfile().getId().equals(profile.getId())){
            throw new RuntimeException("Unauthorized to delete this expense");
        }
        expenseRepository.delete(entity);
    }


    //Get latest 5 expenses for current user
    public List<ExpenseDTO> getLatest5ExpensesForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenseEntity> list = expenseRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream().map(this::toDTO).toList();
    }

    //Get total expenses for current user
    public BigDecimal getTotalExpenseForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal total = expenseRepository.findTotalExpenseByProfileId(profile.getId());
        return total != null ? total : BigDecimal.ZERO;
    }

    //filter expenses
    public List<ExpenseDTO> filterExpenses(LocalDate startDate, LocalDate endDate, String keyword, Sort sort){
            ProfileEntity profile = profileService.getCurrentProfile();
            List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(),startDate,endDate,keyword,sort);
            return list.stream().map(this::toDTO).toList();
    }

    //Notification
    public List<ExpenseDTO> getExpensesForUserOnDate(Long profileId,LocalDate date){
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDate(profileId,date);
        return list.stream().map(this::toDTO).toList();
    }

    //email
    public byte[] generateExpenseExcel() throws IOException, IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Expense");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Amount");
        header.createCell(2).setCellValue("Date");
        header.createCell(3).setCellValue("Category");

        List<ExpenseEntity> expenses = expenseRepository.findAll();

        int rowNum = 1;
        for (ExpenseEntity expense : expenses) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(expense.getName());

            row.createCell(1).setCellValue(
                    expense.getAmount() != null ? expense.getAmount().doubleValue() : 0
            );

            row.createCell(2).setCellValue(
                    expense.getDate() != null ? expense.getDate().toString() : ""
            );

            row.createCell(3).setCellValue(
                    expense.getCategory() != null
                            ? expense.getCategory().getName()
                            : "N/A"
            );
        }

        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }


    //helper method
    private ExpenseEntity toEntity(ExpenseDTO dto, ProfileEntity profile, CategoryEntity category){
        return ExpenseEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .profile(profile)
                .category(category)
                .build();
    }

    private ExpenseDTO toDTO(ExpenseEntity entity){
        return ExpenseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : "N/A")
                .amount(entity.getAmount())
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }


}
