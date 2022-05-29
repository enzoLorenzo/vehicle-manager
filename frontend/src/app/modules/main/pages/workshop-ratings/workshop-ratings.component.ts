import { Component, OnInit } from '@angular/core';
import {AuthService, UserType} from "../../../../core/services/auth/auth.service";
import {MatTableDataSource} from "@angular/material/table";
import {Repair, TaskStatus} from "../../models/task";
import {TaskApiService} from "../../services/task-api.service";
import {MatDialog} from "@angular/material/dialog";
import {MatSelectChange} from "@angular/material/select";

interface ColumnConfig {
  fieldName: string;
  header: string;
}

interface RepairDS {
  id: number;
  description: string;
  workshop: string;
  vehicle: string;
  taskStatus: TaskStatus;
  rating: string;
  comment: string;
}

interface Filter {
  label: string;
  fieldName: string;
  options: string[];
  defaultValue: string;
}


@Component({
  selector: 'app-workshop-ratings',
  templateUrl: './workshop-ratings.component.html',
  styleUrls: ['./workshop-ratings.component.scss']
})
export class WorkshopRatingsComponent implements OnInit {

  userType: UserType;

  displayedColumns: ColumnConfig[] = [
    {fieldName: 'id', header: "REPAIR ID"},
    {fieldName: 'workshop', header: "WORKSHOP"},
    {fieldName: 'description', header: "REPAIR DESCRIPTION"},
    {fieldName: 'vehicle', header: "VEHICLE"},
    {fieldName: 'rating', header: 'RATING'},
    {fieldName: 'comment', header: 'COMMENT'}
  ];

  columnFieldName = this.displayedColumns.map(v => v.fieldName);
  dataSourceFilters = new MatTableDataSource<RepairDS>();
  defaultValue = "All";
  filterDictionary = new Map<string, string>();

  filters: Filter[] = [];
  repairs: Repair[] = [];
  USER_TYPE = UserType;

  constructor(private taskApi: TaskApiService,
              public dialog: MatDialog,
              private authService: AuthService) {
    this.userType = authService.getUserType();
  }

  ngOnInit(): void {
    this.getRepairList();
  }

  applyFilter(ob: MatSelectChange, filter: any) {
    this.filterDictionary.set(filter.fieldName, ob.value);
    this.dataSourceFilters.filter = JSON.stringify(Array.from(this.filterDictionary.entries()));
  }

  private getRepairsWithRatings(){
    this.repairs = this.repairs.filter(repair =>
        repair.rating != null
    )
  }


  private initFilterPredicate() {
    this.dataSourceFilters.filterPredicate = function (record, filter) {
      const map = new Map(JSON.parse(filter));
      let isMatch = false;
      for (let [key, value] of map) {
        isMatch = (value == "All") || (record[key as keyof RepairDS] == value);
        if (!isMatch) return false;
      }
      return isMatch;
    }
  }

  private getRepairList() {
    this.taskApi.getRepairs().subscribe((repairs: Repair[]) => {
      this.repairs = repairs;
      this.getRepairsWithRatings();
      const repairDS = this.convertToDataSource(this.repairs);
      this.dataSourceFilters = new MatTableDataSource(repairDS);
      this.generateFilters();
      this.initFilterPredicate();

    });
  }

  private convertToDataSource(repairs: Repair[]) {
    return repairs.map(repair => {
      return {
        id: repair.id,
        description: repair.description,
        workshop: this.getWorkshopName(repair),
        vehicle: this.getVehicleName(repair),
        taskStatus: repair.taskStatus,
        rating: repair.rating.rating + '/5',
        comment: repair.rating.comment
      } as RepairDS
    });
  }

  private getVehicleName(repair: Repair) {
    return repair.vehicle.brand + " " + repair.vehicle.model;
  }

  private getWorkshopName(repair: Repair) {
    return repair.workshop.name;
  }

  private generateFilters() {
    const workshopsNames: string[] = [this.defaultValue];
    const status: string[] = [this.defaultValue];

    this.repairs.forEach(repair => {
      workshopsNames.push(this.getWorkshopName(repair));
      status.push(repair.taskStatus)
    });
    const reduceWorkshopsNames = Array.from(new Set(workshopsNames));

    this.filters.push({label: "WORKSHOP", fieldName: 'workshop', options: reduceWorkshopsNames, defaultValue: this.defaultValue})
  }


}
