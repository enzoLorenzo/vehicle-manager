import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Workshop} from "../models/workshop";
@Injectable({
  providedIn: 'root'
})
export class WorkshopApiService {

  constructor(private http: HttpClient) {
  }

  getClientWorkshop(): Observable<Workshop[]> {
    return this.http.get<Workshop[]>('/workshop');
  }

  delete(id: number) {
    return this.http.delete(`/workshop/${id}`);
  }

}
