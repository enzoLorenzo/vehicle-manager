import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Vehicle} from "../models/vehicle";
@Injectable({
  providedIn: 'root'
})
export class VehicleApiService {

  constructor(private http: HttpClient) {
  }

  getClientVehicle(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>('/vehicle');
  }

  delete(id: number) {
    return this.http.delete(`/vehicle/${id}`)
  }
}
