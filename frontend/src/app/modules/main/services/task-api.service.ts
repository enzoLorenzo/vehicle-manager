import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Repair} from "../models/task";

@Injectable({
  providedIn: 'root'
})
export class TaskApiService {

  constructor(private http: HttpClient) {
  }

  getRepairs(): Observable<Repair[]> {
    return this.http.get<Repair[]>('/task');
  }

  deleteRepair(id: number){
    return this.http.delete(`/task/${id}`);
  }
}
