import {Component, OnInit} from '@angular/core';
import {TaskApiService} from "../../services/task-api.service";
import {Repair, TaskStatus} from "../../models/task";
import {MatTableDataSource} from "@angular/material/table";
import {MatSelectChange} from "@angular/material/select";
import {MatDialog} from "@angular/material/dialog";
import {DialogAddRepairComponent} from "./dialog-add-repair/dialog-add-repair.component";
import {AuthService, UserType} from "../../../../core/services/auth/auth.service";
import {DialogEditRepairComponent} from "./dialog-edit-repair/dialog-edit-repair.component";
import {DialogRateRepairComponent} from "./dialog-rate-repair/dialog-rate-repair.component";

interface ColumnConfig {
  fieldName: string;
  header: string;
}

interface RepairDS {
  id: number;
  description: string;
  startDate: Date;
  endDate: Date;
  vehicle: string;
  workshop: string;
  taskStatus: TaskStatus;
}

interface Filter {
  label: string;
  fieldName: string;
  options: string[];
  defaultValue: string;
}

@Component({
  selector: 'app-repairs',
  templateUrl: './repairs.component.html',
  styleUrls: ['./repairs.component.scss']
})
export class RepairsComponent implements OnInit {

  userType: UserType;

  displayedColumns: ColumnConfig[] = [
    {fieldName: 'id', header: "ID"},
    {fieldName: 'description', header: "DESCRIPTION"},
    {fieldName: 'vehicle', header: "VEHICLE"},
    {fieldName: 'workshop', header: "WORKSHOP"},
    {fieldName: 'startDate', header: "START"},
    {fieldName: 'endDate', header: "END"},
    {fieldName: 'taskStatus', header: "STATUS"},
    {fieldName: 'action', header: "ACTION"}
  ];

  columnFieldName = this.displayedColumns.map(v => v.fieldName);
  dataSourceFilters = new MatTableDataSource<RepairDS>();
  defaultValue = "All";
  filterDictionary = new Map<string, string>();

  filters: Filter[] = [];

  repairs: Repair[] = []
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

  showDetails(id: number) {
    const repair = this.repairs.find(value => value.id === id);
  }

  deleteRepair(id: number) {
    this.taskApi.deleteRepair(id).subscribe(() => {
      this.getRepairList();
    })
  }

  addRepairDialog(): void {
    const dialogRef = this.dialog.open(DialogAddRepairComponent, {
      width: '300px',
    })
      .afterClosed()
      .subscribe(() => this.getRepairList());
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
      const repairDS = this.convertToDataSource(repairs);
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
        startDate: repair.startDate,
        endDate: repair.endDate,
        vehicle: this.getVehicleName(repair),
        workshop: this.getWorkshopName(repair),
        taskStatus: repair.taskStatus,
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
    const vehiclesNames: string[] = [this.defaultValue];
    const status: string[] = [this.defaultValue];

    this.repairs.forEach(repair => {
      vehiclesNames.push(this.getVehicleName(repair));
      status.push(repair.taskStatus)
    });
    const reduceVehiclesNames = Array.from(new Set(vehiclesNames));
    const statusVehiclesNames = Array.from(new Set(status));

    this.filters.push({label: "VEHICLE", fieldName: 'vehicle', options: reduceVehiclesNames, defaultValue: this.defaultValue})
    this.filters.push({label: "STATUS", fieldName: 'taskStatus', options: statusVehiclesNames, defaultValue: this.defaultValue})
  }

  editRepairDialog(repair: Repair): void {
    const dialogRef = this.dialog.open(DialogEditRepairComponent, {
      width: '300px',
      data: {
        repair: repair
      }
    })
      .afterClosed()
      .subscribe(() => this.getRepairList());
  }

  rateRepairDialog(repair: Repair): void {
    const dialogRef = this.dialog.open(DialogRateRepairComponent, {
      width: '300px',
      data: {
        repair: repair
      }
    })
      .afterClosed()
      .subscribe(() => this.getRepairList());
  }
}
