import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {RepairPost, TaskStatus} from "../../../models/task";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";

import {FileService} from "../../../services/file.service";

import {switchMap} from "rxjs/operators";
import {Observable, tap} from "rxjs";
import {Vehicle} from "../../../models/vehicle";
import {Workshop} from "../../../models/workshop";

@Component({
  selector: 'app-dialog-add-repair',
  templateUrl: './dialog-add-repair.component.html',
  styleUrls: ['./dialog-add-repair.component.scss']
})
export class DialogAddRepairComponent implements OnInit {

  keys = Object.keys;
  startDate = new Date(2022, 5, 14);


  repairFG: FormGroup = new FormGroup({
    descriptionFC: new FormControl('', Validators.required),
    // vehicleFC: new FormControl('', Validators.required),
    // workshopFC: new FormControl('', Validators.required),
    startDateFC: new FormControl('', Validators.required),
    endDateFC: new FormControl('', Validators.required),
    // taskStatusFC: new FormControl('', Validators.required)
  });

  selectedFiles?: FileList;

  @ViewChild("inputImage")
  inputImage!: ElementRef

  constructor(
    public dialogRef: MatDialogRef<DialogAddRepairComponent>,

    private fileService: FileService
  ) {

  }

  submit(): void {

    const newRepair: RepairPost = {

      description: this.repairFG.get("descriptionFC")!.value,
      startDate: this.repairFG.get("startDateFG")!.value,
      endDate: this.repairFG.get("endDateFG")!.value,
      taskStatus: TaskStatus.PENDING
      /*

      vehicle: Vehicle;
      workshop: Workshop;*/

    }

    console.log(this.repairFG.get('startDateFC')!.value);


    // this.repairApiService.addClientRepair(newRepair).pipe(
    //   switchMap(value => {
    //     return new Observable();
    //   }),
    //   tap(() => this.dialogRef.close())
    // ).subscribe();
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
