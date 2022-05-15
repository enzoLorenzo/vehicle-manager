import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {RepairPost, TaskStatus} from "../../../models/task";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";

import {FileService} from "../../../services/file.service";

import {switchMap} from "rxjs/operators";
import {Observable, tap} from "rxjs";
import {Vehicle} from "../../../models/vehicle";
import {Workshop} from "../../../models/workshop";
import {VehicleApiService} from "../../../services/vehicle-api.service";
import {WorkshopApiService} from "../../../services/workshop-api.service";
import {TaskApiService} from "../../../services/task-api.service";

@Component({
  selector: 'app-dialog-add-repair',
  templateUrl: './dialog-add-repair.component.html',
  styleUrls: ['./dialog-add-repair.component.scss']
})
export class DialogAddRepairComponent implements OnInit {

  keys = Object.keys;
  startDate = new Date(2022, 5, 14);
  vehicles: Vehicle[] = [];
  workshops: Workshop[] = [];
  vehicle: Vehicle = {} as Vehicle;
  workshop: Workshop = {} as Workshop;



  private getVehicles() {
    this.vehicleApiService.getClientVehicle()
      .subscribe((vehicles: Vehicle[]) => {
        this.vehicles = vehicles;
        //console.log('inside' + this.vehicles.length);
      });
  }

  private getVehicle(id: number){
    this.vehicleApiService.getClientSingleVehicle(id)
      .subscribe((vehicle: Vehicle) => {
        //this.vehicles.push(vehicle);
        this.vehicle = vehicle;
        console.log('inside' + this.vehicle.registration);
      });
  }

  private getWorkshop(id: number){
    this.workshopApiService.getClientSingleWorkshop(id)
      .subscribe((workshop: Workshop) => {
        this.workshop = workshop;
        //console.log('inside' + this.workshop.name);
      });
  }

  private getWorkshops() {
    this.workshopApiService.getClientWorkshop()
      .subscribe((workshops: Workshop[]) => {
        this.workshops = workshops;
      });
  }

  repairFG: FormGroup = new FormGroup({
    descriptionFC: new FormControl('', Validators.required),
    workshopFC: new FormControl('', Validators.required),
    startDateFC: new FormControl('', Validators.required),
    endDateFC: new FormControl('', Validators.required),
    vehicleIdFC: new FormControl('', Validators.required)
  });

  selectedFiles?: FileList;

  @ViewChild("inputImage")
  inputImage!: ElementRef

  constructor(
    public dialogRef: MatDialogRef<DialogAddRepairComponent>,
    private fileService: FileService,
    private vehicleApiService: VehicleApiService,
    private workshopApiService: WorkshopApiService,
    private repairApiService: TaskApiService
  ) {

  }

  submit(): void {
    this.getVehicle(this.repairFG.get("vehicleIdFC")!.value)
    this.getWorkshop(1);
    //const data = Date(this.repairFG.get("startDateFG")!.value)
    //const data = Date.parse(this.repairFG.get("startDateFG")!.value)
    const data = new Date('2005-02-01')
    const newRepair: RepairPost = {

      description: this.repairFG.get("descriptionFC")!.value,
      startDate: this.repairFG.get("startDateFC")!.value,
      endDate: this.repairFG.get("endDateFC")!.value,
      taskStatus: TaskStatus.PENDING,
      vehicleId: this.repairFG.get("vehicleIdFC")!.value,
      workshopId: 1

    }
    //this.getVehicles();
    //this.getVehicle(1);


    console.log(this.repairFG.get('startDateFC')!.value);
    console.log(this.repairFG.get('endDateFC')!.value);
    // console.log(this.startDate);
    // console.log(data);
    console.log('outside' + this.vehicles.length);
    console.log(this.vehicle.registration)
    console.log(this.workshop.name)




    this.repairApiService.addClientRepair(newRepair).pipe(
      switchMap(value => {
        return new Observable();
      }),
      tap(() => this.dialogRef.close())
    ).subscribe();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    //this.getVehicles();
    this.getWorkshops();
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
