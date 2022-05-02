import {Component, OnInit, Inject} from '@angular/core';
import {VehicleApiService} from "../../services/vehicle-api.service";
import {Vehicle} from "../../models/vehicle";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import {DialogDogComponent} from "./dialog-dog/dialog-dog.component";


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

  addVehicle(): void {
    const dialogRef = this.dialog.open(DialogDogComponent, {
      width: '250px',
    });
    //console.log("new");

  }


}
export interface DialogData {
  animal: string;
  name: string;
}

