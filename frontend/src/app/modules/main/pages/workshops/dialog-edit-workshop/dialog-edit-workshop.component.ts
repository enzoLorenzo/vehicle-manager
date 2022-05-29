import {Component, Inject, OnInit} from '@angular/core';
import {Workshop} from "../../../models/workshop";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {WorkshopApiService} from "../../../services/workshop-api.service";

@Component({
  selector: 'app-dialog-edit-workshop',
  templateUrl: './dialog-edit-workshop.component.html',
  styleUrls: ['./dialog-edit-workshop.component.scss']
})
export class DialogEditWorkshopComponent implements OnInit {

  keys = Object.keys;


  workshopFG: FormGroup = new FormGroup({
    nameFC: new FormControl(this.data.workshop.name, Validators.required),
    addressFC: new FormControl(this.data.workshop.address, Validators.required),
    descriptionFC: new FormControl(this.data.workshop.description, Validators.required),

  });

  constructor(
    public dialogRef: MatDialogRef<DialogEditWorkshopComponent>,
    private workshopApiService: WorkshopApiService,
    public fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: { workshop: Workshop }
  ) {
  }

  submit(): void {

    const workshop = {
      id: this.data.workshop.id,
      name: this.workshopFG.get("nameFC")!.value,
      address: this.workshopFG.get("addressFC")!.value,
      description: this.workshopFG.get("descriptionFC")!.value
    }

    this.workshopApiService.editClientWorkshop(workshop)
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
