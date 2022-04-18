import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VehicleApiService {

  constructor(private http: HttpClient) {
  }

  getClientVehicle(): Observable<Object> {
    return this.http.get('vehicle');
  }
}
