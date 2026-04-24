# Data Processing System

-For generating, processing, and managing large-scale student data.

### Backend
- Java 26.0.1
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- Apache POI (Excel processing)
- OpenCSV (CSV processing)
- PostgreSQL

### Frontend
- Angular 18+
- Angular Reactive Forms
- Angular HttpClient

### Database
- PostgreSQL

The system allows users to:
- Generate large Excel files with up to 1M student records
- Convert Excel data to CSV with transformations
- Upload CSV data into a PostgreSQL database with additional processing rules
- View student records with pagination, filtering, search, and export options (Excel, CSV, PDF)

This project demonstrates a complete ETL-style data pipeline with a simple UI for interaction and scalable backend processing.


##  System Workflow

#### a) Data Generation (Excel Export)
- User inputs number of records (e.g. `1,000,000`)
- System generates student records with fields:
  - `studentId` (incremental starting from 1)
  - `firstName` (random string, 3–8 chars)
  - `lastName` (random string, 3–8 chars)
  - `DOB` (random date between 01-01-2000 and 31-12-2010)
  - `class` (Class1 – Class5)
  - `score` (random between 55–75)

- System exports data to Excel file
- File location:
  - Windows: `C:\var\log\applications\API\dataprocessing\`
  - Linux: `/var/log/applications/api/dataprocessing/`

#### b) Excel to CSV Processing
- User uploads generated Excel file via UI
- System reads file and transforms data:
  - `score = original score + 10`
- Output is saved as CSV file

#### c) CSV to Database Upload
- User uploads CSV file via UI
- System processes and stores data into PostgreSQL
- Before saving:
  - `score = csv score + 5`
  
#### d) Student Report Module
The system provides a reporting UI with:

- Pagination
- Search by `studentId`
- Filter by `class`
- Export options:
  - Excel
  - CSV
  - PD
