import { Component } from '@angular/core';
import { AiRequestService } from './services/ai-request.service';

interface Message {
  type: string;
  message: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  messages: Message[] = [];
  newMessage: string = '';
  title = 'rag-front';
  cargando = false;

  constructor(private requestService: AiRequestService) {}

  sendMessage() {
    if (this.newMessage == '') return;
    this.cargando = true;
    let msg = { message: this.newMessage, type: 'user' };
    this.messages.push(msg);
    this.requestService.sendMessage(this.newMessage).subscribe(
      (response: any) => {
        let responseMsg = { message: response.message, type: 'ai' };
        this.messages.push(responseMsg);
        this.cargando = false;
      },
      (error) => {
        this.messages.push(error.toString());
      }
    );
    this.newMessage = '';
  }

  clean() {
    this.messages = [];
  }
}
