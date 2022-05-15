import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FileService} from "../../../services/file.service";
import {WorkshopApiService} from "../../../services/workshop-api.service";
import {TaskApiService} from "../../../services/task-api.service";
import {Repair, TaskStatus} from "../../../models/task";
import {switchMap} from "rxjs/operators";
import {Observable, tap} from "rxjs";

@Component({
  selector: 'app-dialog-edit-repair',
  templateUrl: './dialog-edit-repair.component.html',
  styleUrls: ['./dialog-edit-repair.component.scss']
})
export class DialogEditRepairComponent implements OnInit {

  keys = Object.keys;
  TASK_STATUS = TaskStatus;


  repairFG: FormGroup = new FormGroup({
    descriptionFC: new FormControl(this.data.repair.description, Validators.required),
    startDateFC: new FormControl(this.data.repair.startDate, Validators.required),
    endDateFC: new FormControl(this.data.repair.endDate, Validators.required),
    taskStatusFC: new FormControl(this.data.repair.taskStatus, Validators.required)
  });

  constructor(
    public dialogRef: MatDialogRef<DialogEditRepairComponent>,
    private fileService: FileService,
    private workshopApiService: WorkshopApiService,
    private repairApiService: TaskApiService,
    @Inject(MAT_DIALOG_DATA) public data: { repair: Repair }
  ) {

  }

  submit(): void {
    const newRepair: Repair = {
      id: this.data.repair.id,
      description: this.repairFG.get("descriptionFC")!.value,
      startDate: this.repairFG.get("startDateFC")!.value,
      endDate: this.repairFG.get("endDateFC")!.value,
      taskStatus: this.repairFG.get("taskStatusFC")!.value,
      vehicle: this.data.repair.vehicle,
      workshop: this.data.repair.workshop
    }

    this.repairApiService.editClientRepair(newRepair).pipe(
      switchMap(value => {
        return new Observable();
      }),
      tap(() => this.dialogRef.close())
    ).subscribe();
    this.dialogRef.close();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

}
