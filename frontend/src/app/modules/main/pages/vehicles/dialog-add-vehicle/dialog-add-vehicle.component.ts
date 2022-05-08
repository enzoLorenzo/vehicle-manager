import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {VehicleApiService} from "../../../services/vehicle-api.service";
import {VehiclePost, VehicleType} from "../../../models/vehicle";
import {FileService} from "../../../services/file.service";
import {Observable, tap} from "rxjs";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-dialog-dog',
  templateUrl: './dialog-add-vehicle.component.html',
  styleUrls: ['./dialog-add-vehicle.component.scss']
})
export class DialogAddVehicleComponent implements OnInit {

  keys = Object.keys;
  VEHICLE_TYPE = VehicleType;

  yearFC = new FormControl('', Validators.compose(
    [Validators.min(1900), Validators.max(2022), Validators.required]));
  engineCapacityFC = new FormControl('', Validators.compose(
    [Validators.min(0), Validators.required]));
  horsePowerFC = new FormControl('', Validators.compose(
    [Validators.min(0), Validators.required]));

  vehicleFG: FormGroup = new FormGroup({
    brandFC: new FormControl('', Validators.required),
    engineCapacityFC: this.engineCapacityFC,
    generationFC: new FormControl('', Validators.required),
    horsePowerFC: this.horsePowerFC,
    modelFC: new FormControl('', Validators.required),
    registrationFC: new FormControl('', Validators.required),
    typeFC: new FormControl(undefined, Validators.required),
    yearFC: this.yearFC,
    imageFC: new FormControl()
  });

  selectedFiles?: FileList;

  @ViewChild("inputImage")
  inputImage!: ElementRef

  constructor(
    public dialogRef: MatDialogRef<DialogAddVehicleComponent>,
    private vehicleApiService: VehicleApiService,
    private fileService: FileService
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


    this.vehicleApiService.addClientVehicle(newVehicle).pipe(
      switchMap(value => {
        if (!!this.selectedFiles && this.selectedFiles.length > 0) {
          return this.uploadImage(value)
        }
        return new Observable();
      }),
      tap(() => this.dialogRef.close())
    ).subscribe();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

  selectFile(event: any) {
    this.selectedFiles = event.target.files;
  }

  cleanInput() {
    this.inputImage.nativeElement.value = "";
    this.selectedFiles = undefined;
  }

  private uploadImage(id: number) {
    return this.fileService.onUpload(id, this.selectedFiles!.item(0)!)
  }
}
