import { Injectable } from '@angular/core';
import { Response } from "@angular/http";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { of } from 'rxjs';
import { BaseService } from '../../shared/services/base.service';
import { Review } from '../../models/review.model';

const ENDPOINTS = {
  CREATE: `/reviews`
}


@Injectable({
  providedIn: 'root'
})
export class ReviewsService extends BaseService {
  reviews: Review[] = [];

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
