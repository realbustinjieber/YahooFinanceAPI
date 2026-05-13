package de.bustinjieber.main.yahoo.statements;

import de.bustinjieber.main.scraper.Parser;
import de.bustinjieber.main.yahoo.Ticker;
import de.bustinjieber.main.yahoo.utils.Period;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;

/**
 * Stores the balancesheet table neatly with calculated fallbacks for zero values.
 */
public class BalanceSheet {

    private Ticker ticker;
    private LocalDate sheetDate;
    private final Parser parser = new Parser();
    private Period period;

    // Balance Sheet Content
    private double totalAssets;
    private double currentAssets;
    private double cashCashEquivalentsAndShortTermInvestments;
    private double cashAndCashEquivalents;
    private double cash;
    private double cashEquivalents;
    private double otherShortTermInvestments;
    private double receivables;
    private double accountsReceivable;
    private double grossAccountsReceivable;
    private double allowanceForDoubtfulAccountsReceivable;
    private double otherReceivables;
    private double inventory;
    private double restrictedCash;
    private double otherCurrentAssets;
    private double totalNonCurrentAssets;
    private double netPPE;
    private double grossPPE;
    private double properties;
    private double landAndImprovements;
    private double machineryFurnitureEquipment;
    private double otherProperties;
    private double leases;
    private double accumulatedDepreciation;
    private double investmentsAndAdvances;
    private double investmentInFinancialAssets;
    private double availableForSaleSecurities;
    private double otherInvestments;
    private double nonCurrentDeferredAssets;
    private double nonCurrentDeferredTaxesAssets;
    private double nonCurrentDeferredLiabilities;
    private double otherNonCurrentAssets;
    private double totalLiabilitiesNetMinorityInterest;
    private double currentLiabilities;
    private double payablesAndAccruedExpenses;
    private double payables;
    private double accountsPayable;
    private double totalTaxPayable;
    private double incomeTaxPayable;
    private double currentAccruedExpenses;
    private double currentProvisions;
    private double pensionAndOtherPostRetirementBenefitPlansCurrent;
    private double currentDebtAndCapitalLeaseObligation;
    private double currentDebt;
    private double commercialPaper;
    private double otherCurrentBorrowings;
    private double currentCapitalLeaseObligation;
    private double currentDeferredLiabilities;
    private double currentDeferredRevenue;
    private double otherCurrentLiabilities;
    private double totalNonCurrentLiabilitiesNetMinorityInterest;
    private double longTermDebtAndCapitalLeaseObligation;
    private double longTermDebt;
    private double longTermCapitalLeaseObligation;
    private double tradeAndOtherPayablesNonCurrent;
    private double employeeBenefits;
    private double nonCurrentPensionAndOtherPostRetirementBenefitPlans;
    private double otherNonCurrentLiabilities;
    private double totalEquityGrossMinorityInterest;
    private double stockholdersEquity;
    private double capitalStock;
    private double commonStock;
    private double retainedEarnings;
    private double additionalPaidInCapital;
    private double gainsLossesNotAffectingRetainedEarnings;
    private double otherEquityAdjustments;
    private double otherEquityInterest;
    private double minorityInterest;
    private double treasuryStock;
    private double totalCapitalization;
    private double commonStockEquity;
    private double capitalLeaseObligations;
    private double netTangibleAssets;
    private double workingCapital;
    private double investedCapital;
    private double tangibleBookValue;
    private double totalDebt;
    private double netDebt;
    private double shareIssued;
    private double ordinarySharesNumber;
    private double treasurySharesNumber;
    private double capitalSurplus;
    private double longTermProvisions;
    private double constructionInProgress;
    private double nonCurrentDeferredRevenue;
    private double goodwillAndOtherIntangibleAssets;
    private double otherIntangibleAssets;
    private double lineOfCredit;
    private double cashFinancial;
    private double preferredStock;
    private double goodwill;
    private double duefromRelatedPartiesCurrent;
    private double buildingsAndImprovements;
    private double duetoRelatedPartiesCurrent;
    private double taxesReceivable;
    private double accruedInterestReceivable;
    private double prepaidAssets;
    private double duetoRelatedPartiesNonCurrent;
    private double duefromRelatedPartiesNonCurrent;
    private double otherPayable;
    private double tradeandOtherPayablesNonCurrent;
    private double nonCurrentDeferredTaxesLiabilities;
    private double longTermEquityInvestment;

    public BalanceSheet(Ticker t, LocalDate d, Period p){
        this.ticker = t;
        this.sheetDate = d;
        this.period = p;
    }

    public BalanceSheet(Ticker t, Period p){
        this.ticker = t;
        this.period = p;
    }

