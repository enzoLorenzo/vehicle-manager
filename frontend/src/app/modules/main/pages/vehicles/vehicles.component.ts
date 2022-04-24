import {Component, OnInit} from '@angular/core';
import {VehicleApiService} from "../../services/vehicle-api.service";
import {Vehicle} from "../../models/vehicle";

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.scss']
})
export class VehiclesComponent implements OnInit {

  vehicles: Vehicle[] = [];

  constructor(private vehicleApiService: VehicleApiService) {
  }

  ngOnInit(): void {
    this.getVehicles();
  }

  private getVehicles() {
    this.vehicleApiService.getClientVehicle()
      .subscribe((vehicles: Vehicle[]) => {
        this.vehicles = vehicles;
      });

  }

  deleteVehicle(id: number) {
    this.vehicleApiService.delete(id)
      .subscribe(() => this.getVehicles());
  }
}
