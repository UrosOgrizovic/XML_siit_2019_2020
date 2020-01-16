import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private toastr: ToastrService) { }

  showSuccess(message: string, title: string, config: any = {}) {
    config.positionClass = 'toast-top-left';
    this.toastr.success(message, title, config)
  }

  showError(message: string, title: string, config: any = {}) {
    config.positionClass = 'toast-top-left';
    this.toastr.error(message, title, config);
  }
}