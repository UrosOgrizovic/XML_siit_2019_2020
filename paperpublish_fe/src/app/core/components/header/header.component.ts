import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/home/services/auth.service';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {


  constructor(private authService: AuthService) {
  }

  ngOnInit() {}

  get isUserLoggedIn() {
    return !!this.authService.activeUser;
  }

  get userType() {
    return this.authService.activeUser && this.authService.activeUser.roles.role[0]
  }
  

  logOut() {
    this.authService.logOut();
  }

}
