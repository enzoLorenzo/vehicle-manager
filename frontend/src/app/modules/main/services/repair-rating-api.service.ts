import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RepairRating, RepairRatingPost} from "../models/repair_rating";
import {AuthService, UserType} from "../../../core/services/auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class RepairRatingApiService {

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  getRatings(): Observable<RepairRating[]> {
    return this.http.get<RepairRating[]>('/repair_rating');
  }

  deleteRepairRating(id: number){
    return this.http.delete(`/repair_rating/${id}`);
  }

  addRepairRating(repair_rating: RepairRatingPost): Observable<Object> {
    return this.http.post('/repair_rating', repair_rating);
  }

  editClientRepairRating(repair_rating: RepairRating): Observable<RepairRating> {
    return this.http.put<RepairRating>(`/repair_rating/${repair_rating.id}`, repair_rating);
  }

}
