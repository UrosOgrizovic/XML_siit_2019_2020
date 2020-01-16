import { Injectable } from '@angular/core';
import { BaseService } from '../../shared/services/base.service';
import { User } from '../../models/user.model';
import { Http, Response } from "@angular/http";
import { LoginModel } from '../models/login.model';
import { RegisterModel } from '../models/register.model';
import { map } from 'rxjs/operators';
import {Router} from '@angular/router';

const ENDPOINTS = {
  LOGIN: '/auth/login',
  REGISTER: '/auth/register',
  LOGOUT: '/auth/logout'
}

@Injectable({
  providedIn: 'root'
})
export class AuthService extends BaseService {
  activeUser: User;

  constructor(private http: Http, private router: Router) {
    super();
    this.activeUser = JSON.parse( localStorage.getItem('user'));
  }

  login(userData: LoginModel) : void {
    this.http.post(`${this.baseUrl}${ENDPOINTS.LOGIN}`, userData)
      .pipe(
        map((res: Response) =>  new User().deserialize(res.json()))
      ).subscribe((user: User) => {
        this.activeUser = user;
        localStorage.setItem('user',JSON.stringify(user));
        this.router.navigateByUrl('/');
      });
  }

  register(userData: RegisterModel) : void {
    this.http.post(`${this.baseUrl}${ENDPOINTS.REGISTER}`, userData)
      .subscribe(() => {
        this.router.navigateByUrl('/login');
      });
  }

  logOut(): void {
    this.http.post(`${this.baseUrl}${ENDPOINTS.LOGOUT}`, {})
      .subscribe(() => {
        this.activeUser = null;
        localStorage.removeItem('user');
        this.router.navigateByUrl('/');
      });
  }


}
