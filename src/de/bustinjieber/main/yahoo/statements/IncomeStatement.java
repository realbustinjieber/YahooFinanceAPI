package de.bustinjieber.main.yahoo.statements;

import de.bustinjieber.main.scraper.Parser;
import de.bustinjieber.main.yahoo.Ticker;
import de.bustinjieber.main.yahoo.utils.Period;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class IncomeStatement {

    private double totalOperatingIncomeAsReported;
    private double taxEffectOfUnusualItems;
    private double netIncomeContinuousOperations;
    private double operatingRevenue;
    private double basicAverageShares;
    private double depreciationAndAmortizationInIncomeStatement;
    private double reconciledDepreciation;
    private double operatingExpense;
    private double netIncomeFromContinuingOperationNetMinorityInterest;
    private double costOfRevenue;
    private double researchAndDevelopment;
    private double interestIncome;
    private double grossProfit;
    private double depreciationAmortizationDepletionIncomeStatement;
    private double restructuringAndMergernAcquisition;
    private double normalizedEBITDA;
    private double dilutedNIAvailtoComStockholders;
    private double netIncomeIncludingNoncontrollingInterests;
    private double amortization;
    private double otherNonOperatingIncomeExpenses;
    private double otherOperatingExpenses;
    private double EBITDA;
    private double basicEPS;
    private double otherSpecialCharges;
    private double totalExpenses;
    private double netNonOperatingInterestIncomeExpense;
    private double taxRateForCalcs;
    private double reconciledCostOfRevenue;
    private double EBIT;
    private double netIncomeCommonStockholders;
    private double dilutedAverageShares;
    private double totalUnusualItems;
    private double normalizedIncome;
    private double specialIncomeCharges;
    private double interestIncomeNonOperating;
    private double netIncome;
    private double amortizationOfIntangiblesIncomeStatement;
    private double interestExpense;
    private double interestExpenseNonOperating;
    private double totalRevenue;
    private double totalUnusualItemsExcludingGoodwill;
    private double operatingIncome;
    private double dilutedEPS;
    private double netIncomeExtraordinary;
    private double otherIncomeExpense;
    private double earningsFromEquityInterest;
    private double taxLossCarryforwardDilutedEPS;
    private double dilutedAccountingChange;
    private double normalizedBasicEPS;
    private double gainOnSaleOfBusiness;
    private double securitiesAmortization;
    private double reportedNormalizedBasicEPS;
    private double otherunderPreferredStockDividend;
    private double generalAndAdministrativeExpense;
    private double basicContinuousOperations;
    private double sellingAndMarketingExpense;
    private double minorityInterests;
    private double otherGandA;
    private double continuingAndDiscontinuedBasicEPS;
    private double otherTaxes;
    private double dilutedContinuousOperations;
    private double taxLossCarryforwardBasicEPS;
    private double provisionForDoubtfulAccounts;
    private double dilutedEPSOtherGainsLosses;
    private double totalOtherFinanceCost;
    private double normalizedDilutedEPS;
    private double dividendPerShare;
    private double dilutedExtraordinary;
    private double reportedNormalizedDilutedEPS;
    private double rentExpenseSupplemental;
    private double gainOnSaleOfPPE;
    private double gainOnSaleOfSecurity;
    private double writeOff;
    private double insuranceAndClaims;
    private double basicDiscontinuousOperations;
    private double preferredStockDividends;
    private double impairmentOfCapitalAssets;
    private double basicExtraordinary;
    private double earningsFromEquityInterestNetOfTax;
    private double exciseTaxes;
    private double rentAndLandingFees;
    private double salariesAndWages;
    private double basicEPSOtherGainsLosses;
    private double depreciationIncomeStatement;
    private double averageDilutionEarnings;
    private double netIncomeDiscontinuousOperations;
    private double basicAccountingChange;
    private double dilutedDiscontinuousOperations;
    private double depletionIncomeStatement;
    private double continuingAndDiscontinuedDilutedEPS;
    private double netIncomeFromTaxLossCarryforward;
    private double netIncomeFromContinuingAndDiscontinuedOperation;
    private double netInterestIncome;
    private double pretaxIncome;
    private double taxProvision;
    private double sellingGeneralAndAdministration;

    private Ticker ticker;
    private LocalDate statementDate;
    private final Parser parser = new Parser();
    private Period period;

    public IncomeStatement(Ticker t, LocalDate d, Period p){
        this.ticker = t;
        this.statementDate = d;
        this.period = p;
    }

    public IncomeStatement(Ticker t, Period p){
        this.ticker = t;
        this.period = p;
    }

    public String getUrl() {
        String base = "https://query1.finance.yahoo.com/ws/fundamentals-timeseries/v1/finance/timeseries/" + ticker.getTicker();

        // Die Liste der Kategorien exakt aus deiner Beispiel-URL
        String type = "annualTaxEffectOfUnusualItems,trailingTaxEffectOfUnusualItems," +
                "annualTaxRateForCalcs,trailingTaxRateForCalcs," +
                "annualNormalizedEBITDA,trailingNormalizedEBITDA," +
                "annualNormalizedDilutedEPS,trailingNormalizedDilutedEPS," +
                "annualNormalizedBasicEPS,trailingNormalizedBasicEPS," +
                "annualTotalUnusualItems,trailingTotalUnusualItems," +
                "annualTotalUnusualItemsExcludingGoodwill,trailingTotalUnusualItemsExcludingGoodwill," +
                "annualNetIncomeFromContinuingOperationNetMinorityInterest,trailingNetIncomeFromContinuingOperationNetMinorityInterest," +
                "annualReconciledDepreciation,trailingReconciledDepreciation," +
                "annualReconciledCostOfRevenue,trailingReconciledCostOfRevenue," +
                "annualEBITDA,trailingEBITDA," +
                "annualEBIT,trailingEBIT," +
                "annualNetInterestIncome,trailingNetInterestIncome," +
                "annualInterestExpense,trailingInterestExpense," +
                "annualInterestIncome,trailingInterestIncome," +
                "annualContinuingAndDiscontinuedDilutedEPS,trailingContinuingAndDiscontinuedDilutedEPS," +
                "annualContinuingAndDiscontinuedBasicEPS,trailingContinuingAndDiscontinuedBasicEPS," +
                "annualNormalizedIncome,trailingNormalizedIncome," +
                "annualNetIncomeFromContinuingAndDiscontinuedOperation,trailingNetIncomeFromContinuingAndDiscontinuedOperation," +
                "annualTotalExpenses,trailingTotalExpenses," +
                "annualRentExpenseSupplemental,trailingRentExpenseSupplemental," +
                "annualReportedNormalizedDilutedEPS,trailingReportedNormalizedDilutedEPS," +
                "annualReportedNormalizedBasicEPS,trailingReportedNormalizedBasicEPS," +
                "annualTotalOperatingIncomeAsReported,trailingTotalOperatingIncomeAsReported," +
                "annualDividendPerShare,trailingDividendPerShare," +
                "annualDilutedAverageShares,trailingDilutedAverageShares," +
                "annualBasicAverageShares,trailingBasicAverageShares," +
                "annualDilutedEPS,trailingDilutedEPS," +
                "annualDilutedEPSOtherGainsLosses,trailingDilutedEPSOtherGainsLosses," +
                "annualTaxLossCarryforwardDilutedEPS,trailingTaxLossCarryforwardDilutedEPS," +
                "annualDilutedAccountingChange,trailingDilutedAccountingChange," +
                "annualDilutedExtraordinary,trailingDilutedExtraordinary," +
                "annualDilutedDiscontinuousOperations,trailingDilutedDiscontinuousOperations," +
                "annualDilutedContinuousOperations,trailingDilutedContinuousOperations," +
                "annualBasicEPS,trailingBasicEPS," +
                "annualBasicEPSOtherGainsLosses,trailingBasicEPSOtherGainsLosses," +
                "annualTaxLossCarryforwardBasicEPS,trailingTaxLossCarryforwardBasicEPS," +
                "annualBasicAccountingChange,trailingBasicAccountingChange," +
                "annualBasicExtraordinary,trailingBasicExtraordinary," +
                "annualBasicDiscontinuousOperations,trailingBasicDiscontinuousOperations," +
                "annualBasicContinuousOperations,trailingBasicContinuousOperations," +
                "annualDilutedNIAvailtoComStockholders,trailingDilutedNIAvailtoComStockholders," +
                "annualAverageDilutionEarnings,trailingAverageDilutionEarnings," +
                "annualNetIncomeCommonStockholders,trailingNetIncomeCommonStockholders," +
                "annualOtherunderPreferredStockDividend,trailingOtherunderPreferredStockDividend," +
                "annualPreferredStockDividends,trailingPreferredStockDividends," +
                "annualNetIncome,trailingNetIncome," +
                "annualMinorityInterests,trailingMinorityInterests," +
                "annualNetIncomeIncludingNoncontrollingInterests,trailingNetIncomeIncludingNoncontrollingInterests," +
                "annualNetIncomeFromTaxLossCarryforward,trailingNetIncomeFromTaxLossCarryforward," +
                "annualNetIncomeExtraordinary,trailingNetIncomeExtraordinary," +
                "annualNetIncomeDiscontinuousOperations,trailingNetIncomeDiscontinuousOperations," +
                "annualNetIncomeContinuousOperations,trailingNetIncomeContinuousOperations," +
                "annualEarningsFromEquityInterestNetOfTax,trailingEarningsFromEquityInterestNetOfTax," +
                "annualTaxProvision,trailingTaxProvision," +
                "annualPretaxIncome,trailingPretaxIncome," +
                "annualOtherIncomeExpense,trailingOtherIncomeExpense," +
                "annualOtherNonOperatingIncomeExpenses,trailingOtherNonOperatingIncomeExpenses," +
                "annualSpecialIncomeCharges,trailingSpecialIncomeCharges," +
                "annualGainOnSaleOfPPE,trailingGainOnSaleOfPPE," +
                "annualGainOnSaleOfBusiness,trailingGainOnSaleOfBusiness," +
                "annualOtherSpecialCharges,trailingOtherSpecialCharges," +
                "annualWriteOff,trailingWriteOff," +
                "annualImpairmentOfCapitalAssets,trailingImpairmentOfCapitalAssets," +
                "annualRestructuringAndMergernAcquisition,trailingRestructuringAndMergernAcquisition," +
                "annualSecuritiesAmortization,trailingSecuritiesAmortization," +
                "annualEarningsFromEquityInterest,trailingEarningsFromEquityInterest," +
                "annualGainOnSaleOfSecurity,trailingGainOnSaleOfSecurity," +
                "annualNetNonOperatingInterestIncomeExpense,trailingNetNonOperatingInterestIncomeExpense," +
                "annualTotalOtherFinanceCost,trailingTotalOtherFinanceCost," +
                "annualInterestExpenseNonOperating,trailingInterestExpenseNonOperating," +
                "annualInterestIncomeNonOperating,trailingInterestIncomeNonOperating," +
                "annualOperatingIncome,trailingOperatingIncome," +
                "annualOperatingExpense,trailingOperatingExpense," +
                "annualOtherOperatingExpenses,trailingOtherOperatingExpenses," +
                "annualOtherTaxes,trailingOtherTaxes," +
                "annualProvisionForDoubtfulAccounts,trailingProvisionForDoubtfulAccounts," +
                "annualDepreciationAmortizationDepletionIncomeStatement,trailingDepreciationAmortizationDepletionIncomeStatement," +
                "annualDepletionIncomeStatement,trailingDepletionIncomeStatement," +
                "annualDepreciationAndAmortizationInIncomeStatement,trailingDepreciationAndAmortizationInIncomeStatement," +
                "annualAmortization,trailingAmortization," +
                "annualAmortizationOfIntangiblesIncomeStatement,trailingAmortizationOfIntangiblesIncomeStatement," +
                "annualDepreciationIncomeStatement,trailingDepreciationIncomeStatement," +
                "annualResearchAndDevelopment,trailingResearchAndDevelopment," +
                "annualSellingGeneralAndAdministration,trailingSellingGeneralAndAdministration," +
                "annualSellingAndMarketingExpense,trailingSellingAndMarketingExpense," +
                "annualGeneralAndAdministrativeExpense,trailingGeneralAndAdministrativeExpense," +
                "annualOtherGandA,trailingOtherGandA," +
                "annualInsuranceAndClaims,trailingInsuranceAndClaims," +
                "annualRentAndLandingFees,trailingRentAndLandingFees," +
                "annualSalariesAndWages,trailingSalariesAndWages," +
                "annualGrossProfit,trailingGrossProfit," +
                "annualCostOfRevenue,trailingCostOfRevenue," +
                "annualTotalRevenue,trailingTotalRevenue," +
                "annualExciseTaxes,trailingExciseTaxes," +
                "annualOperatingRevenue,trailingOperatingRevenue";

        // Perioden-Logik (ersetzt "annual" durch z.B. "quarterly" falls nötig)
        if(period.getPeriod().equals("quarterly")) {
            type = type.replace("annual", period.getPeriod());
        }

        // Zusammenbau der Query-Parameter inkl. lang und region
        String params = String.format("merge=%s&padTimeSeries=%s&period1=%s&period2=%s&type=%s&lang=%s&region=%s",
                URLEncoder.encode("false", StandardCharsets.UTF_8),
                URLEncoder.encode("true", StandardCharsets.UTF_8),
                URLEncoder.encode("493590046", StandardCharsets.UTF_8),
                URLEncoder.encode(String.valueOf(parser.endOfNextYearUnix()), StandardCharsets.UTF_8),
                URLEncoder.encode(type, StandardCharsets.UTF_8),
                URLEncoder.encode("en-US", StandardCharsets.UTF_8),
                URLEncoder.encode("US", StandardCharsets.UTF_8)
        );

        return base + "?" + params;
    }

    @Override
    public String toString() {
        return "Ticker: " + getTicker().getTicker() + "\n"
                + "Statement Date: " + getStatementDate() + "\n"
                + "Total Operating Income As Reported: $ " + getTotalOperatingIncomeAsReported() + "\n"
                + "Tax Effect Of Unusual Items: $ " + getTaxEffectOfUnusualItems() + "\n"
                + "Net Income Continuous Operations: $ " + getNetIncomeContinuousOperations() + "\n"
                + "Operating Revenue: $ " + getOperatingRevenue() + "\n"
                + "Basic Average Shares: " + getBasicAverageShares() + "\n"
                + "Depreciation And Amortization In Income Statement: $ " + getDepreciationAndAmortizationInIncomeStatement() + "\n"
                + "Reconciled Depreciation: $ " + getReconciledDepreciation() + "\n"
                + "Operating Expense: $ " + getOperatingExpense() + "\n"
                + "Net Income From Continuing Operation Net Minority Interest: $ " + getNetIncomeFromContinuingOperationNetMinorityInterest() + "\n"
                + "Cost Of Revenue: $ " + getCostOfRevenue() + "\n"
                + "Research And Development: $ " + getResearchAndDevelopment() + "\n"
                + "Interest Income: $ " + getInterestIncome() + "\n"
                + "Gross Profit: $ " + getGrossProfit() + "\n"
                + "Depreciation Amortization Depletion Income Statement: $ " + getDepreciationAmortizationDepletionIncomeStatement() + "\n"
                + "Restructuring And Merger And Acquisition: $ " + getRestructuringAndMergernAcquisition() + "\n"
                + "Normalized EBITDA: $ " + getNormalizedEBITDA() + "\n"
                + "Diluted NI Available To Common Stockholders: $ " + getDilutedNIAvailtoComStockholders() + "\n"
                + "Net Income Including Noncontrolling Interests: $ " + getNetIncomeIncludingNoncontrollingInterests() + "\n"
                + "Amortization: $ " + getAmortization() + "\n"
                + "Other Non Operating Income Expenses: $ " + getOtherNonOperatingIncomeExpenses() + "\n"
                + "Other Operating Expenses: $ " + getOtherOperatingExpenses() + "\n"
                + "EBITDA: $ " + getEBITDA() + "\n"
                + "Basic EPS: $ " + getBasicEPS() + "\n"
                + "Other Special Charges: $ " + getOtherSpecialCharges() + "\n"
                + "Total Expenses: $ " + getTotalExpenses() + "\n"
                + "Net Non Operating Interest Income Expense: $ " + getNetNonOperatingInterestIncomeExpense() + "\n"
                + "Tax Rate For Calcs: " + getTaxRateForCalcs() + "\n"
                + "Reconciled Cost Of Revenue: $ " + getReconciledCostOfRevenue() + "\n"
                + "EBIT: $ " + getEBIT() + "\n"
                + "Net Income Common Stockholders: $ " + getNetIncomeCommonStockholders() + "\n"
                + "Diluted Average Shares: " + getDilutedAverageShares() + "\n"
                + "Total Unusual Items: $ " + getTotalUnusualItems() + "\n"
                + "Normalized Income: $ " + getNormalizedIncome() + "\n"
                + "Special Income Charges: $ " + getSpecialIncomeCharges() + "\n"
                + "Interest Income Non Operating: $ " + getInterestIncomeNonOperating() + "\n"
                + "Net Income: $ " + getNetIncome() + "\n"
                + "Amortization Of Intangibles Income Statement: $ " + getAmortizationOfIntangiblesIncomeStatement() + "\n"
                + "Interest Expense: $ " + getInterestExpense() + "\n"
                + "Interest Expense Non Operating: $ " + getInterestExpenseNonOperating() + "\n"
                + "Total Revenue: $ " + getTotalRevenue() + "\n"
                + "Total Unusual Items Excluding Goodwill: $ " + getTotalUnusualItemsExcludingGoodwill() + "\n"
                + "Operating Income: $ " + getOperatingIncome() + "\n"
                + "Diluted EPS: $ " + getDilutedEPS() + "\n"
                + "Net Income Extraordinary: $ " + getNetIncomeExtraordinary() + "\n"
                + "Other Income Expense: $ " + getOtherIncomeExpense() + "\n"
                + "Earnings From Equity Interest: $ " + getEarningsFromEquityInterest() + "\n"
                + "Tax Loss Carryforward Diluted EPS: $ " + getTaxLossCarryforwardDilutedEPS() + "\n"
                + "Diluted Accounting Change: $ " + getDilutedAccountingChange() + "\n"
                + "Normalized Basic EPS: $ " + getNormalizedBasicEPS() + "\n"
                + "Gain On Sale Of Business: $ " + getGainOnSaleOfBusiness() + "\n"
                + "Securities Amortization: $ " + getSecuritiesAmortization() + "\n"
                + "Reported Normalized Basic EPS: $ " + getReportedNormalizedBasicEPS() + "\n"
                + "Other Under Preferred Stock Dividend: $ " + getOtherunderPreferredStockDividend() + "\n"
                + "General And Administrative Expense: $ " + getGeneralAndAdministrativeExpense() + "\n"
                + "Basic Continuous Operations: $ " + getBasicContinuousOperations() + "\n"
                + "Selling And Marketing Expense: $ " + getSellingAndMarketingExpense() + "\n"
                + "Minority Interests: $ " + getMinorityInterests() + "\n"
                + "Other G&A: $ " + getOtherGandA() + "\n"
                + "Continuing And Discontinued Basic EPS: $ " + getContinuingAndDiscontinuedBasicEPS() + "\n"
                + "Other Taxes: $ " + getOtherTaxes() + "\n"
                + "Diluted Continuous Operations: $ " + getDilutedContinuousOperations() + "\n"
                + "Tax Loss Carryforward Basic EPS: $ " + getTaxLossCarryforwardBasicEPS() + "\n"
                + "Provision For Doubtful Accounts: $ " + getProvisionForDoubtfulAccounts() + "\n"
                + "Diluted EPS Other Gains Losses: $ " + getDilutedEPSOtherGainsLosses() + "\n"
                + "Total Other Finance Cost: $ " + getTotalOtherFinanceCost() + "\n"
                + "Normalized Diluted EPS: $ " + getNormalizedDilutedEPS() + "\n"
                + "Dividend Per Share: $ " + getDividendPerShare() + "\n"
                + "Diluted Extraordinary: $ " + getDilutedExtraordinary() + "\n"
                + "Reported Normalized Diluted EPS: $ " + getReportedNormalizedDilutedEPS() + "\n"
                + "Rent Expense Supplemental: $ " + getRentExpenseSupplemental() + "\n"
                + "Gain On Sale Of PPE: $ " + getGainOnSaleOfPPE() + "\n"
                + "Gain On Sale Of Security: $ " + getGainOnSaleOfSecurity() + "\n"
                + "Write Off: $ " + getWriteOff() + "\n"
                + "Insurance And Claims: $ " + getInsuranceAndClaims() + "\n"
                + "Basic Discontinuous Operations: $ " + getBasicDiscontinuousOperations() + "\n"
                + "Preferred Stock Dividends: $ " + getPreferredStockDividends() + "\n"
                + "Impairment Of Capital Assets: $ " + getImpairmentOfCapitalAssets() + "\n"
                + "Basic Extraordinary: $ " + getBasicExtraordinary() + "\n"
                + "Earnings From Equity Interest Net Of Tax: $ " + getEarningsFromEquityInterestNetOfTax() + "\n"
                + "Excise Taxes: $ " + getExciseTaxes() + "\n"
                + "Rent And Landing Fees: $ " + getRentAndLandingFees() + "\n"
                + "Salaries And Wages: $ " + getSalariesAndWages() + "\n"
                + "Basic EPS Other Gains Losses: $ " + getBasicEPSOtherGainsLosses() + "\n"
                + "Depreciation Income Statement: $ " + getDepreciationIncomeStatement() + "\n"
                + "Average Dilution Earnings: " + getAverageDilutionEarnings() + "\n"
                + "Net Income Discontinuous Operations: $ " + getNetIncomeDiscontinuousOperations() + "\n"
                + "Basic Accounting Change: $ " + getBasicAccountingChange() + "\n"
                + "Diluted Discontinuous Operations: $ " + getDilutedDiscontinuousOperations() + "\n"
                + "Depletion Income Statement: $ " + getDepletionIncomeStatement() + "\n"
                + "Continuing And Discontinued Diluted EPS: $ " + getContinuingAndDiscontinuedDilutedEPS() + "\n"
                + "Net Income From Tax Loss Carryforward: $ " + getNetIncomeFromTaxLossCarryforward() + "\n"
                + "Net Income From Continuing And Discontinued Operation: $ " + getNetIncomeFromContinuingAndDiscontinuedOperation() + "\n"
                + "Net Interest Income: $ " + getNetInterestIncome() + "\n"
                + "Pre Tax Income: $ " + getPretaxIncome() + "\n"
                + "Tax Provision: $ " + getTaxProvision() + "\n"
                + "Selling General And Administration: $ " + getSellingGeneralAndAdministration() + "\n";
    }

    public double getSellingGeneralAndAdministration() {
        return sellingGeneralAndAdministration;
    }

    public void setSellingGeneralAndAdministration(double sellingGeneralAndAdministration) {
        this.sellingGeneralAndAdministration = sellingGeneralAndAdministration;
    }

    public double getTaxProvision() {
        return taxProvision;
    }

    public void setTaxProvision(double taxProvision) {
        this.taxProvision = taxProvision;
    }

    public double getPretaxIncome() {
        return pretaxIncome;
    }

    public void setPretaxIncome(double pretaxIncome) {
        this.pretaxIncome = pretaxIncome;
    }

    public double getNetInterestIncome() {
        return netInterestIncome;
    }

    public void setNetInterestIncome(double netInterestIncome) {
        this.netInterestIncome = netInterestIncome;
    }

    public double getNetIncomeFromContinuingAndDiscontinuedOperation() {
        return netIncomeFromContinuingAndDiscontinuedOperation;
    }

    public void setNetIncomeFromContinuingAndDiscontinuedOperation(double netIncomeFromContinuingAndDiscontinuedOperation) {
        this.netIncomeFromContinuingAndDiscontinuedOperation = netIncomeFromContinuingAndDiscontinuedOperation;
    }

    public double getTotalOperatingIncomeAsReported() {
        return totalOperatingIncomeAsReported;
    }

    public void setTotalOperatingIncomeAsReported(double totalOperatingIncomeAsReported) {
        this.totalOperatingIncomeAsReported = totalOperatingIncomeAsReported;
    }

    public double getTaxEffectOfUnusualItems() {
        return taxEffectOfUnusualItems;
    }

    public void setTaxEffectOfUnusualItems(double taxEffectOfUnusualItems) {
        this.taxEffectOfUnusualItems = taxEffectOfUnusualItems;
    }

    public double getNetIncomeContinuousOperations() {
        return netIncomeContinuousOperations;
    }

    public void setNetIncomeContinuousOperations(double netIncomeContinuousOperations) {
        this.netIncomeContinuousOperations = netIncomeContinuousOperations;
    }

    public double getOperatingRevenue() {
        return operatingRevenue;
    }

    public void setOperatingRevenue(double operatingRevenue) {
        this.operatingRevenue = operatingRevenue;
    }

    public double getBasicAverageShares() {
        return basicAverageShares;
    }

    public void setBasicAverageShares(double basicAverageShares) {
        this.basicAverageShares = basicAverageShares;
    }

    public double getDepreciationAndAmortizationInIncomeStatement() {
        return depreciationAndAmortizationInIncomeStatement;
    }

    public void setDepreciationAndAmortizationInIncomeStatement(double depreciationAndAmortizationInIncomeStatement) {
        this.depreciationAndAmortizationInIncomeStatement = depreciationAndAmortizationInIncomeStatement;
    }

    public double getReconciledDepreciation() {
        return reconciledDepreciation;
    }

    public void setReconciledDepreciation(double reconciledDepreciation) {
        this.reconciledDepreciation = reconciledDepreciation;
    }

    public double getOperatingExpense() {
        return operatingExpense;
    }

    public void setOperatingExpense(double operatingExpense) {
        this.operatingExpense = operatingExpense;
    }

    public double getNetIncomeFromContinuingOperationNetMinorityInterest() {
        return netIncomeFromContinuingOperationNetMinorityInterest;
    }

    public void setNetIncomeFromContinuingOperationNetMinorityInterest(double netIncomeFromContinuingOperationNetMinorityInterest) {
        this.netIncomeFromContinuingOperationNetMinorityInterest = netIncomeFromContinuingOperationNetMinorityInterest;
    }

    public double getCostOfRevenue() {
        return costOfRevenue;
    }

    public void setCostOfRevenue(double costOfRevenue) {
        this.costOfRevenue = costOfRevenue;
    }

    public double getResearchAndDevelopment() {
        return researchAndDevelopment;
    }

    public void setResearchAndDevelopment(double researchAndDevelopment) {
        this.researchAndDevelopment = researchAndDevelopment;
    }

    public double getInterestIncome() {
        return interestIncome;
    }

    public void setInterestIncome(double interestIncome) {
        this.interestIncome = interestIncome;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public double getDepreciationAmortizationDepletionIncomeStatement() {
        return depreciationAmortizationDepletionIncomeStatement;
    }

    public void setDepreciationAmortizationDepletionIncomeStatement(double depreciationAmortizationDepletionIncomeStatement) {
        this.depreciationAmortizationDepletionIncomeStatement = depreciationAmortizationDepletionIncomeStatement;
    }

    public double getRestructuringAndMergernAcquisition() {
        return restructuringAndMergernAcquisition;
    }

    public void setRestructuringAndMergernAcquisition(double restructuringAndMergernAcquisition) {
        this.restructuringAndMergernAcquisition = restructuringAndMergernAcquisition;
    }

    public double getNormalizedEBITDA() {
        return normalizedEBITDA;
    }

    public void setNormalizedEBITDA(double normalizedEBITDA) {
        this.normalizedEBITDA = normalizedEBITDA;
    }

    public double getDilutedNIAvailtoComStockholders() {
        return dilutedNIAvailtoComStockholders;
    }

    public void setDilutedNIAvailtoComStockholders(double dilutedNIAvailtoComStockholders) {
        this.dilutedNIAvailtoComStockholders = dilutedNIAvailtoComStockholders;
    }

    public double getNetIncomeIncludingNoncontrollingInterests() {
        return netIncomeIncludingNoncontrollingInterests;
    }

    public void setNetIncomeIncludingNoncontrollingInterests(double netIncomeIncludingNoncontrollingInterests) {
        this.netIncomeIncludingNoncontrollingInterests = netIncomeIncludingNoncontrollingInterests;
    }

    public double getAmortization() {
        return amortization;
    }

    public void setAmortization(double amortization) {
        this.amortization = amortization;
    }

    public double getOtherNonOperatingIncomeExpenses() {
        return otherNonOperatingIncomeExpenses;
    }

    public void setOtherNonOperatingIncomeExpenses(double otherNonOperatingIncomeExpenses) {
        this.otherNonOperatingIncomeExpenses = otherNonOperatingIncomeExpenses;
    }

    public double getOtherOperatingExpenses() {
        return otherOperatingExpenses;
    }

    public void setOtherOperatingExpenses(double otherOperatingExpenses) {
        this.otherOperatingExpenses = otherOperatingExpenses;
    }

    public double getEBITDA() {
        return EBITDA;
    }

    public void setEBITDA(double EBITDA) {
        this.EBITDA = EBITDA;
    }

    public double getBasicEPS() {
        return basicEPS;
    }

    public void setBasicEPS(double basicEPS) {
        this.basicEPS = basicEPS;
    }

    public double getOtherSpecialCharges() {
        return otherSpecialCharges;
    }

    public void setOtherSpecialCharges(double otherSpecialCharges) {
        this.otherSpecialCharges = otherSpecialCharges;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getNetNonOperatingInterestIncomeExpense() {
        return netNonOperatingInterestIncomeExpense;
    }

    public void setNetNonOperatingInterestIncomeExpense(double netNonOperatingInterestIncomeExpense) {
        this.netNonOperatingInterestIncomeExpense = netNonOperatingInterestIncomeExpense;
    }

    public double getTaxRateForCalcs() {
        return taxRateForCalcs;
    }

    public void setTaxRateForCalcs(double taxRateForCalcs) {
        this.taxRateForCalcs = taxRateForCalcs;
    }

    public double getReconciledCostOfRevenue() {
        return reconciledCostOfRevenue;
    }

    public void setReconciledCostOfRevenue(double reconciledCostOfRevenue) {
        this.reconciledCostOfRevenue = reconciledCostOfRevenue;
    }

    public double getEBIT() {
        return EBIT;
    }

    public void setEBIT(double EBIT) {
        this.EBIT = EBIT;
    }

    public double getNetIncomeCommonStockholders() {
        return netIncomeCommonStockholders;
    }

    public void setNetIncomeCommonStockholders(double netIncomeCommonStockholders) {
        this.netIncomeCommonStockholders = netIncomeCommonStockholders;
    }

    public double getDilutedAverageShares() {
        return dilutedAverageShares;
    }

    public void setDilutedAverageShares(double dilutedAverageShares) {
        this.dilutedAverageShares = dilutedAverageShares;
    }

    public double getTotalUnusualItems() {
        return totalUnusualItems;
    }

    public void setTotalUnusualItems(double totalUnusualItems) {
        this.totalUnusualItems = totalUnusualItems;
    }

    public double getNormalizedIncome() {
        return normalizedIncome;
    }

    public void setNormalizedIncome(double normalizedIncome) {
        this.normalizedIncome = normalizedIncome;
    }

    public double getSpecialIncomeCharges() {
        return specialIncomeCharges;
    }

    public void setSpecialIncomeCharges(double specialIncomeCharges) {
        this.specialIncomeCharges = specialIncomeCharges;
    }

    public double getInterestIncomeNonOperating() {
        return interestIncomeNonOperating;
    }

    public void setInterestIncomeNonOperating(double interestIncomeNonOperating) {
        this.interestIncomeNonOperating = interestIncomeNonOperating;
    }

    public double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(double netIncome) {
        this.netIncome = netIncome;
    }

    public double getAmortizationOfIntangiblesIncomeStatement() {
        return amortizationOfIntangiblesIncomeStatement;
    }

    public void setAmortizationOfIntangiblesIncomeStatement(double amortizationOfIntangiblesIncomeStatement) {
        this.amortizationOfIntangiblesIncomeStatement = amortizationOfIntangiblesIncomeStatement;
    }

    public double getInterestExpense() {
        return interestExpense;
    }

    public void setInterestExpense(double interestExpense) {
        this.interestExpense = interestExpense;
    }

    public double getInterestExpenseNonOperating() {
        return interestExpenseNonOperating;
    }

    public void setInterestExpenseNonOperating(double interestExpenseNonOperating) {
        this.interestExpenseNonOperating = interestExpenseNonOperating;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getTotalUnusualItemsExcludingGoodwill() {
        return totalUnusualItemsExcludingGoodwill;
    }

    public void setTotalUnusualItemsExcludingGoodwill(double totalUnusualItemsExcludingGoodwill) {
        this.totalUnusualItemsExcludingGoodwill = totalUnusualItemsExcludingGoodwill;
    }

    public double getOperatingIncome() {
        return operatingIncome;
    }

    public void setOperatingIncome(double operatingIncome) {
        this.operatingIncome = operatingIncome;
    }

    public double getDilutedEPS() {
        return dilutedEPS;
    }

    public void setDilutedEPS(double dilutedEPS) {
        this.dilutedEPS = dilutedEPS;
    }

    public double getNetIncomeExtraordinary() {
        return netIncomeExtraordinary;
    }

    public void setNetIncomeExtraordinary(double netIncomeExtraordinary) {
        this.netIncomeExtraordinary = netIncomeExtraordinary;
    }

    public double getOtherIncomeExpense() {
        return otherIncomeExpense;
    }

    public void setOtherIncomeExpense(double otherIncomeExpense) {
        this.otherIncomeExpense = otherIncomeExpense;
    }

    public double getEarningsFromEquityInterest() {
        return earningsFromEquityInterest;
    }

    public void setEarningsFromEquityInterest(double earningsFromEquityInterest) {
        this.earningsFromEquityInterest = earningsFromEquityInterest;
    }

    public double getTaxLossCarryforwardDilutedEPS() {
        return taxLossCarryforwardDilutedEPS;
    }

    public void setTaxLossCarryforwardDilutedEPS(double taxLossCarryforwardDilutedEPS) {
        this.taxLossCarryforwardDilutedEPS = taxLossCarryforwardDilutedEPS;
    }

    public double getDilutedAccountingChange() {
        return dilutedAccountingChange;
    }

    public void setDilutedAccountingChange(double dilutedAccountingChange) {
        this.dilutedAccountingChange = dilutedAccountingChange;
    }

    public double getNormalizedBasicEPS() {
        return normalizedBasicEPS;
    }

    public void setNormalizedBasicEPS(double normalizedBasicEPS) {
        this.normalizedBasicEPS = normalizedBasicEPS;
    }

    public double getGainOnSaleOfBusiness() {
        return gainOnSaleOfBusiness;
    }

    public void setGainOnSaleOfBusiness(double gainOnSaleOfBusiness) {
        this.gainOnSaleOfBusiness = gainOnSaleOfBusiness;
    }

    public double getSecuritiesAmortization() {
        return securitiesAmortization;
    }

    public void setSecuritiesAmortization(double securitiesAmortization) {
        this.securitiesAmortization = securitiesAmortization;
    }

    public double getReportedNormalizedBasicEPS() {
        return reportedNormalizedBasicEPS;
    }

    public void setReportedNormalizedBasicEPS(double reportedNormalizedBasicEPS) {
        this.reportedNormalizedBasicEPS = reportedNormalizedBasicEPS;
    }

    public double getOtherunderPreferredStockDividend() {
        return otherunderPreferredStockDividend;
    }

    public void setOtherunderPreferredStockDividend(double otherunderPreferredStockDividend) {
        this.otherunderPreferredStockDividend = otherunderPreferredStockDividend;
    }

    public double getGeneralAndAdministrativeExpense() {
        return generalAndAdministrativeExpense;
    }

    public void setGeneralAndAdministrativeExpense(double generalAndAdministrativeExpense) {
        this.generalAndAdministrativeExpense = generalAndAdministrativeExpense;
    }

    public double getBasicContinuousOperations() {
        return basicContinuousOperations;
    }

    public void setBasicContinuousOperations(double basicContinuousOperations) {
        this.basicContinuousOperations = basicContinuousOperations;
    }

    public double getSellingAndMarketingExpense() {
        return sellingAndMarketingExpense;
    }

    public void setSellingAndMarketingExpense(double sellingAndMarketingExpense) {
        this.sellingAndMarketingExpense = sellingAndMarketingExpense;
    }

    public double getMinorityInterests() {
        return minorityInterests;
    }

    public void setMinorityInterests(double minorityInterests) {
        this.minorityInterests = minorityInterests;
    }

    public double getOtherGandA() {
        return otherGandA;
    }

    public void setOtherGandA(double otherGandA) {
        this.otherGandA = otherGandA;
    }

    public double getContinuingAndDiscontinuedBasicEPS() {
        return continuingAndDiscontinuedBasicEPS;
    }

    public void setContinuingAndDiscontinuedBasicEPS(double continuingAndDiscontinuedBasicEPS) {
        this.continuingAndDiscontinuedBasicEPS = continuingAndDiscontinuedBasicEPS;
    }

    public double getOtherTaxes() {
        return otherTaxes;
    }

    public void setOtherTaxes(double otherTaxes) {
        this.otherTaxes = otherTaxes;
    }

    public double getDilutedContinuousOperations() {
        return dilutedContinuousOperations;
    }

    public void setDilutedContinuousOperations(double dilutedContinuousOperations) {
        this.dilutedContinuousOperations = dilutedContinuousOperations;
    }

    public double getTaxLossCarryforwardBasicEPS() {
        return taxLossCarryforwardBasicEPS;
    }

    public void setTaxLossCarryforwardBasicEPS(double taxLossCarryforwardBasicEPS) {
        this.taxLossCarryforwardBasicEPS = taxLossCarryforwardBasicEPS;
    }

    public double getProvisionForDoubtfulAccounts() {
        return provisionForDoubtfulAccounts;
    }

    public void setProvisionForDoubtfulAccounts(double provisionForDoubtfulAccounts) {
        this.provisionForDoubtfulAccounts = provisionForDoubtfulAccounts;
    }

    public double getDilutedEPSOtherGainsLosses() {
        return dilutedEPSOtherGainsLosses;
    }

    public void setDilutedEPSOtherGainsLosses(double dilutedEPSOtherGainsLosses) {
        this.dilutedEPSOtherGainsLosses = dilutedEPSOtherGainsLosses;
    }

    public double getTotalOtherFinanceCost() {
        return totalOtherFinanceCost;
    }

    public void setTotalOtherFinanceCost(double totalOtherFinanceCost) {
        this.totalOtherFinanceCost = totalOtherFinanceCost;
    }

    public double getNormalizedDilutedEPS() {
        return normalizedDilutedEPS;
    }

    public void setNormalizedDilutedEPS(double normalizedDilutedEPS) {
        this.normalizedDilutedEPS = normalizedDilutedEPS;
    }

    public double getDividendPerShare() {
        return dividendPerShare;
    }

    public void setDividendPerShare(double dividendPerShare) {
        this.dividendPerShare = dividendPerShare;
    }

    public double getDilutedExtraordinary() {
        return dilutedExtraordinary;
    }

    public void setDilutedExtraordinary(double dilutedExtraordinary) {
        this.dilutedExtraordinary = dilutedExtraordinary;
    }

    public double getReportedNormalizedDilutedEPS() {
        return reportedNormalizedDilutedEPS;
    }

    public void setReportedNormalizedDilutedEPS(double reportedNormalizedDilutedEPS) {
        this.reportedNormalizedDilutedEPS = reportedNormalizedDilutedEPS;
    }

    public double getRentExpenseSupplemental() {
        return rentExpenseSupplemental;
    }

    public void setRentExpenseSupplemental(double rentExpenseSupplemental) {
        this.rentExpenseSupplemental = rentExpenseSupplemental;
    }

    public double getGainOnSaleOfPPE() {
        return gainOnSaleOfPPE;
    }

    public void setGainOnSaleOfPPE(double gainOnSaleOfPPE) {
        this.gainOnSaleOfPPE = gainOnSaleOfPPE;
    }

    public double getGainOnSaleOfSecurity() {
        return gainOnSaleOfSecurity;
    }

    public void setGainOnSaleOfSecurity(double gainOnSaleOfSecurity) {
        this.gainOnSaleOfSecurity = gainOnSaleOfSecurity;
    }

    public double getWriteOff() {
        return writeOff;
    }

    public void setWriteOff(double writeOff) {
        this.writeOff = writeOff;
    }

    public double getInsuranceAndClaims() {
        return insuranceAndClaims;
    }

    public void setInsuranceAndClaims(double insuranceAndClaims) {
        this.insuranceAndClaims = insuranceAndClaims;
    }

    public double getBasicDiscontinuousOperations() {
        return basicDiscontinuousOperations;
    }

    public void setBasicDiscontinuousOperations(double basicDiscontinuousOperations) {
        this.basicDiscontinuousOperations = basicDiscontinuousOperations;
    }

    public double getPreferredStockDividends() {
        return preferredStockDividends;
    }

    public void setPreferredStockDividends(double preferredStockDividends) {
        this.preferredStockDividends = preferredStockDividends;
    }

    public double getImpairmentOfCapitalAssets() {
        return impairmentOfCapitalAssets;
    }

    public void setImpairmentOfCapitalAssets(double impairmentOfCapitalAssets) {
        this.impairmentOfCapitalAssets = impairmentOfCapitalAssets;
    }

    public double getBasicExtraordinary() {
        return basicExtraordinary;
    }

    public void setBasicExtraordinary(double basicExtraordinary) {
        this.basicExtraordinary = basicExtraordinary;
    }

    public double getEarningsFromEquityInterestNetOfTax() {
        return earningsFromEquityInterestNetOfTax;
    }

    public void setEarningsFromEquityInterestNetOfTax(double earningsFromEquityInterestNetOfTax) {
        this.earningsFromEquityInterestNetOfTax = earningsFromEquityInterestNetOfTax;
    }

    public double getExciseTaxes() {
        return exciseTaxes;
    }

    public void setExciseTaxes(double exciseTaxes) {
        this.exciseTaxes = exciseTaxes;
    }

    public double getRentAndLandingFees() {
        return rentAndLandingFees;
    }

    public void setRentAndLandingFees(double rentAndLandingFees) {
        this.rentAndLandingFees = rentAndLandingFees;
    }

    public double getSalariesAndWages() {
        return salariesAndWages;
    }

    public void setSalariesAndWages(double salariesAndWages) {
        this.salariesAndWages = salariesAndWages;
    }

    public double getBasicEPSOtherGainsLosses() {
        return basicEPSOtherGainsLosses;
    }

    public void setBasicEPSOtherGainsLosses(double basicEPSOtherGainsLosses) {
        this.basicEPSOtherGainsLosses = basicEPSOtherGainsLosses;
    }

    public double getDepreciationIncomeStatement() {
        return depreciationIncomeStatement;
    }

    public void setDepreciationIncomeStatement(double depreciationIncomeStatement) {
        this.depreciationIncomeStatement = depreciationIncomeStatement;
    }

    public double getAverageDilutionEarnings() {
        return averageDilutionEarnings;
    }

    public void setAverageDilutionEarnings(double averageDilutionEarnings) {
        this.averageDilutionEarnings = averageDilutionEarnings;
    }

    public double getNetIncomeDiscontinuousOperations() {
        return netIncomeDiscontinuousOperations;
    }

    public void setNetIncomeDiscontinuousOperations(double netIncomeDiscontinuousOperations) {
        this.netIncomeDiscontinuousOperations = netIncomeDiscontinuousOperations;
    }

    public double getBasicAccountingChange() {
        return basicAccountingChange;
    }

    public void setBasicAccountingChange(double basicAccountingChange) {
        this.basicAccountingChange = basicAccountingChange;
    }

    public double getDilutedDiscontinuousOperations() {
        return dilutedDiscontinuousOperations;
    }

    public void setDilutedDiscontinuousOperations(double dilutedDiscontinuousOperations) {
        this.dilutedDiscontinuousOperations = dilutedDiscontinuousOperations;
    }

    public double getDepletionIncomeStatement() {
        return depletionIncomeStatement;
    }

    public void setDepletionIncomeStatement(double depletionIncomeStatement) {
        this.depletionIncomeStatement = depletionIncomeStatement;
    }

    public double getContinuingAndDiscontinuedDilutedEPS() {
        return continuingAndDiscontinuedDilutedEPS;
    }

    public void setContinuingAndDiscontinuedDilutedEPS(double continuingAndDiscontinuedDilutedEPS) {
        this.continuingAndDiscontinuedDilutedEPS = continuingAndDiscontinuedDilutedEPS;
    }

    public double getNetIncomeFromTaxLossCarryforward() {
        return netIncomeFromTaxLossCarryforward;
    }

    public void setNetIncomeFromTaxLossCarryforward(double netIncomeFromTaxLossCarryforward) {
        this.netIncomeFromTaxLossCarryforward = netIncomeFromTaxLossCarryforward;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public LocalDate getStatementDate() {
        return statementDate;
    }

    public void setStatementDate(LocalDate statementDate) {
        this.statementDate = statementDate;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
