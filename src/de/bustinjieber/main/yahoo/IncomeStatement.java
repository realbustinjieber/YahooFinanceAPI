package de.bustinjieber.main.yahoo;

import java.util.Date;

/**
 * Stores the balancesheet table neatly.
 * Idea: Storing multiple balance sheet in a TreeMap<Date, Sheet> (ex: 31/12/25 - sheet1, 31/12/24 - sheet2)
 * https://finance.yahoo.com/quote/TICKER/financials/
 */
public class IncomeStatement {

    private Ticker ticker;
    private Date statementDate;
    private double totalRevenue;
    private double costOfRevenue; // Optional
    private double grossProfit; // Optional
    private double operatingExpenses; // Optional
    private double operatingIncome; // Optional
    private double totalExpenses;
    private double preTaxIncome;
    private double taxProvision;
    private double basicEarningsPerShare;
    private double dilutedEarningsPerShare;
    private double normalizedIncome;
    private double EBIT;
    private double reconciledDepreciation;
    private double EBITDA; // Optional
    private double netNonOperatingInterestLoss; // Optional
    private double otherIncome; // Optional
    private double otherIncomeExpenses; // Optional
    private double netIncomeFromContinuingOperations; // Optional
    private double netIncomeCommonStockholders; // Optional
    private double basicAverageShares; // Optional
    private double dilutedAverageShares; // Optional
    private double totalDepreciation; // Optional
    private double reconciledCostOfRevenue; // Optional
    private double totalInterestExpense; // Optional
    private double netInterestIncome; // Optional
    private double totalUnusualItems; // Optional
    private double normalizedEBITDA; // Optional

    public IncomeStatement(Date d){
        this.statementDate = d;
    }

    @Override
    public String toString() {
        return "Ticker: " + getTicker().getTicker() + "\n"
                + "Statement Date: " + getStatementDate() + "\n"
                + "Total Revenue: $ " + getTotalRevenue() + "\n"
                + "Cost Of Revenue: $ " + getCostOfRevenue() + "\n"
                + "Gross Profit: $ " + getGrossProfit() + "\n"
                + "Operating Expenses: $ " + getOperatingExpenses() + "\n"
                + "Operating Income: $ " + getOperatingIncome() + "\n"
                + "Total Expenses: $ " + getTotalExpenses() + "\n"
                + "Pre-Tax Income: $ " + getPreTaxIncome() + "\n"
                + "Tax Provision: $ " + getTaxProvision() + "\n"
                + "Basic EPS: $ " + getBasicEarningsPerShare() + "\n"
                + "Diluted EPS: $ " + getDilutedEarningsPerShare() + "\n"
                + "Normalized Income: $ " + getNormalizedIncome() + "\n"
                + "EBIT: $ " + getEBIT() + "\n"
                + "Reconciled Depreciation: $ " + getReconciledDepreciation() + "\n"
                + "EBITDA: $ " + getEBITDA() + "\n"
                + "Net Non-Operating Interest Loss: $ " + getNetNonOperatingInterestLoss() + "\n"
                + "Other Income: $ " + getOtherIncome() + "\n"
                + "Other Income Expenses: $ " + getOtherIncomeExpenses() + "\n"
                + "Net Income From Continuing Operations: $ " + getNetIncomeFromContinuingOperations() + "\n"
                + "Net Income Common Stockholders: $ " + getNetIncomeCommonStockholders() + "\n"
                + "Basic Average Shares: " + getBasicAverageShares() + "\n"
                + "Diluted Average Shares: " + getDilutedAverageShares() + "\n"
                + "Total Depreciation: $ " + getTotalDepreciation() + "\n"
                + "Reconciled Cost Of Revenue: $ " + getReconciledCostOfRevenue() + "\n"
                + "Total Interest Expense: $ " + getTotalInterestExpense() + "\n"
                + "Net Interest Income: $ " + getNetInterestIncome() + "\n"
                + "Total Unusual Items: $ " + getTotalUnusualItems() + "\n"
                + "Normalized EBITDA: $ " + getNormalizedEBITDA() + "\n";
    }

