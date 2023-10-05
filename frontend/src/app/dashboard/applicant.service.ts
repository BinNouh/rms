import { HttpClient, HttpParams } from '@angular/common/http';
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

  filterApplicants(gender?: string, submissionStatus?: string): Observable<any> {
    let params = new HttpParams();
    if (gender) {
      params = params.append('gender', gender);
    }
    if (submissionStatus) {
      params = params.append('submissionStatus', submissionStatus);
    }
    return this.http.get(`${this.baseUrl}/filter`, { params: params });
  }

  deleteApplicant(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  // Add other methods as per the API endpoints
}
