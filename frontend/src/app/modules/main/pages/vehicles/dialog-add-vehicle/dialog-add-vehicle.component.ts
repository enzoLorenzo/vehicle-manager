import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {VehicleApiService} from "../../../services/vehicle-api.service";
import {VehiclePost, VehicleType} from "../../../models/vehicle";


@Component({
  selector: 'app-dialog-dog',
  templateUrl: './dialog-add-vehicle.component.html',
  styleUrls: ['./dialog-add-vehicle.component.scss']
})
export class DialogAddVehicleComponent implements OnInit {

  keys = Object.keys;
  VEHICLE_TYPE = VehicleType;

  vehicleFG: FormGroup = new FormGroup({
    brandFC: new FormControl('', Validators.required),
    engineCapacityFC: new FormControl('', Validators.required),
    generationFC: new FormControl('', Validators.required),
    horsePowerFC: new FormControl('', Validators.required),
    modelFC: new FormControl('', Validators.required),
    registrationFC: new FormControl('', Validators.required),
    typeFC: new FormControl(undefined, Validators.required),
    yearFC: new FormControl('', Validators.required)
  });

  constructor(
    public dialogRef: MatDialogRef<DialogAddVehicleComponent>,
    private vehicleApiService: VehicleApiService,
    public fb: FormBuilder
  ) {
  }

  submit(): void {

    const newVehicle: VehiclePost = {
      registration: this.vehicleFG.get("registrationFC")!.value,
      brand: this.vehicleFG.get("brandFC")!.value,
      model: this.vehicleFG.get("modelFC")!.value,
      generation: this.vehicleFG.get("generationFC")!.value,
      year: this.vehicleFG.get("yearFC")!.value,
      engineCapacity: this.vehicleFG.get("engineCapacityFC")!.value,
      horsePower: this.vehicleFG.get("horsePowerFC")!.value,
      type: this.vehicleFG.get("typeFC")!.value
    }

    this.vehicleApiService.addClientVehicle(newVehicle)
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
