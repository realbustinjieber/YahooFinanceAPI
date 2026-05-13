# YahooFinanceAPI v1.2.

A powerful, modernized Java library for retrieving financial data from Yahoo Finance. This version marks a major architectural shift from DOM-based scraping to direct JSON data retrieval, offering unprecedented reliability and speed.

## 🚀 Key Features in v1.2

* **JSON-First Architecture:** I have moved away from fragile HTML parsing. I've implemented a robust system that interfaces directly with Yahoo's internal backend endpoints.
* **Comprehensive Financials:** I added native support for scraping **Balance Sheets**, **Cash Flow Statements**, and **Income Statements**.
* **Period Flexibility:** You can now toggle seamlessly between **Annual** and **Quarterly** reports using the new `Period` enum.
* **Modern Date API:** I have fully committed to `java.time.LocalDate` throughout the project, replacing the legacy `java.util.Date` for financial statements.
* **Performance Optimized:** I am now utilizing `fasterxml.jackson` for lightning-fast JSON processing and Java Reflections for dynamic field mapping to data models.

## 📦 Requirements & Dependencies

To use this library, ensure your project includes:
* **Java 11+**
* **Jackson Databind** (for JSON parsing)
* **Playwright** (for authentication and cookie management)
* **Jsoup** (for basic web connectivity and as a utility)
* **XChart** (optional, used in the provided `Demo.java` for visualization)

## 🛠 Project Structure

```text
src/de/bustinjieber/main/
├── yahoo/
│   ├── statements/    # Data models (BalanceSheet, CashFlow, IncomeStatement)
│   ├── utils/         # Enums (Period, Frequency)
│   └── Ticker.java    # Main Entry Point & Data Container
├── scraper/
│   ├── Scraper.java   # The core JSON-based scraping engine
│   └── Parser.java    # Data conversion and date logic
└── Demo.java          # Implementation showcase & charting

```

## 💻 Quick Start

### Basic Usage

Initialize a `Ticker` and start pulling financial data instantly:

```java
import de.bustinjieber.main.yahoo.Ticker;
import de.bustinjieber.main.yahoo.utils.Period;

public class Main {
    public static void main(String[] args) {
        // Initialize ticker with debug mode enabled
        Ticker apple = new Ticker("AAPL", true);
        
        // Fetch and print Quarterly Balance Sheets
        apple.getBalanceSheets(Period.Quarterly).forEach((date, sheet) -> {
            System.out.println(date + ": Total Assets -> $ " + sheet.getTotalAssets());
        });
    }
}

```

### Historical Data

You can retrieve historical stock prices with customizable frequencies:

```java
import de.bustinjieber.main.yahoo.utils.Frequency;

ticker.getHistoricals(Frequency.Daily);
System.out.println("Latest Close: " + ticker.getHistoricalClose().lastEntry());

```
