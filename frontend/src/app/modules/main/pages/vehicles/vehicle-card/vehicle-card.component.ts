import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Vehicle} from "../../../models/vehicle";
import {MatDialog} from "@angular/material/dialog";
import {DialogEditVehicleComponent} from "../dialog-edit-vehicle/dialog-edit-vehicle.component";
import {VehicleApiService} from "../../../services/vehicle-api.service";

@Component({
  selector: 'app-vehicle-card',
  templateUrl: './vehicle-card.component.html',
  styleUrls: ['./vehicle-card.component.scss']
})
export class VehicleCardComponent implements OnInit {

  @Input()
  vehicle!: Vehicle;

  @Output()
  delete: EventEmitter<any> = new EventEmitter<any>();

  constructor(private vehicleApiService: VehicleApiService, public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  editVehicleDialog(): void {
    const dialogRef = this.dialog.open(DialogEditVehicleComponent, {
      width: '250px',
      data: {
        vehicle: this.vehicle
      }
    })
      .afterClosed()
      .subscribe(() => this.getVehicle());
  }

  private getVehicle() {
    this.vehicleApiService.getClientSingleVehicle(this.vehicle.id)
      .subscribe((vehicle: Vehicle) => {
        this.vehicle = vehicle;
      });

  }

  deleteVehicle() {
    this.delete.emit()
  }
}
