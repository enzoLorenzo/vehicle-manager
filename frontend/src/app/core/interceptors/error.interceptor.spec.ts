import {TestBed} from '@angular/core/testing';

import {ErrorInterceptor} from './error.interceptor';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {environment} from "../../../environments/environment";

describe('ErrorInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [
      ErrorInterceptor,
      {provide: "BASE_API_URL", useValue: environment.apiUrl}
    ]
  }));

  it('should be created', () => {
    const interceptor: ErrorInterceptor = TestBed.inject(ErrorInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
