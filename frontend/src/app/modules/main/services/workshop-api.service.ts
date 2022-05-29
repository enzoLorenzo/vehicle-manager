import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Workshop, WorkshopPost} from "../models/workshop";
import {Vehicle} from "../models/vehicle";

@Injectable({
  providedIn: 'root'
})
export class WorkshopApiService {

  constructor(private http: HttpClient) {
  }

  getClientWorkshop(): Observable<Workshop[]> {
    return this.http.get<Workshop[]>('/workshop');
  }

  getAllWorkshop(): Observable<Workshop[]> {
    return this.http.get<Workshop[]>('/workshop/all');
  }

  delete(id: number) {
    return this.http.delete(`/workshop/${id}`);
  }

  addClientWorkshop(workshop: WorkshopPost): Observable<Workshop> {
    return this.http.post<Workshop>('/workshop', workshop);
  }

  editClientWorkshop(workshop: any ): Observable<Workshop> {
    return this.http.put<Workshop>(`/workshop/${workshop.id}`, workshop )
  }

  getClientSingleWorkshop(id: number): Observable<Workshop> {
    return this.http.get<Workshop>(`/workshop/${id}`);
  }

}
