import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/home/services/auth.service';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  activeUser: User;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {}

  get isUserLoggedIn() {
    return !!this.authService.activeUser;
  }
  

  logOut() {
    this.authService.logOut();
  }

}