    /**
    * Builds a URL for the balance-sheet endpoint (generates a endpoint (period2) in the next year).
    * @return the URL as a string.
    */
    public String getUrl(){
        String base = "https://query1.finance.yahoo.com/ws/fundamentals-timeseries/v1/finance/timeseries/" + ticker.getTicker();
        String type = "annualTreasurySharesNumber,annualPreferredSharesNumber,annualOrdinarySharesNumber,annualShareIssued,annualNetDebt,annualTotalDebt,annualTangibleBookValue,annualInvestedCapital,annualWorkingCapital,annualNetTangibleAssets,annualCapitalLeaseObligations,annualCommonStockEquity,annualPreferredStockEquity,annualTotalCapitalization,annualTotalEquityGrossMinorityInterest,annualMinorityInterest,annualStockholdersEquity,annualOtherEquityInterest,annualGainsLossesNotAffectingRetainedEarnings,annualFixedAssetsRevaluationReserve,annualOtherEquityAdjustments,annualForeignCurrencyTranslationAdjustments,annualMinimumPensionLiabilities,annualUnrealizedGainLoss,annualTreasuryStock,annualAdditionalPaidInCapital,annualRetainedEarnings,annualCapitalStock,annualOtherCapitalStock,annualCommonStock,annualPreferredStock,annualTotalPartnershipCapital,annualGeneralPartnershipCapital,annualLimitedPartnershipCapital,annualTotalLiabilitiesNetMinorityInterest,annualTotalNonCurrentLiabilitiesNetMinorityInterest,annualOtherNonCurrentLiabilities,annualLiabilitiesHeldforSaleNonCurrent,annualRestrictedCommonStock,annualPreferredSecuritiesOutsideStockEquity,annualDerivativeProductLiabilities,annualEmployeeBenefits,annualNonCurrentPensionAndOtherPostretirementBenefitPlans,annualNonCurrentAccruedExpenses,annualDuetoRelatedPartiesNonCurrent,annualTradeandOtherPayablesNonCurrent,annualNonCurrentDeferredLiabilities,annualNonCurrentDeferredRevenue,annualNonCurrentDeferredTaxesLiabilities,annualLongTermDebtAndCapitalLeaseObligation,annualLongTermCapitalLeaseObligation,annualLongTermDebt,annualLongTermProvisions,annualCurrentLiabilities,annualOtherCurrentLiabilities,annualCurrentDeferredLiabilities,annualCurrentDeferredRevenue,annualCurrentDeferredTaxesLiabilities,annualCurrentDebtAndCapitalLeaseObligation,annualCurrentCapitalLeaseObligation,annualCurrentDebt,annualOtherCurrentBorrowings,annualLineOfCredit,annualCommercialPaper,annualCurrentNotesPayable,annualPensionandOtherPostRetirementBenefitPlansCurrent,annualCurrentProvisions,annualPayablesAndAccruedExpenses,annualCurrentAccruedExpenses,annualInterestPayable,annualPayables,annualOtherPayable,annualDuetoRelatedPartiesCurrent,annualDividendsPayable,annualTotalTaxPayable,annualIncomeTaxPayable,annualAccountsPayable,annualTotalAssets,annualTotalNonCurrentAssets,annualOtherNonCurrentAssets,annualDefinedPensionBenefit,annualNonCurrentPrepaidAssets,annualNonCurrentDeferredAssets,annualNonCurrentDeferredTaxesAssets,annualDuefromRelatedPartiesNonCurrent,annualNonCurrentAccountsReceivable,annualNonCurrentNoteReceivables,annualFinancialAssets,annualInvestmentsAndAdvances,annualOtherInvestments,annualInvestmentinFinancialAssets,annualHeldToMaturitySecurities,annualAvailableForSaleSecurities,annualFinancialAssetsDesignatedasFairValueThroughProfitorLossTotal,annualTradingSecurities,annualLongTermEquityInvestment,annualInvestmentsinJointVenturesatCost,annualInvestmentsInOtherVenturesUnderEquityMethod,annualInvestmentsinAssociatesatCost,annualInvestmentsinSubsidiariesatCost,annualInvestmentProperties,annualGoodwillAndOtherIntangibleAssets,annualOtherIntangibleAssets,annualGoodwill,annualNetPPE,annualAccumulatedDepreciation,annualGrossPPE,annualLeases,annualConstructionInProgress,annualOtherProperties,annualMachineryFurnitureEquipment,annualBuildingsAndImprovements,annualLandAndImprovements,annualFlightFleetVehicleAndRelatedEquipments,annualProperties,annualCurrentAssets,annualOtherCurrentAssets,annualHedgingAssetsCurrent,annualAssetsHeldForSaleCurrent,annualCurrentDeferredAssets,annualCurrentDeferredTaxesAssets,annualRestrictedCash,annualPrepaidAssets,annualInventory,annualReceivables,annualReceivablesAdjustmentsAllowances,annualOtherReceivables,annualDuefromRelatedPartiesCurrent,annualTaxesReceivable,annualAccruedInterestReceivable,annualNotesReceivable,annualLoansReceivable,annualAccountsReceivable,annualAllowanceForDoubtfulAccountsReceivable,annualGrossAccountsReceivable,annualCashCashEquivalentsAndShortTermInvestments,annualOtherShortTermInvestments,annualCashAndCashEquivalents,annualCashEquivalents,annualCashFinancial";

        if (period.getPeriod().equals("quarterly")) {
            type = type.replace("annual", period.getPeriod());
        }

        String params = String.format("merge=%s&padTimeSeries=%s&period1=%s&period2=%s&type=%s",
                URLEncoder.encode("false", StandardCharsets.UTF_8),
                URLEncoder.encode("true", StandardCharsets.UTF_8),
                URLEncoder.encode("493590046", StandardCharsets.UTF_8),
                URLEncoder.encode(String.valueOf(parser.endOfNextYearUnix()), StandardCharsets.UTF_8),
                URLEncoder.encode(type, StandardCharsets.UTF_8)
        );

        return base + "?" + params;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public LocalDate getSheetDate() {
        return sheetDate;
    }

    public void setSheetDate(LocalDate sheetDate) {
        this.sheetDate = sheetDate;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public double getCurrentAssets() {
        return currentAssets;
    }

    public void setCurrentAssets(double currentAssets) {
        this.currentAssets = currentAssets;
    }

    public double getCashCashEquivalentsAndShortTermInvestments() {
        return cashCashEquivalentsAndShortTermInvestments;
    }

    public void setCashCashEquivalentsAndShortTermInvestments(double cashCashEquivalentsAndShortTermInvestments) {
        this.cashCashEquivalentsAndShortTermInvestments = cashCashEquivalentsAndShortTermInvestments;
    }

    public double getCashAndCashEquivalents() {
        return cashAndCashEquivalents;
    }

    public void setCashAndCashEquivalents(double cashAndCashEquivalents) {
        this.cashAndCashEquivalents = cashAndCashEquivalents;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getCashEquivalents() {
        return cashEquivalents;
    }

    public void setCashEquivalents(double cashEquivalents) {
        this.cashEquivalents = cashEquivalents;
    }

    public double getOtherShortTermInvestments() {
        return otherShortTermInvestments;
    }

    public void setOtherShortTermInvestments(double otherShortTermInvestments) {
        this.otherShortTermInvestments = otherShortTermInvestments;
    }

    public double getReceivables() {
        return receivables;
    }

    public void setReceivables(double receivables) {
        this.receivables = receivables;
    }

    public double getAccountsReceivable() {
        return accountsReceivable;
    }

    public void setAccountsReceivable(double accountsReceivable) {
        this.accountsReceivable = accountsReceivable;
    }

    public double getGrossAccountsReceivable() {
        return grossAccountsReceivable;
    }

    public void setGrossAccountsReceivable(double grossAccountsReceivable) {
        this.grossAccountsReceivable = grossAccountsReceivable;
    }

    public double getAllowanceForDoubtfulAccountsReceivable() {
        return allowanceForDoubtfulAccountsReceivable;
    }

    public void setAllowanceForDoubtfulAccountsReceivable(double allowanceForDoubtfulAccountsReceivable) {
        this.allowanceForDoubtfulAccountsReceivable = allowanceForDoubtfulAccountsReceivable;
    }

    public double getOtherReceivables() {
        return otherReceivables;
    }

    public void setOtherReceivables(double otherReceivables) {
        this.otherReceivables = otherReceivables;
    }

    public double getInventory() {
        return inventory;
    }

    public void setInventory(double inventory) {
        this.inventory = inventory;
    }

    public double getRestrictedCash() {
        return restrictedCash;
    }

    public void setRestrictedCash(double restrictedCash) {
        this.restrictedCash = restrictedCash;
    }

    public double getOtherCurrentAssets() {
        return otherCurrentAssets;
    }

    public void setOtherCurrentAssets(double otherCurrentAssets) {
        this.otherCurrentAssets = otherCurrentAssets;
    }

    public double getTotalNonCurrentAssets() {
        return totalNonCurrentAssets;
    }

    public void setTotalNonCurrentAssets(double totalNonCurrentAssets) {
        this.totalNonCurrentAssets = totalNonCurrentAssets;
    }

    public double getNetPPE() {
        return netPPE;
    }

    public void setNetPPE(double netPPE) {
        this.netPPE = netPPE;
    }

    public double getGrossPPE() {
        return grossPPE;
    }

    public void setGrossPPE(double grossPPE) {
        this.grossPPE = grossPPE;
    }

    public double getProperties() {
        return properties;
    }

    public void setProperties(double properties) {
        this.properties = properties;
    }

    public double getLandAndImprovements() {
        return landAndImprovements;
    }

    public void setLandAndImprovements(double landAndImprovements) {
        this.landAndImprovements = landAndImprovements;
    }

    public double getMachineryFurnitureEquipment() {
        return machineryFurnitureEquipment;
    }

    public void setMachineryFurnitureEquipment(double machineryFurnitureEquipment) {
        this.machineryFurnitureEquipment = machineryFurnitureEquipment;
    }

    public double getOtherProperties() {
        return otherProperties;
    }

    public void setOtherProperties(double otherProperties) {
        this.otherProperties = otherProperties;
    }

    public double getLeases() {
        return leases;
    }

    public void setLeases(double leases) {
        this.leases = leases;
    }

    public double getAccumulatedDepreciation() {
        return accumulatedDepreciation;
    }

    public void setAccumulatedDepreciation(double accumulatedDepreciation) {
        this.accumulatedDepreciation = accumulatedDepreciation;
    }

    public double getInvestmentsAndAdvances() {
        return investmentsAndAdvances;
    }

    public void setInvestmentsAndAdvances(double investmentsAndAdvances) {
        this.investmentsAndAdvances = investmentsAndAdvances;
    }

    public double getInvestmentInFinancialAssets() {
        return investmentInFinancialAssets;
    }

    public void setInvestmentInFinancialAssets(double investmentInFinancialAssets) {
        this.investmentInFinancialAssets = investmentInFinancialAssets;
    }

    public double getAvailableForSaleSecurities() {
        return availableForSaleSecurities;
    }

    public void setAvailableForSaleSecurities(double availableForSaleSecurities) {
        this.availableForSaleSecurities = availableForSaleSecurities;
    }

    public double getOtherInvestments() {
        return otherInvestments;
    }

    public void setOtherInvestments(double otherInvestments) {
        this.otherInvestments = otherInvestments;
    }

    public double getNonCurrentDeferredAssets() {
        return nonCurrentDeferredAssets;
    }

    public void setNonCurrentDeferredAssets(double nonCurrentDeferredAssets) {
        this.nonCurrentDeferredAssets = nonCurrentDeferredAssets;
    }

    public double getNonCurrentDeferredTaxesAssets() {
        return nonCurrentDeferredTaxesAssets;
    }

    public void setNonCurrentDeferredTaxesAssets(double nonCurrentDeferredTaxesAssets) {
        this.nonCurrentDeferredTaxesAssets = nonCurrentDeferredTaxesAssets;
    }

    public double getNonCurrentDeferredLiabilities() {
        return nonCurrentDeferredLiabilities;
    }

    public void setNonCurrentDeferredLiabilities(double nonCurrentDeferredLiabilities) {
        this.nonCurrentDeferredLiabilities = nonCurrentDeferredLiabilities;
    }

    public double getOtherNonCurrentAssets() {
        return otherNonCurrentAssets;
    }

    public void setOtherNonCurrentAssets(double otherNonCurrentAssets) {
        this.otherNonCurrentAssets = otherNonCurrentAssets;
    }

    public double getTotalLiabilitiesNetMinorityInterest() {
        return totalLiabilitiesNetMinorityInterest;
    }

    public void setTotalLiabilitiesNetMinorityInterest(double totalLiabilitiesNetMinorityInterest) {
        this.totalLiabilitiesNetMinorityInterest = totalLiabilitiesNetMinorityInterest;
    }

    public double getCurrentLiabilities() {
        return currentLiabilities;
    }

    public void setCurrentLiabilities(double currentLiabilities) {
        this.currentLiabilities = currentLiabilities;
    }

    public double getPayablesAndAccruedExpenses() {
        return payablesAndAccruedExpenses;
    }

    public void setPayablesAndAccruedExpenses(double payablesAndAccruedExpenses) {
        this.payablesAndAccruedExpenses = payablesAndAccruedExpenses;
    }

    public double getPayables() {
        return payables;
    }

    public void setPayables(double payables) {
        this.payables = payables;
    }

    public double getAccountsPayable() {
        return accountsPayable;
    }

    public void setAccountsPayable(double accountsPayable) {
        this.accountsPayable = accountsPayable;
    }

    public double getTotalTaxPayable() {
        return totalTaxPayable;
    }

    public void setTotalTaxPayable(double totalTaxPayable) {
        this.totalTaxPayable = totalTaxPayable;
    }

    public double getIncomeTaxPayable() {
        return incomeTaxPayable;
    }

    public void setIncomeTaxPayable(double incomeTaxPayable) {
        this.incomeTaxPayable = incomeTaxPayable;
    }

    public double getCurrentAccruedExpenses() {
        return currentAccruedExpenses;
    }

    public void setCurrentAccruedExpenses(double currentAccruedExpenses) {
        this.currentAccruedExpenses = currentAccruedExpenses;
    }

    public double getCurrentProvisions() {
        return currentProvisions;
    }

    public void setCurrentProvisions(double currentProvisions) {
        this.currentProvisions = currentProvisions;
    }

    public double getPensionAndOtherPostRetirementBenefitPlansCurrent() {
        return pensionAndOtherPostRetirementBenefitPlansCurrent;
    }

    public void setPensionAndOtherPostRetirementBenefitPlansCurrent(double pensionAndOtherPostRetirementBenefitPlansCurrent) {
        this.pensionAndOtherPostRetirementBenefitPlansCurrent = pensionAndOtherPostRetirementBenefitPlansCurrent;
    }

    public double getCurrentDebtAndCapitalLeaseObligation() {
        return currentDebtAndCapitalLeaseObligation;
    }

    public void setCurrentDebtAndCapitalLeaseObligation(double currentDebtAndCapitalLeaseObligation) {
        this.currentDebtAndCapitalLeaseObligation = currentDebtAndCapitalLeaseObligation;
    }

    public double getCurrentDebt() {
        return currentDebt;
    }

    public void setCurrentDebt(double currentDebt) {
        this.currentDebt = currentDebt;
    }

    public double getCommercialPaper() {
        return commercialPaper;
    }

    public void setCommercialPaper(double commercialPaper) {
        this.commercialPaper = commercialPaper;
    }

    public double getOtherCurrentBorrowings() {
        return otherCurrentBorrowings;
    }

    public void setOtherCurrentBorrowings(double otherCurrentBorrowings) {
        this.otherCurrentBorrowings = otherCurrentBorrowings;
    }

    public double getCurrentCapitalLeaseObligation() {
        return currentCapitalLeaseObligation;
    }

    public void setCurrentCapitalLeaseObligation(double currentCapitalLeaseObligation) {
        this.currentCapitalLeaseObligation = currentCapitalLeaseObligation;
    }

    public double getCurrentDeferredLiabilities() {
        return currentDeferredLiabilities;
    }

    public void setCurrentDeferredLiabilities(double currentDeferredLiabilities) {
        this.currentDeferredLiabilities = currentDeferredLiabilities;
    }

    public double getCurrentDeferredRevenue() {
        return currentDeferredRevenue;
    }

    public void setCurrentDeferredRevenue(double currentDeferredRevenue) {
        this.currentDeferredRevenue = currentDeferredRevenue;
    }

    public double getOtherCurrentLiabilities() {
        return otherCurrentLiabilities;
    }

    public void setOtherCurrentLiabilities(double otherCurrentLiabilities) {
        this.otherCurrentLiabilities = otherCurrentLiabilities;
    }

    public double getTotalNonCurrentLiabilitiesNetMinorityInterest() {
        return totalNonCurrentLiabilitiesNetMinorityInterest;
    }

    public void setTotalNonCurrentLiabilitiesNetMinorityInterest(double totalNonCurrentLiabilitiesNetMinorityInterest) {
        this.totalNonCurrentLiabilitiesNetMinorityInterest = totalNonCurrentLiabilitiesNetMinorityInterest;
    }

    public double getLongTermDebtAndCapitalLeaseObligation() {
        return longTermDebtAndCapitalLeaseObligation;
    }

    public void setLongTermDebtAndCapitalLeaseObligation(double longTermDebtAndCapitalLeaseObligation) {
        this.longTermDebtAndCapitalLeaseObligation = longTermDebtAndCapitalLeaseObligation;
    }

    public double getLongTermDebt() {
        return longTermDebt;
    }

    public void setLongTermDebt(double longTermDebt) {
        this.longTermDebt = longTermDebt;
    }

    public double getLongTermCapitalLeaseObligation() {
        return longTermCapitalLeaseObligation;
    }

    public void setLongTermCapitalLeaseObligation(double longTermCapitalLeaseObligation) {
        this.longTermCapitalLeaseObligation = longTermCapitalLeaseObligation;
    }

    public double getTradeAndOtherPayablesNonCurrent() {
        return tradeAndOtherPayablesNonCurrent;
    }

    public void setTradeAndOtherPayablesNonCurrent(double tradeAndOtherPayablesNonCurrent) {
        this.tradeAndOtherPayablesNonCurrent = tradeAndOtherPayablesNonCurrent;
    }

    public double getEmployeeBenefits() {
        return employeeBenefits;
    }

    public void setEmployeeBenefits(double employeeBenefits) {
        this.employeeBenefits = employeeBenefits;
    }

    public double getNonCurrentPensionAndOtherPostRetirementBenefitPlans() {
        return nonCurrentPensionAndOtherPostRetirementBenefitPlans;
    }

    public void setNonCurrentPensionAndOtherPostRetirementBenefitPlans(double nonCurrentPensionAndOtherPostRetirementBenefitPlans) {
        this.nonCurrentPensionAndOtherPostRetirementBenefitPlans = nonCurrentPensionAndOtherPostRetirementBenefitPlans;
    }

    public double getOtherNonCurrentLiabilities() {
        return otherNonCurrentLiabilities;
    }

    public void setOtherNonCurrentLiabilities(double otherNonCurrentLiabilities) {
        this.otherNonCurrentLiabilities = otherNonCurrentLiabilities;
    }

    public double getTotalEquityGrossMinorityInterest() {
        return totalEquityGrossMinorityInterest;
    }

    public void setTotalEquityGrossMinorityInterest(double totalEquityGrossMinorityInterest) {
        this.totalEquityGrossMinorityInterest = totalEquityGrossMinorityInterest;
    }

    public double getStockholdersEquity() {
        return stockholdersEquity;
    }

    public void setStockholdersEquity(double stockholdersEquity) {
        this.stockholdersEquity = stockholdersEquity;
    }

    public double getCapitalStock() {
        return capitalStock;
    }

    public void setCapitalStock(double capitalStock) {
        this.capitalStock = capitalStock;
    }

    public double getCommonStock() {
        return commonStock;
    }

    public void setCommonStock(double commonStock) {
        this.commonStock = commonStock;
    }

    public double getRetainedEarnings() {
        return retainedEarnings;
    }

    public void setRetainedEarnings(double retainedEarnings) {
        this.retainedEarnings = retainedEarnings;
    }

    public double getAdditionalPaidInCapital() {
        return additionalPaidInCapital;
    }

    public void setAdditionalPaidInCapital(double additionalPaidInCapital) {
        this.additionalPaidInCapital = additionalPaidInCapital;
    }

    public double getGainsLossesNotAffectingRetainedEarnings() {
        return gainsLossesNotAffectingRetainedEarnings;
    }

    public void setGainsLossesNotAffectingRetainedEarnings(double gainsLossesNotAffectingRetainedEarnings) {
        this.gainsLossesNotAffectingRetainedEarnings = gainsLossesNotAffectingRetainedEarnings;
    }

    public double getOtherEquityAdjustments() {
        return otherEquityAdjustments;
    }

    public void setOtherEquityAdjustments(double otherEquityAdjustments) {
        this.otherEquityAdjustments = otherEquityAdjustments;
    }

    public double getOtherEquityInterest() {
        return otherEquityInterest;
    }

    public void setOtherEquityInterest(double otherEquityInterest) {
        this.otherEquityInterest = otherEquityInterest;
    }

    public double getMinorityInterest() {
        return minorityInterest;
    }

    public void setMinorityInterest(double minorityInterest) {
        this.minorityInterest = minorityInterest;
    }

    public double getTreasuryStock() {
        return treasuryStock;
    }

    public void setTreasuryStock(double treasuryStock) {
        this.treasuryStock = treasuryStock;
    }

    public double getTotalCapitalization() {
        return totalCapitalization;
    }

    public void setTotalCapitalization(double totalCapitalization) {
        this.totalCapitalization = totalCapitalization;
    }

    public double getCommonStockEquity() {
        return commonStockEquity;
    }

    public void setCommonStockEquity(double commonStockEquity) {
        this.commonStockEquity = commonStockEquity;
    }

    public double getCapitalLeaseObligations() {
        return capitalLeaseObligations;
    }

    public void setCapitalLeaseObligations(double capitalLeaseObligations) {
        this.capitalLeaseObligations = capitalLeaseObligations;
    }

    public double getNetTangibleAssets() {
        return netTangibleAssets;
    }

    public void setNetTangibleAssets(double netTangibleAssets) {
        this.netTangibleAssets = netTangibleAssets;
    }

    public double getWorkingCapital() {
        return workingCapital;
    }

    public void setWorkingCapital(double workingCapital) {
        this.workingCapital = workingCapital;
    }

    public double getInvestedCapital() {
        return investedCapital;
    }

    public void setInvestedCapital(double investedCapital) {
        this.investedCapital = investedCapital;
    }

    public double getTangibleBookValue() {
        return tangibleBookValue;
    }

    public void setTangibleBookValue(double tangibleBookValue) {
        this.tangibleBookValue = tangibleBookValue;
    }

    public double getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(double totalDebt) {
        this.totalDebt = totalDebt;
    }

    public double getNetDebt() {
        return netDebt;
    }

    public void setNetDebt(double netDebt) {
        this.netDebt = netDebt;
    }

    public double getShareIssued() {
        return shareIssued;
    }

    public void setShareIssued(double shareIssued) {
        this.shareIssued = shareIssued;
    }

    public double getOrdinarySharesNumber() {
        return ordinarySharesNumber;
    }

    public void setOrdinarySharesNumber(double ordinarySharesNumber) {
        this.ordinarySharesNumber = ordinarySharesNumber;
    }

    public double getTreasurySharesNumber() {
        return treasurySharesNumber;
    }

    public void setTreasurySharesNumber(double treasurySharesNumber) {
        this.treasurySharesNumber = treasurySharesNumber;
    }

    public double getCapitalSurplus() {
        return capitalSurplus;
    }

    public void setCapitalSurplus(double capitalSurplus) {
        this.capitalSurplus = capitalSurplus;
    }

    public double getLongTermProvisions() {
        return longTermProvisions;
    }

    public void setLongTermProvisions(double longTermProvisions) {
        this.longTermProvisions = longTermProvisions;
    }

    public double getConstructionInProgress() {
        return constructionInProgress;
    }

    public void setConstructionInProgress(double constructionInProgress) {
        this.constructionInProgress = constructionInProgress;
    }

    public double getNonCurrentDeferredRevenue() {
        return nonCurrentDeferredRevenue;
    }

    public void setNonCurrentDeferredRevenue(double nonCurrentDeferredRevenue) {
        this.nonCurrentDeferredRevenue = nonCurrentDeferredRevenue;
    }

    public double getGoodwillAndOtherIntangibleAssets() {
        return goodwillAndOtherIntangibleAssets;
    }

    public void setGoodwillAndOtherIntangibleAssets(double goodwillAndOtherIntangibleAssets) {
        this.goodwillAndOtherIntangibleAssets = goodwillAndOtherIntangibleAssets;
    }

    public double getOtherIntangibleAssets() {
        return otherIntangibleAssets;
    }

    public void setOtherIntangibleAssets(double otherIntangibleAssets) {
        this.otherIntangibleAssets = otherIntangibleAssets;
    }

    public double getLineOfCredit() {
        return lineOfCredit;
    }

    public void setLineOfCredit(double lineOfCredit) {
        this.lineOfCredit = lineOfCredit;
    }

    public double getCashFinancial() {
        return cashFinancial;
    }

    public void setCashFinancial(double cashFinancial) {
        this.cashFinancial = cashFinancial;
    }

    public double getPreferredStock() {
        return preferredStock;
    }

    public void setPreferredStock(double preferredStock) {
        this.preferredStock = preferredStock;
    }

    public double getGoodwill() {
        return goodwill;
    }

    public void setGoodwill(double goodwill) {
        this.goodwill = goodwill;
    }

    public double getDuefromRelatedPartiesCurrent() {
        return duefromRelatedPartiesCurrent;
    }

    public void setDuefromRelatedPartiesCurrent(double duefromRelatedPartiesCurrent) {
        this.duefromRelatedPartiesCurrent = duefromRelatedPartiesCurrent;
    }

    public double getBuildingsAndImprovements() {
        return buildingsAndImprovements;
    }

    public void setBuildingsAndImprovements(double buildingsAndImprovements) {
        this.buildingsAndImprovements = buildingsAndImprovements;
    }

    public double getDuetoRelatedPartiesCurrent() {
        return duetoRelatedPartiesCurrent;
    }

    public void setDuetoRelatedPartiesCurrent(double duetoRelatedPartiesCurrent) {
        this.duetoRelatedPartiesCurrent = duetoRelatedPartiesCurrent;
    }

    public double getTaxesReceivable() {
        return taxesReceivable;
    }

    public void setTaxesReceivable(double taxesReceivable) {
        this.taxesReceivable = taxesReceivable;
    }

    public double getAccruedInterestReceivable() {
        return accruedInterestReceivable;
    }

    public void setAccruedInterestReceivable(double accruedInterestReceivable) {
        this.accruedInterestReceivable = accruedInterestReceivable;
    }

    public double getPrepaidAssets() {
        return prepaidAssets;
    }

    public void setPrepaidAssets(double prepaidAssets) {
        this.prepaidAssets = prepaidAssets;
    }

    public double getDuetoRelatedPartiesNonCurrent() {
        return duetoRelatedPartiesNonCurrent;
    }

    public void setDuetoRelatedPartiesNonCurrent(double duetoRelatedPartiesNonCurrent) {
        this.duetoRelatedPartiesNonCurrent = duetoRelatedPartiesNonCurrent;
    }

    public double getDuefromRelatedPartiesNonCurrent() {
        return duefromRelatedPartiesNonCurrent;
    }

    public void setDuefromRelatedPartiesNonCurrent(double duefromRelatedPartiesNonCurrent) {
        this.duefromRelatedPartiesNonCurrent = duefromRelatedPartiesNonCurrent;
    }

    public double getOtherPayable() {
        return otherPayable;
    }

    public void setOtherPayable(double otherPayable) {
        this.otherPayable = otherPayable;
    }

    public double getTradeandOtherPayablesNonCurrent() {
        return tradeandOtherPayablesNonCurrent;
    }

    public void setTradeandOtherPayablesNonCurrent(double tradeandOtherPayablesNonCurrent) {
        this.tradeandOtherPayablesNonCurrent = tradeandOtherPayablesNonCurrent;
    }

    public double getNonCurrentDeferredTaxesLiabilities() {
        return nonCurrentDeferredTaxesLiabilities;
    }

    public void setNonCurrentDeferredTaxesLiabilities(double nonCurrentDeferredTaxesLiabilities) {
        this.nonCurrentDeferredTaxesLiabilities = nonCurrentDeferredTaxesLiabilities;
    }

    public double getLongTermEquityInvestment() {
        return longTermEquityInvestment;
    }

    public void setLongTermEquityInvestment(double longTermEquityInvestment) {
        this.longTermEquityInvestment = longTermEquityInvestment;
    }

    @Override
    public String toString() {
        return "Ticker: " + getTicker().getTicker() + "\n"
                + "Sheet Date: " + getSheetDate() + "\n"
                + "Total Assets: $ " + getTotalAssets() + "\n"
                + "Current Assets: $ " + getCurrentAssets() + "\n"
                + "Cash, Cash Equivalents & Short-Term Investments: $ " + getCashCashEquivalentsAndShortTermInvestments() + "\n"
                + "Cash & Cash Equivalents: $ " + getCashAndCashEquivalents() + "\n"
                + "Cash Financial: $ " + getCashFinancial() + "\n"
                + "Cash: $ " + getCash() + "\n"
                + "Cash Equivalents: $ " + getCashEquivalents() + "\n"
                + "Other Short-Term Investments: $ " + getOtherShortTermInvestments() + "\n"
                + "Receivables: $ " + getReceivables() + "\n"
                + "Accounts Receivable: $ " + getAccountsReceivable() + "\n"
                + "Gross Accounts Receivable: $ " + getGrossAccountsReceivable() + "\n"
                + "Allowance For Doubtful Accounts Receivable: $ " + getAllowanceForDoubtfulAccountsReceivable() + "\n"
                + "Other Receivables: $ " + getOtherReceivables() + "\n"
                + "Inventory: $ " + getInventory() + "\n"
                + "Restricted Cash: $ " + getRestrictedCash() + "\n"
                + "Other Current Assets: $ " + getOtherCurrentAssets() + "\n"
                + "Total Non-Current Assets: $ " + getTotalNonCurrentAssets() + "\n"
                + "Net PPE: $ " + getNetPPE() + "\n"
                + "Gross PPE: $ " + getGrossPPE() + "\n"
                + "Properties: $ " + getProperties() + "\n"
                + "Land And Improvements: $ " + getLandAndImprovements() + "\n"
                + "Machinery, Furniture & Equipment: $ " + getMachineryFurnitureEquipment() + "\n"
                + "Construction In Progress: $ " + getConstructionInProgress() + "\n"
                + "Other Properties: $ " + getOtherProperties() + "\n"
                + "Leases: $ " + getLeases() + "\n"
                + "Accumulated Depreciation: $ " + getAccumulatedDepreciation() + "\n"
                + "Goodwill And Other Intangible Assets: $ " + getGoodwillAndOtherIntangibleAssets() + "\n"
                + "Goodwill: $ " + getGoodwill() + "\n"
                + "Other Intangible Assets: $ " + getOtherIntangibleAssets() + "\n"
                + "Investments And Advances: $ " + getInvestmentsAndAdvances() + "\n"
                + "Investment In Financial Assets: $ " + getInvestmentInFinancialAssets() + "\n"
                + "Available-for-Sale Securities: $ " + getAvailableForSaleSecurities() + "\n"
                + "Other Investments: $ " + getOtherInvestments() + "\n"
                + "Non-Current Deferred Assets: $ " + getNonCurrentDeferredAssets() + "\n"
                + "Non-Current Deferred Taxes Assets: $ " + getNonCurrentDeferredTaxesAssets() + "\n"
                + "Non-Current Deferred Liabilities: $ " + getNonCurrentDeferredLiabilities() + "\n"
                + "Other Non-Current Assets: $ " + getOtherNonCurrentAssets() + "\n"
                + "Total Liabilities Net Minority Interest: $ " + getTotalLiabilitiesNetMinorityInterest() + "\n"
                + "Current Liabilities: $ " + getCurrentLiabilities() + "\n"
                + "Payables & Accrued Expenses: $ " + getPayablesAndAccruedExpenses() + "\n"
                + "Payables: $ " + getPayables() + "\n"
                + "Accounts Payable: $ " + getAccountsPayable() + "\n"
                + "Total Tax Payable: $ " + getTotalTaxPayable() + "\n"
                + "Income Tax Payable: $ " + getIncomeTaxPayable() + "\n"
                + "Current Accrued Expenses: $ " + getCurrentAccruedExpenses() + "\n"
                + "Current Provisions: $ " + getCurrentProvisions() + "\n"
                + "Pension & Other Post-Retirement Benefit Plans (Current): $ " + getPensionAndOtherPostRetirementBenefitPlansCurrent() + "\n"
                + "Current Debt & Capital Lease Obligation: $ " + getCurrentDebtAndCapitalLeaseObligation() + "\n"
                + "Current Debt: $ " + getCurrentDebt() + "\n"
                + "Line Of Credit: $ " + getLineOfCredit() + "\n"
                + "Commercial Paper: $ " + getCommercialPaper() + "\n"
                + "Other Current Borrowings: $ " + getOtherCurrentBorrowings() + "\n"
                + "Current Capital Lease Obligation: $ " + getCurrentCapitalLeaseObligation() + "\n"
                + "Current Deferred Liabilities: $ " + getCurrentDeferredLiabilities() + "\n"
                + "Current Deferred Revenue: $ " + getCurrentDeferredRevenue() + "\n"
                + "Other Current Liabilities: $ " + getOtherCurrentLiabilities() + "\n"
                + "Total Non-Current Liabilities Net Minority Interest: $ " + getTotalNonCurrentLiabilitiesNetMinorityInterest() + "\n"
                + "Long-Term Debt & Capital Lease Obligation: $ " + getLongTermDebtAndCapitalLeaseObligation() + "\n"
                + "Long-Term Debt: $ " + getLongTermDebt() + "\n"
                + "Long-Term Capital Lease Obligation: $ " + getLongTermCapitalLeaseObligation() + "\n"
                + "Trade & Other Payables (Non-Current): $ " + getTradeAndOtherPayablesNonCurrent() + "\n"
                + "Non-Current Deferred Revenue: $ " + getNonCurrentDeferredRevenue() + "\n"
                + "Employee Benefits: $ " + getEmployeeBenefits() + "\n"
                + "Non-Current Pension & Other Post-Retirement Benefit Plans: $ " + getNonCurrentPensionAndOtherPostRetirementBenefitPlans() + "\n"
                + "Long-Term Provisions: $ " + getLongTermProvisions() + "\n"
                + "Other Non-Current Liabilities: $ " + getOtherNonCurrentLiabilities() + "\n"
                + "Total Equity Gross Minority Interest: $ " + getTotalEquityGrossMinorityInterest() + "\n"
                + "Stockholders' Equity: $ " + getStockholdersEquity() + "\n"
                + "Preferred Stock: $ " + getPreferredStock() + "\n"
                + "Capital Stock: $ " + getCapitalStock() + "\n"
                + "Common Stock: $ " + getCommonStock() + "\n"
                + "Retained Earnings: $ " + getRetainedEarnings() + "\n"
                + "Additional Paid-In Capital: $ " + getAdditionalPaidInCapital() + "\n"
                + "Gains/Losses Not Affecting Retained Earnings: $ " + getGainsLossesNotAffectingRetainedEarnings() + "\n"
                + "Other Equity Adjustments: $ " + getOtherEquityAdjustments() + "\n"
                + "Other Equity Interest: $ " + getOtherEquityInterest() + "\n"
                + "Minority Interest: $ " + getMinorityInterest() + "\n"
                + "Treasury Stock: $ " + getTreasuryStock() + "\n"
                + "Total Capitalization: $ " + getTotalCapitalization() + "\n"
                + "Common Stock Equity: $ " + getCommonStockEquity() + "\n"
                + "Capital Lease Obligations: $ " + getCapitalLeaseObligations() + "\n"
                + "Net Tangible Assets: $ " + getNetTangibleAssets() + "\n"
                + "Working Capital: $ " + getWorkingCapital() + "\n"
                + "Invested Capital: $ " + getInvestedCapital() + "\n"
                + "Tangible Book Value: $ " + getTangibleBookValue() + "\n"
                + "Total Debt: $ " + getTotalDebt() + "\n"
                + "Net Debt: $ " + getNetDebt() + "\n"
                + "Share Issued: " + getShareIssued() + "\n"
                + "Ordinary Shares Number: " + getOrdinarySharesNumber() + "\n"
                + "Treasury Shares Number: " + getTreasurySharesNumber() + "\n"
                + "Capital Surplus: $ " + getCapitalSurplus() + "\n"
                + "Due From Related Parties (Current): $ " + getDuefromRelatedPartiesCurrent() + "\n"
                + "Buildings And Improvements: $ " + getBuildingsAndImprovements() + "\n"
                + "Due To Related Parties (Current): $ " + getDuetoRelatedPartiesCurrent() + "\n"
                + "Taxes Receivable: $ " + getTaxesReceivable() + "\n"
                + "Accrued Interest Receivable: $ " + getAccruedInterestReceivable() + "\n"
                + "Prepaid Assets: $ " + getPrepaidAssets() + "\n"
                + "Due To Related Parties (Non-Current): $ " + getDuetoRelatedPartiesNonCurrent() + "\n"
                + "Due From Related Parties (Non-Current): $ " + getDuefromRelatedPartiesNonCurrent() + "\n"
                + "Other Payable: $ " + getOtherPayable() + "\n"
                + "Trade And Other Payables (Non-Current): $ " + getTradeandOtherPayablesNonCurrent() + "\n"
                + "Non-Current Deferred Taxes Liabilities: $ " + getNonCurrentDeferredTaxesLiabilities() + "\n"
                + "Long Term Equity Investment: $ " + getLongTermEquityInvestment() + "\n";
    }
}