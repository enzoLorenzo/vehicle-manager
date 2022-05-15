import {Component, OnInit} from '@angular/core';
import {RepairPost, TaskStatus} from "../../../models/task";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {FileService} from "../../../services/file.service";
import {switchMap} from "rxjs/operators";
import {Observable, tap} from "rxjs";
import {Workshop} from "../../../models/workshop";
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
  workshops: Workshop[] = [];

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

  constructor(
    public dialogRef: MatDialogRef<DialogAddRepairComponent>,
    private fileService: FileService,
    private workshopApiService: WorkshopApiService,
    private repairApiService: TaskApiService
  ) {

  }

  submit(): void {

    const newRepair: RepairPost = {
      description: this.repairFG.get("descriptionFC")!.value,
      startDate: this.repairFG.get("startDateFC")!.value,
      endDate: this.repairFG.get("endDateFC")!.value,
      taskStatus: TaskStatus.PENDING,
      vehicleId: this.repairFG.get("vehicleIdFC")!.value,
      workshopId: this.repairFG.get("workshopFC")!.value.id
    }

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
    this.getWorkshops();
  }

}
