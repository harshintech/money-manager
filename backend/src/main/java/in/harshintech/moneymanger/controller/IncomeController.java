package in.harshintech.moneymanger.controller;

import in.harshintech.moneymanger.dto.ExpenseDTO;
import in.harshintech.moneymanger.dto.IncomeDTO;
import in.harshintech.moneymanger.entity.ProfileEntity;
import in.harshintech.moneymanger.service.EmailService;
import in.harshintech.moneymanger.service.IncomeService;
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
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;
    private final EmailService emailService;
    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<IncomeDTO> addIncome(@RequestBody IncomeDTO dto){
        IncomeDTO saved = incomeService.addIncomes(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<IncomeDTO>> getAll() {
        return ResponseEntity.ok(incomeService.getAllIncomes());
    }

    @GetMapping
    public ResponseEntity<List<IncomeDTO>> getIncomes(){
        List<IncomeDTO> incomes = incomeService.getCurrentMonthIncomesForCurrentUser();
        return ResponseEntity.ok(incomes);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id){
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }


    //Excel Sheet Download
    @GetMapping("/excel/download")
    public ResponseEntity<byte[]> downloadIncomeExcel() throws IOException {
        System.out.println("API HIT SUCCESS ✅");
        byte[] excelData = incomeService.generateIncomeExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=income.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelData);
    }

    //Email Send with excel sheet
    @PostMapping("/excel/email")
    public ResponseEntity<String> sendIncomeExcelEmail() throws IOException {

        ProfileEntity profile = profileService.getCurrentProfile();
        byte[] excelData = incomeService.generateIncomeExcel();

        String body = "Hi " + profile.getFullName() + ",<br><br>"
                + "Please find attached your income report.<br><br>"
                + "Regards,<br>Money Manager";

        emailService.sendEmailWithAttachment(
                profile.getEmail(),
                "Your Income Report",
                body,
                excelData,
                "income.xlsx"
        );

        return ResponseEntity.ok("Email sent successfully");
    }


}
