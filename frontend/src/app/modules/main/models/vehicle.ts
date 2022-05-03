export enum VehicleType {
  CAR = "OSOBOWE",
  VAN = "DOSTAWCZE",
  MOTORCYCLE = "MOTOCYKL",
  TRUCK = "CIĘŻAROWY",
  CONSTRUCTION = "BUDOWLANE",
  FARM = "ROLNICZY",
}

export interface Vehicle{
  id: number;
  registration: string;
  brand: string;
  model: string;
  generation: string;
  year: string;
  engineCapacity: string;
  horsePower: string;
  type: VehicleType;
}
