import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Vehicle, VehiclePost} from "../models/vehicle";
@Injectable({
  providedIn: 'root'
})
export class VehicleApiService {

  constructor(private http: HttpClient) {
  }

  getClientVehicle(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>('/vehicle');
  }

  getClientSingleVehicle(id: number): Observable<Vehicle> {
    return this.http.get<Vehicle>(`/vehicle/${id}`);
  }

  delete(id: number) {
    return this.http.delete(`/vehicle/${id}`);
  }


  addClientVehicle(vehicle: VehiclePost): Observable<Vehicle> {
    return this.http.post<Vehicle>('/vehicle', vehicle);
  }

  editClientVehicle(vehicle: Vehicle ): Observable<Vehicle> {
    return this.http.put<Vehicle>(`/vehicle/${vehicle.id}`, vehicle )
  }
}
