import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AiRequestService {
  private apiURL = 'http://localhost:8080/chat';

  constructor(private http: HttpClient) {}

  sendMessage(message: string) {
    return this.http.post(this.apiURL, { message });
  }
}
