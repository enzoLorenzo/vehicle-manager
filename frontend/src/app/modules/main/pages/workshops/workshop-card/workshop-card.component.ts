import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {Workshop} from "../../../models/workshop";
import {WorkshopApiService} from "../../../services/workshop-api.service";
import {DialogEditWorkshopComponent} from "../../workshops/dialog-edit-workshop/dialog-edit-workshop.component";

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

  private getWorkshop() {
    this.workshopApiService.getClientSingleWorkshop(this.workshop.id)
      .subscribe((workshop: Workshop) => {
        this.workshop = workshop;
      });

  }
  editWorkshopDialog(): void {
    const dialogRef = this.dialog.open(DialogEditWorkshopComponent, {
      width: '250px',
      data: {
        workshop: this.workshop
      }
    })
      .afterClosed()
      .subscribe(() => this.getWorkshop());
  }

  ngOnInit(): void {
  }

  deleteWorkshop() {
    this.delete.emit()
  }

}
