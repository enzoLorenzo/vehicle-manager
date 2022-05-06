import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {Workshop} from "../../../models/workshop";
import {WorkshopApiService} from "../../../services/workshop-api.service";

@Component({
  selector: 'app-workshop-card',
  templateUrl: './workshop-card.component.html',
  styleUrls: ['./workshop-card.component.scss']
})
export class WorkshopCardComponent implements OnInit {

  @Input()
  workshop!: Workshop;

  @Output()
  delete: EventEmitter<any> = new EventEmitter<any>();

  constructor(private workshopApiService: WorkshopApiService, public dialog: MatDialog) { }
  /*
  private getWorkshop() {
    this.workshopApiService.getClientSingleVehicle(this.workshop.id)
      .subscribe((workshop: Workshop) => {
        this.workshop = workshop;
      });

  }*/

  ngOnInit(): void {
  }

  deleteWorkshop() {
    this.delete.emit()
  }

}
