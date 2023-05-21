package com.booleanuk.core;

import com.booleanuk.core.model.*;
import com.booleanuk.core.model.enumerations.ACCOUNT_TYPE;
import com.booleanuk.core.model.enumerations.OVERDRAFT_STATE;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

public class BankController {
    private final Bank bank;

    BankController(String bankName, String bankCode, Locale locale) {
        this.bank = createBank(bankName,bankCode, locale);
        if (this.bank == null) System.out.println("Something went wrong. Bank wasn't created.");
    }

    private Bank createBank(String bankName, String bankCode, Locale locale) {
        try {
            if(this.bank == null) {
                Bank bank = new Bank(bankName, bankCode, locale);
                System.out.println("Bank was created.");
                return bank;
            } else {
                System.out.println("This controller already controls a bank.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Bank wasn't created.");
        }
        return null;
    }

    Bank getBank() {
        return this.bank;
    }

    BankManager createBankManager() {
        try {
            this.bank.createBankManager();
            return bank.geBankManager();
        } catch (Exception e) {
            System.out.println("Something went wrong. Branch wasn't created.");
        }
        return null;
    }

    Branch createBranch(String branchName, String bankCode) {
        try {
            this.bank.createBranch(branchName, bankCode);
            System.out.println("Branch was created.");
            return this.bank.getBranches().get(this.bank.getBranches().size() - 1);
        } catch (Exception e) {
            System.out.println("Something went wrong. Branch wasn't created.");
            //e.printStackTrace();
        }
        return null;
    }

    Customer createCustomer(Branch branch, String tin, String idCard, String givenNames, String surname, LocalDate dateOfBirth) {
        try {
            branch.createCustomer(tin, idCard, givenNames, surname, dateOfBirth);
            System.out.println("Customer was created.");
            return branch.getCustomers().get(branch.getCustomers().size() - 1);
        } catch (Exception e) {
            System.out.println("Something went wrong. Customer wasn't created.");
            //e.printStackTrace();
        }
        return null;
    }

    CurrentAccount createCurrentAccount(Customer customer) {
        try {
            customer.createAccount(ACCOUNT_TYPE.CURRENT);
            Account account = customer.getAccounts().get(customer.getAccounts().size() - 1);
            if (account instanceof CurrentAccount) {
                System.out.println("Current Account was created.");
                return (CurrentAccount) account;
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Current account wasn't created.");
            //e.printStackTrace();
        }
        return null;
    }

    CurrentAccount createCurrentAccount(Customer customer, BigDecimal amount) {
        try {
            customer.createAccount(ACCOUNT_TYPE.CURRENT, amount);
            Account account = customer.getAccounts().get(customer.getAccounts().size() - 1);
            if (account instanceof CurrentAccount) {
                System.out.println("Current Account was created.");
                return (CurrentAccount) account;
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Current account wasn't created.");
            //e.printStackTrace();
        }
        return null;
    }

    SavingsAccount createSavingsAccount(Customer customer) {
        try {
            customer.createAccount(ACCOUNT_TYPE.SAVINGS);
            Account account = customer.getAccounts().get(customer.getAccounts().size() - 1);
            if (account instanceof SavingsAccount) {
                System.out.println("Savings Account was created.");
                return (SavingsAccount) account;
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Savings account wasn't created.");
            //e.printStackTrace();
        }
        return null;
    }

    SavingsAccount createSavingsAccount(Customer customer, BigDecimal amount) {
        try {
            customer.createAccount(ACCOUNT_TYPE.SAVINGS, amount);
            Account account = customer.getAccounts().get(customer.getAccounts().size() - 1);
            if (account instanceof SavingsAccount) {
                System.out.println("Savings Account was created.");
                return (SavingsAccount) account;
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Savings account wasn't created.");
            //e.printStackTrace();
        }
        return null;
    }


    void deposit(Account account,BigDecimal amount) {
        try {
            account.deposit(amount);
        } catch (Exception e) {
            System.out.println("Something went wrong. Deposit wasn't completed.");
        }
    }

    void withdraw(Account account,BigDecimal amount) {
        try {
            account.withdraw(amount);
        } catch (Exception e) {
            System.out.println("Something went wrong. Deposit wasn't completed.");
        }
    }

    OverdraftRequest createOverdraftRequest(CurrentAccount account, BigDecimal amount) {
        if (account instanceof CurrentAccount) {
            CurrentAccount currentAccount = (CurrentAccount) account;
            try {
                currentAccount.requestOverdraft(amount);
                System.out.println("Overdraft request was created.");
                return currentAccount.getOverdraftRequests().get(currentAccount.getOverdraftRequests().size() - 1);
            } catch (Exception e) {
                System.out.println("Something went wrong. Overdraft request wasn't created.");
                //e.printStackTrace();
            }
        } else {
            System.out.println(account.getClass().getName());
            System.out.println("You can't request an overdraft on a savings account.");
        }
        return null;
    }

    void approveOverdraftRequest(OverdraftRequest request) {
        try {
            this.bank.geBankManager().processOverdraftRequest(request, OVERDRAFT_STATE.APPROVED);
            System.out.println("Overdraft request was approved.");
        } catch (Exception e) {
            System.out.println("Something went wrong. Overdraft request wasn't approved.");
        }
    }

    void denyOverdraftRequest(OverdraftRequest request) {
        try {
            this.bank.geBankManager().processOverdraftRequest(request, OVERDRAFT_STATE.DENIED);
            System.out.println("Overdraft request was denied.");
        } catch (Exception e) {
            System.out.println("Something went wrong. Overdraft request wasn't approved.");
        }
    }

    void overdraft(Account account, OverdraftRequest request) {
        if (account instanceof CurrentAccount) {
            CurrentAccount currentAccount = (CurrentAccount) account;
            try {
                currentAccount.overdraft(request);
            } catch (Exception e) {
                System.out.println("Something went wrong. Overdraft wasn't completed.");
                //e.printStackTrace();
            }
        } else {
            System.out.println("You can't overdraft from a savings account.");
        }
    }

    void printBankStatement(Account account) {
        if(account != null) {
            try {
                account.printBankStatement();
            } catch (Exception e) {
                System.out.println("Something went wrong. Bank statement wasn't printed.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Account must be non-null.");
        }
    }
}
