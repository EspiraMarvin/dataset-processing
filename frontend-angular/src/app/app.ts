import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected records = 1000000;
  protected isGenerating = false;
  protected errorMessage = '';
  protected successMessage = '';

  constructor(private readonly http: HttpClient) {}

  protected generateExcel(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (!Number.isInteger(this.records) || this.records < 1 || this.records > 1000000) {
      this.errorMessage = 'Please enter an integer between 1 and 1000000.';
      return;
    }

    this.isGenerating = true;
    this.http
      .post<GenerateExcelResponse>('http://localhost:8080/api/data-generation/excel', {
        records: this.records
      })
      .pipe(finalize(() => (this.isGenerating = false)))
      .subscribe({
        next: (response) => {
          this.successMessage = `Excel created: ${response.filePath} (${response.generationTimeMs} ms)`;
        },
        error: (error) => {
          this.errorMessage = error?.error?.message ?? 'Failed to generate Excel file.';
        }
      });
  }
}

interface GenerateExcelResponse {
  fileName: string;
  filePath: string;
  recordsGenerated: number;
  generationTimeMs: number;
}
