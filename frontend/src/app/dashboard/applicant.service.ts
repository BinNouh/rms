import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApplicantService {
  private baseUrl = 'http://localhost:8081/api/dashboard';

  constructor(private http: HttpClient) {}

  getAllApplicants(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

  getApplicant(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  addApplicant(applicant: any): Observable<any> {
    return this.http.post(`${this.baseUrl}`, applicant);
  }
  
  updateApplicantStatus(id: number, status: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}/status?status=${status}`, {});
}
  filterApplicantsByStatus(status: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/filter-by-status`, {params: {status}});
  }

  filterApplicantsByGender(gender: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/filter-by-gender`, {params: {gender}});
  }

  deleteApplicant(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  // Add other methods as per the API endpoints
}
