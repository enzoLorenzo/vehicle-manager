import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {WorkshopApiService} from "../../../services/workshop-api.service";
import {WorkshopPost} from "../../../models/workshop";

@Component({
  selector: 'app-dialog-add-workshop',
  templateUrl: './dialog-add-workshop.component.html',
  styleUrls: ['./dialog-add-workshop.component.scss']
})
export class DialogAddWorkshopComponent implements OnInit {

  keys = Object.keys;


  workshopFG: FormGroup = new FormGroup({
    nameFC: new FormControl('', Validators.required),
    addressFC: new FormControl('', Validators.required),
    descriptionFC: new FormControl('', Validators.required)


  });

  constructor(
    public dialogRef: MatDialogRef<DialogAddWorkshopComponent>,
    private workshopApiService: WorkshopApiService,
    public fb: FormBuilder
  ) {
  }

  submit(): void {

    const newWorkshop: WorkshopPost = {
      name: this.workshopFG.get("nameFC")!.value,
      address: this.workshopFG.get("addressFC")!.value,
      description: this.workshopFG.get("descriptionFC")!.value,


    }

    this.workshopApiService.addClientWorkshop(newWorkshop)
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
