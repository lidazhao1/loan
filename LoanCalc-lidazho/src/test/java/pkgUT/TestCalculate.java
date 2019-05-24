package pkgUT;

import app.LoanResolver;
import javafx.collections.ObservableList;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TestCalculate {
    @Test
    public void testCalculate(){
    	
        double Amount=5000.00,additionalPayment=0.00;
        double interest_rate=0.3,expectedTotalPayment=5849.21,expectedTotalInterest=849.21;
        int Years=1;
        LocalDate firstPaymentDate= LocalDate.of(2020,1,1);
        double expectedPaymentPerMonth[]= {487.44, 487.44, 487.44, 487.44, 487.44, 487.44, 487.44, 487.44, 487.44, 487.44, 487.44, 487.37};
        double expectedInterestPerMonth[]= {125.00, 115.94, 106.65, 97.13, 87.37, 77.37, 67.12, 56.61, 45.84, 34.80, 23.49, 11.89};
        double expectedPrincipalPerMonth[]= {362.44, 371.50, 380.79, 390.31, 400.07, 410.07, 420.32, 430.83, 441.60, 452.64, 463.95, 475.48};
        double expectedBalancePerMonth[]={4637.56, 4266.06, 3885.27, 3494.96, 3094.89, 2684.82, 2264.50, 1833.67, 1392.07, 939.43, 475.48, 0.00};
        LoanResolver loanResolver = new LoanResolver();
        ObservableList<LoanResolver.PaymentItem> data
                = loanResolver.CalculatePayment(Amount,interest_rate,Years,additionalPayment,firstPaymentDate);

        for(int i=1;i<data.size();++i){
            assertEquals(Double.parseDouble(data.get(i).getPayment()),expectedPaymentPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getInterest()),expectedInterestPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getPrinciple()),expectedPrincipalPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getBalance()),expectedBalancePerMonth[i-1],0.01);
        }
        assertEquals(expectedTotalInterest,loanResolver.getTotalInterests(),0.01);
        assertEquals(expectedTotalPayment,loanResolver.getTotalPayments(),0.01);
    }
}
