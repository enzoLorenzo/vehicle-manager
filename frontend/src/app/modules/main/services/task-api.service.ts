import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Repair, RepairPost} from "../models/task";
import {Workshop, WorkshopPost} from "../models/workshop";

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

  addClientRepair(repair: RepairPost): Observable<Repair> {
    return this.http.post<Repair>('/task', repair);
  }

}
