import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Repair, RepairPost, RepairPut} from "../models/task";
import {AuthService, UserType} from "../../../core/services/auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class TaskApiService {

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  getRepairs(): Observable<Repair[]> {
    const url = this.authService.getUserType() === UserType.CLIENT ? '/task/client' : '/task/dealer' ;
    return this.http.get<Repair[]>(url);
  }

  deleteRepair(id: number){
    return this.http.delete(`/task/${id}`);
  }

  addRepair(repair: RepairPost): Observable<Object> {
    return this.http.post('/task', repair);
  }

  editClientRepair(repair: RepairPut): Observable<RepairPut> {
    return this.http.put<RepairPut>(`/task/${repair.id}`, repair);
  }

}
