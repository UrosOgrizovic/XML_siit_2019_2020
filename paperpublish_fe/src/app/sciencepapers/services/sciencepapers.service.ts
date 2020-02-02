import { Injectable } from '@angular/core';
import { Response } from "@angular/http";
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { of } from 'rxjs';
import { BaseService } from '../../shared/services/base.service';
import { SciencePaper } from '../../models/science-paper.model';
import { User} from 'src/app/models/user.model';

const ENDPOINTS = {
  GET_ALL: `/sciencepapers/findall`,
  GET_ALL_ACCEPTED: `/sciencepapers/accepted`,
  GET_HTML: (id: number) => `/sciencepapers/getHTML/${id}`,
  GET_PDF: (id: number) => `/sciencepapers/getPDF/${id}`,
  GET_ONE: (id) => `/sciencepapers/${id}`,
  GET_BY_AUTHOR_USERNAME: (username) => `/sciencepapers/getByAuthorUsername/${username}`,
  GET_FOR_REVIEW: (username) => `/sciencepapers/${username}/for-review`,
  CREATE: `/sciencepapers`,
  UPDATE: (id) => `/sciencepapers`,
  DELETE: (id) => `/sciencepapers/${id}`,
  PROPOSALS: (id) => `/sciencepapers/${id}/proposals`,
  ADD_REVIEWER: (id: number, username: string) => `/sciencepapers/${id}/reviewers/${username}`,
  CHANGE_STATUS: (id: number, status: string) => `/sciencepapers/${id}/status/${status}`,
  SEARCH_BY_METADATA: (keywords: string, paperPublishDate: string, authorUserName: string, searchOnlyMyPapers: boolean) =>
  `/sciencepapers/searchByMetadata?keywords=${keywords}&paperPublishDate=${paperPublishDate}&authorUserName=${authorUserName}&searchOnlyMyPapers=${searchOnlyMyPapers}`,
  SEARCH_BY_TEXT: (text: string, authorUserName: string, searchOnlyMyPapers: boolean) =>
  `/sciencepapers/searchByText?text=${text}&authorUserName=${authorUserName}&searchOnlyMyPapers=${searchOnlyMyPapers}`,
  PERFORM_SEARCH: (keywords: string, paperPublishDate: string, text: string, authorUserName: string, searchOnlyMyPapers: boolean) =>
  `/sciencepapers/performSearch?keywords=${keywords}&paperPublishDate=${paperPublishDate}&text=${text}&authorUserName=${authorUserName}&searchOnlyMyPapers=${searchOnlyMyPapers}`
};


@Injectable({
  providedIn: 'root'
})
export class SciencePapersService extends BaseService {
  sciencePapers: SciencePaper[] = [];
  sciencePapersOfAuthor: SciencePaper[] = [];
  proposals: User[] = [];

  constructor(private http: HttpClient) {
    super();
  }

