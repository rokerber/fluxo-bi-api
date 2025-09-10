package eu.europa.ec.estat.e4.fluxobiapi.service;// Imports necessários
import eu.europa.ec.estat.e4.fluxobiapi.domain.Expense;
import eu.europa.ec.estat.e4.fluxobiapi.dto.ExpenseRequestDTO;
import eu.europa.ec.estat.e4.fluxobiapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Transactional
    public List<Expense> createExpense(ExpenseRequestDTO dto) {
        List<Expense> createdExpenses = new ArrayList<>();

        int numberOfInstallments = (dto.installments() != null && dto.installments() > 1) ? dto.installments() : 1;

        for (int i = 0; i < numberOfInstallments; i++) {
            Expense newExpense = new Expense();

            String description = dto.description();
            if (numberOfInstallments > 1) {
                description += " (" + (i + 1) + "/" + numberOfInstallments + ")";
            }

            newExpense.setDescription(description);
            newExpense.setAmount(dto.amount());
            newExpense.setDueDate(dto.dueDate().plusMonths(i));
            newExpense.setPaid(false);

            createdExpenses.add(expenseRepository.save(newExpense));
        }

        return createdExpenses;
    }
}