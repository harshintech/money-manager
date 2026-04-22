package in.harshintech.moneymanger.controller;


import in.harshintech.moneymanger.dto.ExpenseDTO;
import in.harshintech.moneymanger.entity.ProfileEntity;
import in.harshintech.moneymanger.service.EmailService;
import in.harshintech.moneymanger.service.ExpenseService;
import in.harshintech.moneymanger.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ProfileService profileService;
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseDTO dto){
        ExpenseDTO saved = expenseService.addExpenses(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExpenseDTO>> getAll() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpenses(){
        List<ExpenseDTO> expenses = expenseService.getCurrentMonthExpensesForCurrentUsre();
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id){
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    //Expense Sheet Download
    @GetMapping("/excel/download")
    public ResponseEntity<byte[]> downloadExpenseExcel() throws IOException {
        System.out.println("API HIT SUCCESS ✅");
        byte[] excelData = expenseService.generateExpenseExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expense.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelData);
    }

    //Email Send with excel sheet
    @PostMapping("/excel/email")
    public ResponseEntity<String> sendExpenseExcelEmail() throws IOException {

        ProfileEntity profile = profileService.getCurrentProfile();
        byte[] excelData = expenseService.generateExpenseExcel();

        String body = "Hi " + profile.getFullName() + ",<br><br>"
                + "Please find attached your expense report.<br><br>"
                + "Regards,<br>Money Manager";

        emailService.sendEmailWithAttachment(
                profile.getEmail(),
                "Your Expense Report",
                body,
                excelData,
                "expense.xlsx"
        );

        return ResponseEntity.ok("Email sent successfully");
    }
}
