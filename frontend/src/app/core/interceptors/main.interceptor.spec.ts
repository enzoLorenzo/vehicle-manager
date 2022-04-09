import {TestBed} from '@angular/core/testing';

import {MainInterceptor} from './main.interceptor';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {environment} from "../../../environments/environment";

describe('MainInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [
      MainInterceptor,
      {provide: "BASE_API_URL", useValue: environment.apiUrl}
    ]
  }));

  it('should be created', () => {
    const interceptor: MainInterceptor = TestBed.inject(MainInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
