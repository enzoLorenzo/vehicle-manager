import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PriceListPosition} from "../models/workshop";

@Injectable({
  providedIn: 'root'
})
export class PriceListApiService {

  constructor(private http: HttpClient) {
  }

  getPriceListByWorkshopId(id: number) {
    return this.http.get<PriceListPosition[]>(`/workshop/price-list/${id}`);
  }

  updatePriceList(workshopId: number, priceList: PriceListPosition[]) {
    return this.http.put<PriceListPosition[]>(`/workshop/${workshopId}/price-list/`, priceList);
  }
}
