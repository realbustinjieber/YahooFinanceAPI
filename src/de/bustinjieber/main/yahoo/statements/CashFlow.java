package de.bustinjieber.main.yahoo.statements;

import de.bustinjieber.main.scraper.Parser;
import de.bustinjieber.main.yahoo.Ticker;
import de.bustinjieber.main.yahoo.utils.Period;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class CashFlow {

    private double cashFlowsFromUsedInOperatingActivitiesDirect;
    private double operatingCashFlow;
    private double cashFlowFromContinuingOperatingActivities;
    private double netIncomeFromContinuingOperations;
    private double depreciationAmortizationDepletion;
    private double depreciationAndAmortization;
    private double deferredTax;
    private double deferredIncomeTax;
    private double stockBasedCompensation;
    private double otherNonCashItems;
    private double changeInWorkingCapital;
    private double changeInReceivables;
    private double changesInAccountReceivables;
    private double changeInInventory;
    private double changeInPayablesAndAccruedExpense;
    private double changeInPayable;
    private double changeInAccountPayable;
    private double changeInOtherCurrentAssets;
    private double changeInOtherCurrentLiabilities;
    private double changeInOtherWorkingCapital;
    private double investingCashFlow;
    private double cashFlowFromContinuingInvestingActivities;
    private double netPPEPurchaseAndSale;
    private double purchaseOfPPE;
    private double netBusinessPurchaseAndSale;
    private double purchaseOfBusiness;
    private double netInvestmentPurchaseAndSale;
    private double purchaseOfInvestment;
    private double saleOfInvestment;
    private double netOtherInvestingChanges;
    private double financingCashFlow;
    private double cashFlowFromContinuingFinancingActivities;
    private double netIssuancePaymentsOfDebt;
    private double netLongTermDebtIssuance;
    private double longTermDebtIssuance;
    private double longTermDebtPayments;
    private double netShortTermDebtIssuance;
    private double netCommonStockIssuance;
    private double commonStockPayments;
    private double cashDividendsPaid;
    private double commonStockDividendPaid;
    private double netOtherFinancingCharges;
    private double cashFlowFromDiscontinuedOperation;
    private double otherCashAdjustmentInsideChangeInCash;
    private double endCashPosition;
    private double changesInCash;
    private double beginningCashPosition;
    private double incomeTaxPaidSupplementalData;
    private double interestPaidSupplementalData;
    private double issuanceOfCapitalStock;
    private double repaymentOfDebt;
    private double repurchaseOfCapitalStock;
    private double freeCashFlow;
    private double adjustedGeographySegmentData;
    private double domesticSales;
    private double issuanceOfDebt;
    private double depreciation;
    private double changeInPrepaidAssets;
    private double amortizationCashFlow;
    private double amortizationOfIntangibles;
    private double capitalExpenditure;
    private double proceedsFromStockOptionExercised;
    private double changeInAccruedExpense;
    private double operatingGainsLosses;
    private double gainLossOnInvestmentSecurities;
    private double effectOfExchangeRateChanges;
    private double netForeignCurrencyExchangeGainLoss;
    private double assetImpairmentCharge;
    private double gainLossOnSaleOfPPE;

    private Ticker ticker;
    private LocalDate cashFlowDate;
    private final Parser parser = new Parser();
    private Period period;


    public CashFlow(Ticker t, LocalDate d, Period p){
        this.ticker = t;
        this.cashFlowDate = d;
        this.period = p;
    }

    public CashFlow(Ticker t, Period p){
        this.ticker = t;
        this.period = p;
    }

    public String getUrl() {
        String base = "https://query1.finance.yahoo.com/ws/fundamentals-timeseries/v1/finance/timeseries/" + ticker.getTicker();

        String type = "annualForeignSales,trailingForeignSales,annualDomesticSales,trailingDomesticSales,annualAdjustedGeographySegmentData,trailingAdjustedGeographySegmentData,annualFreeCashFlow,trailingFreeCashFlow,annualRepurchaseOfCapitalStock,trailingRepurchaseOfCapitalStock,annualRepaymentOfDebt,trailingRepaymentOfDebt,annualIssuanceOfDebt,trailingIssuanceOfDebt,annualIssuanceOfCapitalStock,trailingIssuanceOfCapitalStock,annualCapitalExpenditure,trailingCapitalExpenditure,annualInterestPaidSupplementalData,trailingInterestPaidSupplementalData,annualIncomeTaxPaidSupplementalData,trailingIncomeTaxPaidSupplementalData,annualEndCashPosition,trailingEndCashPosition,annualOtherCashAdjustmentOutsideChangeinCash,trailingOtherCashAdjustmentOutsideChangeinCash,annualBeginningCashPosition,trailingBeginningCashPosition,annualEffectOfExchangeRateChanges,trailingEffectOfExchangeRateChanges,annualChangesInCash,trailingChangesInCash,annualOtherCashAdjustmentInsideChangeinCash,trailingOtherCashAdjustmentInsideChangeinCash,annualCashFlowFromDiscontinuedOperation,trailingCashFlowFromDiscontinuedOperation,annualFinancingCashFlow,trailingFinancingCashFlow,annualCashFromDiscontinuedFinancingActivities,trailingCashFromDiscontinuedFinancingActivities,annualCashFlowFromContinuingFinancingActivities,trailingCashFlowFromContinuingFinancingActivities,annualNetOtherFinancingCharges,trailingNetOtherFinancingCharges,annualInterestPaidCFF,trailingInterestPaidCFF,annualProceedsFromStockOptionExercised,trailingProceedsFromStockOptionExercised,annualCashDividendsPaid,trailingCashDividendsPaid,annualPreferredStockDividendPaid,trailingPreferredStockDividendPaid,annualCommonStockDividendPaid,trailingCommonStockDividendPaid,annualNetPreferredStockIssuance,trailingNetPreferredStockIssuance,annualPreferredStockPayments,trailingPreferredStockPayments,annualPreferredStockIssuance,trailingPreferredStockIssuance,annualNetCommonStockIssuance,trailingNetCommonStockIssuance,annualCommonStockPayments,trailingCommonStockPayments,annualCommonStockIssuance,trailingCommonStockIssuance,annualNetIssuancePaymentsOfDebt,trailingNetIssuancePaymentsOfDebt,annualNetShortTermDebtIssuance,trailingNetShortTermDebtIssuance,annualShortTermDebtPayments,trailingShortTermDebtPayments,annualShortTermDebtIssuance,trailingShortTermDebtIssuance,annualNetLongTermDebtIssuance,trailingNetLongTermDebtIssuance,annualLongTermDebtPayments,trailingLongTermDebtPayments,annualLongTermDebtIssuance,trailingLongTermDebtIssuance,annualInvestingCashFlow,trailingInvestingCashFlow,annualCashFromDiscontinuedInvestingActivities,trailingCashFromDiscontinuedInvestingActivities,annualCashFlowFromContinuingInvestingActivities,trailingCashFlowFromContinuingInvestingActivities,annualNetOtherInvestingChanges,trailingNetOtherInvestingChanges,annualInterestReceivedCFI,trailingInterestReceivedCFI,annualDividendsReceivedCFI,trailingDividendsReceivedCFI,annualNetInvestmentPurchaseAndSale,trailingNetInvestmentPurchaseAndSale,annualSaleOfInvestment,trailingSaleOfInvestment,annualPurchaseOfInvestment,trailingPurchaseOfInvestment,annualNetInvestmentPropertiesPurchaseAndSale,trailingNetInvestmentPropertiesPurchaseAndSale,annualSaleOfInvestmentProperties,trailingSaleOfInvestmentProperties,annualPurchaseOfInvestmentProperties,trailingPurchaseOfInvestmentProperties,annualNetBusinessPurchaseAndSale,trailingNetBusinessPurchaseAndSale,annualSaleOfBusiness,trailingSaleOfBusiness,annualPurchaseOfBusiness,trailingPurchaseOfBusiness,annualNetIntangiblesPurchaseAndSale,trailingNetIntangiblesPurchaseAndSale,annualSaleOfIntangibles,trailingSaleOfIntangibles,annualPurchaseOfIntangibles,trailingPurchaseOfIntangibles,annualNetPPEPurchaseAndSale,trailingNetPPEPurchaseAndSale,annualSaleOfPPE,trailingSaleOfPPE,annualPurchaseOfPPE,trailingPurchaseOfPPE,annualCapitalExpenditureReported,trailingCapitalExpenditureReported,annualOperatingCashFlow,trailingOperatingCashFlow,annualCashFromDiscontinuedOperatingActivities,trailingCashFromDiscontinuedOperatingActivities,annualCashFlowFromContinuingOperatingActivities,trailingCashFlowFromContinuingOperatingActivities,annualTaxesRefundPaid,trailingTaxesRefundPaid,annualInterestReceivedCFO,trailingInterestReceivedCFO,annualInterestPaidCFO,trailingInterestPaidCFO,annualDividendReceivedCFO,trailingDividendReceivedCFO,annualDividendPaidCFO,trailingDividendPaidCFO,annualChangeInWorkingCapital,trailingChangeInWorkingCapital,annualChangeInOtherWorkingCapital,trailingChangeInOtherWorkingCapital,annualChangeInOtherCurrentLiabilities,trailingChangeInOtherCurrentLiabilities,annualChangeInOtherCurrentAssets,trailingChangeInOtherCurrentAssets,annualChangeInPayablesAndAccruedExpense,trailingChangeInPayablesAndAccruedExpense,annualChangeInAccruedExpense,trailingChangeInAccruedExpense,annualChangeInInterestPayable,trailingChangeInInterestPayable,annualChangeInPayable,trailingChangeInPayable,annualChangeInDividendPayable,trailingChangeInDividendPayable,annualChangeInAccountPayable,trailingChangeInAccountPayable,annualChangeInTaxPayable,trailingChangeInTaxPayable,annualChangeInIncomeTaxPayable,trailingChangeInIncomeTaxPayable,annualChangeInPrepaidAssets,trailingChangeInPrepaidAssets,annualChangeInInventory,trailingChangeInInventory,annualChangeInReceivables,trailingChangeInReceivables,annualChangesInAccountReceivables,trailingChangesInAccountReceivables,annualOtherNonCashItems,trailingOtherNonCashItems,annualExcessTaxBenefitFromStockBasedCompensation,trailingExcessTaxBenefitFromStockBasedCompensation,annualStockBasedCompensation,trailingStockBasedCompensation,annualUnrealizedGainLossOnInvestmentSecurities,trailingUnrealizedGainLossOnInvestmentSecurities,annualProvisionandWriteOffofAssets,trailingProvisionandWriteOffofAssets,annualAssetImpairmentCharge,trailingAssetImpairmentCharge,annualAmortizationOfSecurities,trailingAmortizationOfSecurities,annualDeferredTax,trailingDeferredTax,annualDeferredIncomeTax,trailingDeferredIncomeTax,annualDepreciationAmortizationDepletion,trailingDepreciationAmortizationDepletion,annualDepletion,trailingDepletion,annualDepreciationAndAmortization,trailingDepreciationAndAmortization,annualAmortizationCashFlow,trailingAmortizationCashFlow,annualAmortizationOfIntangibles,trailingAmortizationOfIntangibles,annualDepreciation,trailingDepreciation,annualOperatingGainsLosses,trailingOperatingGainsLosses,annualPensionAndEmployeeBenefitExpense,trailingPensionAndEmployeeBenefitExpense,annualEarningsLossesFromEquityInvestments,trailingEarningsLossesFromEquityInvestments,annualGainLossOnInvestmentSecurities,trailingGainLossOnInvestmentSecurities,annualNetForeignCurrencyExchangeGainLoss,trailingNetForeignCurrencyExchangeGainLoss,annualGainLossOnSaleOfPPE,trailingGainLossOnSaleOfPPE,annualGainLossOnSaleOfBusiness,trailingGainLossOnSaleOfBusiness,annualNetIncomeFromContinuingOperations,trailingNetIncomeFromContinuingOperations,annualCashFlowsfromusedinOperatingActivitiesDirect,trailingCashFlowsfromusedinOperatingActivitiesDirect,annualTaxesRefundPaidDirect,trailingTaxesRefundPaidDirect,annualInterestReceivedDirect,trailingInterestReceivedDirect,annualInterestPaidDirect,trailingInterestPaidDirect,annualDividendsReceivedDirect,trailingDividendsReceivedDirect,annualDividendsPaidDirect,trailingDividendsPaidDirect,annualClassesofCashPayments,trailingClassesofCashPayments,annualOtherCashPaymentsfromOperatingActivities,trailingOtherCashPaymentsfromOperatingActivities,annualPaymentsonBehalfofEmployees,trailingPaymentsonBehalfofEmployees,annualPaymentstoSuppliersforGoodsandServices,trailingPaymentstoSuppliersforGoodsandServices,annualClassesofCashReceiptsfromOperatingActivities,trailingClassesofCashReceiptsfromOperatingActivities,annualOtherCashReceiptsfromOperatingActivities,trailingOtherCashReceiptsfromOperatingActivities,annualReceiptsfromGovernmentGrants,trailingReceiptsfromGovernmentGrants,annualReceiptsfromCustomers,trailingReceiptsfromCustomers";
        if(period.getPeriod().equals("quarterly")) {
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

    public double getEffectOfExchangeRateChanges() {
        return effectOfExchangeRateChanges;
    }

    public void setEffectOfExchangeRateChanges(double effectOfExchangeRateChanges) {
        this.effectOfExchangeRateChanges = effectOfExchangeRateChanges;
    }

    public double getNetForeignCurrencyExchangeGainLoss() {
        return netForeignCurrencyExchangeGainLoss;
    }

    public void setNetForeignCurrencyExchangeGainLoss(double netForeignCurrencyExchangeGainLoss) {
        this.netForeignCurrencyExchangeGainLoss = netForeignCurrencyExchangeGainLoss;
    }

    public double getAssetImpairmentCharge() {
        return assetImpairmentCharge;
    }

    public void setAssetImpairmentCharge(double assetImpairmentCharge) {
        this.assetImpairmentCharge = assetImpairmentCharge;
    }

    public double getGainLossOnSaleOfPPE() {
        return gainLossOnSaleOfPPE;
    }

    public void setGainLossOnSaleOfPPE(double gainLossOnSaleOfPPE) {
        this.gainLossOnSaleOfPPE = gainLossOnSaleOfPPE;
    }

    public double getOperatingCashFlow() {
        return operatingCashFlow;
    }

    public double getCashFlowFromContinuingOperatingActivities() {
        return cashFlowFromContinuingOperatingActivities;
    }

    public double getChangeInWorkingCapital() {
        return changeInWorkingCapital;
    }

    public double getCashFlowFromContinuingInvestingActivities() {
        return cashFlowFromContinuingInvestingActivities;
    }

    public double getNetPPEPurchaseAndSale() {
        return netPPEPurchaseAndSale;
    }

    public double getNetInvestmentPurchaseAndSale() {
        return netInvestmentPurchaseAndSale;
    }

    public double getCashFlowFromContinuingFinancingActivities() {
        return cashFlowFromContinuingFinancingActivities;
    }

    public double getNetIssuancePaymentsOfDebt() {
        return netIssuancePaymentsOfDebt;
    }

    public double getNetLongTermDebtIssuance() {
        return netLongTermDebtIssuance;
    }

    public double getNetCommonStockIssuance() {
        return netCommonStockIssuance;
    }

    public double getEndCashPosition() {
        return endCashPosition;
    }

    public double getChangesInCash() {
        return changesInCash;
    }

    public double getFreeCashFlow() {
        return freeCashFlow;
    }

    public double getIssuanceOfDebt() {
        return issuanceOfDebt;
    }

    public void setIssuanceOfDebt(double issuanceOfDebt) {
        this.issuanceOfDebt = issuanceOfDebt;
    }

    public double getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(double depreciation) {
        this.depreciation = depreciation;
    }

    public double getChangeInPrepaidAssets() {
        return changeInPrepaidAssets;
    }

    public void setChangeInPrepaidAssets(double changeInPrepaidAssets) {
        this.changeInPrepaidAssets = changeInPrepaidAssets;
    }

    public double getAmortizationCashFlow() {
        return amortizationCashFlow;
    }

    public void setAmortizationCashFlow(double amortizationCashFlow) {
        this.amortizationCashFlow = amortizationCashFlow;
    }

    public double getAmortizationOfIntangibles() {
        return amortizationOfIntangibles;
    }

    public void setAmortizationOfIntangibles(double amortizationOfIntangibles) {
        this.amortizationOfIntangibles = amortizationOfIntangibles;
    }

    public double getCapitalExpenditure() {
        return capitalExpenditure;
    }

    public void setCapitalExpenditure(double capitalExpenditure) {
        this.capitalExpenditure = capitalExpenditure;
    }

    public double getProceedsFromStockOptionExercised() {
        return proceedsFromStockOptionExercised;
    }

    public void setProceedsFromStockOptionExercised(double proceedsFromStockOptionExercised) {
        this.proceedsFromStockOptionExercised = proceedsFromStockOptionExercised;
    }

    public double getChangeInAccruedExpense() {
        return changeInAccruedExpense;
    }

    public void setChangeInAccruedExpense(double changeInAccruedExpense) {
        this.changeInAccruedExpense = changeInAccruedExpense;
    }

    public double getOperatingGainsLosses() {
        return operatingGainsLosses;
    }

    public void setOperatingGainsLosses(double operatingGainsLosses) {
        this.operatingGainsLosses = operatingGainsLosses;
    }

    public double getGainLossOnInvestmentSecurities() {
        return gainLossOnInvestmentSecurities;
    }

    public void setGainLossOnInvestmentSecurities(double gainLossOnInvestmentSecurities) {
        this.gainLossOnInvestmentSecurities = gainLossOnInvestmentSecurities;
    }

    public double getCashFlowsFromUsedInOperatingActivitiesDirect() {
        return cashFlowsFromUsedInOperatingActivitiesDirect;
    }

    public void setCashFlowsFromUsedInOperatingActivitiesDirect(double cashFlowsFromUsedInOperatingActivitiesDirect) {
        this.cashFlowsFromUsedInOperatingActivitiesDirect = cashFlowsFromUsedInOperatingActivitiesDirect;
    }

    public void setOperatingCashFlow(double operatingCashFlow) {
        this.operatingCashFlow = operatingCashFlow;
    }

    public void setCashFlowFromContinuingOperatingActivities(double cashFlowFromContinuingOperatingActivities) {
        this.cashFlowFromContinuingOperatingActivities = cashFlowFromContinuingOperatingActivities;
    }

    public double getNetIncomeFromContinuingOperations() {
        return netIncomeFromContinuingOperations;
    }

    public void setNetIncomeFromContinuingOperations(double netIncomeFromContinuingOperations) {
        this.netIncomeFromContinuingOperations = netIncomeFromContinuingOperations;
    }

    public double getDepreciationAmortizationDepletion() {
        return depreciationAmortizationDepletion;
    }

    public void setDepreciationAmortizationDepletion(double depreciationAmortizationDepletion) {
        this.depreciationAmortizationDepletion = depreciationAmortizationDepletion;
    }

    public double getDepreciationAndAmortization() {
        return depreciationAndAmortization;
    }

    public void setDepreciationAndAmortization(double depreciationAndAmortization) {
        this.depreciationAndAmortization = depreciationAndAmortization;
    }

    public double getDeferredTax() {
        return deferredTax;
    }

    public void setDeferredTax(double deferredTax) {
        this.deferredTax = deferredTax;
    }

    public double getDeferredIncomeTax() {
        return deferredIncomeTax;
    }

    public void setDeferredIncomeTax(double deferredIncomeTax) {
        this.deferredIncomeTax = deferredIncomeTax;
    }

    public double getStockBasedCompensation() {
        return stockBasedCompensation;
    }

    public void setStockBasedCompensation(double stockBasedCompensation) {
        this.stockBasedCompensation = stockBasedCompensation;
    }

    public double getOtherNonCashItems() {
        return otherNonCashItems;
    }

    public void setOtherNonCashItems(double otherNonCashItems) {
        this.otherNonCashItems = otherNonCashItems;
    }

    public void setChangeInWorkingCapital(double changeInWorkingCapital) {
        this.changeInWorkingCapital = changeInWorkingCapital;
    }

    public double getChangeInReceivables() {
        return changeInReceivables;
    }

    public void setChangeInReceivables(double changeInReceivables) {
        this.changeInReceivables = changeInReceivables;
    }

    public double getChangesInAccountReceivables() {
        return changesInAccountReceivables;
    }

    public void setChangesInAccountReceivables(double changesInAccountReceivables) {
        this.changesInAccountReceivables = changesInAccountReceivables;
    }

    public double getChangeInInventory() {
        return changeInInventory;
    }

    public void setChangeInInventory(double changeInInventory) {
        this.changeInInventory = changeInInventory;
    }

    public double getChangeInPayablesAndAccruedExpense() {
        return changeInPayablesAndAccruedExpense;
    }

    public void setChangeInPayablesAndAccruedExpense(double changeInPayablesAndAccruedExpense) {
        this.changeInPayablesAndAccruedExpense = changeInPayablesAndAccruedExpense;
    }

    public double getChangeInPayable() {
        return changeInPayable;
    }

    public void setChangeInPayable(double changeInPayable) {
        this.changeInPayable = changeInPayable;
    }

    public double getChangeInAccountPayable() {
        return changeInAccountPayable;
    }

    public void setChangeInAccountPayable(double changeInAccountPayable) {
        this.changeInAccountPayable = changeInAccountPayable;
    }

    public double getChangeInOtherCurrentAssets() {
        return changeInOtherCurrentAssets;
    }

    public void setChangeInOtherCurrentAssets(double changeInOtherCurrentAssets) {
        this.changeInOtherCurrentAssets = changeInOtherCurrentAssets;
    }

    public double getChangeInOtherCurrentLiabilities() {
        return changeInOtherCurrentLiabilities;
    }

    public void setChangeInOtherCurrentLiabilities(double changeInOtherCurrentLiabilities) {
        this.changeInOtherCurrentLiabilities = changeInOtherCurrentLiabilities;
    }

    public double getChangeInOtherWorkingCapital() {
        return changeInOtherWorkingCapital;
    }

    public void setChangeInOtherWorkingCapital(double changeInOtherWorkingCapital) {
        this.changeInOtherWorkingCapital = changeInOtherWorkingCapital;
    }

    public double getInvestingCashFlow() {
        return investingCashFlow;
    }

    public void setInvestingCashFlow(double investingCashFlow) {
        this.investingCashFlow = investingCashFlow;
    }

    public void setCashFlowFromContinuingInvestingActivities(double cashFlowFromContinuingInvestingActivities) {
        this.cashFlowFromContinuingInvestingActivities = cashFlowFromContinuingInvestingActivities;
    }

    public void setNetPPEPurchaseAndSale(double netPPEPurchaseAndSale) {
        this.netPPEPurchaseAndSale = netPPEPurchaseAndSale;
    }

    public double getPurchaseOfPPE() {
        return purchaseOfPPE;
    }

    public void setPurchaseOfPPE(double purchaseOfPPE) {
        this.purchaseOfPPE = purchaseOfPPE;
    }

    public double getNetBusinessPurchaseAndSale() {
        return netBusinessPurchaseAndSale;
    }

    public void setNetBusinessPurchaseAndSale(double netBusinessPurchaseAndSale) {
        this.netBusinessPurchaseAndSale = netBusinessPurchaseAndSale;
    }

    public double getPurchaseOfBusiness() {
        return purchaseOfBusiness;
    }

    public void setPurchaseOfBusiness(double purchaseOfBusiness) {
        this.purchaseOfBusiness = purchaseOfBusiness;
    }

    public void setNetInvestmentPurchaseAndSale(double netInvestmentPurchaseAndSale) {
        this.netInvestmentPurchaseAndSale = netInvestmentPurchaseAndSale;
    }

    public double getPurchaseOfInvestment() {
        return purchaseOfInvestment;
    }

    public void setPurchaseOfInvestment(double purchaseOfInvestment) {
        this.purchaseOfInvestment = purchaseOfInvestment;
    }

    public double getSaleOfInvestment() {
        return saleOfInvestment;
    }

    public void setSaleOfInvestment(double saleOfInvestment) {
        this.saleOfInvestment = saleOfInvestment;
    }

    public double getNetOtherInvestingChanges() {
        return netOtherInvestingChanges;
    }

    public void setNetOtherInvestingChanges(double netOtherInvestingChanges) {
        this.netOtherInvestingChanges = netOtherInvestingChanges;
    }

    public double getFinancingCashFlow() {
        return financingCashFlow;
    }

    public void setFinancingCashFlow(double financingCashFlow) {
        this.financingCashFlow = financingCashFlow;
    }

    public void setCashFlowFromContinuingFinancingActivities(double cashFlowFromContinuingFinancingActivities) {
        this.cashFlowFromContinuingFinancingActivities = cashFlowFromContinuingFinancingActivities;
    }

    public void setNetIssuancePaymentsOfDebt(double netIssuancePaymentsOfDebt) {
        this.netIssuancePaymentsOfDebt = netIssuancePaymentsOfDebt;
    }

    public void setNetLongTermDebtIssuance(double netLongTermDebtIssuance) {
        this.netLongTermDebtIssuance = netLongTermDebtIssuance;
    }

    public double getLongTermDebtIssuance() {
        return longTermDebtIssuance;
    }

    public void setLongTermDebtIssuance(double longTermDebtIssuance) {
        this.longTermDebtIssuance = longTermDebtIssuance;
    }

    public double getLongTermDebtPayments() {
        return longTermDebtPayments;
    }

    public void setLongTermDebtPayments(double longTermDebtPayments) {
        this.longTermDebtPayments = longTermDebtPayments;
    }

    public double getNetShortTermDebtIssuance() {
        return netShortTermDebtIssuance;
    }

    public void setNetShortTermDebtIssuance(double netShortTermDebtIssuance) {
        this.netShortTermDebtIssuance = netShortTermDebtIssuance;
    }

    public void setNetCommonStockIssuance(double netCommonStockIssuance) {
        this.netCommonStockIssuance = netCommonStockIssuance;
    }

    public double getCommonStockPayments() {
        return commonStockPayments;
    }

    public void setCommonStockPayments(double commonStockPayments) {
        this.commonStockPayments = commonStockPayments;
    }

    public double getCashDividendsPaid() {
        return cashDividendsPaid;
    }

    public void setCashDividendsPaid(double cashDividendsPaid) {
        this.cashDividendsPaid = cashDividendsPaid;
    }

    public double getCommonStockDividendPaid() {
        return commonStockDividendPaid;
    }

    public void setCommonStockDividendPaid(double commonStockDividendPaid) {
        this.commonStockDividendPaid = commonStockDividendPaid;
    }

    public double getNetOtherFinancingCharges() {
        return netOtherFinancingCharges;
    }

    public void setNetOtherFinancingCharges(double netOtherFinancingCharges) {
        this.netOtherFinancingCharges = netOtherFinancingCharges;
    }

    public double getCashFlowFromDiscontinuedOperation() {
        return cashFlowFromDiscontinuedOperation;
    }

    public void setCashFlowFromDiscontinuedOperation(double cashFlowFromDiscontinuedOperation) {
        this.cashFlowFromDiscontinuedOperation = cashFlowFromDiscontinuedOperation;
    }

    public double getOtherCashAdjustmentInsideChangeInCash() {
        return otherCashAdjustmentInsideChangeInCash;
    }

    public void setOtherCashAdjustmentInsideChangeInCash(double otherCashAdjustmentInsideChangeInCash) {
        this.otherCashAdjustmentInsideChangeInCash = otherCashAdjustmentInsideChangeInCash;
    }

    public void setEndCashPosition(double endCashPosition) {
        this.endCashPosition = endCashPosition;
    }

    public void setChangesInCash(double changesInCash) {
        this.changesInCash = changesInCash;
    }

    public double getBeginningCashPosition() {
        return beginningCashPosition;
    }

    public void setBeginningCashPosition(double beginningCashPosition) {
        this.beginningCashPosition = beginningCashPosition;
    }

    public double getIncomeTaxPaidSupplementalData() {
        return incomeTaxPaidSupplementalData;
    }

    public void setIncomeTaxPaidSupplementalData(double incomeTaxPaidSupplementalData) {
        this.incomeTaxPaidSupplementalData = incomeTaxPaidSupplementalData;
    }

    public double getInterestPaidSupplementalData() {
        return interestPaidSupplementalData;
    }

    public void setInterestPaidSupplementalData(double interestPaidSupplementalData) {
        this.interestPaidSupplementalData = interestPaidSupplementalData;
    }

    public double getIssuanceOfCapitalStock() {
        return issuanceOfCapitalStock;
    }

    public void setIssuanceOfCapitalStock(double issuanceOfCapitalStock) {
        this.issuanceOfCapitalStock = issuanceOfCapitalStock;
    }

    public double getRepaymentOfDebt() {
        return repaymentOfDebt;
    }

    public void setRepaymentOfDebt(double repaymentOfDebt) {
        this.repaymentOfDebt = repaymentOfDebt;
    }

    public double getRepurchaseOfCapitalStock() {
        return repurchaseOfCapitalStock;
    }

    public void setRepurchaseOfCapitalStock(double repurchaseOfCapitalStock) {
        this.repurchaseOfCapitalStock = repurchaseOfCapitalStock;
    }

    public void setFreeCashFlow(double freeCashFlow) {
        this.freeCashFlow = freeCashFlow;
    }

    public double getAdjustedGeographySegmentData() {
        return adjustedGeographySegmentData;
    }

    public void setAdjustedGeographySegmentData(double adjustedGeographySegmentData) {
        this.adjustedGeographySegmentData = adjustedGeographySegmentData;
    }

    public double getDomesticSales() {
        return domesticSales;
    }

    public void setDomesticSales(double domesticSales) {
        this.domesticSales = domesticSales;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public LocalDate getCashFlowDate() {
        return cashFlowDate;
    }

    public void setCashFlowDate(LocalDate cashFlowDate) {
        this.cashFlowDate = cashFlowDate;
    }

    public Parser getParser() {
        return parser;
    }

    @Override
    public String toString() {
        return "Ticker: " + getTicker().getTicker() + "\n"
                + "Sheet Date: " + getCashFlowDate() + "\n"
                + "CashFlowsFromUsedInOperatingActivitiesDirect: $ " + getCashFlowsFromUsedInOperatingActivitiesDirect() + "\n"
                + "OperatingCashFlow: $ " + getOperatingCashFlow() + "\n"
                + "CashFlowFromContinuingOperatingActivities: $ " + getCashFlowFromContinuingOperatingActivities() + "\n"
                + "NetIncomeFromContinuingOperations: $ " + getNetIncomeFromContinuingOperations() + "\n"
                + "DepreciationAmortizationDepletion: $ " + getDepreciationAmortizationDepletion() + "\n"
                + "DepreciationAndAmortization: $ " + getDepreciationAndAmortization() + "\n"
                + "DeferredTax: $ " + getDeferredTax() + "\n"
                + "DeferredIncomeTax: $ " + getDeferredIncomeTax() + "\n"
                + "StockBasedCompensation: $ " + getStockBasedCompensation() + "\n"
                + "OtherNonCashItems: $ " + getOtherNonCashItems() + "\n"
                + "ChangeInWorkingCapital: $ " + getChangeInWorkingCapital() + "\n"
                + "ChangeInReceivables: $ " + getChangeInReceivables() + "\n"
                + "ChangesInAccountReceivables: $ " + getChangesInAccountReceivables() + "\n"
                + "ChangeInInventory: $ " + getChangeInInventory() + "\n"
                + "ChangeInPayablesAndAccruedExpense: $ " + getChangeInPayablesAndAccruedExpense() + "\n"
                + "ChangeInPayable: $ " + getChangeInPayable() + "\n"
                + "ChangeInAccountPayable: $ " + getChangeInAccountPayable() + "\n"
                + "ChangeInOtherCurrentAssets: $ " + getChangeInOtherCurrentAssets() + "\n"
                + "ChangeInOtherCurrentLiabilities: $ " + getChangeInOtherCurrentLiabilities() + "\n"
                + "ChangeInOtherWorkingCapital: $ " + getChangeInOtherWorkingCapital() + "\n"
                + "InvestingCashFlow: $ " + getInvestingCashFlow() + "\n"
                + "CashFlowFromContinuingInvestingActivities: $ " + getCashFlowFromContinuingInvestingActivities() + "\n"
                + "NetPPEPurchaseAndSale: $ " + getNetPPEPurchaseAndSale() + "\n"
                + "PurchaseOfPPE: $ " + getPurchaseOfPPE() + "\n"
                + "NetBusinessPurchaseAndSale: $ " + getNetBusinessPurchaseAndSale() + "\n"
                + "PurchaseOfBusiness: $ " + getPurchaseOfBusiness() + "\n"
                + "NetInvestmentPurchaseAndSale: $ " + getNetInvestmentPurchaseAndSale() + "\n"
                + "PurchaseOfInvestment: $ " + getPurchaseOfInvestment() + "\n"
                + "SaleOfInvestment: $ " + getSaleOfInvestment() + "\n"
                + "NetOtherInvestingChanges: $ " + getNetOtherInvestingChanges() + "\n"
                + "FinancingCashFlow: $ " + getFinancingCashFlow() + "\n"
                + "CashFlowFromContinuingFinancingActivities: $ " + getCashFlowFromContinuingFinancingActivities() + "\n"
                + "NetIssuancePaymentsOfDebt: $ " + getNetIssuancePaymentsOfDebt() + "\n"
                + "NetLongTermDebtIssuance: $ " + getNetLongTermDebtIssuance() + "\n"
                + "LongTermDebtIssuance: $ " + getLongTermDebtIssuance() + "\n"
                + "LongTermDebtPayments: $ " + getLongTermDebtPayments() + "\n"
                + "NetShortTermDebtIssuance: $ " + getNetShortTermDebtIssuance() + "\n"
                + "NetCommonStockIssuance: $ " + getNetCommonStockIssuance() + "\n"
                + "CommonStockPayments: $ " + getCommonStockPayments() + "\n"
                + "CashDividendsPaid: $ " + getCashDividendsPaid() + "\n"
                + "CommonStockDividendPaid: $ " + getCommonStockDividendPaid() + "\n"
                + "NetOtherFinancingCharges: $ " + getNetOtherFinancingCharges() + "\n"
                + "CashFlowFromDiscontinuedOperation: $ " + getCashFlowFromDiscontinuedOperation() + "\n"
                + "OtherCashAdjustmentInsideChangeInCash: $ " + getOtherCashAdjustmentInsideChangeInCash() + "\n"
                + "EndCashPosition: $ " + getEndCashPosition() + "\n"
                + "ChangesInCash: $ " + getChangesInCash() + "\n"
                + "BeginningCashPosition: $ " + getBeginningCashPosition() + "\n"
                + "IncomeTaxPaidSupplementalData: $ " + getIncomeTaxPaidSupplementalData() + "\n"
                + "InterestPaidSupplementalData: $ " + getInterestPaidSupplementalData() + "\n"
                + "IssuanceOfCapitalStock: $ " + getIssuanceOfCapitalStock() + "\n"
                + "RepaymentOfDebt: $ " + getRepaymentOfDebt() + "\n"
                + "RepurchaseOfCapitalStock: $ " + getRepurchaseOfCapitalStock() + "\n"
                + "FreeCashFlow: $ " + getFreeCashFlow() + "\n"
                + "AdjustedGeographySegmentData: $ " + getAdjustedGeographySegmentData() + "\n"
                + "DomesticSales: $ " + getDomesticSales() + "\n"
                + "IssuanceOfDebt: $ " + getIssuanceOfDebt() + "\n"
                + "Depreciation: $ " + getDepreciation() + "\n"
                + "ChangeInPrepaidAssets: $ " + getChangeInPrepaidAssets() + "\n"
                + "AmortizationCashFlow: $ " + getAmortizationCashFlow() + "\n"
                + "AmortizationOfIntangibles: $ " + getAmortizationOfIntangibles() + "\n"
                + "CapitalExpenditure: $ " + getCapitalExpenditure() + "\n"
                + "ProceedsFromStockOptionExercised: $ " + getProceedsFromStockOptionExercised() + "\n"
                + "ChangeInAccruedExpense: $ " + getChangeInAccruedExpense() + "\n"
                + "OperatingGainsLosses: $ " + getOperatingGainsLosses() + "\n"
                + "GainLossOnInvestmentSecurities: $ " + getGainLossOnInvestmentSecurities() + "\n"
                + "Effect Of Exchange Rate Changes: $ " + getEffectOfExchangeRateChanges() + "\n"
                + "Net Foreign Currency Exchange Gain Loss: $ " + getNetForeignCurrencyExchangeGainLoss() + "\n"
                + "Asset Impairment Charge: $ " + getAssetImpairmentCharge() + "\n"
                + "Gain Loss On Sale Of PPE: $ " + getGainLossOnSaleOfPPE() + "\n";
    }
}