    public Date getStatementDate() {
        return statementDate;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getCostOfRevenue() {
        return costOfRevenue;
    }

    public void setCostOfRevenue(double costOfRevenue) {
        this.costOfRevenue = costOfRevenue;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public double getOperatingExpenses() {
        return operatingExpenses;
    }

    public void setOperatingExpenses(double operatingExpenses) {
        this.operatingExpenses = operatingExpenses;
    }

    public double getOperatingIncome() {
        return operatingIncome;
    }

    public void setOperatingIncome(double operatingIncome) {
        this.operatingIncome = operatingIncome;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getPreTaxIncome() {
        return preTaxIncome;
    }

    public void setPreTaxIncome(double preTaxIncome) {
        this.preTaxIncome = preTaxIncome;
    }

    public double getTaxProvision() {
        return taxProvision;
    }

    public void setTaxProvision(double taxProvision) {
        this.taxProvision = taxProvision;
    }

    public double getBasicEarningsPerShare() {
        return basicEarningsPerShare;
    }

    public void setBasicEarningsPerShare(double basicEarningsPerShare) {
        this.basicEarningsPerShare = basicEarningsPerShare;
    }

    public double getDilutedEarningsPerShare() {
        return dilutedEarningsPerShare;
    }

    public void setDilutedEarningsPerShare(double dilutedEarningsPerShare) {
        this.dilutedEarningsPerShare = dilutedEarningsPerShare;
    }

    public double getNormalizedIncome() {
        return normalizedIncome;
    }

    public void setNormalizedIncome(double normalizedIncome) {
        this.normalizedIncome = normalizedIncome;
    }

    public double getEBIT() {
        return EBIT;
    }

    public void setEBIT(double EBIT) {
        this.EBIT = EBIT;
    }

    public double getReconciledDepreciation() {
        return reconciledDepreciation;
    }

    public void setReconciledDepreciation(double reconciledDepreciation) {
        this.reconciledDepreciation = reconciledDepreciation;
    }

    public double getEBITDA() {
        return EBITDA;
    }

    public void setEBITDA(double EBITDA) {
        this.EBITDA = EBITDA;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public double getNetNonOperatingInterestLoss() {
        return netNonOperatingInterestLoss;
    }

    public void setNetNonOperatingInterestLoss(double netNonOperatingInterestLoss) {
        this.netNonOperatingInterestLoss = netNonOperatingInterestLoss;
    }

    public double getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(double otherIncome) {
        this.otherIncome = otherIncome;
    }

    public double getOtherIncomeExpenses() {
        return otherIncomeExpenses;
    }

    public void setOtherIncomeExpenses(double otherIncomeExpenses) {
        this.otherIncomeExpenses = otherIncomeExpenses;
    }

    public double getNetIncomeFromContinuingOperations() {
        return netIncomeFromContinuingOperations;
    }

    public void setNetIncomeFromContinuingOperations(double netIncomeFromContinuingOperations) {
        this.netIncomeFromContinuingOperations = netIncomeFromContinuingOperations;
    }

    public double getNetIncomeCommonStockholders() {
        return netIncomeCommonStockholders;
    }

    public void setNetIncomeCommonStockholders(double netIncomeCommonStockholders) {
        this.netIncomeCommonStockholders = netIncomeCommonStockholders;
    }

    public double getBasicAverageShares() {
        return basicAverageShares;
    }

    public void setBasicAverageShares(double basicAverageShares) {
        this.basicAverageShares = basicAverageShares;
    }

    public double getDilutedAverageShares() {
        return dilutedAverageShares;
    }

    public void setDilutedAverageShares(double dilutedAverageShares) {
        this.dilutedAverageShares = dilutedAverageShares;
    }

    public double getTotalDepreciation() {
        return totalDepreciation;
    }

    public void setTotalDepreciation(double totalDepreciation) {
        this.totalDepreciation = totalDepreciation;
    }

    public double getReconciledCostOfRevenue() {
        return reconciledCostOfRevenue;
    }

    public void setReconciledCostOfRevenue(double reconciledCostOfRevenue) {
        this.reconciledCostOfRevenue = reconciledCostOfRevenue;
    }

    public double getTotalInterestExpense() {
        return totalInterestExpense;
    }

    public void setTotalInterestExpense(double totalInterestExpense) {
        this.totalInterestExpense = totalInterestExpense;
    }

    public double getNetInterestIncome() {
        return netInterestIncome;
    }

    public void setNetInterestIncome(double netInterestIncome) {
        this.netInterestIncome = netInterestIncome;
    }

    public double getTotalUnusualItems() {
        return totalUnusualItems;
    }

    public void setTotalUnusualItems(double totalUnusualItems) {
        this.totalUnusualItems = totalUnusualItems;
    }

    public double getNormalizedEBITDA() {
        return normalizedEBITDA;
    }

    public void setNormalizedEBITDA(double normalizedEBITDA) {
        this.normalizedEBITDA = normalizedEBITDA;
    }
}
