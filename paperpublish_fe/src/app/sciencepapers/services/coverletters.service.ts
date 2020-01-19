import { Injectable } from '@angular/core';
import { Response } from "@angular/http";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { of } from 'rxjs';
import { BaseService } from '../../shared/services/base.service';
import { CoverLetter } from '../../models/cover-letter.model';

const ENDPOINTS = {
  CREATE: `/coverletters`
}


@Injectable({
  providedIn: 'root'
})
export class CoverLettersService extends BaseService {
  coverLetters: CoverLetter[] = [];

  constructor(private http: HttpClient) {
    super();
  }


  getOne(id: number) {
    // TO-DO: Implement
  }

  delete(id: number) {
    // TO-DO: Implement
  }

  create(data: any) {
    return this.http.post(`${this.baseUrl}${ENDPOINTS.CREATE}`, data)
      .pipe(
        map((res: any) => {
          let response = res;
          console.log(response);
        })
      )
  }
  
}
