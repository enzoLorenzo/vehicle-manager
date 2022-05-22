import {Component, Inject, OnInit} from '@angular/core';
import {Repair, RepairPost, TaskStatus} from "../../../models/task";
import {RepairRatingPost} from "../../../models/repair_rating";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TaskApiService} from "../../../services/task-api.service";
import {RepairRatingApiService} from "../../../services/repair-rating-api.service";
import {DialogEditRepairComponent} from "../dialog-edit-repair/dialog-edit-repair.component";
import {switchMap} from "rxjs/operators";
import {Observable, tap} from "rxjs";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-dialog-rate-repair',
  templateUrl: './dialog-rate-repair.component.html',
  styleUrls: ['./dialog-rate-repair.component.scss']
})
export class DialogRateRepairComponent implements OnInit {
  starRating = 0;

  repairRatingFG: FormGroup = new FormGroup({
    commentFC: new FormControl('', Validators.required)
  });


  constructor( public dialogRef: MatDialogRef<DialogEditRepairComponent>,
               private repairRatingApiService: RepairRatingApiService,
              @Inject(MAT_DIALOG_DATA) public data: { repair: Repair }) { }

  ngOnInit(): void {
  }

  submit(): void {

    const newRepairRating: RepairRatingPost = {
      rating: this.starRating,
      comment: this.repairRatingFG.get("commentFC")!.value,
      taskId: this.data.repair.id
    }
    console.log(newRepairRating.rating);
    console.log(newRepairRating.taskId);
    this.repairRatingApiService.addRepairRating(newRepairRating).pipe(
      switchMap(value => {
        return new Observable();
      }),
      tap(() => this.dialogRef.close())
    ).subscribe();
    this.dialogRef.close();
  }

}
