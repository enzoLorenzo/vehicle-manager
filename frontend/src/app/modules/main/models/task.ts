import {Vehicle} from "./vehicle";
import {Workshop} from "./workshop";

export interface Repair extends Task {
}

export interface Task {
  id: number;
  description: string;
  startDate: Date;
  endDate: Date;
  taskStatus: TaskStatus;
  vehicle: Vehicle;
  workshop: Workshop;
}

export enum TaskStatus {
  PENDING = "PENDING",
  IN_PROGRESS = "IN_PROGRESS",
  ON_HOLD = "ON_HOLD",
  CANCELLED = "CANCELLED",
  FINISHED = "FINISHED"
}
