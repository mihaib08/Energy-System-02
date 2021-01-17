package entity;

import common.Constants;
import entities.Distributors;
import input.InConsumer;

public final class Consumer extends Entity {
    /** The current budget of the consumer */
    private long budget;

    private long income;

    /** The remaining length of the current contract */
    private int contractLength;

    /** the payment to which the consumer has a debt */
    private long debtPayment;

    /**
     * the id of the distributor
     * for whom the consumer has a debt
     */
    private int debtDistributorId;

    /**
     * How much the consumer
     * has to pay per month
     */
    private long payment;

    /**
     * Current state of an user
     *     --> 0 - has no debts
     *     --> 1 - has a debt
     *     --> 2 - bankrupt
     */
    private int bankState;

    /**
     * id of the current distributor
     */
    private int currDistributorId;


    public Consumer(final InConsumer inConsumer) {
        super(inConsumer.getId());
        budget = inConsumer.getInitialBudget();
        income = inConsumer.getMonthlyIncome();

        contractLength = 0;
        bankState = 0;
        currDistributorId = -1;
        payment = 0;
        debtPayment = 0;
    }

    /**
     * Pay the current installment
     */
    public void payInstallment(final Distributors distributors) {
        budget += income;
        long res = budget - payment;

        if (bankState == 1) {
            long debt = Math.round(Math.floor((Constants.PRODUCTION_PERCENT + 1) * debtPayment));
            res -= debt;

            if (res >= 0) {
                /* managed to pay the debt */
                distributors.addRevenue(debtDistributorId, debt);
                /* erase the debt */
                bankState = 0;
            } else {
                long aux = res + payment;
                /*
                 * check if the debt is to another distributor
                 *     --> can delay the new payment
                 */
                if (aux >= 0 && currDistributorId != debtDistributorId) {
                    distributors.addRevenue(debtDistributorId, debt);

                    res = aux;
                    /* transfer the debt to the current distributor */
                    debtPayment = payment;
                    debtDistributorId = currDistributorId;
                }
            }
        }

        if (res < 0) {
            bankState++;

            if (bankState == 1) {
                debtPayment = payment;
                debtDistributorId = currDistributorId;
            }
        } else {
            budget = res;
        }
        contractLength--;
    }

    @Override
    public String toString() {
        return "Consumer{"
                + "ID = " + super.getId()
                + ", budget=" + budget
                + ", income=" + income
                + ", contractLength=" + contractLength
                + ", debtPayment=" + debtPayment
                + ", debtDistributorId=" + debtDistributorId
                + ", payment=" + payment
                + ", bankState=" + bankState
                + ", currDistributorId=" + currDistributorId
                + '}';
    }

    /** Getters + Setters */

    public long getBudget() {
        return budget;
    }

    public void setBudget(final long budget) {
        this.budget = budget;
    }

    public long getIncome() {
        return income;
    }

    public void setIncome(final long income) {
        this.income = income;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getBankState() {
        return bankState;
    }

    public void setBankState(final int bankState) {
        this.bankState = bankState;
    }

    public int getCurrDistributorId() {
        return currDistributorId;
    }

    public void setCurrDistributorId(final int currDistributorId) {
        this.currDistributorId = currDistributorId;
    }

    public long getPayment() {
        return payment;
    }

    public void setPayment(final long payment) {
        this.payment = payment;
    }
}