  getAll() : Observable<any> {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.GET_ALL}`)
      .pipe(
        map((res: any) => {
          let response = res;
          if (response) {
            this.sciencePapers = response.map((sciencePaper: SciencePaper) => new SciencePaper().deserialize(sciencePaper))
          }
          return this.sciencePapers
        })
      )
  }

  getAllAccepted() : Observable<any> {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.GET_ALL_ACCEPTED}`)
      .pipe(
        map((res: any) => {
          let response = res;
          if (response) {
            this.sciencePapers = response.map((sciencePaper: SciencePaper) => new SciencePaper().deserialize(sciencePaper))
          }
          return this.sciencePapers
        })
      )
  }

  getForReview(username: string) : Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')
    return this.http.get(`${this.baseUrl}${ENDPOINTS.GET_FOR_REVIEW(username)}`, { headers })
      .pipe(
        map((res: any) => {
          let response = res;
          if (response) {
            this.sciencePapers = response.map((sciencePaper: SciencePaper) => new SciencePaper().deserialize(sciencePaper));
          }
          return this.sciencePapers;
        })
      )
  }

  addReviewer(id: any, username: string): Observable<any> {
    return this.http.post(`${this.baseUrl}${ENDPOINTS.ADD_REVIEWER(id, username)}`, {});
  }

  getProposals(id: number) : Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')
    return this.http.get(`${this.baseUrl}${ENDPOINTS.PROPOSALS(id)}`, { headers })
      .pipe(
        map((res: any) => {
          let response = res;
          if (response) {
            this.proposals = response.map((user: User) => new User().deserialize(user))
          }
          return this.proposals
        })
      )
  }


  getByAuthorUsername(username: string): Observable<any> {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.GET_BY_AUTHOR_USERNAME(username)}`)
      .pipe(
        map((res: any) => {
          let response = res;
          if (response) {
            this.sciencePapersOfAuthor = response.map((sciencePaper: SciencePaper) => new SciencePaper().deserialize(sciencePaper))
          }
          return this.sciencePapersOfAuthor;
        })
      )
  }

  getOne(id: number) {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.GET_ONE(id)}`, {responseType: 'text'})
      .pipe(
        map((res: any) => {
          console.log(res.toString())
          return res;
        })
      )
  }

  changeStatus(id: any, status: string) {
    return this.http.put(`${this.baseUrl}${ENDPOINTS.CHANGE_STATUS(id, status)}`, {})
      .pipe(
        map((res: any) => {
          console.log(res)
          return res;
        })
      )
  }

  delete(id: number) {
    return this.http.delete(`${this.baseUrl}${ENDPOINTS.DELETE(id)}`)
      .pipe(
        map((res: any) => {
          this.sciencePapers = this.sciencePapers.filter((paper: SciencePaper) => {
            return paper.documentId != id;
          });
          return this.sciencePapers
        })
      )
  }

  create(data: any) {
    return this.http.post(`${this.baseUrl}${ENDPOINTS.CREATE}`, data)
      .pipe(
        map((res: any) => {
          console.log(res);
        })
      )
  }

  update(id, data: any) {
    return this.http.put(`${this.baseUrl}${ENDPOINTS.UPDATE(id)}`, data)
      .pipe(
        map((res: any) => {
          console.log(res)
        })
      )
  }

  getHTML(id: number, resType: string) {

    if (resType === 'blob') {
      return this.http.get(`${this.baseUrl}${ENDPOINTS.GET_HTML(id)}`, {responseType: 'blob'}).pipe(
        map((res: any) => {
          return res;
        })
      );
    } else if (resType === 'text') {
      return this.http.get(`${this.baseUrl}${ENDPOINTS.GET_HTML(id)}`, {responseType: 'text'}).pipe(
        map((res: any) => {
          return res;
        })
      );
    }
  }

  getPDF(id: number) {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.GET_PDF(id)}`, {responseType: 'blob'}).pipe(
      map((res: any) => {
        return res;
      })
    );
  }


  searchByMetadata(keywords: string, paperPublishDate: string, authorUserName: string, searchOnlyMyPapers: boolean) {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.SEARCH_BY_METADATA(keywords, paperPublishDate, authorUserName, searchOnlyMyPapers)}`)
    .pipe(
      map((res: any) => {
        return res;
      }));
  }

  searchByText(text: string, authorUserName: string, searchOnlyMyPapers: boolean) {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.SEARCH_BY_TEXT(text, authorUserName, searchOnlyMyPapers)}`).pipe(
      map((res: any) => {
        return res;
      }));
  }

  performSearch(keywords: string, paperPublishDate: string, text: string, authorUserName: string, searchOnlyMyPapers: boolean) {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.PERFORM_SEARCH(keywords, paperPublishDate, text, authorUserName, searchOnlyMyPapers)}`)
    .pipe(
      map((res: any) => {
        return res;
      }));
  }

}
