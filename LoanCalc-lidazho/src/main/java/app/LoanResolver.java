package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import org.apache.poi.ss.formula.functions.FinanceLib;
import java.time.LocalDate;

public class LoanResolver {
    private final ObservableList<PaymentItem> D = FXCollections.observableArrayList();
    private double Total, annuay_Interests;

    public double getTotalPayments() {
        Total=0;
        for(int i=1;i<D.size();++i){
            Total= Total + Double.parseDouble(D.get(i).getPayment());
        }
        Total=Rounding2(Total);
        return Total;
    }

    public double getTotalInterests() {
        annuay_Interests=0;
        for(int i=1;i<D.size();++i){
            annuay_Interests = annuay_Interests + Double.parseDouble(D.get(i).getInterest());
        }
        annuay_Interests=Rounding2(annuay_Interests);
        return annuay_Interests;
    }

    public ObservableList<PaymentItem> CalculatePayment(double totalloan, double interest_a, double data_Years, double extraPayment, LocalDate time){
        double remaining=Rounding2(totalloan-extraPayment);
        D.add(new PaymentItem(null,null,null,null,null,null,String.format("%.2f",remaining)));
        double PMT = Rounding2(Math.abs(FinanceLib.pmt(interest_a/12.00, data_Years*12.00, totalloan, 0, false)));

        int paymentNumber=0;
        while(remaining>0){
            double interest=Rounding2(remaining*interest_a/12.00);
            double principal;
            double payment;
            if(Rounding2(PMT-(remaining+interest))>=-0.01){
                principal=remaining;
                payment=Rounding2(remaining+interest);
                remaining=0;
            }else{
                principal=Rounding2(PMT-interest);
                payment=PMT;
                remaining=Rounding2(remaining-principal);
            }
            paymentNumber++;
            D.add(
                    new PaymentItem(Integer.toString(paymentNumber),time.toString(),String.format("%.2f",payment),null,String.format("%.2f",interest),String.format("%.2f",principal),String.format("%.2f",remaining)));
            time=time.plusMonths(1);
        }
        return D;
    }

    public static double Rounding2(double num) {
        return ((double)Math.round(num*100))/100;
    }

    public static class PaymentItem {
        private final SimpleStringProperty payment1;
        private final SimpleStringProperty date1;
        private final SimpleStringProperty payment2;
        private final SimpleStringProperty extra_Payment;
        private final SimpleStringProperty interest_1;
        private final SimpleStringProperty principle;
        private final SimpleStringProperty balance;

        public PaymentItem(String paymentNumber, String date, String payment, String additionalPayment, String interest, String principle, String balance) {
            this.payment1 = new SimpleStringProperty(paymentNumber);
            this.date1 = new SimpleStringProperty(date);
            this.payment2 = new SimpleStringProperty(payment);
            this.extra_Payment = new SimpleStringProperty(additionalPayment);
            this.interest_1 = new SimpleStringProperty(interest);
            this.principle = new SimpleStringProperty(principle);
            this.balance = new SimpleStringProperty(balance);
        }
        public String getBalance() {
            return this.balance.get();
        }

        public void setBalance(String balance) {
            this.balance.set(balance);
        }

        public String getPaymentNumber() {
            return this.payment1.get();
        }

        public void setPaymentNumber(String num) {
            this.payment1.set(num);
        }

        public String getDate() {
            return this.date1.get();
        }

        public void setDate(String date) {
            this.date1.set(date);
        }

        

        public String getAdditionalPayment() {
            return this.extra_Payment.get();
        }

        public void setAdditionalPayment(String additionalPayment) {
            this.extra_Payment.set(additionalPayment);
        }

        public String getInterest() {
            return this.interest_1.get();
        }

        public void setInterest(String interest_actual) {
            this.interest_1.set(interest_actual);
        }

        public String getPrinciple() {
            return this.principle.get();
        }

        public void setPrinciple(String principle) {
            this.principle.set(principle);
        }

       
        public String getPayment() {
            return this.payment2.get();
        }

        public void setPayment(String set_value) {
            this.payment2.set(set_value);
        }
    }
}
