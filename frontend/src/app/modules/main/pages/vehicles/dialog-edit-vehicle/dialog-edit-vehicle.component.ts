import {Component, Inject, OnInit} from '@angular/core';
import {Vehicle, VehicleType} from "../../../models/vehicle";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {VehicleApiService} from "../../../services/vehicle-api.service";

@Component({
  selector: 'app-dialog-edit-vehicle',
  templateUrl: './dialog-edit-vehicle.component.html',
  styleUrls: ['./dialog-edit-vehicle.component.scss']
})
export class DialogEditVehicleComponent implements OnInit {

  keys = Object.keys;
  VEHICLE_TYPE = VehicleType;

  yearFC = new FormControl(this.data.vehicle.year, Validators.compose(
    [Validators.min(1900), Validators.max(2022), Validators.required]));
  engineCapacityFC = new FormControl(this.data.vehicle.engineCapacity, Validators.compose(
    [Validators.min(0), Validators.required]));
  horsePowerFC = new FormControl(this.data.vehicle.horsePower, Validators.compose(
    [Validators.min(0), Validators.required]));

  vehicleFG: FormGroup = new FormGroup({
    brandFC: new FormControl(this.data.vehicle.brand, Validators.required),
    engineCapacityFC: this.engineCapacityFC,
    generationFC: new FormControl(this.data.vehicle.generation, Validators.required),
    horsePowerFC: this.horsePowerFC,
    modelFC: new FormControl(this.data.vehicle.model, Validators.required),
    registrationFC: new FormControl(this.data.vehicle.registration, Validators.required),
    typeFC: new FormControl(this.data.vehicle.type, Validators.required),
    yearFC: this.yearFC

  });

  constructor(
    public dialogRef: MatDialogRef<DialogEditVehicleComponent>,
    private vehicleApiService: VehicleApiService,
    public fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: { vehicle: Vehicle }
  ) {
  }

  submit(): void {

    const vehicle: Vehicle = {
      id: this.data.vehicle.id,
      registration: this.vehicleFG.get("registrationFC")!.value,
      brand: this.vehicleFG.get("brandFC")!.value,
      model: this.vehicleFG.get("modelFC")!.value,
      generation: this.vehicleFG.get("generationFC")!.value,
      year: this.vehicleFG.get("yearFC")!.value,
      engineCapacity: this.vehicleFG.get("engineCapacityFC")!.value,
      horsePower: this.vehicleFG.get("horsePowerFC")!.value,
      type: this.vehicleFG.get("typeFC")!.value,
      task: this.data.vehicle.task

    }

    this.vehicleApiService.editClientVehicle(vehicle)
      .subscribe({
        next: (response) => console.log(response),
        error: (error) => console.log(error),
      });

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

}
