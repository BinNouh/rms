import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class FormService {
  private apiBaseUrl = 'http://localhost:8081/api/form';

  constructor(private httpClient: HttpClient) { }

    // define function to upload files
    upload(formData: FormData): Observable<HttpEvent<string[]>> {
      return this.httpClient.post<string[]>(`${this.apiBaseUrl}/upload`, formData, {
        reportProgress: true,
        observe: 'events'
      });
    }
  
    // define function to download files
    download(filename: string): Observable<HttpEvent<Blob>> {
      return this.httpClient.get(`${this.apiBaseUrl}/download/${filename}/`, {
        reportProgress: true,
        observe: 'events',
        responseType: 'blob'
      });
    }

    submitForm(formData: FormData): Observable<any> {
      return this.httpClient.post<any>(`${this.apiBaseUrl}/submit`, formData);
    }

  // You can also create methods for update and view by id
}


