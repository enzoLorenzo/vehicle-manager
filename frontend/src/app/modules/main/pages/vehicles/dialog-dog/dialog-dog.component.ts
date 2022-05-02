import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../vehicles.component";
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {VehicleApiService} from "../../../services/vehicle-api.service";
import {Vehicle, VehiclePost, VehicleType} from "../../../models/vehicle";


@Component({
  selector: 'app-dialog-dog',
  templateUrl: './dialog-dog.component.html',
  styleUrls: ['./dialog-dog.component.scss']
})
export class DialogDogComponent implements OnInit {
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
  name = new FormControl('');
  constructor(
    public dialogRef: MatDialogRef<DialogDogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private vehicleApiService: VehicleApiService,
    public fb: FormBuilder

  ) {

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

  updateName() {
    this.name.setValue('Nancy');
  }

  submit(): void {
    const newVehicle: VehiclePost = {
      registration: "abba",
      brand: this.vehicleFG.get("brandFC")!.value,
      model: "abba",
      generation: "",
      year: "2341",
      engineCapacity: this.vehicleFG.get("engineCapacityFC")!.value,
      horsePower: "345",
      type: VehicleType.CAR,



    }
    this.vehicleApiService.addClientVehicle(newVehicle)
      .subscribe({
        next: (response) => console.log(response),
        error: (error) => console.log(error),
      });
  }


}
