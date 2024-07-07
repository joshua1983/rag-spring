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

  constructor(private requestService: AiRequestService) {}

  sendMessage() {
    if (this.newMessage == '') return;
    let msg = { message: this.newMessage, type: 'user' };
    this.messages.push(msg);
    this.requestService.sendMessage(this.newMessage).subscribe(
      (response: any) => {
        let responseMsg = { message: response.message, type: 'ai' };
        this.messages.push(responseMsg);
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
