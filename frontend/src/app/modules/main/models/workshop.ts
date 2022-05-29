export interface Workshop {
  name: string;
  address: string;
  description: string;
  id: number;
  priceList: PriceListPosition[]

  // providedServices: ProvidedServices TODO
}

export interface WorkshopPost {
  name: string;
  address: string;
  description: string;


  // providedServices: ProvidedServices TODO
}

export interface PriceListPosition {
  name: string;
  description: string;
  price: number;
}
