import { Component, OnInit } from '@angular/core';
import {Workshop} from "../../models/workshop";
import {WorkshopApiService} from "../../services/workshop-api.service";
import {MatDialog} from "@angular/material/dialog";
//import {DialogAddWorkshopComponent} from "../workshops/dialog-add-workshop/dialog-add-workshop.component";

@Component({
  selector: 'app-workshops',
  templateUrl: './workshops.component.html',
  styleUrls: ['./workshops.component.scss']
})
export class WorkshopsComponent implements OnInit {

  workshops: Workshop[] = [];

  constructor(private workshopApiService: WorkshopApiService, public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.getWorkshops();
  }

  private getWorkshops() {
    this.workshopApiService.getClientWorkshop()
      .subscribe((workshops: Workshop[]) => {
        this.workshops = workshops;
      });

  }

  deleteWorkshop(id: number) {
    this.workshopApiService.delete(id)
      .subscribe(() => this.getWorkshops());
  }
  /*
  addWorkshopDialog(): void {
    const dialogRef = this.dialog.open(DialogAddWorkshopComponent, {
      width: '250px',
    })
      .afterClosed()
      .subscribe(() => this.getWorkshops());
  }*/
}
