import {Inject, Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "../services/auth/auth.service";
import {tap} from "rxjs/operators";

@Injectable()
export class MainInterceptor implements HttpInterceptor {

  constructor(public authService: AuthService,
              @Inject('BASE_API_URL') private baseUrl: string) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.authService.getAccessToken();
    if (token) {
      request = this.authService.addToken(request, token);
    }
    const apiReq = request.clone({url: `${this.baseUrl}${request.url}`});
    console.log(apiReq)
    return next.handle(apiReq).pipe(
      tap(response => console.log(response)),
    );
  }
}
