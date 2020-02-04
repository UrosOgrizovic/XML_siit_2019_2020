import { Injectable } from '@angular/core';
import { Response } from "@angular/http";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { of } from 'rxjs';
import { BaseService } from '../../shared/services/base.service';
import { Review } from '../../models/review.model';
import { ReviewAssignment } from 'src/app/models/review-assignment.model';

const ENDPOINTS = {
  CREATE: `/reviews`,
  MERGE: (id) => `/reviews/${id}/merge`,
  ALL_ASSIGNMENTS: (username) => `/reviews/${username}/assignments`,
  ACCEPT_ASSIGNMENT: (id, username) => `/reviews/${id}/accept-assign/${username}`,
  DECLINE_ASSIGNMENT: (id, username) => `/reviews/${id}/decline-assign/${username}`,
  GET_REVIEWS_HTML: (id) => `/reviews/getReviewsHtml/${id}`
};


@Injectable({
  providedIn: 'root'
})
export class ReviewsService extends BaseService {
  reviews: Review[] = [];
  assignments: ReviewAssignment[] = [];

  constructor(private http: HttpClient) {
    super();
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

  merge(id: number) {
    return this.http.post(`${this.baseUrl}${ENDPOINTS.MERGE(id)}`, {})
      .pipe(
        map((res: any) => {
          let response = res;
          console.log(response);
        })
      )
  }

  getAllAssignments(username: string) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')

    return this.http.get(`${this.baseUrl}${ENDPOINTS.ALL_ASSIGNMENTS(username)}`, { headers })
      .pipe(
        map((res: any) => {
          if (res) {
            this.assignments = res.map((assignment: ReviewAssignment) => new ReviewAssignment().deserialize(assignment))
          }
          return this.assignments
        })
      )
  }

  acceptAssignment(id: number, username: string) {
    return this.http.post(`${this.baseUrl}${ENDPOINTS.ACCEPT_ASSIGNMENT(id, username)}`, {})
      .pipe(
        map((res: any) => {
          let response = res;
          console.log(response);
        })
      )
  }

  declineAssignment(id: number, username: string) {
    return this.http.post(`${this.baseUrl}${ENDPOINTS.DECLINE_ASSIGNMENT(id, username)}`, {})
      .pipe(
        map((res: any) => {
          let response = res;
          console.log(response);
        })
      )
  }

  viewMergedReviews(documentId: number) {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.GET_REVIEWS_HTML(documentId)}`, {responseType: 'text'})
    .pipe(map((res: any) => {
      return res;
    }));
  }
  
}
