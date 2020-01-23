import { Injectable } from '@angular/core';
import { Response } from "@angular/http";
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { of } from 'rxjs';
import { BaseService } from '../../shared/services/base.service';
import { SciencePaper } from '../../models/science-paper.model';

const ENDPOINTS = {
  GET_ALL: `/sciencepapers/findall`,
  CREATE: `/sciencepapers`,

  DOWNLOAD_HTML: (id: number) => `/sciencepapers/downloadHTML/${id}`,
  DOWNLOAD_PDF: (id: number) => `/sciencepapers/downloadPDF/${id}`,
  DELETE: (id) => `/sciencepapers/${id}`,
  GET_ONE: (id) => `/sciencepapers/${id}`,
  UPDATE: (id) => `/sciencepapers`
}


@Injectable({
  providedIn: 'root'
})
export class SciencePapersService extends BaseService {
  sciencePapers: SciencePaper[] = [];

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

  getOne(id: number) {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.GET_ONE(id)}`, {responseType: 'text'})
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

  downloadHTML(id: number) {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.DOWNLOAD_HTML(id)}`, {responseType: 'blob'}).pipe(
      map((res: any) => {
        return res;
      })
    );
  }

  downloadPDF(id: number) {
    return this.http.get(`${this.baseUrl}${ENDPOINTS.DOWNLOAD_PDF(id)}`, {responseType: 'blob'}).pipe(
      map((res: any) => {
        return res;
      })
    );
  }
  
}
