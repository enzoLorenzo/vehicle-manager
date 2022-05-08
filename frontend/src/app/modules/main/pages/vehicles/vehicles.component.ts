import {Component, OnInit} from '@angular/core';
import {VehicleApiService} from "../../services/vehicle-api.service";
import {Vehicle} from "../../models/vehicle";
import {MatDialog} from "@angular/material/dialog";
import {DialogAddVehicleComponent} from "./dialog-add-vehicle/dialog-add-vehicle.component";


@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.scss']
})
export class VehiclesComponent implements OnInit {

  vehicles: Vehicle[] = [];

  constructor(private vehicleApiService: VehicleApiService, public dialog: MatDialog) {
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

  addVehicleDialog(): void {
    const dialogRef = this.dialog.open(DialogAddVehicleComponent, {
      width: '300px',
    })
      .afterClosed()
      .subscribe(() => this.getVehicles());
  }


}

